package adapter.controllers.mvc.action

import cats.data.OptionT

import domain.model.group.Group
import domain.model.groupmembership.GroupMembership
import domain.model.user.User
import domain.model.usersession.{Token, UserSession}
import domain.repository.{GroupMembershipRepository, UserRepository, UserSessionRepository}
import play.api.mvc.Results.Unauthorized
import play.api.mvc.{ActionBuilder, AnyContent, BodyParser, PlayBodyParsers, Request, Result, WrappedRequest}

import java.time.LocalDateTime
import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class UserRequest[A](
  val groupId: Long,
  val user:    User,
  request:     Request[A]
) extends WrappedRequest[A](request)

class AuthenticatedAction @Inject() (
  playBodyParsers:           PlayBodyParsers,
  userSessionRepository:     UserSessionRepository,
  userRepository:            UserRepository,
  groupMembershipRepository: GroupMembershipRepository
)(implicit val executionContext: ExecutionContext)
  extends ActionBuilder[UserRequest, AnyContent] {
  override def parser: BodyParser[AnyContent] = playBodyParsers.anyContent

  override def invokeBlock[A](
    request: Request[A],
    block: UserRequest[A] => Future[Result]
  ): Future[Result] = {
    val groupIdOpt = request.cookies.get("groupId")
    val tokenOpt = request.session.get("token")

    (groupIdOpt, tokenOpt) match {
      case (Some(groupId), Some(token)) =>
        val data = (for {
          userSession <-
            OptionT(
              userSessionRepository.findByToken(Token(token))
            )
          user <-
            OptionT(userRepository.findByUserId(userSession.userId))
          groupMembershipSeq <-
            OptionT.liftF(
              groupMembershipRepository.filterByGroupId(groupId.value.toLong)
            )
        } yield (userSession, user, groupMembershipSeq, groupId.value.toLong)).value

        data
          .flatMap {
            case Some((userSession: UserSession, user: User, groupMembershipSeq: Seq[GroupMembership], groupId: Long)) =>
              if (
                userSession.expiryDate.isAfter(
                  LocalDateTime.now
                ) && groupMembershipSeq.exists(_.userId == user.id)
              )
                block(new UserRequest[A](groupId, user, request))
              else Future.successful(Unauthorized("session_is_expired"))
            case _                                                                                                     =>
              Future.successful(Unauthorized("user_not_found"))
          }
      case _                            =>
        Future(Unauthorized("token_not_found_in_session"))
    }
  }
}
