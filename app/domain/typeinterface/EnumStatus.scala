package domain.typeinterface

trait EnumStatus {
  val code: Short
  val values: Set[_ <: EnumStatus]
}
