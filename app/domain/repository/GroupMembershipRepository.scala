package domain.repository

import domain.model.groupmembership.GroupMembership

import scala.concurrent.Future

trait GroupMembershipRepository {
  def findByUserId(id: Long): Future[Option[GroupMembership]]
}
