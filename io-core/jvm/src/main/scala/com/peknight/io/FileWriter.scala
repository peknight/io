package com.peknight.io

import cats.effect.{Resource, Sync}
import cats.syntax.applicativeError.*
import fs2.io.file.Path

import java.io.FileWriter as JFileWriter

object FileWriter:
  def apply[F[_]: Sync](path: Path): Resource[F, JFileWriter] =
    Resource.fromAutoCloseable[F, JFileWriter](Sync[F].blocking(new JFileWriter(path.toNioPath.toFile)))

  def handleReleaseErrorWith[F[_]: Sync](path: Path)(f: Throwable => F[Unit]): Resource[F, JFileWriter] =
    Resource.make[F, JFileWriter](Sync[F].blocking(new JFileWriter(path.toNioPath.toFile))) { closeable =>
      Sync[F].blocking(closeable.close()).handleErrorWith(f)
    }
end FileWriter
