package adapter.controllers.mvc

import cats.data.EitherT
import play.api.mvc.Result

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.language.implicitConversions

trait ImplicitConverter {

  implicit def convertEitherTtoResult(
    f: EitherT[Future, Result, Result]
  ): Future[Result] =
    f.fold(
      result => result,
      result => result
    )

}
