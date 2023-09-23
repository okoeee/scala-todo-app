package domain.repository

import domain.model.usersession.UserSession

import scala.concurrent.Future

trait UserSessionRepository {
  def insert(session: UserSession): Future[Int]
}
