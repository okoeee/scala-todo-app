package controllers

import adapter.json.JsValueTodo
import domain.repository.TodoRepository
import play.api.libs.json.Json

import javax.inject._
import play.api.mvc._

import scala.concurrent.ExecutionContext.Implicits.global

@Singleton
class TodoController @Inject() (
  todoRepository:           TodoRepository,
  val controllerComponents: ControllerComponents
) extends BaseController {

  def index(): Action[AnyContent] = Action.async { implicit req =>
    for {
      todoList <- todoRepository.all
      jsValueTodoList = todoList.map(todo => JsValueTodo(todo))
    } yield {
      Ok(Json.toJson(jsValueTodoList))
    }
  }

}
