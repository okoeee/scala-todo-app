package persistence.repository

import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

// todo 後で移動
case class Todo(
  // todo ひとまずLong型に
  id: Long,
  name: String
)
class TodoRepository @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)(implicit executionContext: ExecutionContext) extends HasDatabaseConfigProvider[JdbcProfile] {
  import profile.api._

  private val Todos = TableQuery[TodosTable]

  def all: Future[Seq[Todo]] = db.run(Todos.result)

  // todo 後で移動
  private class TodosTable(tag: Tag) extends Table[Todo](tag, "todo") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def name = column[String]("name")
    def * = (id, name) <> (Todo.tupled, Todo.unapply _)
  }

}
