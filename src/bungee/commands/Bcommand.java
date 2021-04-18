package bungee.commands;

import java.util.List;

import bungee.main.BAipp;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class Bcommand extends Command{

	public Bcommand() {
		super("aipp");
	}

	private BAipp plugin = BAipp.getInstance();
	
	public void execute(CommandSender sender, String[] args) {
		if(!(sender instanceof ProxiedPlayer)) {
			plugin.getProxy().getConsole().sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', "&cThis command cannot be run from the console")));
		}else { 
			ProxiedPlayer player = (ProxiedPlayer) sender;
			if(args.length > 0) {
				try {
					if(args[0].equalsIgnoreCase("add")) {
						String name = args[2];
						String ip = args[3];
						if(player.hasPermission(plugin.getConfig().getString("Config.Permissions.add")) || player.hasPermission(plugin.getConfig().getString("Config.Permissions.all"))) {
							if(args.length == 4) {
								if(BungeeCord.getInstance().getPlayer(name) == null) {
									if(args[1].equalsIgnoreCase("account")) {
										if(!plugin.getPlayers().contains("Players.Accountsprotected." + name)) {
											List<String> ips = plugin.getPlayers().getStringList("Players.Accountsprotected." + name);
											ips.add(ip);
											plugin.getPlayers().set("Players.Accountsprotected." + name, ips);
											plugin.saveConfig();
											List<String> l = plugin.getMessages().getStringList("Messages.Add.Account");
											for(int i=0;i<l.size();i++) {
												String text = l.get(i);
												player.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', text).replaceAll("%account%", name).replaceAll("%ip%", ip)));
												plugin.saveConfig();
											}
										}else {
											player.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', "&fThis account is already &aprotected")));
										}
									}else if(args[1].equalsIgnoreCase("ip")) {
										if(plugin.getPlayers().contains("Players.Accountsprotected." + name)) {
											List<String> ips = plugin.getPlayers().getStringList("Players.Accountsprotected." + name);
											if(!ips.contains(ip)) {
												ips.add(ip);
												plugin.getPlayers().set("Players.Accountsprotected." + name, ips);
												plugin.saveConfig();
												List<String> l = plugin.getMessages().getStringList("Messages.Add.IP");
												for(int i=0;i<l.size();i++) {
													String text = l.get(i);
													player.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', text).replaceAll("%account%", name).replaceAll("%ip%", ip)));
													plugin.saveConfig();
												}
											}else{
												player.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', " &fIP &e" + ip + " &fis already protecting the &e" + name + " &faccount.")));
											}
										}else {
											player.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', "&fThis account is already &aprotected")));
										}							
									}else {
										player.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', "&fThis command no &cexist&f, run the &b/aipp &fcommand to see all the plugin commands")));
									}
								}else {
									player.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', " &fThis player is connected. It is mandatory that the user is disconnected")));
								}
							}else {
								player.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', "&fThe command is &7misspelled&f, run the &b/aipp &fcommand to see all the plugin commands")));	
							}
						}else {
							List<String> l = plugin.getMessages().getStringList("Messages.NoPermission");
							for(int i=0;i<l.size();i++) {
								String text = l.get(i);
								player.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', text)));
								plugin.saveConfig();
							}
						}
					}else if(args[0].equalsIgnoreCase("remove")) {		
						if(player.hasPermission(plugin.getConfig().getString("Config.Permissions.remove")) || player.hasPermission(plugin.getConfig().getString("Config.Permissions.all"))) {
							if(args[1].equalsIgnoreCase("account")) {
								String name = args[2];
								if(args.length == 3) {
									if(plugin.getPlayers().contains("Players.Accountsprotected." + name)) {
										plugin.getPlayers().set("Players.Accountsprotected." + name, null);
										plugin.saveConfig();	
										List<String> l = plugin.getMessages().getStringList("Messages.Remove.Account");
										for(int i=0;i<l.size();i++) {
											String text = l.get(i);
											player.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', text).replaceAll("%account%", name)));
											plugin.saveConfig();
										}
									}else {
										player.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', "&e" + name + "&f account was &cnever protected")));
									}	
								}else {
									player.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', "&fThe command is &7misspelled&f, run the &b/aipp &fcommand to see all the plugin commands")));
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
											plugin.saveConfig();
											List<String> l = plugin.getMessages().getStringList("Messages.Remove.IP");
											for(int i=0;i<l.size();i++) {
												String text = l.get(i);
												player.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', text).replaceAll("%account%", name).replaceAll("%ip%", ip)));
												plugin.saveConfig();
											}
										}else{
											player.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', " &fThe IP &e" + ip + " &fis &cnot protecting &fthis account, you cannot delete the ip of that account")));
										}
									}else {
										player.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', "&e" + name + "&f account was &cnever protected")));
									}	
								}else {
									player.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', "&fThe command is &7misspelled&f, run the &b/aipp &fcommand to see all the plugin commands")));	
								}
							}else {
								player.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', "&fThis command no &cexist&f, run the &b/aipp &fcommand to see all the plugin commands")));
							}
						}else {
							List<String> l = plugin.getMessages().getStringList("Messages.NoPermission");
							for(int i=0;i<l.size();i++) {
								String text = l.get(i);
								player.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', text)));
								plugin.saveConfig();
							}
						}
					}else if(args[0].equalsIgnoreCase("check")) {
						if(player.hasPermission(plugin.getConfig().getString("Config.Permissions.check")) || player.hasPermission(plugin.getConfig().getString("Config.Permissions.all"))) {
							if(args.length == 2) {
								String name = args[1];
								if(plugin.getPlayers().contains("Players.Accountsprotected." + name)) {
									List<String> l = plugin.getMessages().getStringList("Messages.Check.Protected");
									List<String> ips = plugin.getPlayers().getStringList("Players.Accountsprotected." + name);
									for(int i=0;i<l.size();i++) {
										String text = l.get(i);
										for(int c=0;c<ips.size();c++) {
											String lip = ips.get(c);
											player.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', text).replaceAll("%iplist%", lip).replaceAll("%account%", name)));
										}
									}
								}else {
									List<String> l = plugin.getMessages().getStringList("Messages.Check.NotProtected");
									for(int i=0;i<l.size();i++) {
										String text = l.get(i);
										List<String> ips = plugin.getPlayers().getStringList("Players.Accountsprotected." + name);
										for(int c=0;c<ips.size();c++) {
											String lip = ips.get(c);
											player.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', text).replaceAll("%iplist%", lip).replaceAll("%account%", name)));
											plugin.saveConfig();
										}
									}
								}
							}else {
								player.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', "&fThe command is &7misspelled&f, run the &b/aipp &fcommand to see all the plugin commands")));
							}
						}else {
							List<String> l = plugin.getMessages().getStringList("Messages.NoPermission");
							for(int i=0;i<l.size();i++) {
								String text = l.get(i);
								player.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', text)));
								plugin.saveConfig();
							}
						}
					}else if(args[0].equalsIgnoreCase("reload")) {
						if(player.hasPermission(plugin.getConfig().getString("Config.Permissions.reload")) || player.hasPermission(plugin.getConfig().getString("Config.Permissions.all"))) {
							if(args.length == 1) {
								plugin.reloadConfig();
								plugin.saveConfig();
								List<String> l = plugin.getMessages().getStringList("Messages.Reload");
								for(int i=0;i<l.size();i++) {
									String text = l.get(i);
									player.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', text)));
								}
							}else {
								player.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', "&fThe command is &7misspelled&f, run the &b/aipp &fcommand to see all the plugin commands")));
							}
						}else {
							List<String> l = plugin.getMessages().getStringList("Messages.NoPermission");
							for(int i=0;i<l.size();i++) {
								String text = l.get(i);
								player.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', text)));
								plugin.saveConfig();
							}
						}
					}else {
						player.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', "&fThis command no &cexist&f, run the &b/aipp &fcommand to see all the plugin commands")));
					}
				}catch(ArrayIndexOutOfBoundsException e) {
					player.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', "&fThe command is &7misspelled&f, run the &b/aipp &fcommand to see all the plugin commands")));
				}				
			}else {
				if(player.hasPermission(plugin.getConfig().getString("Config.Permissions.notify")) || player.hasPermission(plugin.getConfig().getString("Config.Permissions.remove")) || player.hasPermission(plugin.getConfig().getString("Config.Permissions.check")) || player.hasPermission(plugin.getConfig().getString("Config.Permissions.reload")) || player.hasPermission(plugin.getConfig().getString("Config.Permissions.add")) || player.hasPermission(plugin.getConfig().getString("Config.Permissions.all"))) {
					List<String> l = plugin.getMessages().getStringList("Messages.AIPP");
					for(int i=0;i<l.size();i++) {
						String text = l.get(i);
						player.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', text)));
						plugin.saveConfig();
					}
				}else {
					List<String> l = plugin.getMessages().getStringList("Messages.NoPermission");
					for(int i=0;i<l.size();i++) {
						String text = l.get(i);
						player.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', text)));
						plugin.saveConfig();
					}
				}
			}
		}
	}
}
