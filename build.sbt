import sbt.Keys.version

// Make sure when this is updated, it is also updated in SlyceCommon/slyce.package.scala
val Version = "0.1.0"

lazy val SlyceCommon =
  project
    .in(file("SlyceCommon"))
    .settings(
      name := "SlyceCommon",
      version := Version,
      scalaVersion := "2.13.2",
      libraryDependencies ++=
        List(
          "org.scalaz" %% "scalaz-core" % "7.2.30",
          "org.scalactic" %% "scalactic" % "3.1.1",
          "org.scalatest" %% "scalatest" % "3.1.1" % "test",
          "org.scalameta" %% "scalameta" % "4.3.10",
          "com.chuusai" %% "shapeless" % "2.3.3",
        ),
    )

lazy val SlyceParse =
  project
    .in(file("SlyceParse"))
    .settings(
      name := "SlyceParse",
      version := Version,
      scalaVersion := "2.13.2",
    )
    .dependsOn(SlyceCommon)

lazy val SlyceGenerate =
  project
    .in(file("SlyceGenerate"))
    .settings(
      name := "SlyceGenerate",
      version := Version,
      scalaVersion := "2.13.2",
    )
    .dependsOn(SlyceCommon)

lazy val SlyceTest =
  project
    .in(file("SlyceTest"))
    .settings(
      name := "SlyceTest",
      version := Version,
      scalaVersion := "2.13.2",
    )
    .dependsOn(SlyceGenerate, SlyceParse)
