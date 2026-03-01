package com.aniruddamabarkar.healthmodule

actual fun platform() = "Android"
actual fun hello() = {
    return "Hello, ${platform()}!"
}
