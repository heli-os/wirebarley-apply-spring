package apply.aaron.wirebarley.util


@Suppress("UNCHECKED_CAST")
fun <T> lateInit(): T = null as T

inline fun <T : Any> T?.notNull(lazyMessage: () -> Any): T = requireNotNull(this, lazyMessage)
