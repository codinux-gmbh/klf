package net.codinux.log

class NativeSampleApp {

    private val log by logger()

    fun runSample() {
        log.debug { "Debug" }

        log.info { "Info" }

        log.warn { "Warn" }
    }
}