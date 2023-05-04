#!/usr/local/bin/nss
@file:Import("set_env.nss")
@file:Import("setup_docker.nss")

dockerPreBuildActions()
dir(dockerPath) {
  exec {
    arg("docker", "build", "-t", "$dockerImage:latest", ".")
  }

  dockerCompose("up", "--detach")
}
