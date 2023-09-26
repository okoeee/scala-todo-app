package adapter.controllers.mvc.action

import domain.model.usersession.{Token, UserSession}
import domain.repository.UserSessionRepository
import play.api.i18n.MessagesApi
import play.api.mvc.Results.Unauthorized
import play.api.mvc.{
  ActionBuilder,
  AnyContent,
  BodyParser,
  MessagesRequest,
  PlayBodyParsers,
  Request,
  Result
}

import java.time.LocalDateTime
import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class AuthenticatedAction @Inject() (
  playBodyParsers:       PlayBodyParsers,
  messagesApi:           MessagesApi,
  userSessionRepository: UserSessionRepository
)(implicit val executionContext: ExecutionContext)
  extends ActionBuilder[MessagesRequest, AnyContent] {
  override def parser: BodyParser[AnyContent] = playBodyParsers.anyContent

  override def invokeBlock[A](
    request: Request[A],
    block: (MessagesRequest[A] => Future[Result])
  ): Future[Result] = {
    request.session.get("token") match {
      // todo 以下のコード、わかりやすく書く
      case Some(token) =>
        // tokenを検索
        userSessionRepository.findByToken(Token(token)) flatMap {
          case Some(userSession: UserSession) =>
            if (userSession.expiryDate.isBefore(LocalDateTime.now))
              block(new MessagesRequest[A](request, messagesApi))
            else Future(Unauthorized("expired_access_token"))
          case None                           =>
            // todo ひとまずUnauthorizedに
            Future(Unauthorized("token_not_found_in_db"))
        }
      case None        =>
        // todo ひとまずUnauthorizedに
        Future(Unauthorized("token_not_found_in_session"))
    }
  }
}
