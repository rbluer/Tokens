package net.thirdshift.tokens.util.config;

import net.thirdshift.tokens.Tokens;
import net.thirdshift.tokens.combatlogx.TokensCombatManager;
import net.thirdshift.tokens.commands.redeem.redeemcommands.FactionsRedeemModule;
import net.thirdshift.tokens.commands.redeem.redeemcommands.McMMORedeemModule;
import net.thirdshift.tokens.commands.redeem.redeemcommands.VaultRedeemModule;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class TokensConfigHandler {
	private boolean mySQLEnabled = false;
	private boolean sqlliteEnabled = true;
	private boolean isRunningMySQL = false;

	private boolean hasFactions = false;
	private boolean factionsEnabled = false;
	private boolean isRunningFactions = false;
	private int tokenToFactionPower;

	private boolean hasMCMMO = false;
	private boolean mcmmoEnabled = false;
	private boolean isRunningMCMMO = false;
	private int tokensToMCMMOLevels;

	private boolean hasCombatLogX = false;
	private boolean combatLogXEnabled = false;
	private boolean combatLogXBlockTokens = false;
	private boolean isRunningCombatLogX = false;
	private TokensCombatManager tokensCombatManager;

	private boolean hasVault = false;
	private boolean vaultEnabled = false;
	private boolean vaultBuy = false;
	private boolean vaultSell = false;
	private boolean isRunningVault = false;
	private double vaultBuyPrice;
	private double vaultSellPrice;

	private boolean updateCheckEnabled = true;
	private int updateDelay = 40;
	private int updateTaskID = -1;

	private final Tokens plugin;
	private ConfigHandler configHandler;

	public TokensConfigHandler(final Tokens plugin){
		this.plugin = plugin;
		configHandler = new ConfigHandler(plugin);
	}

	public void reloadConfig(){
		mySQLEnabled = configHandler.getBoolean("MySQL.Enabled", false);

		// vault related config options
		vaultEnabled = configHandler.getBoolean("VaultEco.Enabled", false);
		vaultBuy = configHandler.getBoolean("VaultEco.Buy-Tokens", false);
		vaultBuyPrice = configHandler.getDouble("VaultEco.Buy-Price", 1000);
		vaultSell = configHandler.getBoolean("VaultEco.Sell-Tokens", false);
		vaultSellPrice = configHandler.getDouble("VaultEco.Sell-Price", 1000);

		// factions related config options
		factionsEnabled = configHandler.getBoolean("Factions.Enabled", false);
		tokenToFactionPower = configHandler.getInt("Factions.Tokens-To-Power", 2);

		// combatlogx related config options
		combatLogXEnabled = configHandler.getBoolean("CombatLogX.Enabled", false);

		// mcmmo related config options
		mcmmoEnabled = configHandler.getBoolean("mcMMO.Enabled", false);
		tokensToMCMMOLevels = configHandler.getInt("mcMMO.Tokens-To-Levels", 1);

		// Auto-check update
		updateCheckEnabled = configHandler.getBoolean("UpdateCheck.Enabled", true);
		updateDelay = configHandler.getInt("UpdateCheck.Delay", 40);

		// MySQL Check
		if (mySQLEnabled) {
			if(plugin.getSqllite()!=null){
				plugin.nullSQLLite();
			}
			configHandler.readMySQL();
			sqlliteEnabled = false;
			plugin.mySQLWork();
			isRunningMySQL = true;
			plugin.getLogger().info("Storage Type: SQLLite | [ MySQL ]");
		} else {
			if(plugin.getMySQL()!=null){
				plugin.getMySQL().stopSQLConnection();
				plugin.nullMySQL();
			}
			isRunningMySQL = false;
			sqlliteEnabled = true;
			plugin.doSQLLiteWork();
			plugin.getLogger().info("Storage Type: [ SQLLite ] | MySQL ( Default )");
		}

		// Factions Check
		if (factionsEnabled) {
			Plugin factionsPlug = Bukkit.getPluginManager().getPlugin("Factions");
			if (factionsPlug != null && factionsPlug.isEnabled()) {
				hasFactions = true;
				isRunningFactions = true;
				plugin.getRedeemCommandExecutor().registerRedeemModule(new FactionsRedeemModule());
			} else if (factionsPlug == null || !factionsPlug.isEnabled()) {
				plugin.getLogger().warning("Factions addon is enabled but Factions is not installed on the server!");
				isRunningFactions = false;
			}
		} else {
			isRunningFactions = false;
		}

		// Vault Check
		if (vaultEnabled) {
			Plugin vaultPlug = Bukkit.getPluginManager().getPlugin("Vault");
			if (vaultPlug != null && vaultPlug.isEnabled()) {
				hasVault = true;
				plugin.getRedeemCommandExecutor().registerRedeemModule(new VaultRedeemModule());
				plugin.vaultIntegration();
			} else if (vaultPlug == null || !vaultPlug.isEnabled()) {
				isRunningVault = false;
				plugin.getLogger().warning("Vault addon is enabled but Vault is not installed on the server!");
			}
		} else {
			isRunningVault = false;
		}

		// CombatLogX Check
		if (combatLogXEnabled) {
			Plugin combPlug = Bukkit.getPluginManager().getPlugin("CombatLogX");
			if (combPlug != null && combPlug.isEnabled()) {
				hasCombatLogX = true;
				isRunningCombatLogX = true;
				if (tokensCombatManager==null)
					tokensCombatManager = new TokensCombatManager(this);
			} else if (combPlug == null || !combPlug.isEnabled()) {
				isRunningCombatLogX = false;
				plugin.getLogger().warning("CombatLogX addon is enabled but CombatLogX is not installed on the server!");
			}
		} else {
			isRunningCombatLogX = false;
		}

		// mcMMO Check
		if (mcmmoEnabled) {
			Plugin mcmmoPlug = Bukkit.getPluginManager().getPlugin("mcMMO");
			if (mcmmoPlug != null && mcmmoPlug.isEnabled()) {
				hasMCMMO = true;
				isRunningMCMMO = true;
				plugin.getRedeemCommandExecutor().registerRedeemModule(new McMMORedeemModule());
			} else if (mcmmoPlug == null || !mcmmoPlug.isEnabled()) {
				isRunningMCMMO = false;
				plugin.getLogger().warning("mcMMO addon is enabled but mcMMO is not installed on the server!");
			}
		} else {
			isRunningMCMMO = false;
		}

		// Prevents people like https://www.spigotmc.org/members/jcv.510317/ saying the plugin is broken <3
		if (!mcmmoEnabled && !factionsEnabled && !vaultEnabled) {
			plugin.getLogger().warning("You don't have any supported plugins enabled.");
		}

		if (updateCheckEnabled) {
			// Auto-check updates related code
			Runnable runnable = plugin::checkForUpdates;

			// Initial check for updates, then schedule one once every 20 minutes
			final int minutesDelay = updateDelay * 60 * 20;
			updateTaskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, runnable, 0, minutesDelay);
			if (updateTaskID == -1) {
				plugin.getLogger().warning("Couldn't schedule an auto-update check!");
			}
		} else if(updateTaskID != -1){
			if (Bukkit.getScheduler().isQueued(updateTaskID)){
				Bukkit.getScheduler().cancelTask(updateTaskID);
			}
			updateTaskID = -1;
		}
	}

	public boolean isRunningMySQL(){
		return mySQLEnabled;
	}

	public boolean isRunningMCMMO() {
		return isRunningMCMMO;
	}

	public int getTokensToMCMMOLevels() {
		return tokensToMCMMOLevels;
	}

	public boolean isRunningVault() {
		return isRunningVault;
	}

	public boolean isRunningFactions() {
		return isRunningFactions;
	}

	public int getTokenToFactionPower() {
		return tokenToFactionPower;
	}

	public boolean isRunningCombatLogX() {
		return isRunningCombatLogX;
	}

	public boolean isVaultBuy() {
		return vaultBuy;
	}

	public boolean isVaultSell() {
		return vaultSell;
	}

	public double getVaultBuyPrice() {
		return vaultBuyPrice;
	}

	public double getVaultSellPrice() {
		return vaultSellPrice;
	}

	public TokensCombatManager getTokensCombatManager() {
		return tokensCombatManager;
	}
}
