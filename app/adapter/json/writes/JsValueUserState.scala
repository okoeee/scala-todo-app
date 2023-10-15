package adapter.json.writes

import play.api.libs.json.{Json, OWrites}

case class JsValueUserState(
  csrfToken: String
)
object JsValueUserState {
  implicit val writes: OWrites[JsValueUserState] = Json.writes[JsValueUserState]
}
