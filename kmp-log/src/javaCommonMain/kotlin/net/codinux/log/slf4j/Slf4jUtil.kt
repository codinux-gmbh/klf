package net.codinux.log.slf4j

object Slf4jUtil {

    val isSlf4jOnClasspath = isClassAvailable("org.slf4j.Logger")

    private fun isClassAvailable(qualifiedClassName: String): Boolean {
        try {
            Class.forName(qualifiedClassName)

            return true
        } catch (ignored: Exception) { }

        return false
    }

}