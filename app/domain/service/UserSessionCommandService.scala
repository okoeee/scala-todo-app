package domain.service

import cats.data.OptionT
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

  def login(email: String, password: String): Future[Option[Token]] = {
    (for {
      user <-
        OptionT(userRepository.findByEmailAndPassword(email, Password(password)))
      token = UserSession.newToken(user.id)
      _ <- OptionT.liftF(
             userSessionRepository.insert(
               UserSession.newUserSession(user.id, token)
             )
           )
    } yield token).value
  }

}
