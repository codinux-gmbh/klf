package net.codinux.log

class NativeSampleApp {

    private val log by logger()

    private val logByName by logger("Liebe")


    fun runSample() {
        log.debug { "Debug" }

        log.info { "Info" }

        log.warn { "Warn" }

        log.error(Exception("Just a test, no animals have been harmed")) { "Error with Exception" }

        logByName.info { "Info from (lazy instantiated) Logger by name" }
    }
}