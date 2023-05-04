#!/usr/local/bin/nss
@file:Import("set_env.nss")
@file:Import("setup_docker.nss")

import java.nio.file.Files

// Clean harvest/expand to rejuvenate
dir(sourceBasePath) {
  ns {
    if (Files.exists(expansionPath)) {
      harvest {}
    }
    expand {}
  }
}

// Build expanded project
dir(expansionPath) {
  mvn {
    maxThreads()
    task("clean")
    task("package")
  }
}

// Build Docker
dockerPreBuildActions()
dir(dockerPath) {
  exec {
    arg("docker", "build", "-t", "$dockerImage:latest", ".")
  }
}
