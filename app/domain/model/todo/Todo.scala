package domain.model.todo

import slick.ast.BaseTypedType
import slick.jdbc.JdbcType
import slick.jdbc.MySQLProfile.api._

case class Todo(
  // todo ひとまずLong型に
  id: Long,
  categoryId: Long,
  title: String,
  body: String,
  state: TodoStatus
)

sealed abstract class TodoStatus(
  val code: Short,
  val name: String
)
object TodoStatus {

  implicit val todoStatusColumnType: JdbcType[TodoStatus] with BaseTypedType[TodoStatus] = MappedColumnType.base[TodoStatus, Short](
    ts => ts.code,
    s => TodoStatus.values.find(_.code == s).getOrElse(throw new IllegalArgumentException(s"Unknown code: $s"))
  )

  case object NOT_STARTED extends TodoStatus(code = 0, name = "未着手")
  case object IN_PROGRESS extends TodoStatus(code = 1, name = "進行中")
  case object COMPLETED extends TodoStatus(code = 2, name = "完了")

  val values: Set[TodoStatus] = Set(NOT_STARTED, IN_PROGRESS, COMPLETED)
}
