package domain.model.user

import slick.lifted.MappedTo

import com.github.t3hnar.bcrypt._

case class Password(
  value: String
) extends MappedTo[String] {
  def encrypt: Password =
    Password(this.value.bcryptSafeBounded.get)
}
