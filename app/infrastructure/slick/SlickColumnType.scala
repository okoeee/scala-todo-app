package infrastructure.slick

import domain.model.todo.TodoStatus
import domain.typeinterface.EnumStatus
import slick.jdbc.MySQLProfile.api._

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
