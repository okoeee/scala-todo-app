package infrastructure.slick

import domain.model.todo.TodoStatus
import domain.typeinterface.EnumStatus
import slick.ast.BaseTypedType
import slick.jdbc.JdbcType
import slick.jdbc.MySQLProfile.api._

import java.time.LocalDateTime

trait SlickColumnType {

  implicit def todoStatusMapper[T <: EnumStatus]: BaseColumnType[TodoStatus] =
    MappedColumnType.base[TodoStatus, Short](
      es => es.code,
      code =>
        TodoStatus.values
          .find(_.code == code)
          .getOrElse(throw new IllegalArgumentException("Unknown code"))
    )

  // todo IDE上でエラーが出るので明示的に渡す。本当はimplicitで渡したい
  val localDateTimeMapping: JdbcType[LocalDateTime] with BaseTypedType[LocalDateTime] =
    MappedColumnType.base[LocalDateTime, java.sql.Timestamp](
      dateTime => java.sql.Timestamp.valueOf(dateTime),
      timestamp => timestamp.toLocalDateTime
    )

}
