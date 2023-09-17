package domain.model.todo

import domain.typeinterface.EnumStatus

case class Todo(
  // todo ひとまずLong型に
  id:         Long,
  categoryId: Long,
  title:      String,
  body:       String,
  state:      TodoStatus
)

sealed abstract class TodoStatus(
  val code: Short,
  val name: String
) extends EnumStatus {
  override val values: Set[TodoStatus] = TodoStatus.values
}
object TodoStatus {

  case object NOT_STARTED extends TodoStatus(code = 0, name = "未着手")
  case object IN_PROGRESS extends TodoStatus(code = 1, name = "進行中")
  case object COMPLETED extends TodoStatus(code = 2, name = "完了")

  val values: Set[TodoStatus] = Set(NOT_STARTED, IN_PROGRESS, COMPLETED)
}
