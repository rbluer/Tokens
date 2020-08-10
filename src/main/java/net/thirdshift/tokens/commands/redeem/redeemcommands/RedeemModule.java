package net.thirdshift.tokens.commands.redeem.redeemcommands;

import net.thirdshift.tokens.Tokens;
import net.thirdshift.tokens.TokensHandler;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public abstract class RedeemModule {
	protected Tokens plugin;
	protected TokensHandler tokensHandler;
	protected String command;

	public RedeemModule() {
		plugin = Tokens.getInstance();
		tokensHandler = plugin.getHandler();
	}

	public abstract String getCommand();

	/**
	 * Used to provide aliases to the main command
	 * @return Aliases of the main command
	 */
	public abstract String[] getCommandAliases();

	/**
	 * Used to inform the player how to use the command
	 * @return How to use the command
	 */
	public abstract String getCommandUsage();

	/**
	 * This is ran when a player uses /redeem 'command'
	 * @param player Target player
	 * @param args Array of arguments sent with the command
	 */
	public abstract void redeem(final Player player, final ArrayList<String> args);
}
