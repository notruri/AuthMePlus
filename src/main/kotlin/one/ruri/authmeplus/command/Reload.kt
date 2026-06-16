package one.ruri.authmeplus.command

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import one.ruri.authmeplus.Logger
import one.ruri.authmeplus.Utils
import org.bukkit.command.CommandSender
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.plugin.java.JavaPlugin

object Reload {
    fun execute(
        sender: CommandSender,
        plugin: JavaPlugin,
        log: Logger,
        cfg: FileConfiguration,
    ): FileConfiguration? {
        if (!sender.hasPermission("amp.reload") && !sender.isOp) {
            sender.sendMessage(Component.text("You do not have permission to use this command.", NamedTextColor.RED))
            return null
        }
        return try {
            plugin.reloadConfig()
            val newCfg = plugin.config
            log.debug = newCfg.getBoolean("settings.debug", false)
            sender.sendMessage(Utils.getMessage(newCfg, "messages.reload_success", "&aConfiguration reloaded."))
            log.info("Configuration reloaded by ${sender.name}")
            newCfg
        } catch (e: Exception) {
            sender.sendMessage(Utils.getMessage(cfg, "messages.reload_fail", "&cFailed to reload config."))
            log.warning("Failed to reload config: ${e.message}")
            null
        }
    }
}
