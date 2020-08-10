package net.thirdshift.tokens.commands.redeem.redeemcommands;

import net.thirdshift.tokens.messages.messageData.PlayerSender;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import plus.crates.Handlers.CrateHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CratesPlusRedeemModule extends RedeemModule{
	private final HashMap<String, Integer> crates;
	private final CrateHandler crateHandler;

	public CratesPlusRedeemModule(HashMap<String, Object> crates,final CrateHandler crateHandler) {
		this.crates = new HashMap<>();
		for(String crateName : crates.keySet()){
			this.crates.put(crateName, (Integer) crates.get(crateName));
		}
		this.crateHandler = crateHandler;
	}

	@Override
	public String getCommand() {
		return "crate";
	}

	@Override
	public String[] getCommandAliases() {
		return new String[]{"crates", "cratesplus"};
	}

	@Override
	public String getCommandUsage() {
		StringBuilder usage = new StringBuilder("/redeem crate <");
		for(String string : crates.keySet()){
			usage.append(string);
			usage.append(" ");
		}
		usage.append(">");
		return usage.toString();
	}

	@Override
	public void redeem(Player player, ArrayList<String> args) {
		List<Object> objects = new ArrayList<>();
		if (args.size()!=1){
			objects.add(new PlayerSender(player));
			objects.add(getCommandUsage());
			player.sendMessage(plugin.messageHandler.useMessage("tokens.errors.invalid-command.correction", objects));
			return;
		}

		String crateName = args.get(0);
		if(crates.containsKey(crateName)){
			int cost = crates.get(crateName);
			objects.add(cost);
			objects.add(new PlayerSender(player));
			if (tokensHandler.hasTokens(player, cost)){
				tokensHandler.removeTokens(player, cost);
				crateHandler.giveCrateKey(player, crateName, 1, false, false);
				player.sendMessage("Successfully redeemed crate" + crateName + " for " + ChatColor.GOLD + cost + " Tokens");
			}else{
				player.sendMessage(plugin.messageHandler.useMessage("redeem.errors.not-enough", objects));
			}
		} else {
			player.sendMessage(crateName + " is either not a crate, or can't be redeemed with " + ChatColor.GOLD + "Tokens");
		}
	}
}
