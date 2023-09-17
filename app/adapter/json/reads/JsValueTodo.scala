package adapter.json.reads

import play.api.libs.json.{Json, Reads}

case class JsValueTodo(
  categoryId: Long,
  title:      String,
  body:       String,
  state:      Short
)
object JsValueTodo {
  implicit val reads: Reads[JsValueTodo] = Json.reads[JsValueTodo]
}
