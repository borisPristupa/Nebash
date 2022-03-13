package ru.itmo.sd.nebash.backend

import ru.itmo.sd.nebash.NebashException
import java.io.IOException

abstract class BackendException(message: String, e: Throwable?) : NebashException(message, e)

class IOExecutionException(e: IOException) : BackendException(e.message.orEmpty(), e)

class FailToStartExternalProcess(e: RuntimeException) : BackendException(e.message.orEmpty(), e)
