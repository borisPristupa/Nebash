package ru.itmo.sd.nebash

import ru.itmo.sd.nebash.backend.execute
import ru.itmo.sd.nebash.frontend.raw.RawStmt
import ru.itmo.sd.nebash.frontend.toStmt

/**
 * Represents Nebash interpreter.
 */
class Nebash(private val state: MutableState = MutableState()) {
    fun execute(stmt: RawStmt) = stmt.toStmt(state).execute(state)
}

/**
 * Base class for all [Nebash] exceptions.
 */
abstract class NebashException(message: String, e: Throwable? = null) : RuntimeException(message, e)
