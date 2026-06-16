package one.ruri.authmeplus;

import java.lang.reflect.Method;
import java.util.logging.Logger;
import org.bukkit.entity.Player;

public final class Bridge {

    private final Logger logger;
    private Object authMeApiInstance = null;
    private Method mIsAuthenticated = null;
    private Method mForceLogin = null;

    public Bridge(Logger logger) {
        this.logger = logger;
    }

    public boolean findAuthMeApi() {
        try {
            Class<?> c = Class.forName("fr.xephi.authme.api.v3.AuthMeApi");
            Method getInstance = c.getMethod("getInstance");
            Object instance = getInstance.invoke(null);
            Method isAuth = c.getMethod("isAuthenticated", Player.class);
            Method force = c.getMethod("forceLogin", Player.class);
            this.authMeApiInstance = instance;
            this.mIsAuthenticated = isAuth;
            this.mForceLogin = force;
            this.logger.info(
                "Found AuthMe API: fr.xephi.authme.api.v3.AuthMeApi"
            );
            return true;
        } catch (Throwable throwable) {
            try {
                Class<?> c = Class.forName("fr.xephi.authme.api.API");
                Method getInstance = c.getMethod("getInstance");
                Object instance = getInstance.invoke(null);
                Method isAuth = c.getMethod("isAuthenticated", Player.class);
                Method force = c.getMethod("forceLogin", Player.class);
                this.authMeApiInstance = instance;
                this.mIsAuthenticated = isAuth;
                this.mForceLogin = force;
                this.logger.info("Found AuthMe API: fr.xephi.authme.api.API");
                return true;
            } catch (Throwable throwable1) {
                try {
                    Class<?> main = Class.forName("fr.xephi.authme.AuthMe");
                    Method getInstance = main.getMethod("getInstance");
                    Object mainInstance = getInstance.invoke(null);
                    Method getAPI = main.getMethod("getAPI");
                    Object apiInstance = getAPI.invoke(mainInstance);
                    Class<?> apiClass = apiInstance.getClass();
                    Method isAuth = apiClass.getMethod(
                        "isAuthenticated",
                        Player.class
                    );
                    Method force = apiClass.getMethod(
                        "forceLogin",
                        Player.class
                    );
                    this.authMeApiInstance = apiInstance;
                    this.mIsAuthenticated = isAuth;
                    this.mForceLogin = force;
                    this.logger.info(
                        "Found AuthMe API via fr.xephi.authme.AuthMe.getInstance().getAPI()"
                    );
                    return true;
                } catch (Throwable throwable2) {
                    return false;
                }
            }
        }
    }

    public boolean isAuthenticated(Player p) {
        if (this.mIsAuthenticated == null) return false;
        try {
            Object res = this.mIsAuthenticated.invoke(
                this.authMeApiInstance,
                p
            );
            if (res instanceof Boolean) return (Boolean) res;
        } catch (Exception e) {
            this.logger.warning(
                "Error invoking isAuthenticated: " + e.getMessage()
            );
        }
        return false;
    }

    public void forceLogin(Player p) {
        if (this.mForceLogin == null) return;
        try {
            this.mForceLogin.invoke(this.authMeApiInstance, p);
        } catch (Exception e) {
            this.logger.warning("Error invoking forceLogin: " + e.getMessage());
        }
    }
}
