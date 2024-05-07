package com.peknight.io

import cats.effect.{Resource, Sync}
import cats.syntax.applicativeError.*
import fs2.io.file.Path

import java.io.FileReader as JFileReader

object FileReader:
  def apply[F[_]: Sync](path: Path): Resource[F, JFileReader] =
    Resource.fromAutoCloseable[F, JFileReader](Sync[F].blocking(new JFileReader(path.toNioPath.toFile)))

  def handleReleaseErrorWith[F[_]: Sync](path: Path)(f: Throwable => F[Unit]): Resource[F, JFileReader] =
    Resource.make[F, JFileReader](Sync[F].blocking(new JFileReader(path.toNioPath.toFile))) { closeable =>
      Sync[F].blocking(closeable.close()).handleErrorWith(f)
    }
end FileReader
