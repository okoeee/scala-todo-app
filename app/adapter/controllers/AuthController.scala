package adapter.controllers

import adapter.controllers.helpers.FormHelper
import adapter.controllers.mvc.ImplicitConverter
import adapter.json.reads.JsValueLogin
import cats.data.EitherT
import domain.service.UserSessionCommandService
import play.api.libs.json.Json
import play.api.mvc.{BaseController, ControllerComponents}

import javax.inject.{Inject, Singleton}
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

@Singleton
class AuthController @Inject() (
  userSessionCommandService: UserSessionCommandService,
  val controllerComponents:  ControllerComponents
) extends BaseController
  with ImplicitConverter {

  def login() = Action.async(parse.json) { implicit req =>
    // reqのbodyを調べて、emailとpasswordを取得
    val param = for {
      jsValueLogin <- FormHelper.fromRequest[JsValueLogin]
    } yield (jsValueLogin.email, jsValueLogin.password)

    EitherT.fromEither[Future](param) semiflatMap { case (email, password) =>
      (for {
        token <- userSessionCommandService.login(email, password)
      } yield Ok(Json.obj("message" -> "success"))
        .withSession("sid" -> token.toString)) recover {
        case _: NoSuchElementException =>
          Ok(Json.obj("message" -> "no user exists"))
      }
    }
  }

}
