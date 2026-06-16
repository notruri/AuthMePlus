package one.ruri.authmeplus.event

import one.ruri.authmeplus.command.Dispatch
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.Command as BukkitCommand

class Command(
    private val dispatch: Dispatch,
) : CommandExecutor {
    override fun onCommand(
        sender: CommandSender,
        command: BukkitCommand,
        label: String,
        args: Array<String>,
    ): Boolean {
        if (args.isEmpty()) {
            dispatch.showHelp(sender)
            return true
        }

        return dispatch.dispatch(sender, args)
    }
}
