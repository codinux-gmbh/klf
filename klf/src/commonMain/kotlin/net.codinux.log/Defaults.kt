package net.codinux.log

import net.codinux.kotlin.concurrent.Thread
import net.codinux.kotlin.text.LineSeparator

object Defaults {

    var lineSeparator = LineSeparator.System

    fun getCurrentThreadName(): String = Thread.current.name

}