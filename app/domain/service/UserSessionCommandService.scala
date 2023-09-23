package domain.service

import domain.model.user.Password
import domain.model.usersession.UserSession
import domain.repository.{UserRepository, UserSessionRepository}

import java.time.LocalDateTime
import java.util.UUID
import javax.inject.Inject
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class UserSessionCommandService @Inject() (
  userRepository:        UserRepository,
  userSessionRepository: UserSessionRepository
) {

  def login(email: String, password: String): Future[String] = {
    for {
      // todo passwordのハッシュ化を行う
      Some(user) <-
        userRepository.findByEmailAndPassword(email, Password(password))
      // todo ドメインオブジェクトに移動
      // tokenの発行
      // userが存在する場合にtokenを発行して保存
      token = s"${user.id}-${UUID.randomUUID().toString}"
      expiryDate = LocalDateTime.now.plusDays(30)
      _ <- userSessionRepository.insert(
             UserSession.newUserSession(user.id, token, expiryDate)
           )
    } yield token
  }

}
