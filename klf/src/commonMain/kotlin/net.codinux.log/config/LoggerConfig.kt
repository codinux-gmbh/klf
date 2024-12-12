package net.codinux.log.config

class LoggerConfig{

    companion object {
        const val useCallerMethodIfLoggerNameNotSetDefault = false
    }


    /**
     * If no logger tag is passed to log statement, e.g. with `Log.info { ".." }`,
     * and `LoggerFactory.config.useCallerMethodIfLoggerNameNotSet` or in debug mode
     * `LoggerFactory.debugConfig.useMethodNameIfLogTagIsNotSet` is set to true,
     * then the method that executed the log statement will be used as logger name,
     * in the format `<class name>.<method name>`.
     *
     * This comes in handy in Compose as in composable functions there's (usually) no class to
     * reference with `by logger()` or `Log.info<ClassName> { }`.
     *
     * It's also trying to remove auto generated class name parts like they are applied to Coroutine
     * functions (like "org.example.AppKt$App$2$1$2.invokeSuspend" -> "org.example.App").
     * This may not work reliably under all circumstances as we have to extract the class name and
     * method name from strings and we may are not aware of all possible formats yet.
     *
     * But be aware that this is a bit resource intensive as it walks up the call
     * stack for each log call to find the calling method, so preferably only enable it in debug mode.
     * For UI applications this should be fine anyway, just don't enable it on high load servers.
     *
     * This currently works only on `JVM` and `Android`.
     */
    var useCallerMethodIfLoggerNameNotSet: Boolean = useCallerMethodIfLoggerNameNotSetDefault


    override fun toString() = "useCallerMethodIfLoggerNameNotSet = $useCallerMethodIfLoggerNameNotSet"
}