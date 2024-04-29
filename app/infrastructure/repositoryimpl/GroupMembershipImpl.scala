package infrastructure.repositoryimpl

import domain.model.groupmembership.GroupMembership
import domain.repository.GroupMembershipRepository
import infrastructure.slick.SlickColumnType
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile

import javax.inject.Inject
import scala.concurrent.Future

class GroupMembershipImpl @Inject() (
  protected val dbConfigProvider: DatabaseConfigProvider
) extends HasDatabaseConfigProvider[JdbcProfile]
  with GroupMembershipRepository
  with SlickColumnType {
  import profile.api._

  private val GroupMemberships = TableQuery[GroupMembershipTable]

  override def findByGroupId(groupId: Long): Future[Option[GroupMembership]] =
    db.run(
      GroupMemberships
        .filter(_.groupId === groupId)
        .result
        .headOption
    )

  override def findByUserId(
    userId: Long
  ): Future[Option[GroupMembership]] =
    db.run(
      GroupMemberships
        .filter(_.userId === userId)
        .result
        .headOption
    )

  private class GroupMembershipTable(tag: Tag)
    extends Table[GroupMembership](tag, "group_membership") {
    def groupId = column[Long]("group_id", O.PrimaryKey, O.AutoInc)
    def userId = column[Long]("user_id")
    def * = (
      groupId,
      userId
    ) <> (GroupMembership.tupled, GroupMembership.unapply)
  }
}
