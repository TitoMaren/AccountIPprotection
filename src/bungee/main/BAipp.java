package bungee.main;

import java.io.File;
import java.io.IOException;
import java.util.List;

import bungee.commands.Bcommand;
import bungee.events.BPlayerListener;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginDescription;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

public class BAipp extends Plugin{
	
    private static BAipp instance;
    public File file;
    public Configuration config, players, messages;
	private PluginDescription pdffile = getDescription();
	public String version = ChatColor.LIGHT_PURPLE + pdffile.getVersion();
	public String name = ChatColor.DARK_AQUA + "[" + pdffile.getName() + "]";
    public String titulo = "&4ACCOUNT PROBABLY HACKED";
    public String subtitulo = "&fAccount :&e %account% &fIP :&e %ip%";
    
    @Override
    public void onEnable() {
        setInstance(this);
        createFolder();
        getProxy().getPluginManager().registerCommand(this, new Bcommand());
        getProxy().getPluginManager().registerListener(this, new BPlayerListener());
        getProxy().getConsole().sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', "&4<&c----------------------------------------------------&4>")));
        getProxy().getConsole().sendMessage(new TextComponent(" "));
        getProxy().getConsole().sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', " "+ name + "&f Has been enable &8version: " + version + " ")));
        getProxy().getConsole().sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', "     &7Bungee&ecord")));
        getProxy().getConsole().sendMessage(new TextComponent(" "));
        getProxy().getConsole().sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', "&4<&c----------------------------------------------------&4>")));
    }
    
    public void onDisable() {
    	getProxy().getConsole().sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', "&4<&c----------------------------------------------------&4>")));
        getProxy().getConsole().sendMessage(new TextComponent(" "));
        getProxy().getConsole().sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', " "+ name + "&f Has been disable &8version: " + version + " ")));
        getProxy().getConsole().sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', "     &8Bungeecord version")));
        getProxy().getConsole().sendMessage(new TextComponent(" "));
        getProxy().getConsole().sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', "&4<&c----------------------------------------------------&4>")));
    }

