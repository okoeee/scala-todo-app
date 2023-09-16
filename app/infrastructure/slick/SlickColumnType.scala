package infrastructure.slick

import domain.model.todo.TodoStatus
import slick.jdbc.MySQLProfile.api._

import scala.reflect.ClassTag

// todo 後で移動
trait EnumStatus {
  val code: Short
  val values: Set[_ <: EnumStatus]
}

trait SlickColumnType {

  implicit def todoStatusMapper[T <: EnumStatus]: BaseColumnType[TodoStatus] =
    MappedColumnType.base[TodoStatus, Short](
      es => es.code,
      code =>
        TodoStatus.values
          .find(_.code == code)
          .getOrElse(throw new IllegalArgumentException("Unknown code"))
    )

}
