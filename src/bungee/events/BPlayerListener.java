package bungee.events;

import java.util.List;
import java.util.UUID;

import bungee.main.BAipp;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class BPlayerListener implements Listener{

	private BAipp plugin = BAipp.getInstance();
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void toEnter(PostLoginEvent event) {
		UUID uuid = event.getPlayer().getUniqueId();
		ProxiedPlayer p = plugin.getProxy().getPlayer(uuid);
		String name = p.getName();
		String ip = p.getAddress().getHostString();
		 
		 if(plugin.getPlayers().contains("Players.Accountsprotected." + name)) {
			List<String> ips = plugin.getPlayers().getStringList("Players.Accountsprotected." + name );
			if(!ips.contains(ip)) {				
				List<String> cmd = plugin.getMessages().getStringList("Messages.Warning.Console");
				for(int i=0;i<cmd.size();i++) {
					String text = cmd.get(i);
					plugin.getProxy().getConsole().sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', text.replaceAll("%ip%", ip).replaceAll("%account%", name))));
				}
				
		        for(ProxiedPlayer player : BungeeCord.getInstance().getPlayers()) {
		        	if(player.hasPermission(plugin.getConfig().getString("Config.Permissions.notify")) || player.hasPermission(plugin.getConfig().getString("Config.Permissions.all"))) {
		        		List<String> message = plugin.getMessages().getStringList("Messages.Warning.Message");
		                for(int i=0;i<message.size();i++) {
		                	String text = message.get(i);
		                	player.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', text).replaceAll("%account%", name).replaceAll("%ip%", ip)));
		                }
		                
		                ProxyServer.getInstance().createTitle()
		                .title(new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', plugin.getMessages().getString("Messages.Warning.Title").replaceAll("%account%", name).replaceAll("%ip%", ip)))
		                	.create())
		                	.subTitle(new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', plugin.getMessages().getString("Messages.Warning.SubTitle").replaceAll("%account%", name).replaceAll("%ip%", ip)))
		                	.create())
		                	.fadeIn(5)
		                	.stay(60)
		                	.fadeOut(20)
		                .send(player);
		        	}
		        }

		        List<String> km = plugin.getMessages().getStringList("Messages.Kick");
		        String str = "";
				for (String kmt : km) {
					str += kmt +"\n";
				}
				event.getPlayer().disconnect(new TextComponent(ChatColor.translateAlternateColorCodes('&', str)));
			}
		}
	}
}
