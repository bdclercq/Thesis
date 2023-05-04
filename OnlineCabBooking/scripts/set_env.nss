#!/usr/local/bin/nss

@file:MinVersion("1.9.0")

val applicationName = "OnlineCabBooking"

val sourceBasePath = ctx.script.path.parent
val expansionPath = sourceBasePath.resolve("expansions/$applicationName")

// anchor:variables:start
val dockerImage = "nsapp/onlinecabbooking"
val dockerPath = sourceBasePath.resolve("docker")

fun ScriptScope.dockerCompose(vararg args: String) {
  exec {
    arg("docker", "compose")
    arg("-f", "docker-compose.yml")
    arg("-f", "docker-compose.local.yml")
    arg(*args)
  }
}
// anchor:variables:end
