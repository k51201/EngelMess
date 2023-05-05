package ru.vampa.engelmess
package dto

import io.circe.Decoder
import io.circe.generic.semiauto.deriveDecoder

case class PostMessage(author: String, text: String)

object PostMessage {
  implicit val decoder: Decoder[PostMessage] = deriveDecoder[PostMessage]
}
