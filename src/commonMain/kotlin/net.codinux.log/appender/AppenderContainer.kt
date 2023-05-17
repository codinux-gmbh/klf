package net.codinux.log.appender

interface AppenderContainer {

    fun addAppender(appender: Appender)

    fun getAppenders(): Collection<Appender>

}