package net.codinux.log

import net.codinux.log.mdc.LoggerWithMDC


fun Logger.withMdc(vararg mdc: Pair<String, String>) = this.withMdc(mdc.toMap())

fun Logger.withMdc(mdc: Map<String, String>) = LoggerWithMDC(this, mdc)