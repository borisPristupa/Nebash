package ru.itmo.sd.nebash.runtime.commands

import ru.itmo.sd.nebash.Env
import ru.itmo.sd.nebash.toVn
import java.nio.file.Path

val HOME_VN = "HOME".toVn()

fun workingDir(): Path = Path.of(System.getProperty("user.dir")).toAbsolutePath()

/** Returns `null` iff HOME environment variable is not set */
fun homeDir(env: Env): Path? {
    val home = env[HOME_VN]?.value
    return home?.let { Path.of(it).toAbsolutePath() }
}

/**
 * Since the real working directory of the program can't (or is hard to) change, we
 * hold current working dir as a 'user.dir' system property. Hence, our path interpretation of
 * strings is different from the system's.
 */
fun String.asOurPath(env: Env) : Path {
    val parent = ".."
    val current = "."
    val home = "~"

    val original = Path.of(this.trim())
    val originalFirst = original.firstOrNull()?.let {
        if (original.isAbsolute) {
            Path.of("/").resolve(it)
        } else {
            it
        }
    } ?: return original // [original] represents root

    val first = when (originalFirst.toString()) {
        parent -> workingDir().parent
        current -> workingDir()
        home -> homeDir(env) ?: Path.of(home) // just don't expand
        else -> originalFirst
    }

    return when (original.nameCount) {
        1 -> first
        else -> first.resolve(original.subpath(1, original.nameCount))
    }
}
