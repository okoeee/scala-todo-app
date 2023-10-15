package adapter.controllers

import adapter.controllers.helpers.FormHelper
import adapter.controllers.mvc.ImplicitConverter
import adapter.controllers.mvc.action.AuthenticatedAction
import adapter.json._
import adapter.json.reads.JsValueTodo.toTodo
import cats.data.EitherT
import domain.repository.TodoRepository
import play.api.libs.json.{JsValue, Json}
import play.api.mvc._

import javax.inject._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

@Singleton
class TodoController @Inject() (
  authenticatedAction:      AuthenticatedAction,
  todoRepository:           TodoRepository,
  val controllerComponents: ControllerComponents
) extends BaseController
  with ImplicitConverter {

  def index(): Action[AnyContent] = Action.async { implicit req =>
    for {
      // todo userIdでfilterをかける
      todoList <- todoRepository.all
      jsValueTodoList = todoList.map(todo => writes.JsValueTodo(todo))
    } yield {
      Ok(Json.toJson(jsValueTodoList))
    }
  }

  def post: Action[JsValue] = authenticatedAction.async(parse.json) {
    implicit req =>
      val param = for {
        jsValueTodo <- FormHelper.fromRequest[reads.JsValueTodo]
      } yield jsValueTodo

      EitherT.fromEither[Future](param) semiflatMap { jsValueTodo =>
        for {
          _ <- todoRepository.insert(toTodo(req.user.id: Long, jsValueTodo))
        } yield NoContent
      }
  }

}
