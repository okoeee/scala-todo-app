package domain.model.todo

case class Todo(
  // todo ひとまずLong型に
  id: Long,
  categoryId: Long,
  title: String,
  body: String,
  state: Int
)

sealed abstract class TodoStatus(
  val code: Short,
  val name: String
)
object TodoStatus {
  case object NOT_STARTED extends TodoStatus(code = 0, name = "未着手")
  case object IN_PROGRESS extends TodoStatus(code = 1, name = "進行中")
  case object COMPLETED extends TodoStatus(code = 2, name = "完了")
}
