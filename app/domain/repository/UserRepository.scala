package domain.repository

import domain.model.user.{Password, User}

import scala.concurrent.Future

trait UserRepository {
  def findByEmailAndPassword(
    email: String,
    password: Password
  ): Future[Option[User]]
}
