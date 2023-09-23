package domain.model.user

import slick.lifted.MappedTo

case class Password(value: String) extends MappedTo[String]
