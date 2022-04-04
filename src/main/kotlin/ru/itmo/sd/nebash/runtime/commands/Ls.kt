package ru.itmo.sd.nebash.runtime.commands

import kotlinx.coroutines.flow.*
import ru.itmo.sd.nebash.Env
import ru.itmo.sd.nebash.runtime.*
import java.nio.file.Files
import kotlin.io.path.isDirectory
import kotlin.io.path.name
import kotlin.io.path.notExists
import kotlin.streams.asSequence

private class LsException(msg: String) : NebashRuntimeException("ls: $msg")

object Ls : Command {
    override fun invoke(env: Env, args: List<CommandArg>, stdin: Stdin, stderr: Stderr): Stdout = flow {
        if (args.size > 1) {
            throw LsException("too many arguments, expected at most 1")
        }

        val path = when {
            args.isEmpty() -> Pwd.invoke(env, args, emptyFlow(), MutableSharedFlow()).first()
            else -> args[0].arg
        }.asOurPath(env)

        if (path.notExists()) {
            throw LsException("$path: No such file or directory")
        }

        if (path.isDirectory()) {
            emitAll(
                Files.list(path)
                    .map { it.name + "\n" }
                    .asSequence()
                    .asFlow()
            )
        } else {
            emit(path.name + "\n")
        }
    }
}
