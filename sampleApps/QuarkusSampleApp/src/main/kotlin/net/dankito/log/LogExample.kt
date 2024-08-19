package net.dankito.log

import io.quarkus.runtime.Startup
import jakarta.annotation.PostConstruct
import jakarta.inject.Singleton
import net.codinux.log.Log
import net.codinux.log.logger

@Singleton
@Startup
class LogExample {

    companion object {
        private val companionLog by logger()
    }

    private val log by logger()

    private val logByName by logger("LoggerByName")


    @PostConstruct
    internal fun showLogExample() {
        log.debug { "Debug" }

        log.info { "Info" }

        log.warn { "Warn" }

        log.error(Exception("Just a test, no animals have been harmed")) { "Error with Exception" }

        logByName.info { "Info from (lazy instantiated) Logger by name" }


        // unwrapping Companion object

        companionLog.info { "Companion Logger: Info" }

        companionLog.error(Exception("Just a test, no animals have been harmed")) { "Companion Logger: Error with Exception" }


        // static logger

        Log.info<LogExample> { "Static Logger: Info" }

        Log.error<LogExample>(Exception("Just a test, no animals have been harmed")) { "Static Logger: Error with Exception" }
    }

}