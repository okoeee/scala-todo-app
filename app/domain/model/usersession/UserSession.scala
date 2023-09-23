package domain.model.usersession

import java.time.LocalDateTime

case class UserSession(
  id:         Long,
  userId:     Long,
  token:      String,
  expiryDate: LocalDateTime
)
