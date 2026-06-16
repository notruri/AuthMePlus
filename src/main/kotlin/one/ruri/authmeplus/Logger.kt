package one.ruri.authmeplus

class Logger(
    private val inner: java.util.logging.Logger,
) {
    @Volatile
    var debug: Boolean = false

    fun info(message: String) = inner.info(message)

    fun warning(message: String) = inner.warning(message)

    fun debug(message: String) {
        if (debug) inner.info(message)
    }
}
