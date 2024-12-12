package net.codinux.log

import kotlin.experimental.ExperimentalNativeApi
import kotlin.native.Platform

@OptIn(ExperimentalNativeApi::class)
object NativeDefaults {

    val isRunningInDebugMode: Boolean =
        Platform.isDebugBinary

}