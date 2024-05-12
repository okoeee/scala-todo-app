package domain.repository

import domain.model.groupmembership.GroupMembership

import scala.concurrent.Future

trait GroupMembershipRepository {
  def findByGroupId(groupId: Long): Future[Option[GroupMembership]]
  def findByUserId(id: Long): Future[Option[GroupMembership]]
  def filterByGroupId(groupId: Long): Future[Seq[GroupMembership]]
}
