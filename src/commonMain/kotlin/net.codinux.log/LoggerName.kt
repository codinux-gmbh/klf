package net.codinux.log

import kotlin.reflect.KClass

expect fun <T : Any> getLoggerName(forClass: KClass<T>): String