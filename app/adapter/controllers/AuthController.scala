package adapter.controllers

import adapter.controllers.helpers.FormHelper
import adapter.controllers.mvc.ImplicitConverter
import adapter.json.reads.JsValueLogin
import cats.data.EitherT
import domain.model.usersession.UserSession
import domain.repository.{UserRepository, UserSessionRepository}
import play.api.libs.json.Json
import play.api.mvc.{BaseController, ControllerComponents}

import java.time.LocalDateTime
import java.util.UUID
import javax.inject.{Inject, Singleton}
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

@Singleton
class AuthController @Inject() (
  userRepository:           UserRepository,
  userSessionRepository:    UserSessionRepository,
  val controllerComponents: ControllerComponents
) extends BaseController
  with ImplicitConverter {

  def login() = Action.async(parse.json) { implicit req =>
    // reqのbodyを調べて、emailとpasswordを取得
    val param = for {
      jsValueLogin <- FormHelper.fromRequest[JsValueLogin]
    } yield (jsValueLogin.email, jsValueLogin.password)

    EitherT.fromEither[Future](param) semiflatMap { case (email, password) =>
      (for {
        // todo passwordのハッシュ化を行う
        Some(user) <- userRepository.findByEmailAndPassword(email, password)
        // todo ドメインオブジェクトに移動
        // tokenの発行
        // userが存在する場合にtokenを発行して保存
        token = s"$user.id-${UUID.randomUUID().toString}"
        expiryDate = LocalDateTime.now.plusDays(30)
        _ <- userSessionRepository.insert(
               UserSession.newUserSession(user.id, token, expiryDate)
             )
      } yield Ok(Json.obj("message" -> "success"))
        .withSession("sid" -> token)) recover {
        case _: NoSuchElementException =>
          Ok(Json.obj("message" -> "no user exists"))
      }
    }
  }

}
