package adapter.controllers.mvc.action

import domain.model.usersession.Token
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
    request.session.get("user_session") match {
      case Some(token) =>
        // tokenを検索
        userSessionRepository.findByToken(Token(token)) flatMap {
          case Some(_) =>
            block(new MessagesRequest[A](request, messagesApi))
          case None    =>
            // todo ひとまずUnauthorizedに
            Future(Unauthorized)
        }
      case None        =>
        // todo ひとまずUnauthorizedに
        Future(Unauthorized)
    }
  }
}
