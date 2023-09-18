package controllers

import adapter.json._
import adapter.json.reads.JsValueTodo.toTodo
import cats.data.EitherT
import controllers.helpers.FormHelper
import domain.repository.TodoRepository
import play.api.libs.json.{JsError, JsValue, Json}

import javax.inject._
import play.api.mvc._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

@Singleton
class TodoController @Inject() (
  todoRepository:           TodoRepository,
  val controllerComponents: ControllerComponents
) extends BaseController {

  implicit def convertEitherToResult(f: Either[Result, Result]): Result =
    f match {
      case Right(r) => r
      case Left(l)  => l
    }

  implicit def convertEitherTtoResult(
    f: EitherT[Future, Result, Result]
  ): Future[Result] =
    f.valueOr(v => v)

  def index(): Action[AnyContent] = Action.async { implicit req =>
    for {
      todoList <- todoRepository.all
      jsValueTodoList = todoList.map(todo => writes.JsValueTodo(todo))
    } yield {
      Ok(Json.toJson(jsValueTodoList))
    }
  }

  def post: Action[JsValue] = Action.async(parse.json) { implicit req =>
    val param = for {
      jsValueTodo <- FormHelper.fromRequest[reads.JsValueTodo]
    } yield jsValueTodo

    EitherT.fromEither[Future](param) semiflatMap { jsValueTodo =>
      for {
        _ <- todoRepository.insert(toTodo(jsValueTodo))
      } yield NoContent
    }
  }

}
