package net.ryanmck.hiddenchat;

import java.util.concurrent.Callable;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ServerChatPlayerListener implements Listener 
{
	private final HiddenChat plugin;	
	
	ServerChatPlayerListener(HiddenChat plugin) {
        this.plugin = plugin;
    }
	
	@EventHandler
	public void onPlayerChat(final AsyncPlayerChatEvent event) {
		String message = event.getMessage();
		
		if(message.startsWith("P ")) {
			plugin.getServer().getScheduler().callSyncMethod(plugin, new Callable<Void>() {
				@Override
				public Void call() {
					String message = String.format(event.getFormat(), event.getPlayer().getDisplayName(), event.getMessage());
					for(Player p : plugin.getServer().getOnlinePlayers()) {
						p.sendMessage(message);
					}
					return null;
				}
			});
			event.setCancelled(true);
		}
	}
}
