package controllers.helpers

import play.api.mvc.{Request, Result}
import play.api.libs.json.{JsError, JsValue, Reads}
import play.api.mvc.Results.BadRequest

object FormHelper {

  def fromRequest[T](implicit
    req: Request[JsValue],
    reads: Reads[T]
  ): Either[Result, T] = {
    req.body
      .validate[T]
      .fold(
        errors => Left(BadRequest(JsError.toJson(errors))),
        data => Right(data)
      )
  }

}
