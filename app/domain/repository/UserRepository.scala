package domain.repository

import domain.model.user.User

import scala.concurrent.Future

trait UserRepository {
  def findByEmailAndPassword(
    email: String,
    password: String
  ): Future[Option[User]]
}
