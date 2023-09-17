package controllers

import adapter.json._
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
    req.body
      .validate[reads.JsValueTodo]
      .fold(
        errors => {
          val errorMsg = JsError.toJson(errors).toString
          Future.successful(BadRequest(Json.obj("message" -> errorMsg)))
        },
        todoData => {
          println(todoData)
          Future.successful(Ok(Json.obj("message" -> "success")))
        }
      )
  }

}
