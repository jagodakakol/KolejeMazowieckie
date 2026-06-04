package pl.kakol.kolejemazowieckie

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform