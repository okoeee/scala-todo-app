package domain.model.todo

case class Todo(
  // todo ひとまずLong型に
  id: Long,
  categoryId: Long,
  title: String,
  body: String,
  state: Int
)
