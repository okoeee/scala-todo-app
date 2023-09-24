package domain.repository

import domain.model.usersession.{Token, UserSession}

import scala.concurrent.Future

trait UserSessionRepository {
  def findByToken(token: Token): Future[Option[UserSession]]
  def insert(session: UserSession): Future[Int]
}
