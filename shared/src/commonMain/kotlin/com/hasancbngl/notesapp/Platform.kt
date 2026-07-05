package com.hasancbngl.notesapp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform