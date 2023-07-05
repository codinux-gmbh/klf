package net.codinux.log

import kotlin.native.Platform

object NativeDefaults {

    val isRunningInDebugMode: Boolean =
        Platform.isDebugBinary

}