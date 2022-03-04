lazy val root = project
  .in(file("."))
  .enablePlugins(JavaAppPackaging, GraalVMNativeImagePlugin)
  .settings(
    name := "databricks-job-manager",
    version := "1.0.0",
    organization := "com.alhuelamo",
    scalaVersion := "2.13.8",
    libraryDependencies ++= Seq(
      // Config
      "com.github.alexarchambault" %% "case-app" % "2.0.1",
      "org.ini4j"                  % "ini4j"     % "0.5.4",
      // Http
      "com.lihaoyi" %% "requests" % "0.7.0" % Compile,
      "com.lihaoyi" %% "ujson"    % "0.9.6" % Compile,
      // Tests
      "org.scalatest" %% "scalatest" % "3.2.10" % Test
    ),
    graalVMNativeImageOptions ++= Seq(
      "--no-fallback"
    )
  )
