package domain.model.session

import java.time.LocalDateTime

case class Session(
  id:         Long,
  userId:     Long,
  token:      String,
  expiryDate: LocalDateTime
)
