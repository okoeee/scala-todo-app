package infrastructure.repositoryimpl

import domain.model.user.{Password, User}
import domain.repository.UserRepository
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile

import javax.inject.Inject
import scala.concurrent.Future

class UserRepositoryImpl @Inject() (
  protected val dbConfigProvider: DatabaseConfigProvider
) extends HasDatabaseConfigProvider[JdbcProfile]
  with UserRepository {
  import profile.api._

  private val Users = TableQuery[UserTable]

  override def findByEmailAndPassword(
    email: String,
    password: Password
  ): Future[Option[User]] =
    db.run(
      Users
        .filter(_.email === email)
        .filter(_.password === password)
        .result
        .headOption
    )

  private class UserTable(tag: Tag) extends Table[User](tag, "user") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def name = column[String]("name")
    def email = column[String]("email")
    def password = column[Password]("password")
    def * = (id, name, email, password) <> (User.tupled, User.unapply)
  }
}
