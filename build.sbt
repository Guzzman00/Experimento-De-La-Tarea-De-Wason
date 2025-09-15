ThisBuild / scalaVersion := "3.3.3"
ThisBuild / version      := "0.1.0-SNAPSHOT"

lazy val build = taskKey[Unit]("Construye la aplicación completa en la carpeta 'dist'")

lazy val root = (project in file("."))
  .enablePlugins(ScalaJSPlugin)
  .settings(
    name := "experimento-de-la-tarea-de-Wason",
    libraryDependencies += "com.raquo" %%% "laminar" % "15.0.1",

    scalaJSUseMainModuleInitializer := true,
    Compile / fastLinkJS / scalaJSLinkerOutputDirectory := baseDirectory.value / "dist",

    build := {
      (Compile / fastLinkJS).value

      val sourceHtml = baseDirectory.value / "index.html"
      val targetHtml = baseDirectory.value / "dist" / "index.html"

      IO.copyFile(sourceHtml, targetHtml)

      println("✅ Build completo. La carpeta 'dist' está lista con main.js e index.html.")
    }
  )