package net.codinux.log

import kotlin.native.Platform

object NativeDefaults {

    @kotlin.experimental.ExperimentalNativeApi
    val isRunningInDebugMode: Boolean =
        Platform.isDebugBinary

}