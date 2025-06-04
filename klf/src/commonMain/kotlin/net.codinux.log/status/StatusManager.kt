package net.codinux.log.status

import net.codinux.kotlin.concurrent.collections.ConcurrentList
import net.codinux.kotlin.concurrent.collections.ConcurrentSet

/**
 * To notify about internal klf status, add [Status] messages to this object
 * and handle them by adding a [StatusListener].
 */
object StatusManager {

    var countStatusToBuffer = 0

    private val bufferedStatus = ConcurrentList<Status>()

    private val listeners = ConcurrentSet<StatusListener>()


    fun newError(origin: Any, message: String, exception: Throwable? = null) =
        addStatus(Status.error(origin, message, exception))

    fun newWarning(origin: Any, message: String, exception: Throwable? = null) =
        addStatus(Status.warning(origin, message, exception))

    fun newInfo(origin: Any, message: String, exception: Throwable? = null) =
        addStatus(Status.info(origin, message, exception))

    fun addStatus(status: Status) {
        callListeners(status)

        if (countStatusToBuffer > 0) {
            bufferedStatus.add(status)

            while (bufferedStatus.size > countStatusToBuffer) {
                bufferedStatus.removeAt(0)
            }
        }
    }


    fun getCopyOfStatusList(): List<Status> = bufferedStatus.toList()

    fun getCountBufferedStatus(): Int = bufferedStatus.size


    fun addListener(listener: StatusListener): Boolean =
        listeners.add(listener)

    fun removeListener(listener: StatusListener): Boolean =
        listeners.remove(listener)

    private fun callListeners(status: Status) {
        listeners.toList().forEach { listener ->
            try {
                listener.newStatus(status)
            } catch (e: Throwable) {
                println("Calling listener $listener failed for status $status: e")
            }
        }
    }

}