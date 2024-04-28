package infrastructure.repositoryimpl

import com.google.inject.Inject
import domain.model.group.Group
import domain.repository.GroupRepository
import infrastructure.slick.SlickColumnType
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile

import scala.concurrent.Future

class GroupRepositoryImpl @Inject() (
  protected val dbConfigProvider: DatabaseConfigProvider
) extends HasDatabaseConfigProvider[JdbcProfile]
  with GroupRepository
  with SlickColumnType {
  import profile.api._

  private val Groups = TableQuery[GroupTable]

  override def findById(id: Long): Future[Option[Group]] =
    db.run(
      Groups
        .filter(_.id === id)
        .result
        .headOption
    )

  private class GroupTable(tag: Tag) extends Table[Group](tag, "group") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def name = column[String]("name")
    def * = (
      id,
      name
    ) <> (Group.tupled, Group.unapply)
  }
}
