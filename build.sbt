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
  scalaVersion := "3.1.1",
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
      "com.monovore" %% "decline" % "2.2.0",
      "org.ini4j"    %  "ini4j"   % "0.5.4",
      // Http
      "com.lihaoyi" %% "requests" % "0.7.0",
      "com.lihaoyi" %% "ujson"    % "1.5.0",
      // Tests
      "org.scalactic" %% "scalactic" % "3.2.11",
      "org.scalatest" %% "scalatest" % "3.2.11" % Test,
    ),

    Compile / mainClass := Some("com.alhuelamo.databricks.jobmanager.Main"),
    nativeImageOptions ++= Seq(
      "-H:+ReportExceptionStackTraces",
      "--no-fallback"
    )
  )
