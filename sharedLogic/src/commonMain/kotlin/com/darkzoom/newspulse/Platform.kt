package com.darkzoom.newspulse

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform