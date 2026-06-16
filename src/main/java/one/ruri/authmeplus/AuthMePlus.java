package one.ruri.authmeplus;

import org.bukkit.plugin.java.JavaPlugin;

public class AuthMePlus extends JavaPlugin {

    private Handlers handlers;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        reloadConfig();

        Bridge authMe = new Bridge(getLogger());
        if (!authMe.findAuthMeApi()) {
            getLogger().warning(
                "AuthMe API not found via reflection. Plugin will still run but cannot force-login players until AuthMe is present."
            );
        }

        this.handlers = new Handlers(this, getConfig(), authMe);
        this.handlers.register();

        getLogger().info(
            "AuthMePlus enabled"
        );
    }

    @Override
    public void onDisable() {
        if (this.handlers != null) {
            this.handlers.shutdown();
        }
        getLogger().info("AuthMePlus disabled");
    }
}
