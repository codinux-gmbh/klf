package net.codinux.log

import net.codinux.kotlin.concurrent.Thread
import net.codinux.kotlin.platform.Environment
import net.codinux.kotlin.text.LineSeparator

object Defaults {

    private val environment = Environment()


    var lineSeparator = LineSeparator.System

    var isRunningInDebugMode: Boolean = environment.isRunningInDebugMode

    fun getCurrentThreadName(): String = Thread.current.name

}