package com.peknight.io.syntax

import cats.effect.Sync
import fs2.io.file.Path

trait PathSyntax:
  extension (path: Path)
    def existsF[F[_]: Sync]: F[Boolean] = Sync[F].blocking(path.toNioPath.toFile.exists())
  end extension
end PathSyntax
object PathSyntax extends PathSyntax
