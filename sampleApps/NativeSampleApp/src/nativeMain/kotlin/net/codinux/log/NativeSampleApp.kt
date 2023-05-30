package net.codinux.log

class NativeSampleApp {

    private val log by logger()

    fun runSample() {
        log.debug { "Debug" }

        log.info { "Info" }

        log.warn { "Warn" }

        log.error(Exception("Just a test, no animals have been harmed")) { "Error with Exception" }
    }
}