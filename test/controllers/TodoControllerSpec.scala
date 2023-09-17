package controllers

import org.scalatestplus.play._
import org.scalatestplus.play.guice._
import play.api.test._
import play.api.test.Helpers._

class TodoControllerSpec
  extends PlaySpec
  with GuiceOneAppPerTest
  with Injecting {

  "TodoController GET" must {
    "return json from the application" in {
      val request = FakeRequest(GET, "/")
      val response = route(app, request).get

      status(response) mustBe OK
      contentType(response) mustBe Some("application/json")
    }
  }

}
