package ru.itmo.sd.nebash.runtime.commands

import ru.itmo.sd.nebash.runtime.Command
import ru.itmo.sd.nebash.runtime.CommandName
import ru.itmo.sd.nebash.runtime.toCn

/**
 * Provides command by its name.
 */
fun commandByName(name: CommandName): Command =
    commands[name] ?: External(name)

private val commands = mapOf(
    "cat".toCn() to Cat,
    "cd".toCn() to Cd,
    "echo".toCn() to Echo,
    "ls".toCn() to Ls,
    "pwd".toCn() to Pwd,
    "wc".toCn() to Wc
)
