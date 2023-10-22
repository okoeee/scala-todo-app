package infrastructure.repositoryimpl

import domain.model.user.{Password, User}
import domain.repository.UserRepository
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import com.github.t3hnar.bcrypt._

import javax.inject.Inject
import scala.concurrent.Future
import scala.util.Success

import scala.concurrent.ExecutionContext.Implicits.global

class UserRepositoryImpl @Inject() (
  protected val dbConfigProvider: DatabaseConfigProvider
) extends HasDatabaseConfigProvider[JdbcProfile]
  with UserRepository {
  import profile.api._

  private val Users = TableQuery[UserTable]

  override def findByEmailAndPassword(
    email: String,
    password: Password
  ): Future[Option[User]] = {
    db.run(
      Users
        .filter(_.email === email)
        .result
        .headOption
    ).map { userOpt =>
      userOpt.map(user => {
        val passwordFromDB = user.password
        password.value.isBcryptedSafeBounded(passwordFromDB.value) match {
          case Success(isExistUser) if isExistUser => user
          case _                                   => throw new NoSuchElementException()
        }
      })
    }
  }

  override def findByUserId(userId: Long): Future[Option[User]] =
    db.run(
      Users
        .filter(_.id === userId)
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
