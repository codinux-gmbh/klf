package net.codinux.log

enum class PlatformType {
    Android,
    Jvm,
    Apple,
    LinuxOrMingw,
    Js,
    Wasm;


    val isJvmOrAndroid: Boolean by lazy { this == Jvm || this == Android }

    val isJsOrWasm: Boolean by lazy { this == Js || this == Wasm }

}