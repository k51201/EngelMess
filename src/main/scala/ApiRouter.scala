package ru.vampa.engelmess

import dto._

import cats.effect.Async
import cats.syntax.all._
import io.circe.syntax.EncoderOps
import org.http4s.HttpRoutes
import org.http4s.circe._
import org.http4s.dsl.Http4sDsl
import org.http4s.server.middleware.CORS


class ApiRouter[F[_] : Async](msgService: MsgService[F]) extends Http4sDsl[F] {

  private val httpRoutes = HttpRoutes.of[F] {
    case req@POST -> Root / "api" / "msg" =>
      for {
        postMsg <- req.decodeJson[PostMessage]
        value <- msgService.addMessage(postMsg).as(NoContent())
        handled <- value.handleErrorWith { error =>
          BadRequest(error.getMessage)
        }
      } yield handled


    case GET -> Root / "api" / "msgs" :? count(count) =>
      count match {
        case Some(c) if c <= 0 => BadRequest("count parameter should be greater than 0")
        case Some(c) =>
          msgService
            .getMessages(c)
            .flatMap(msgs => Ok(msgs.asJson))
        case None =>
          msgService
            .getMessages(100)
            .flatMap(msgs => Ok(msgs.asJson))
      }
  }

  def routes: HttpRoutes[F] = {
    CORS.policy.withAllowOriginAll.withAllowCredentials(false).apply(httpRoutes)
  }

  object count extends OptionalQueryParamDecoderMatcher[Int]("count")
}

object ApiRouter {
  def apply[F[_] : Async](msgService: MsgService[F]): ApiRouter[F] =
    new ApiRouter[F](msgService)
}
