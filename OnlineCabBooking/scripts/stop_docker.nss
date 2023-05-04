#!/usr/local/bin/nss
@file:Import("set_env.nss")

dir(dockerPath) {
  dockerCompose("down")
}
