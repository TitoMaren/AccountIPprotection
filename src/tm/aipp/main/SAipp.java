package tm.aipp.main;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import tm.aipp.commands.Scommand;
import tm.aipp.events.PlayerListener;
import tm.aipp.nms.*;

public class SAipp extends JavaPlugin{
	
	public String routeConfig;
	public FileConfiguration players, messages;
	public File playersfile, messagesfile;
	private PluginDescriptionFile pdffile = getDescription();
	public String version = ChatColor.LIGHT_PURPLE + pdffile.getVersion();
	public String latestversion;
	public String name = ChatColor.DARK_AQUA + "[" + pdffile.getName() + "]";
	private Title titles;
	public Title getTitles() {
	    return titles;
	}
	
	public void onEnable(){
		Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&4<&c----------------------------------------------------&4>"));
		Bukkit.getConsoleSender().sendMessage(" ");
		Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', " "+ name + "&f Has been enable &8version: " + version + " "));
		Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "     &6Spigot"));
		Bukkit.getConsoleSender().sendMessage(" ");
		Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&4<&c----------------------------------------------------&4>"));
		registerConfig();
		registerPlayers();
		registerMessages();
		registerEvents();
		registerCommands();
		if (setupTitle()) {
			
		}
	}
	
	public void onDisable(){
		Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&4<&c----------------------------------------------------&4>"));
		Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', " "));
		Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', " " + name + "&f Has been disable &8version: " + version + " "));
		Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "     &6Spigot"));
		Bukkit.getConsoleSender().sendMessage(" ");
		Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&4<&c----------------------------------------------------&4>"));
		
	}
	
	public void registerConfig() {
		File config = new File(this.getDataFolder(), "config.yml");
		routeConfig = config.getPath();
		if(!config.exists()) {
			this.getConfig().options().copyDefaults(true);
			saveConfig();
		}
	}
	
	public void registerPlayers() {
		
		FileConfiguration config = getConfig();
		
		if (!getDataFolder().exists()) {
			getDataFolder().mkdir();
		}

		playersfile = new File(getDataFolder(), "players.yml");

		if (!playersfile.exists()) {
			try {
				playersfile.createNewFile();
			} catch (IOException e) {
				Bukkit.getServer().getConsoleSender()
						.sendMessage(ChatColor.RED + "Could not create the players.yml file");
			}
		}
		players = YamlConfiguration.loadConfiguration(playersfile);

		if(config.contains("Config.Accountsprotected")) {
			for(String keys : config.getConfigurationSection("Config.Accountsprotected").getKeys(true)) {
				List<String> ips = config.getStringList("Config.Accountsprotected." + keys);
				players.set("Players.Accountsprotected." + keys, ips);
				savePlayers();				
			}
			
			config.set("Config.Accountsprotected", null);
			saveConfig();
		}
	}
	
	public void registerMessages() {
		
		FileConfiguration config = getConfig();
		
		if (!getDataFolder().exists()) {
			getDataFolder().mkdir();
		}

		messagesfile = new File(getDataFolder(), "messages.yml");

		if (!messagesfile.exists()) {
			try {
				messagesfile.createNewFile();
			} catch (IOException e) {
				Bukkit.getServer().getConsoleSender()
						.sendMessage(ChatColor.RED + "Could not create the messages.yml file");
			}
		}
		
		messages = YamlConfiguration.loadConfiguration(messagesfile);
		
		//config
		
		if(config.contains("Config.Messages")) {
        	config.set("Config.Messages", null);
            saveConfig();
        }
        
        if(config.contains("Config.Titles")) {
        	config.set("Config.Titles", null);
            saveConfig();
        }
        
        if(!config.contains("Config.Permissions")) {
        	config.set("Config.Permissions.all", "aipp.all");
        	config.set("Config.Permissions.notify", "aipp.notify");
        	config.set("Config.Permissions.add", "aipp.add");
        	config.set("Config.Permissions.remove", "aipp.remove");
        	config.set("Config.Permissions.check", "aipp.check");
        	config.set("Config.Permissions.reload", "aipp.reload");
        	saveConfig();
        }
        
        //messages
		
		if(messages.contains("Messages.Kick")) {
         	messages.set("Messages.Kick-Message", null);
         	saveConfig();
         }
		
		if(!messages.contains("Messages.Kick")) {
	       	 List<String> list = getMessages().getStringList("Messages.Kick");
	       	 list.add("      &cYou kicked from the server");
	       	 list.add("");
	       	 list.add("&f&m                                              ");
	       	 list.add("");
	       	 list.add("  &cby : &fConsole");
	       	 list.add("  &creason : &fYou account is protected");
	       	 list.add("");
	       	 list.add("&f&m                                               ");
	       	 messages.set("Messages.Kick", list);
	        	 saveConfig();
	        }
		
		if(config.contains("Config.Sounds.WarningSounds.Sound")) {
			messages.set("Messages.Warning.Sound", config.getString("Config.Sounds.WarningSounds.Sound"));
			saveMessages();
			config.set("Config.Sounds", null);
			saveConfig();
		}else {
			if(!messages.contains("Messages.Warning.Sound")) {
				messages.set("Messages.Warning.Sound", "ENDERDRAGON_HIT;10;2");
				saveMessages();
			}
		}
         
         if(!messages.contains("Messages.Warning.Message")) {
        	List<String> list = messages.getStringList("Messages.Warning.Message");
        	list.add("&m                                          ");
        	list.add("");
        	list.add("");
        	list.add("");
        	list.add("");
        	list.add("&4ACCOUNT PROBABLY HACKED");
        	list.add("&fAccount :&e %account% &fIP :&e %ip% ");
        	list.add("");
        	list.add("");
        	list.add("");
        	list.add("");
        	list.add("&m                                          ");
         	messages.set("Messages.Warning.Message", list);
         	saveMessages();
         }
         
         if(!messages.contains("Messages.Warning.Title")) {
         	messages.set("Messages.Warning.Title", "&4ACCOUNT PROBABLY HACKED");
         	saveMessages();
         }
         
         if(!messages.contains("Messages.Warning.SubTitle")) {
         	messages.set("Messages.Warning.SubTitle", "&fAccount :&e %account% &fIP :&e %ip% ");
         	saveMessages();
         }
         
         if(!messages.contains("Messages.Warning.Console")) {
         	List<String> list = messages.getStringList("Messages.Warning.Console");
         	list.add("&c ______________________________________________________________________________________________________");
         	list.add("&c ------------------------------------------------------------------------------------------------------");
         	list.add("                               &4___                  ___");
         	list.add("                 &4|  /|  /  /| |   | |  /| || |  /| |   |");
         	list.add("                 &4| / | /  /_| |---  | / | || | / | | __");
         	list.add("                 &4|/  |/  /  | | |   |/  | || |/  | |___|");
         	list.add(" ");
         	list.add(" ");
         	list.add("&fA person has tried to enter with the &e%account% &faccount from ip &e%ip% &fand has been &ckicked &ffrom the server");
         	list.add("&fPlease advise the original owner of the account that it was &cprobably hacked");
         	list.add("&8Server protected by &3AccountIPprotection");
         	list.add(" ");
         	list.add("&c ______________________________________________________________________________________________________");
         	list.add("&c ------------------------------------------------------------------------------------------------------");
         	messages.set("Messages.Warning.Console", list);
         	saveMessages();
         }
         
         if(!messages.contains("Messages.AIPP")) {
         	List<String> list = messages.getStringList("Messages.AIPP");
         	list.add("             &5&o&lAccountIPprotection");
         	list.add(" ");
         	list.add("&b/aipp &fFor see the plugin commands");
         	list.add("&b/aipp reload &fFor recharged the config");
         	list.add("&a--------------------- &2[&a+&2] &a&lADD &a----------------------");
         	list.add("&b/aipp add account <Account> <IP>");
         	list.add("   &fFor &aadd &fprotection to a account");
         	list.add("&b/aipp add ip <Account> <IP> ");
         	list.add("   &fFor &aadd &fa connection IP to a protected account");
         	list.add("&a-----------------------------------------------------");
         	list.add("&c-------------------- &4[&c-&4] &c&lREMOVE &c---------------------");
         	list.add("&b/aipp remove account <Account>");
         	list.add("   &fTo &cremove &fprotection from this account");
         	list.add("&b/aipp remove ip <Account> <IP>");
         	list.add("   &fFor &cremove &fa connection IP to a protected account");
         	list.add("&c-----------------------------------------------------");
         	list.add("&6----------------------- &6&lCHECK &6----------------------");
         	list.add("&b/aipp check <Account>");
         	list.add("   &fFor check if an account is protected and all IP's allowed");
         	list.add("&6-----------------------------------------------------");
         	messages.set("Messages.AIPP", list);
         	saveMessages();
         }
         
         if(!messages.contains("Messages.Add.Account")) {
         	List<String> list = messages.getStringList("Messages.Add.Account");
         	list.add("&a---------------------- &2[&a+&2] &a&lADD &a----------------------");
         	list.add(" &fThe &e%account% &faccount has been correctly &aprotected");
         	list.add("&a----------------------------------------------------");
         	messages.set("Messages.Add.Account", list);
         	saveMessages();
         }
         
         if(!messages.contains("Messages.Add.IP")) {
         	List<String> list = messages.getStringList("Messages.Add.IP");
         	list.add("&a---------------------- &2[&a+&2] &a&lADD &a----------------------");
         	list.add(" &fIP &e%ip% &fhas been &asuccessfully added &fto the &e%account% &faccount");
         	list.add("&a----------------------------------------------------");
         	messages.set("Messages.Add.IP", list);
         	saveMessages();
         }
         
         if(!messages.contains("Messages.Remove.Account")) {
         	List<String> list = messages.getStringList("Messages.Remove.Account");
         	list.add("&c--------------------- &4[&c-&4] &c&lREMOVE &c--------------------");
         	list.add(" &e%account% &faccount has been successfully &cremoved");
         	list.add("&c-----------------------------------------------------");
         	messages.set("Messages.Remove.Account", list);
         	saveMessages();
         }
         
         if(!messages.contains("Messages.Remove.IP")) {
         	List<String> list = messages.getStringList("Messages.Remove.IP");
         	list.add("&c--------------------- &4[&c-&4] &c&lREMOVE &c--------------------");
         	list.add(" &fIP &e%ip% &fhas been &csuccessfully remove &fto the &e%account% &faccount");
         	list.add("&c-----------------------------------------------------");
         	messages.set("Messages.Remove.IP", list);
         	saveMessages();
         }
         
         if(!messages.contains("Messages.Check.Protected")) {
         	List<String> list = messages.getStringList("Messages.Check.Protected");
         	list.add("&6----------------------- &6&lCHECK &6----------------------");
         	list.add(" &e%account% &faccount is &aprotected");
         	list.add(" ");
         	list.add(" &b- &7%iplist%");
         	list.add("&6-----------------------------------------------------");
         	messages.set("Messages.Check.Protected", list);
         	saveMessages();
         }
         
         if(!messages.contains("Messages.Check.NotProtected")) {
         	List<String> list = messages.getStringList("Messages.Check.NotProtected");
         	list.add("&6----------------------- &6&lCHECK &6----------------------");
         	list.add(" &e%account% &faccount is &cnot protected");
         	list.add("&6-----------------------------------------------------");
         	messages.set("Messages.Check.NotProtected", list);
         	saveMessages();
         }
         
         if(!messages.contains("Messages.Reload")) {
         	List<String> list = messages.getStringList("Messages.Reload");
         	list.add("&b-----------------------------------------------------");
         	list.add(" &d&l[&5AIPP&d&l] &fThe configuration has been reloaded correctly &b&k::");
         	list.add("&b-----------------------------------------------------");
         	messages.set("Messages.Reload", list);
         	saveMessages();
         }
         
         if(!messages.contains("Messages.NoPermission")) {
         	List<String> list = messages.getStringList("Messages.NoPermission");
         	list.add("&cYou don't has permission for execute this command");
         	messages.set("Messages.NoPermission", list);
         	saveMessages();
         }
	}

	public FileConfiguration getPlayers() {
		return players;
	}
	
	public FileConfiguration getMessages() {
		return messages;
	}

	public void savePlayers() {
		try {
			players.save(playersfile);
		} catch (IOException e) {
			Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "Could not save the players.yml file");
		}
	}
	
	public void saveMessages() {
		try {
			messages.save(messagesfile);
		} catch (IOException e) {
			Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "Could not save the messages.yml file");
		}
	}

	public void reloadPlayers() {
		players = YamlConfiguration.loadConfiguration(playersfile);
	}
	
	public void reloadMessages() {
		messages = YamlConfiguration.loadConfiguration(messagesfile);
	}
	
	public void registerEvents() {
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new PlayerListener(this), this);
	}
	
	public void registerCommands() {
		this.getCommand("aipp").setExecutor(new Scommand(this));
	}
	
	private boolean setupTitle() {
        String version;
        try {
            version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];

        } catch (ArrayIndexOutOfBoundsException whatVersionAreYouUsingException) {
            return false;
        }

        if (version.equals("v1_8_R3")) {
            titles = new Title_1_8_R3();
        }else if(version.equals("v1_9_R2")) {
        	titles = new Title_1_9_R2();
        }else if(version.equals("v1_10_R1")) {
        	titles = new Title_1_10_R1();
        }else if(version.equals("v1_11_R1")) {
        	titles = new Title_1_11_R1();
        }else if(version.equals("v1_12_R1")) {
        	titles = new Title_1_12_R1();
        }else if(version.equals("v1_13_R2")) {
        	titles = new Title_1_13_R2();
        }else if(version.equals("v1_14_R1")) {
        	titles = new Title_1_14_R1();
        }else if(version.equals("v1_15_R1")) {
        	titles = new Title_1_15_R1();
        }else if(version.equals("v1_16_R1")) {
        	titles = new Title_1_16_R1();
        }else if(version.equals("v1_16_R2")) {
        	titles = new Title_1_16_R2();
        }else if(version.equals("v1_16_R3")) {
        	titles = new Title_1_16_R3();
        }
     return titles != null;
    }
}
