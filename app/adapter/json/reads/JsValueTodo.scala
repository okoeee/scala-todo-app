package adapter.json.reads

import domain.model.todo.{Todo, TodoStatus}
import play.api.libs.json.{Json, Reads}

case class JsValueTodo(
  categoryId: Long,
  title:      String,
  body:       String,
  state:      Short
)
object JsValueTodo {
  implicit val reads: Reads[JsValueTodo] = Json.reads[JsValueTodo]

  def toTodo(groupId: Long, jsValueTodo: JsValueTodo): Todo =
    Todo(
      id = 0,
      groupId = groupId,
      categoryId = jsValueTodo.categoryId,
      title = jsValueTodo.title,
      body = jsValueTodo.body,
      state = TodoStatus.fromShort(jsValueTodo.state)
    )

}
