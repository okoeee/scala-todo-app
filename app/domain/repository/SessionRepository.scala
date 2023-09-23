package domain.repository

import domain.model.session.Session

import scala.concurrent.Future

trait SessionRepository {
  def insert(session: Session): Future[Int]
}
