package tm.aipp.commands;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import tm.aipp.main.SAipp;
import net.md_5.bungee.api.ChatColor;

public class Scommand implements CommandExecutor{
	
	private SAipp plugin;
	
	public Scommand(SAipp plugin){
		this.plugin = plugin;
	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(!(sender instanceof Player)) {
			Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&cThis command cannot be run from the console"));
			return false;
		}else {
			Player player = (Player) sender;
			if(args.length > 0) {
				try {
					if(args[0].equalsIgnoreCase("add")) {
						String name = args[2];
						String ip = args[3];
						if(player.hasPermission(plugin.getConfig().getString("Config.Permissions.add")) || player.hasPermission(plugin.getConfig().getString("Config.Permissions.all"))) {
							if(args.length == 4) {
								if(Bukkit.getPlayer(name) == null) {
									if(args[1].equalsIgnoreCase("account")) {
										if(!plugin.getPlayers().contains("Players.Accountsprotected." + name)) {
											List<String> ips = plugin.getPlayers().getStringList("Players.Accountsprotected." + name);
											ips.add(ip);
											plugin.getPlayers().set("Players.Accountsprotected." + name, ips);
											plugin.savePlayers();
											List<String> l = plugin.getMessages().getStringList("Messages.Add.Account");
											for(int i=0;i<l.size();i++) {
												String text = l.get(i);
												player.sendMessage(ChatColor.translateAlternateColorCodes('&', text).replaceAll("%account%", name).replaceAll("%ip%", ip));
											}
										}else {
											player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&fThis account is already &aprotected"));
											return false;
										}
									}else if(args[1].equalsIgnoreCase("ip")) {
										if(plugin.getPlayers().contains("Players.Accountsprotected." + name)) {
											List<String> ips = plugin.getPlayers().getStringList("Players.Accountsprotected." + name);
											if(!ips.contains(ip)) {
												ips.add(ip);
												plugin.getPlayers().set("Players.Accountsprotected." + name, ips);
												plugin.savePlayers();
												List<String> l = plugin.getMessages().getStringList("Messages.Add.IP");
												for(int i=0;i<l.size();i++) {
													String text = l.get(i);
													player.sendMessage(ChatColor.translateAlternateColorCodes('&', text).replaceAll("%account%", name).replaceAll("%ip%", ip));
												}
											}else{
												player.sendMessage(ChatColor.translateAlternateColorCodes('&', " &fIP &e" + ip + " &fis already protecting the &e" + name + " &faccount."));
												return false;
											}
										}else {
											player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&fThis account is already &aprotected"));
											return false;
										}							
									}else {
										player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&fThis command no &cexist&f, run the &b/aipp &fcommand to see all the plugin commands"));
										return false;
									}
								}else {
									player.sendMessage(ChatColor.translateAlternateColorCodes('&', " &fThis player is connected. It is mandatory that the user is disconnected"));
									return false;
								}
							}else {
								player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&fThe command is &7misspelled&f, run the &b/aipp &fcommand to see all the plugin commands"));	
								return false;
							}
						}else {
							List<String> l = plugin.getMessages().getStringList("Messages.NoPermission");
							for(int i=0;i<l.size();i++) {
								String text = l.get(i);
								player.sendMessage(ChatColor.translateAlternateColorCodes('&', text));
							}
							return false;
						}
					}else if(args[0].equalsIgnoreCase("remove")) {		
						if(player.hasPermission(plugin.getConfig().getString("Config.Permissions.remove")) || player.hasPermission(plugin.getConfig().getString("Config.Permissions.all"))) {
							if(args[1].equalsIgnoreCase("account")) {
								String name = args[2];
								if(args.length == 3) {
									if(plugin.getPlayers().contains("Players.Accountsprotected." + name)) {
										plugin.getPlayers().set("Players.Accountsprotected." + name, null);
										plugin.savePlayers();	
										List<String> l = plugin.getMessages().getStringList("Messages.Remove.Account");
										for(int i=0;i<l.size();i++) {
											String text = l.get(i);
											player.sendMessage(ChatColor.translateAlternateColorCodes('&', text).replaceAll("%account%", name));
										}
									}else {
										player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&e" + name + "&f account was &cnever protected"));
										return false;
									}	
								}else {
									player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&fThe command is &7misspelled&f, run the &b/aipp &fcommand to see all the plugin commands"));
									return false;
								}
							}else if(args[1].equalsIgnoreCase("ip")) {
								String name = args[2];
								String ip = args[3];
								if(args.length == 4) {
									if(plugin.getPlayers().contains("Players.Accountsprotected." + name)) {
										List<String> ips = plugin.getPlayers().getStringList("Players.Accountsprotected." + name);
										if(ips.contains(ip)) {
											ips.remove(ip);
											plugin.getPlayers().set("Players.Accountsprotected." + name, ips);
											plugin.savePlayers();
											List<String> l = plugin.getMessages().getStringList("Messages.Remove.IP");
											for(int i=0;i<l.size();i++) {
												String text = l.get(i);
												player.sendMessage(ChatColor.translateAlternateColorCodes('&', text).replaceAll("%account%", name).replaceAll("%ip%", ip));
											}
										}else{
											player.sendMessage(ChatColor.translateAlternateColorCodes('&', " &fThe IP &e" + ip + " &fis &cnot protecting &fthis account, you cannot delete the ip of that account"));
											return false;
										}
									}else {
										player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&e" + name + "&f account was &cnever protected"));
										return false;
									}	
								}else {
									player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&fThe command is &7misspelled&f, run the &b/aipp &fcommand to see all the plugin commands"));	
									return false;
								}
							}else {
								player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&fThis command no &cexist&f, run the &b/aipp &fcommand to see all the plugin commands"));
								return false;
							}
						}else {
							List<String> l = plugin.getMessages().getStringList("Messages.NoPermission");
							for(int i=0;i<l.size();i++) {
								String text = l.get(i);
								player.sendMessage(ChatColor.translateAlternateColorCodes('&', text));
							}
							return false;
						}
					}else if(args[0].equalsIgnoreCase("check")) {
						if(player.hasPermission(plugin.getConfig().getString("Config.Permissions.check")) || player.hasPermission(plugin.getConfig().getString("Config.Permissions.all"))) {
							if(args.length == 2) {
								String name = args[1];
								if(plugin.getPlayers().contains("Players.Accountsprotected." + name)) {
									List<String> l = plugin.getMessages().getStringList("Messages.Check.Protected");
									for(int i=0;i<l.size();i++) {
										String text = l.get(i);
										List<String> ips = plugin.getPlayers().getStringList("Players.Accountsprotected." + name);
										for(int c=0;c<ips.size();c++) {
											String lip = ips.get(c);
											player.sendMessage(ChatColor.translateAlternateColorCodes('&', text).replaceAll("%iplist%", lip).replaceAll("%account%", name));
										}
									}
								}else {
									List<String> l = plugin.getMessages().getStringList("Messages.Check.NotProtected");
									for(int i=0;i<l.size();i++) {
										String text = l.get(i);
										List<String> ips = plugin.getPlayers().getStringList("Players.Accountsprotected." + name);
										for(int c=0;c<ips.size();c++) {
											String lip = ips.get(c);
											player.sendMessage(ChatColor.translateAlternateColorCodes('&', text).replaceAll("%iplist%", lip).replaceAll("%account%", name));
										}
									}
									return false;
								}
							}else {
								player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&fThe command is &7misspelled&f, run the &b/aipp &fcommand to see all the plugin commands"));
								return false;
							}
						}else {
							List<String> l = plugin.getMessages().getStringList("Messages.NoPermission");
							for(int i=0;i<l.size();i++) {
								String text = l.get(i);
								player.sendMessage(ChatColor.translateAlternateColorCodes('&', text));
							}
							return false;
						}
					}else if(args[0].equalsIgnoreCase("reload")) {
						if(player.hasPermission(plugin.getConfig().getString("Config.Permissions.reload")) || player.hasPermission(plugin.getConfig().getString("Config.Permissions.all"))) {
							if(args.length == 1) {
								plugin.reloadConfig();
								plugin.saveConfig();
								plugin.reloadPlayers();
								plugin.savePlayers();
								plugin.reloadMessages();
								plugin.saveMessages();
								List<String> l = plugin.getMessages().getStringList("Messages.Reload");
								for(int i=0;i<l.size();i++) {
									String text = l.get(i);
									player.sendMessage(ChatColor.translateAlternateColorCodes('&', text));
								}
							}else {
								player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&fThe command is &7misspelled&f, run the &b/aipp &fcommand to see all the plugin commands"));
								return false;
							}
						}else {
							List<String> l = plugin.getMessages().getStringList("Messages.NoPermission");
							for(int i=0;i<l.size();i++) {
								String text = l.get(i);
								player.sendMessage(ChatColor.translateAlternateColorCodes('&', text));
							}
							return false;
						}
					}else {
						player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&fThis command no &cexist&f, run the &b/aipp &fcommand to see all the plugin commands"));
						return false;
					}
				}catch(ArrayIndexOutOfBoundsException e) {
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&fThe command is &7misspelled&f, run the &b/aipp &fcommand to see all the plugin commands"));
					return false;
				}				
			}else {
				if(player.hasPermission(plugin.getConfig().getString("Config.Permissions.notify")) || player.hasPermission(plugin.getConfig().getString("Config.Permissions.remove")) || player.hasPermission(plugin.getConfig().getString("Config.Permissions.check")) || player.hasPermission(plugin.getConfig().getString("Config.Permissions.reload")) || player.hasPermission(plugin.getConfig().getString("Config.Permissions.add")) || player.hasPermission(plugin.getConfig().getString("Config.Permissions.all"))) {
					List<String> l = plugin.getMessages().getStringList("Messages.AIPP");
					for(int i=0;i<l.size();i++) {
						String text = l.get(i);
						player.sendMessage(ChatColor.translateAlternateColorCodes('&', text));
					}
				}else {
					List<String> l = plugin.getMessages().getStringList("Messages.NoPermission");
					for(int i=0;i<l.size();i++) {
						String text = l.get(i);
						player.sendMessage(ChatColor.translateAlternateColorCodes('&', text));
					}
					return false;
				}
			}
			return true;
		}
	}
}