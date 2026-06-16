package one.ruri.authmeplus.command

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import one.ruri.authmeplus.Utils
import org.bukkit.command.CommandSender
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.plugin.java.JavaPlugin

object Version {
    fun execute(
        sender: CommandSender,
        plugin: JavaPlugin,
        cfg: FileConfiguration,
    ): Boolean {
        val version = plugin.pluginMeta.version
        val lines = cfg.getStringList("messages.version")
        if (lines.isEmpty()) {
            sender.sendMessage(Component.text("AuthMePlus v$version", NamedTextColor.GOLD))
            return true
        }
        for (line in lines) {
            sender.sendMessage(Utils.color(line.replace("%version%", version)))
        }
        return true
    }
}
