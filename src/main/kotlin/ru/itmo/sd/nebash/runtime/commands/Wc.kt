package ru.itmo.sd.nebash.runtime.commands

import kotlinx.coroutines.flow.flow
import ru.itmo.sd.nebash.Env
import ru.itmo.sd.nebash.runtime.*
import ru.itmo.sd.nebash.utils.collectWhileNotNull
import kotlin.io.path.forEachLine

/**
 * Word count unix-like utility: takes files and count lines, words and chars in them.
 */
object Wc : Command {
    override fun invoke(env: Env, args: List<CommandArg>, stdin: Stdin, stderr: Stderr): Stdout = flow {
        var nLines = 0
        var nWords = 0
        var nChars = 0

        fun String.process() {
            nLines += count { it == '\n' }
            nWords += split("""\s+""".toRegex()).count { it.isNotBlank() }
            nChars += length
        }

        fun prettyPrint(filename: String? = null): String {
            val line = "\t${if (nLines == 0) 1 else nLines}\t$nWords\t$nChars"
            val end = if (filename == null) "\n" else "\t$filename\n"
            nLines = 0
            nWords = 0
            nChars = 0
            return line + end
        }

        if (args.isEmpty()) {
            stdin.collectWhileNotNull { it.process() }
            emit(prettyPrint())
            return@flow
        }
        args.forEach {
            workingDir().resolve(it.arg.asOurPath(env)).forEachLine { line -> line.process() }
            emit(prettyPrint(it.arg))
        }
    }
}
