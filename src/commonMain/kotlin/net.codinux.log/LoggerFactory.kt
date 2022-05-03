package net.codinux.log

class LoggerFactory {

    companion object {

        var factory: ILoggerFactory = ConsoleLoggerFactory()


        fun getLogger(name: String): Logger {
            return factory.getLogger(name)
        }

    }

}