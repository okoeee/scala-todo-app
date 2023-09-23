package domain.model.usersession

import java.time.LocalDateTime

case class UserSession(
  id:         Long,
  userId:     Long,
  token:      String,
  expiryDate: LocalDateTime
)
object UserSession {
  def newUserSession(
    userId: Long,
    token: String,
    expiryDate: LocalDateTime
  ): UserSession = {
    UserSession(
      id = 0,
      userId = userId,
      token = token,
      expiryDate = expiryDate
    )
  }
}
