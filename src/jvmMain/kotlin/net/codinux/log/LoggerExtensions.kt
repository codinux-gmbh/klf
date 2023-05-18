package net.codinux.log

import org.slf4j.MDC


fun Logger.withMdc(vararg mdc: Pair<String, String>) = this.withMdc(mdc.toMap())

fun Logger.withMdc(mdc: Map<String, String>) = LoggerWithMDC(this, mdc)


inline fun <T> runWithMdc(vararg mdc: Pair<String, String>, block: () -> T) =
    runWithMdc(mdc.toMap(), block)

inline fun <T> runWithMdc(mdc: Map<String, String>, block: () -> T): T {
    if (Platform.isSlf4jOnClasspath) {
        mdc.forEach { (key, value) ->
            MDC.put(key, value)
        }
    }

    try {
        return block()
    } finally {
        if (Platform.isSlf4jOnClasspath) {
            mdc.forEach { (key, _) ->
                MDC.remove(key)
            }
        }
    }
}