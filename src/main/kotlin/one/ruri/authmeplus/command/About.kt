package one.ruri.authmeplus.command

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import one.ruri.authmeplus.Utils
import org.bukkit.command.CommandSender
import org.bukkit.configuration.file.FileConfiguration

object About {
    fun execute(
        sender: CommandSender,
        cfg: FileConfiguration,
    ): Boolean {
        val lines = cfg.getStringList("messages.about")
        if (lines.isEmpty()) {
            sender.sendMessage(Component.text("No plugin information has been configured.", NamedTextColor.RED))
            return true
        }
        for (line in lines) {
            sender.sendMessage(Utils.color(line))
        }
        return true
    }
}
