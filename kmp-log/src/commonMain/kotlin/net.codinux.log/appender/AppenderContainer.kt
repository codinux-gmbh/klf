package net.codinux.log.appender

interface AppenderContainer {

    val doesAnyAppenderLogThreadName: Boolean

    fun addAppender(appender: Appender)

    fun getAppenders(): Collection<Appender>

}