package net.codinux.log.klf.android.log

import android.util.Log

class LogTest {

    private val Tag = "LogTest"

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

}