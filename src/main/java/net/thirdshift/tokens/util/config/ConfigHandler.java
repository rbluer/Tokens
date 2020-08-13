package net.thirdshift.tokens.util.config;

import net.thirdshift.tokens.Tokens;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigHandler {
	final Tokens plugin;
	final FileConfiguration config;

	public ConfigHandler(final Tokens plugin){
		this.plugin=plugin;
		config = plugin.getConfig();
	}

	public boolean getBoolean(final String path, final boolean defaultValue){
		if (config.contains(path)){
			return config.getBoolean(path);
		} else{
			config.addDefault(path, defaultValue);
			return defaultValue;
		}
	}

	public double getDouble(final String path, final double defaultValue){
		if (config.contains(path)){
			return config.getDouble(path);
		} else{
			config.addDefault(path, defaultValue);
			return defaultValue;
		}
	}

	public int getInt(final String path, final int defaultValue){
		if (config.contains(path)){
			return config.getInt(path);
		} else{
			config.addDefault(path, defaultValue);
			return defaultValue;
		}
	}

	public void readMySQL() {
		if (!config.contains("MySQL")){
			config.createSection("MySQL");
			config.addDefault("MySQL.Enabled", false);
			config.addDefault("MySQL.Username", "username");
			config.addDefault("MySQL.Password", "password");
			config.addDefault("MySQL.Database-Name", "DatabaseName");
			config.createSection("MySQL.Server");
			config.addDefault("MySQL.Server.SSL", false);
			config.addDefault("MySQL.Server.Port", 3306);
			config.addDefault("MySQL.Server.Address", "localhost");
		}
	}
}
