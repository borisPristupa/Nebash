package ru.itmo.sd.nebash.runtime.commands

import kotlinx.coroutines.flow.flow
import ru.itmo.sd.nebash.Env
import ru.itmo.sd.nebash.runtime.*

/**
 * Print working directory unix-like command.
 */
object Pwd : Command {
    override fun invoke(env: Env, args: List<CommandArg>, stdin: Stdin, stderr: Stderr): Stdout = flow {
        emit(workingDir().toString() + '\n')
    }
}
