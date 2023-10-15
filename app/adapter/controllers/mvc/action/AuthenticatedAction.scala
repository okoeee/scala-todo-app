package adapter.controllers.mvc.action

import cats.data.OptionT
import domain.model.user.User
import domain.model.usersession.{Token, UserSession}
import domain.repository.{UserRepository, UserSessionRepository}
import play.api.i18n.MessagesApi
import play.api.mvc.Results.Unauthorized
import play.api.mvc.{
  ActionBuilder,
  AnyContent,
  BodyParser,
  PlayBodyParsers,
  Request,
  Result,
  WrappedRequest
}

import java.time.LocalDateTime
import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class UserRequest[A](val user: User, request: Request[A])
  extends WrappedRequest[A](request)

class AuthenticatedAction @Inject() (
  playBodyParsers:       PlayBodyParsers,
  userSessionRepository: UserSessionRepository,
  userRepository:        UserRepository
)(implicit val executionContext: ExecutionContext)
  extends ActionBuilder[UserRequest, AnyContent] {
  override def parser: BodyParser[AnyContent] = playBodyParsers.anyContent

  override def invokeBlock[A](
    request: Request[A],
    block: (UserRequest[A] => Future[Result])
  ): Future[Result] = {
    request.session.get("token") match {
      // todo 以下のコード、わかりやすく書く
      case Some(token) =>
        val data = for {
          userSession <- OptionT(
                           userSessionRepository.findByToken(Token(token))
                         )
          user <- OptionT(userRepository.findByUserId(userSession.userId))
        } yield (userSession, user)

        data
          .semiflatMap {
            case (userSession: UserSession, user: User) =>
              if (userSession.expiryDate.isAfter(LocalDateTime.now))
                block(new UserRequest[A](user, request))
              else Future.successful(Unauthorized("expired_access_token"))
            case _                                      =>
              Future.successful(
                Unauthorized("user_information_or_session_not_found")
              )
          }
          .getOrElse(Unauthorized)
      case None        =>
        // todo ひとまずUnauthorizedに
        Future(Unauthorized("token_not_found_in_session"))
    }
  }
}
