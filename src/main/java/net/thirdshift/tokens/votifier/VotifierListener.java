package net.thirdshift.tokens.votifier;

import com.vexsoftware.votifier.model.Vote;
import com.vexsoftware.votifier.model.VotifierEvent;
import net.thirdshift.tokens.Tokens;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.Objects;

public class VotifierListener {

	public void votifierEvent(Event param){
		VotifierEvent event = (VotifierEvent) param;
		Vote vote = event.getVote();
		Player player = Bukkit.getPlayer(vote.getUsername());
		if(player==null){
			for(OfflinePlayer offlinePlayer : Bukkit.getOfflinePlayers()){
				if (Objects.equals(offlinePlayer.getName(), vote.getUsername())){
					Tokens.getInstance().getLogger().info(vote.getUsername()+" is offline, but they're getting Tokens now.");
					Tokens.getInstance().getHandler().addTokens(offlinePlayer.getUniqueId(), 20);
					break;
				}
			}
			return;
		}
		Tokens.getInstance().getHandler().addTokens(player, 10);
		player.sendMessage("You voted on "+vote.getServiceName()+" and earned 10 Tokens!");
	}
}
