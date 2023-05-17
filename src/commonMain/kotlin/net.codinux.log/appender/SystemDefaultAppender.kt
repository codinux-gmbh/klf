package net.codinux.log.appender

expect class SystemDefaultAppender {

    companion object {

        fun getDefaultAppender(): Appender

    }

}