package domain.model.user

import slick.lifted.MappedTo

import com.github.t3hnar.bcrypt._

case class Password(
  value: String
) extends MappedTo[String] {
  // todo PasswordにはUserIdの情報をもたせ、別テーブルで情報を管理したほうが良さそう
  def encrypt: Password =
    Password(this.value.bcryptSafeBounded.get)
}
