package one.ruri.authmeplus

import one.ruri.authmeplus.protocol.Protocol
import org.bukkit.plugin.java.JavaPlugin

class Main : JavaPlugin() {
    private var eventHandlers: EventHandlers? = null
    private var protocolHandler: Protocol? = null
    private lateinit var log: Logger

    override fun onEnable() {
        saveDefaultConfig()
        reloadConfig()

        log = Logger(logger)
        log.debug = config.getBoolean("settings.debug", false)

        protocolHandler = Protocol(this, log).also { it.register() }
        log.info("ProtocolLib v${Protocol.protocolLibVersion()} - real session verification enabled")

        eventHandlers = EventHandlers(this, config, protocolHandler!!, log)
        eventHandlers!!.register()

        val cfg = config
        log.info(
            "AuthMePlus enabled (accept_cracked=${cfg.getBoolean(
                "settings.accept_cracked",
                false,
            )}, enabled=${cfg.getBoolean("settings.enableplugin", true)})",
        )
    }

    override fun onDisable() {
        protocolHandler?.unregister()
        eventHandlers?.shutdown()
        if (::log.isInitialized) log.info("AuthMePlus disabled")
    }
}
