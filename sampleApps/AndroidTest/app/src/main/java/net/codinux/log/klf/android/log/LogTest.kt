package net.codinux.log.klf.android.log

import android.util.Log
import net.codinux.log.logger

class LogTest {

    companion object {
        private const val Tag = "LogTest"
    }

    private val log by logger()

    fun logWithLogcat() {
        Log.i(Tag, "Testing logging with Logcat:")

        // this shows clearly that Log.isLoggable() is quite useless. For DEBUG and VERBOSE it
        // returns false, but at least on debug builds Log.d() and Log.v() get logged anyway
        Log.i(Tag, "Is log level Error enabled? ${Log.isLoggable(Tag, Log.ERROR)}")
        Log.i(Tag, "Is log level Warn enabled? ${Log.isLoggable(Tag, Log.WARN)}")
        Log.i(Tag, "Is log level Info enabled? ${Log.isLoggable(Tag, Log.INFO)}")
        Log.i(Tag, "Is log level Debug enabled? ${Log.isLoggable(Tag, Log.DEBUG)}")
        Log.i(Tag, "Is log level Verbose enabled? ${Log.isLoggable(Tag, Log.VERBOSE)}")

        Log.e(Tag, "Error message")
        Log.w(Tag, "Warn message")
        Log.i(Tag, "Info message")
        Log.d(Tag, "Debug message")
        Log.v(Tag, "Verbose message")
    }

    fun logWithKlf() {
        log.info { "Testing logging with Klf:" }

        log.info { "Is log level Error enabled? ${log.isErrorEnabled}" }
        log.info { "Is log level Warn enabled? ${log.isWarnEnabled}" }
        log.info { "Is log level Info enabled? ${log.isInfoEnabled}" }
        log.info { "Is log level Debug enabled? ${log.isDebugEnabled}" }
        log.info { "Is log level Trace enabled? ${log.isTraceEnabled}" }

        log.error { "Error message" }
        log.warn { "Warn message" }
        log.info { "Info message" }
        log.debug { "Debug message" }
        log.trace { "Verbose message" }
    }

}