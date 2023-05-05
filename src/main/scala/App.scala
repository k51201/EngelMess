package ru.vampa.engelmess

import cats.effect.{Async, ExitCode, IO, IOApp}
import cats.syntax.all._
import com.comcast.ip4s._
import org.http4s.ember.server.EmberServerBuilder

object App extends IOApp {
  private def runServer[F[_]: Async](): F[ExitCode] = {
    for {
      msgService <- MsgService.apply[F]
      code <- EmberServerBuilder
        .default[F]
        .withHost(ipv4"0.0.0.0")
        .withPort(port"8080")
        .withHttpApp(ApiRouter(msgService).routes.orNotFound)
        .build
        .use(_ => Async[F].never[Unit])
        .as(ExitCode.Success)
    } yield code
  }

  override def run(args: List[String]): IO[ExitCode] =
    runServer[IO]()
}
