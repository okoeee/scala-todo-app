package infrastructure.repositoryimpl

import domain.model.todo.Todo
import domain.repository.TodoRepository
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile

import javax.inject.Inject
import scala.concurrent.Future

class TodoRepositoryImpl @Inject()(
  protected val dbConfigProvider: DatabaseConfigProvider)(
  )
  extends HasDatabaseConfigProvider[JdbcProfile]
  with TodoRepository {
  import profile.api._

  private val Todos = TableQuery[TodosTable]

  override def all: Future[Seq[Todo]] = db.run(Todos.result)

  // todo 後で移動
  private class TodosTable(tag: Tag) extends Table[Todo](tag, "todo") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def name = column[String]("name")
    def * = (id, name) <> (Todo.tupled, Todo.unapply _)
  }

}
