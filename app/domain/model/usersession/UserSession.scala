package domain.model.usersession

import java.time.LocalDateTime
import java.util.UUID

case class UserSession(
  id:         Long,
  userId:     Long,
  token:      Token,
  expiryDate: LocalDateTime
)
object UserSession {

  def newUserSession(
    userId: Long,
    token: Token
  ): UserSession = {
    UserSession(
      id = 0,
      userId = userId,
      token = token,
      expiryDate = getExpiryDate
    )
  }

  def newToken(userId: Long): Token = {
    Token(s"$userId-${UUID.randomUUID().toString}")
  }

  private def getExpiryDate: LocalDateTime = {
    LocalDateTime.now.plusDays(30)
  }

}
