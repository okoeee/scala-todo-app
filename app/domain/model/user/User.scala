package domain.model.user

case class User(
  id:       Long,
  name:     String,
  email:    String,
  password: Password
)
