lazy val root = (project in file("."))
  .enablePlugins(PlayScala)
  .settings(
    name := """scala-todo-app""",
    organization := "com.example",
    version := "1.0-SNAPSHOT",
    scalaVersion := "2.13.11",
    libraryDependencies ++= Seq(
      guice,
      "org.scalatestplus.play" %% "scalatestplus-play" % "5.1.0" % Test,
      "mysql" % "mysql-connector-java" % "8.0.33",
      "com.typesafe.play" %% "play-slick" % "5.1.0",
      "com.typesafe.play" %% "play-slick-evolutions" % "5.1.0"
    )
  )
