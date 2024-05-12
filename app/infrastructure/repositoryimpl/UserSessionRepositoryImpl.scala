package infrastructure.repositoryimpl

import domain.model.usersession.{Token, UserSession}
import domain.repository.UserSessionRepository
import infrastructure.slick.SlickColumnType
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile

import java.time.LocalDateTime
import javax.inject.Inject
import scala.concurrent.Future

class UserSessionRepositoryImpl @Inject() (
  protected val dbConfigProvider: DatabaseConfigProvider
) extends HasDatabaseConfigProvider[JdbcProfile]
  with UserSessionRepository
  with SlickColumnType {
  import profile.api._

  private val Sessions = TableQuery[SessionTable]

  override def findByToken(token: Token): Future[Option[UserSession]] =
    db.run(Sessions.filter(_.token === token).result.headOption)
  override def insert(session: UserSession): Future[Int] =
    db.run(Sessions += session)

  private class SessionTable(tag: Tag) extends Table[UserSession](tag, "user_session") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def userId = column[Long]("user_id")
    def token = column[Token]("token")
    def expiryDate =
      column[LocalDateTime]("expiry_date")(localDateTimeMapping)

    def * = (
      id,
      userId,
      token,
      expiryDate
    ) <> ((UserSession.apply _).tupled, UserSession.unapply)
  }
}
