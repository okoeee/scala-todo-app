package domain.repository

import domain.model.group.Group

import scala.concurrent.Future

trait GroupRepository {
  def findById(id: Long): Future[Option[Group]]
}
