package infrastructure.repositoryimpl

import domain.model.usersession.UserSession
import domain.repository.UserSessionRepository
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile

import java.time.LocalDateTime
import javax.inject.Inject
import scala.concurrent.Future

class UserSessionRepositoryImpl @Inject()(
  protected val dbConfigProvider: DatabaseConfigProvider
) extends HasDatabaseConfigProvider[JdbcProfile]
  with UserSessionRepository {
  import profile.api._

  private val Sessions = TableQuery[SessionTable]

  override def insert(session: UserSession): Future[Int] =
    db.run(Sessions += session)

  private class SessionTable(tag: Tag) extends Table[UserSession](tag, "session") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def userId = column[Long]("user_id")
    def token = column[String]("token")
    def expiryDate = column[LocalDateTime]("expiry_date")
    def * = (id, userId, token, expiryDate) <> (UserSession.tupled, UserSession.unapply)
  }
}
