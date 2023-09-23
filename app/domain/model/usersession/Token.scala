package domain.model.usersession

import slick.lifted.MappedTo

case class Token(value: String) extends MappedTo[String]
