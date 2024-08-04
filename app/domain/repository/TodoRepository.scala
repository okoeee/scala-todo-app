package domain.repository

import domain.model.todo.Todo

import scala.concurrent.Future

trait TodoRepository {
  def all: Future[Seq[Todo]]
  def findByUserId(userId: Long): Future[Seq[Todo]]
  def filterByGroupId(groupId: Long): Future[Seq[Todo]]
  def insert(todo: Todo): Future[Int]
  def selectByPlainQuery(userId: Long, body: String): Future[Vector[(String, Int)]]
}
