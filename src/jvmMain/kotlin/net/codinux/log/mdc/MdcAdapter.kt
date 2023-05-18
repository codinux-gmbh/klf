package net.codinux.log.mdc

import net.codinux.log.Platform
import org.slf4j.MDC

object MdcAdapter {

    fun addAllToMdc(mdc: Map<String, String?>) {
        if (Platform.isSlf4jOnClasspath) {
            mdc.forEach { (key, value) ->
                MDC.put(key, value)
            }
        }
    }

    fun removeAllToMdc(mdc: Map<String, String?>) {
        if (Platform.isSlf4jOnClasspath) {
            mdc.forEach { (key, _) ->
                MDC.remove(key)
            }
        }
    }

}