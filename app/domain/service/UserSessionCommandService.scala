package domain.service

import domain.model.user.Password
import domain.model.usersession.{Token, UserSession}
import domain.repository.{UserRepository, UserSessionRepository}

import javax.inject.Inject
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class UserSessionCommandService @Inject() (
  userRepository:        UserRepository,
  userSessionRepository: UserSessionRepository
) {

  def login(email: String, password: String): Future[Token] = {
    for {
      Some(user) <-
        userRepository.findByEmailAndPassword(email, Password(password))
      token = UserSession.newToken(user.id)
      _ <- userSessionRepository.insert(
             UserSession.newUserSession(user.id, token)
           )
    } yield token
  }

}
