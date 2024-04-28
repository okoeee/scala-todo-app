package adapter.controllers

import adapter.controllers.helpers.FormHelper
import adapter.controllers.mvc.ImplicitConverter
import adapter.controllers.mvc.action.AuthenticatedAction
import adapter.json.reads.JsValueLogin
import adapter.json.writes.JsValueUserState
import cats.data.EitherT
import domain.model.user.Password
import domain.repository.{
  GroupMembershipRepository,
  GroupRepository,
  UserRepository
}
import domain.service.UserSessionCommandService
import play.api.libs.json.{JsValue, Json}
import play.api.mvc.{
  Action,
  AnyContent,
  BaseController,
  ControllerComponents,
  Cookie
}
import play.filters.csrf.CSRF

import javax.inject.{Inject, Singleton}
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

@Singleton
class AuthController @Inject() (
  authenticatedAction:       AuthenticatedAction,
  userSessionCommandService: UserSessionCommandService,
  userRepository:            UserRepository,
  groupRepository:           GroupRepository,
  groupMembershipRepository: GroupMembershipRepository,
  val controllerComponents:  ControllerComponents
) extends BaseController
  with ImplicitConverter {

  def verify(): Action[AnyContent] = authenticatedAction.async { implicit req =>
    CSRF.getToken(req) match {
      case Some(token) =>
        Future(
          Ok(Json.toJson(JsValueUserState(isLoggedIn = true, token.value)))
        )
      case None        => Future(Ok(Json.obj("message" -> "CSRF Token none")))
    }
  }

  def login(): Action[JsValue] = Action.async(parse.json) { implicit req =>
    val param = for {
      jsValueLogin <- FormHelper.fromRequest[JsValueLogin]
    } yield (jsValueLogin.email, jsValueLogin.password)

    EitherT.fromEither[Future](param) semiflatMap { case (email, password) =>
      for {
        token <- userSessionCommandService.login(email, password)
        Some(user) <-
          userRepository.findByEmailAndPassword(email, Password(password))
        Some(groupMembership) <- groupMembershipRepository.findByUserId(user.id)
        Some(group) <- groupRepository.findById(groupMembership.groupId)
      } yield {
        val cookie = Cookie("groupId", group.id.toString)
        Ok(Json.obj("message" -> "success"))
          .withSession("token" -> token.value)
          .withCookies(cookie)
      }
    }
  }

  def logout(): Action[AnyContent] = authenticatedAction.async { implicit req =>
    Future.successful(Ok.withNewSession)
  }

}
