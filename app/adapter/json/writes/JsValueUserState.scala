package adapter.json.writes

import play.api.libs.json.{Json, OWrites}

case class JsValueUserState(
  token: String
)
object JsValueUserState {
  implicit val writes: OWrites[JsValueUserState] = Json.writes[JsValueUserState]
}
