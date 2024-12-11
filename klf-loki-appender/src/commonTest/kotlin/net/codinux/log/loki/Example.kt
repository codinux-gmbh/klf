package net.codinux.log.loki

import net.codinux.log.LoggerFactory
import net.codinux.log.config.WriterConfig
import net.codinux.log.loki.config.LokiLogAppenderConfig

class Example {

    fun showLokiLogAppenderExample() {
        // configure Loki url and credentials:
        // if using http (e.g. for tests in local network) on Android add `android:usesCleartextTraffic="true"` to AndroidManifest.xml -> <application> tag
        val config = LokiLogAppenderConfig(writer = WriterConfig("http://192.168.0.27:3100", username = "..", password = ".."))

        // then add LokiAppender to klf LoggerFactory
        LoggerFactory.addAppender(LokiAppender(config))
    }

}