package one.ruri.authmeplus.event

import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import java.util.Locale

class TabComplete : TabCompleter {
    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        alias: String,
        args: Array<String>,
    ): MutableList<String> {
        val completions = mutableListOf<String>()
        if (args.size == 1) {
            val partial = args[0].lowercase(Locale.ROOT)
            for (cmd in arrayOf("reload", "version", "about")) {
                if (cmd.startsWith(partial)) completions.add(cmd)
            }
        }
        return completions
    }
}
