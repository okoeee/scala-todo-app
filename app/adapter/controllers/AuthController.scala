package adapter.controllers

import adapter.controllers.helpers.FormHelper
import adapter.controllers.mvc.ImplicitConverter
import adapter.controllers.mvc.action.AuthenticatedAction
import adapter.json.reads.JsValueLogin
import adapter.json.writes.JsValueUserState
import cats.data.EitherT
import domain.service.UserSessionCommandService
import play.api.libs.json.{JsValue, Json}
import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents}
import play.filters.csrf.CSRF

import javax.inject.{Inject, Singleton}
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

@Singleton
class AuthController @Inject() (
  authenticatedAction:       AuthenticatedAction,
  userSessionCommandService: UserSessionCommandService,
  val controllerComponents:  ControllerComponents
) extends BaseController
  with ImplicitConverter {

  def verify(): Action[AnyContent] = authenticatedAction.async { implicit req =>
    CSRF.getToken(req) match {
      // todo csrfTokenのJsonを変更
      case Some(token) =>
        Future(Ok(Json.toJson(JsValueUserState(token.value))))
      case None        => Future(Ok(Json.obj("message" -> "CSRF Token none")))
    }
  }

  def login(): Action[JsValue] = Action.async(parse.json) { implicit req =>
    val param = for {
      jsValueLogin <- FormHelper.fromRequest[JsValueLogin]
    } yield (jsValueLogin.email, jsValueLogin.password)

    EitherT.fromEither[Future](param) semiflatMap { case (email, password) =>
      (for {
        token <- userSessionCommandService.login(email, password)
      } yield Ok(Json.obj("message" -> "success"))
        .withSession("token" -> token.value)) recover {
        case _: NoSuchElementException =>
          Ok(Json.obj("message" -> "no user exists"))
      }
    }
  }

  def logout(): Action[AnyContent] = authenticatedAction.async { implicit req =>
    Future.successful(Ok.withNewSession)
  }

}
