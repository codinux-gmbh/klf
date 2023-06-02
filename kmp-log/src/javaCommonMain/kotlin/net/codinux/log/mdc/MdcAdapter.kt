package net.codinux.log.mdc

import net.codinux.log.slf4j.Slf4jUtil
import org.slf4j.MDC

object MdcAdapter {

    fun addAllToMdc(mdc: Map<String, String?>) {
        if (Slf4jUtil.useSlf4j) {
            mdc.forEach { (key, value) ->
                MDC.put(key, value)
            }
        }
    }

    fun removeAllToMdc(mdc: Map<String, String?>) {
        if (Slf4jUtil.useSlf4j) {
            mdc.forEach { (key, _) ->
                MDC.remove(key)
            }
        }
    }

}