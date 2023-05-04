#!/usr/local/bin/nss
@file:Import("set_env.nss")

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
