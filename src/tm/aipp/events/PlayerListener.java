package tm.aipp.events;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent.Result;

import tm.aipp.main.SAipp;
import tm.aipp.nms.Title;

public class PlayerListener implements Listener{
	
	private SAipp plugin;
	
	public PlayerListener(SAipp plugin){
		this.plugin = plugin;
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void toEnter(AsyncPlayerPreLoginEvent event) {
		FileConfiguration players = plugin.getPlayers();
		String name = event.getName();
		String ip = event.getAddress().getHostAddress();	
		Title title = plugin.getTitles();
		String str = "";
		
		if(players.contains("Players.Accountsprotected." + name)) {
			List<String> ips = players.getStringList("Players.Accountsprotected." + name);	
			if(!ips.contains(ip)) {
				event.setLoginResult(Result.KICK_BANNED);
				
				List<String> km = plugin.getMessages().getStringList("Messages.Kick");
				for (String kmt : km) {
					str += kmt +"\n";
				}			
				event.setKickMessage(ChatColor.translateAlternateColorCodes('&', str));		
				
				List<String> cmd = plugin.getMessages().getStringList("Messages.Warning.Console");
				for(int i=0;i<cmd.size();i++) {
					String text = cmd.get(i);
					Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', text.replaceAll("%ip%", ip).replaceAll("%account%", name)));
				}
				
				for(Player p : Bukkit.getOnlinePlayers()) {
					if(p.isOp() || p.hasPermission(plugin.getConfig().getString("Config.Permissions.notify")) || p.hasPermission(plugin.getConfig().getString("Config.Permissions.all"))) {
						List<String> message = plugin.getMessages().getStringList("Messages.Warning.Message");	
						for(int i=0;i<message.size();i++) {
							String text = message.get(i);
							p.sendMessage(ChatColor.translateAlternateColorCodes('&', text).replaceAll("%account%", name).replaceAll("%ip%", ip));		
						}
						
						title.sendTitle(p, ChatColor.translateAlternateColorCodes('&', plugin.getMessages().getString("Messages.Warning.Title").replaceAll("%account%", name).replaceAll("%ip%", ip)), ChatColor.translateAlternateColorCodes('&', plugin.getMessages().getString("Messages.Warning.SubTitle").replaceAll("%account%", name).replaceAll("%ip%", ip)));
						
						String sound = plugin.getMessages().getString("Messages.Warning.Sound");
						String[] separes = sound.split(";");
						
						try {
							int volume = Integer.valueOf(separes[1]);
							float pitch = Float.valueOf(separes[2]);
							Sound sounds = Sound.valueOf(separes[0]);
							p.playSound(p.getLocation(), sounds, volume, pitch);	
							
						}catch(IllegalArgumentException e){
							Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&c--------------------------------------"));
							Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&4ERROR! &fthe " + separes[0] +" sound is invalid "));
							Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&c--------------------------------------"));
							
							p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c--------------------------------------"));
							p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4ERROR! &fthe " + separes[0] +" sound is invalid "));
							p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c--------------------------------------"));
							return;
						}						
					}
				}
			}
		}
	}	
}