    private void createFolder() {
        if (!getDataFolder().exists()) {
            getDataFolder().mkdirs();
        }
   
        File configFile = new File(getDataFolder(), "config.yml");
        File playersFile = new File(getDataFolder(), "players.yml");
        File messagesFile = new File(getDataFolder(), "messages.yml");

        if (!configFile.exists()) {
            try {
                configFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (!playersFile.exists()) {
            try {
                playersFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        if (!messagesFile.exists()) {
            try {
                messagesFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
   
        loaderConfiguration();
        
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

         if(messages.contains("Messages.Kick-Message")) {
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
         
         if(!messages.contains("Messages.Warning.Message")) {
        	List<String> list = messages.getStringList("Messages.Warning.Message");
        	list.add("&m                                ");
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
        	list.add("&m                                ");
         	messages.set("Messages.Warning.Message", list);
         	saveConfig();
         }
         
         if(!messages.contains("Messages.Warning.Title")) {
         	messages.set("Messages.Warning.Title", "&4ACCOUNT PROBABLY HACKED");
         	saveConfig();
         }
         
         if(!messages.contains("Messages.Warning.SubTitle")) {
         	messages.set("Messages.Warning.SubTitle", "&fAccount :&e %account% &fIP :&e %ip% ");
         	saveConfig();
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
         	saveConfig();
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
         	saveConfig();
         }
         
         if(!messages.contains("Messages.Add.Account")) {
         	List<String> list = messages.getStringList("Messages.Add.Account");
         	list.add("&a---------------------- &2[&a+&2] &a&lADD &a----------------------");
         	list.add(" &fThe &e%account% &faccount has been correctly &aprotected");
         	list.add("&a----------------------------------------------------");
         	messages.set("Messages.Add.Account", list);
         	saveConfig();
         }
         
         if(!messages.contains("Messages.Add.IP")) {
         	List<String> list = messages.getStringList("Messages.Add.IP");
         	list.add("&a---------------------- &2[&a+&2] &a&lADD &a----------------------");
         	list.add(" &fIP &e%ip% &fhas been &asuccessfully added &fto the &e%account% &faccount");
         	list.add("&a----------------------------------------------------");
         	messages.set("Messages.Add.IP", list);
         	saveConfig();
         }
         
         if(!messages.contains("Messages.Remove.Account")) {
         	List<String> list = messages.getStringList("Messages.Remove.Account");
         	list.add("&c--------------------- &4[&c-&4] &c&lREMOVE &c--------------------");
         	list.add(" &e%account% &faccount has been successfully &cremoved");
         	list.add("&c-----------------------------------------------------");
         	messages.set("Messages.Remove.Account", list);
         	saveConfig();
         }
         
         if(!messages.contains("Messages.Remove.IP")) {
         	List<String> list = messages.getStringList("Messages.Remove.IP");
         	list.add("&c--------------------- &4[&c-&4] &c&lREMOVE &c--------------------");
         	list.add(" &fIP &e%ip% &fhas been &csuccessfully remove &fto the &e%account% &faccount");
         	list.add("&c-----------------------------------------------------");
         	messages.set("Messages.Remove.IP", list);
         	saveConfig();
         }
         
         if(!messages.contains("Messages.Check.Protected")) {
         	List<String> list = messages.getStringList("Messages.Check.Protected");
         	list.add("&6----------------------- &6&lCHECK &6----------------------");
         	list.add(" &e%account% &faccount is &aprotected");
         	list.add(" ");
         	list.add(" &b- &7%iplist%");
         	list.add("&6-----------------------------------------------------");
         	messages.set("Messages.Check.Protected", list);
         	saveConfig();
         }
         
         if(!messages.contains("Messages.Check.NotProtected")) {
         	List<String> list = messages.getStringList("Messages.Check.NotProtected");
         	list.add("&6----------------------- &6&lCHECK &6----------------------");
         	list.add(" &e%account% &faccount is &cnot protected");
         	list.add("&6-----------------------------------------------------");
         	messages.set("Messages.Check.NotProtected", list);
         	saveConfig();
         }
         
         if(!messages.contains("Messages.Reload")) {
         	List<String> list = messages.getStringList("Messages.Reload");
         	list.add("&b-----------------------------------------------------");
         	list.add(" &d&l[&5AIPP&d&l] &fThe configuration has been reloaded correctly &b&k::");
         	list.add("&b-----------------------------------------------------");
         	messages.set("Messages.Reload", list);
         	saveConfig();
         }
         
         if(!messages.contains("Messages.NoPermission")) {
         	List<String> list = messages.getStringList("Messages.NoPermission");
         	list.add("&cYou don't has permission for execute this command");
         	messages.set("Messages.NoPermission", list);
         	saveConfig();
         }
        
        //players
        
        if(config.contains("Config.Accountsprotected")) {
        	for(String keys : config.getSection("Config.Accountsprotected").getKeys()) {
        		List<String> ips = config.getStringList("Config.Accountsprotected." + keys);
  				players.set("Players.Accountsprotected." + keys, ips);
  				saveConfig();
        	}
        	
        	config.set("Config.Accountsprotected", null);
  			saveConfig();
        }
    }

    public void loaderConfiguration() {
        try {
            config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(getDataFolder(), "config.yml"));
            players = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(getDataFolder(), "players.yml"));
            messages = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(getDataFolder(), "messages.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveConfig() {
        if ((config != null)) {
            try {
                ConfigurationProvider.getProvider(YamlConfiguration.class).save(config, new File(getDataFolder(), "config.yml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if ((players != null)) {
            try {
                ConfigurationProvider.getProvider(YamlConfiguration.class).save(players, new File(getDataFolder(), "players.yml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        if ((messages != null)) {
            try {
                ConfigurationProvider.getProvider(YamlConfiguration.class).save(messages, new File(getDataFolder(), "messages.yml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    public void reloadConfig() {
        loaderConfiguration();
    }
    
    public static BAipp getInstance() {
        return instance;
    }

    private static void setInstance(BAipp instance) {
    	BAipp.instance = instance;
    }
    
    public Configuration getPlayers() {
    	return players;
    }
    
    public Configuration getConfig() {
    	return config;
    }
    
    public Configuration getMessages() {
    	return messages;
    }
}
