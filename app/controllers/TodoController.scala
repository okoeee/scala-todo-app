package controllers

import adapter.json._
import adapter.json.reads.JsValueTodo.toTodo
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

  def index(): Action[AnyContent] = Action.async { implicit req =>
    for {
      todoList <- todoRepository.all
      jsValueTodoList = todoList.map(todo => writes.JsValueTodo(todo))
    } yield {
      Ok(Json.toJson(jsValueTodoList))
    }
  }

  def post: Action[JsValue] = Action.async(parse.json) { implicit req =>
    val jsValueTodo = for {
      jsValueTodo <- FormHelper.fromRequest[reads.JsValueTodo]
    } yield jsValueTodo
    jsValueTodo match {
      case Right(jsValueTodo)   =>
        todoRepository
          .insert(toTodo(jsValueTodo))
          .map(_ => {
            Ok(Json.obj("message" -> "sucess"))
          })
      case Left(result: Result) => Future.successful(result)
    }
  }

}
