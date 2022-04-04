package ru.itmo.sd.nebash.runtime.commands

import kotlinx.coroutines.flow.flow
import ru.itmo.sd.nebash.Env
import ru.itmo.sd.nebash.runtime.*
import ru.itmo.sd.nebash.utils.collectWhileNotNull
import kotlin.io.path.forEachLine

/**
 * Unix-like utility that prints stdin to stdout.
 */
object Cat : Command {
    override fun invoke(env: Env, args: List<CommandArg>, stdin: Stdin, stderr: Stderr): Stdout = flow {
        if (args.isEmpty()) stdin.collectWhileNotNull { emit(it) }
        else args.forEach {
            workingDir().resolve(it.arg.asOurPath(env)).forEachLine { line ->
                emit(line + '\n')
            }
        }
    }
}
