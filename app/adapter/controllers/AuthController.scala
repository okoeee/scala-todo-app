package adapter.controllers

import adapter.controllers.helpers.FormHelper
import adapter.controllers.mvc.ImplicitConverter
import adapter.json.reads.JsValueLogin
import play.api.libs.json.Json
import play.api.mvc.{BaseController, ControllerComponents}

import javax.inject.{Inject, Singleton}
import scala.concurrent.Future

@Singleton
class AuthController @Inject() (
  val controllerComponents: ControllerComponents
) extends BaseController
  with ImplicitConverter {

  def login() = Action.async(parse.json) { implicit req =>
    // reqのbodyを調べて、emailとpasswordを取得
    val param = for {
      jsValueLogin <- FormHelper.fromRequest[JsValueLogin]
    } yield jsValueLogin

    // UserRepositoryからemailとpasswordが一致するユーザーを検索
    Future.successful(Ok(Json.obj("message" -> "success")))
  }

}
