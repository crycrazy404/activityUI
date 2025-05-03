package util.dotEnv

import io.github.cdimascio.dotenv.Dotenv

object ENV {

    private val dotenv = Dotenv.configure()
        .ignoreIfMissing()
        .ignoreIfMissing()
        .directory("./")
        .load()

    val apiUrl = dotenv.get("API_URL") ?: error("API_URL not found")

}

