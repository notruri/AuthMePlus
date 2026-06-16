package one.ruri.authmeplus

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import org.bukkit.configuration.file.FileConfiguration
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.InetAddress
import java.net.URI
import java.nio.charset.StandardCharsets
import java.util.logging.Logger

object Utils {
    private val LEGACY = LegacyComponentSerializer.legacyAmpersand()

    fun getMessage(
        cfg: FileConfiguration,
        path: String,
        def: String,
    ): Component = LEGACY.deserialize(cfg.getString(path, def)!!)

    fun color(text: String): Component = LEGACY.deserialize(text)

    fun isIpSafe(
        address: InetAddress?,
        cfg: FileConfiguration,
    ): Boolean {
        if (address == null) return false
        if (address.isLoopbackAddress || address.isAnyLocalAddress) return false
        if (address.isSiteLocalAddress && !cfg.getBoolean("settings.allow_local_ips", false)) return false
        return true
    }

    fun checkUsernameIsPremium(
        logger: Logger,
        username: String,
    ): Int {
        var con: HttpURLConnection? = null
        try {
            val url = "https://api.mojang.com/users/profiles/minecraft/$username"
            con = URI(url).toURL().openConnection() as HttpURLConnection
            con.connectTimeout = 10000
            con.readTimeout = 10000
            con.requestMethod = "GET"
            con.setRequestProperty("User-Agent", "AuthMePlus/1.0")
            val code = con.responseCode
            if (code == 200) {
                BufferedReader(InputStreamReader(con.inputStream, StandardCharsets.UTF_8)).use { reader ->
                    val sb = StringBuilder()
                    var line: String?
                    while (reader.readLine().also { line = it } != null) sb.append(line)
                    return if (sb.isNotEmpty()) 1 else 0
                }
            }
            return 0
        } catch (e: Exception) {
            logger.warning("Error checking Mojang API for $username: ${e.message}")
            return -1
        } finally {
            con?.disconnect()
        }
    }
}
