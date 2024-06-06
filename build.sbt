ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.4.2"

ThisBuild / organization := "com.peknight"

lazy val commonSettings = Seq(
  scalacOptions ++= Seq(
    "-feature",
    "-deprecation",
    "-unchecked",
    "-Xfatal-warnings",
    "-language:strictEquality",
    "-Xmax-inlines:64"
  ),
)

lazy val io = (project in file("."))
  .aggregate(
    ioCore.jvm,
    ioCore.js,
  )
  .settings(commonSettings)
  .settings(
    name := "io",
  )

lazy val ioCore = (crossProject(JSPlatform, JVMPlatform) in file("io-core"))
  .settings(commonSettings)
  .settings(
    name := "io-core",
    libraryDependencies ++= Seq(
      "co.fs2" %%% "fs2-io" % fs2Version,
    ),
  )

val fs2Version = "3.10.2"

