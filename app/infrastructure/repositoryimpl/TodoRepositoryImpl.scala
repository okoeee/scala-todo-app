package infrastructure.repositoryimpl

import domain.model.todo.{Todo, TodoStatus}
import domain.repository.TodoRepository
import infrastructure.slick.SlickColumnType
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile

import javax.inject.Inject
import scala.concurrent.Future

class TodoRepositoryImpl @Inject() (
  protected val dbConfigProvider: DatabaseConfigProvider
)(
) extends HasDatabaseConfigProvider[JdbcProfile]
  with TodoRepository
  with SlickColumnType {
  import profile.api._

  private val Todos = TableQuery[TodosTable]

  override def all: Future[Seq[Todo]] = db.run(Todos.result)

  override def findByUserId(groupId: Long): Future[Seq[Todo]] =
    db.run(
      Todos
        .filter(_.groupId === groupId)
        .result
    )

  override def insert(todo: Todo): Future[Int] = db.run(Todos += todo)

  // todo 後で移動
  private class TodosTable(tag: Tag) extends Table[Todo](tag, "todo") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def groupId = column[Long]("group_id")
    def categoryId = column[Long]("category_id")
    def createdUserId = column[Long]("created_user_id")
    def title = column[String]("title")
    def body = column[String]("body")
    def state = column[TodoStatus]("state")
    def * = (
      id,
      groupId,
      categoryId,
      createdUserId,
      title,
      body,
      state
    ) <> (Todo.tupled, Todo.unapply)
  }

}
