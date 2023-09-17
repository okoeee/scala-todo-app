package adapter.json.writes

import domain.model.todo.{Todo, TodoStatus}
import play.api.libs.json.{Json, Writes}

case class JsValueTodo(
  // todo ひとまずLong型に
  id:         Long,
  categoryId: Long,
  title:      String,
  body:       String,
  state:      TodoStatus
)
object JsValueTodo {

  def apply(todo: Todo): JsValueTodo = {
    JsValueTodo(
      todo.id,
      todo.categoryId,
      todo.title,
      todo.body,
      todo.state
    )
  }

  implicit val writes: Writes[JsValueTodo] = new Writes[JsValueTodo] {
    def writes(jsValueTodo: JsValueTodo) = Json.obj(
      "id" -> jsValueTodo.id,
      "categoryId" -> jsValueTodo.categoryId,
      "title" -> jsValueTodo.title,
      "body" -> jsValueTodo.body,
      "state" -> jsValueTodo.state.code // todo 後ほど修正
    )
  }
}
