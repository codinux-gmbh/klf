package net.codinux.log

enum class PlatformType {
    Android,
    Jvm,
    Apple,
    LinuxOrMingw,
    Js,
    Wasm
}

val PlatformType.isJvmOrAndroid: Boolean
    get() = this == PlatformType.Jvm || this == PlatformType.Android