package controllers

import domain.repository.TodoRepository

import javax.inject._
import play.api.mvc._

import scala.concurrent.ExecutionContext.Implicits.global

@Singleton
class HomeController @Inject()(
  todoRepository: TodoRepository,
  val controllerComponents: ControllerComponents
) extends BaseController {

  def index() = Action { implicit req: Request[AnyContent] =>
    todoRepository.all.map(todos => println(s"""
        |--------
        |$todos
        |""".stripMargin))
    Ok(views.html.index())
  }

}
