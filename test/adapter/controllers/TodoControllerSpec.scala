package adapter.controllers

import org.scalatestplus.play._
import org.scalatestplus.play.guice._
import play.api.libs.json.Json
import play.api.test._
import play.api.test.Helpers._

class TodoControllerSpec
  extends PlaySpec
  with GuiceOneAppPerTest
  with Injecting {

  "GET /todo" must {
    "without session" in {
      val request = FakeRequest(GET, "/todo")
      val response = route(app, request).get

      status(response) mustBe UNAUTHORIZED
      contentAsString(response) mustBe "token_not_found_in_session"
    }
  }

  "POST /todo" must {
    "without session" in {
      val request = FakeRequest(POST, "/todo")
        .withJsonBody(
          Json.obj(
            "categoryId" -> "0"
          )
        )
      val response = route(app, request).get

      status(response) mustBe UNAUTHORIZED
      contentAsString(response) mustBe "token_not_found_in_session"
    }
  }

}
