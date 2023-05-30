package net.codinux.log

class NativeSampleApp {

    private val log by logger()

    fun runSample() {
        log.info { "Info" }
    }
}