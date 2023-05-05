package ru.vampa.engelmess
package dto

import io.circe.Encoder
import io.circe.generic.semiauto.deriveEncoder

import java.time.Instant

case class Message(id: Long, author: String, text: String, timestamp: Instant)

object Message {
  implicit val encoder: Encoder[Message] = deriveEncoder[Message]
}
