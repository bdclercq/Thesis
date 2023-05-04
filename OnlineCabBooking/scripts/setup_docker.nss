#!/usr/local/bin/nss
@file:Import("set_env.nss")

import java.nio.file.Files
import java.nio.file.StandardCopyOption

/**
 * Actions to be executed before building the Dockerfile into an image.
 */
fun dockerPreBuildActions() {
    Files.copy(expansionPath.resolve("ear/target/$applicationName.ear"),
        dockerPath.resolve("deploy/$applicationName.ear"),
        StandardCopyOption.REPLACE_EXISTING)

    Files.copy(expansionPath.resolve("database/000-init-database.sql"),
        dockerPath.resolve("sql/V1.1__init_database.sql"),
        StandardCopyOption.REPLACE_EXISTING)
    Files.copy(expansionPath.resolve("database/001-app-init-data.sql"),
        dockerPath.resolve("sql/V1.2__app_init_data.sql"),
        StandardCopyOption.REPLACE_EXISTING)
}
