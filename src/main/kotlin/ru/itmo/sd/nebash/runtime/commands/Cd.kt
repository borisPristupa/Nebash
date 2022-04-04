package ru.itmo.sd.nebash.runtime.commands

import kotlinx.coroutines.flow.emptyFlow
import ru.itmo.sd.nebash.Env
import ru.itmo.sd.nebash.runtime.*

private class CdException(msg: String) : NebashRuntimeException("cd: $msg")

object Cd : Command {
    override fun invoke(env: Env, args: List<CommandArg>, stdin: Stdin, stderr: Stderr): Stdout {
        if (args.size > 1) {
            throw CdException("too many arguments, expected at most 1")
        }

        val destination = when {
            args.isEmpty() -> env[HOME_VN]?.value ?: throw CdException("${HOME_VN.name} env variable is not set")
            else -> args[0].arg
        }

        System.setProperty("user.dir", workingDir().resolve(destination.asOurPath(env)).normalize().toString())
        return emptyFlow()
    }
}
