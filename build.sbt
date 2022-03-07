inThisBuild(List(
  organization := "com.alhuelamo",
  homepage := Some(url("https://github.com/alhuelamo/dbjobs")),
  licenses := List("Apache-2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0")),
  developers := List(
    Developer(
      "alhuelamo",
      "Alberto Huélamo",
      "alhuelamo@gmail.com",
      url("https://alhuelamo.com"),
    )
  ),
  scalaVersion := "2.13.8",
  versionScheme := Some("early-semver"),
))

lazy val root = project
  .in(file("."))
  .enablePlugins(JavaAppPackaging, NativeImagePlugin)
  .settings(
    name := "dbjobs",
    description := "A CLI app to manage Databricks Jobs",

    libraryDependencies ++= Seq(
      // Config
      "com.github.alexarchambault" %% "case-app" % "2.1.0-M13",
      "org.ini4j"                  % "ini4j"     % "0.5.4",
      // Http
      "com.lihaoyi" %% "requests" % "0.7.0" % Compile,
      "com.lihaoyi" %% "ujson"    % "0.9.6" % Compile,
      // Tests
      "org.scalatest" %% "scalatest" % "3.2.10" % Test
    ),

    Compile / mainClass := Some("com.alhuelamo.databricks.jobmanager.Main"),
    nativeImageOptions ++= Seq(
      "-H:+ReportExceptionStackTraces",
      "--no-fallback"
    )
  )
