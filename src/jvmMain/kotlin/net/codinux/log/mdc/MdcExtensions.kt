package net.codinux.log.mdc


inline fun <T> runWithMdc(vararg mdc: Pair<String, String>, block: () -> T) =
    runWithMdc(mdc.toMap(), block)

inline fun <T> runWithMdc(mdc: Map<String, String>, block: () -> T): T {
    MdcAdapter.addAllToMdc(mdc)

    try {
        return block()
    } finally {
        MdcAdapter.removeAllToMdc(mdc)
    }
}