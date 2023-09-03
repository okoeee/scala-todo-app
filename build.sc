import mill._
import $ivy.`com.lihaoyi::mill-contrib-playlib:`,  mill.playlib._

object todo extends PlayModule with SingleModule {

  def scalaVersion = "Scala 2.13.11"
  def playVersion = "2.8.19"
  def twirlVersion = "1.5.1"

  object test extends PlayTests
}
