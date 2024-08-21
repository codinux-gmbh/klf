package net.codinux.log.test

import net.codinux.log.Platform

object TestPlatform {

    // on all other platforms the logger name starts with the package name
    val isRunningInJs = Platform.getLoggerName(this::class) == "TestPlatform"

}