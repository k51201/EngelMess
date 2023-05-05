package ru.vampa.engelmess

import cats.effect.kernel.Ref
import dto._

import cats.effect.Sync
import cats.syntax.all._

import java.time.Instant
import java.util.concurrent.atomic.AtomicLong

trait MsgService[F[_]] {
  def addMessage(msg: PostMessage): F[Unit]

  def getMessages(count: Int): F[List[Message]]
}

object MsgService {
  def apply[F[_] : Sync]: F[MsgService[F]] = Ref.of[F, List[Message]](List.empty).map { state =>
    new MsgService[F] {
      private val idCounter = new AtomicLong(0L)

      override def addMessage(msg: PostMessage): F[Unit] =
        state.update(_ :+ Message(idCounter.incrementAndGet(), msg.author, msg.text, Instant.now()))

      override def getMessages(count: Int): F[List[Message]] =
        state.get.map(_.takeRight(count))
    }
  }
}
