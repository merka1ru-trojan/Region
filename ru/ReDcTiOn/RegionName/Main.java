package ru.ReDcTiOn.RegionName;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import mcstats.MetricsLite;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import ru.ReDcTiOn.RegionName.nms.RegionName_1_10_R1;
import ru.ReDcTiOn.RegionName.nms.RegionName_1_11_R1;
import ru.ReDcTiOn.RegionName.nms.RegionName_1_12_R1;
import ru.ReDcTiOn.RegionName.nms.RegionName_1_13_R2;
import ru.ReDcTiOn.RegionName.nms.RegionName_1_14_R1;
import ru.ReDcTiOn.RegionName.nms.RegionName_1_15_R1;
import ru.ReDcTiOn.RegionName.nms.RegionName_1_16_R1;
import ru.ReDcTiOn.RegionName.nms.RegionName_1_16_R2;
import ru.ReDcTiOn.RegionName.nms.RegionName_1_16_R3;
import ru.ReDcTiOn.RegionName.nms.RegionName_1_8_R1;
import ru.ReDcTiOn.RegionName.nms.RegionName_1_8_R2;
import ru.ReDcTiOn.RegionName.nms.RegionName_1_8_R3;
import ru.ReDcTiOn.RegionName.nms.RegionName_1_9_R1;
import ru.ReDcTiOn.RegionName.nms.RegionName_1_9_R2;
import ru.ReDcTiOn.RegionName.nms.RegionName_nms;

public class Main extends JavaPlugin implements Listener {
   static String region;
   static String not_region;
   static Integer mode;
   static String modeNum;
   static String reload;
   static String lang;
   static RegionName_nms regionname_nms;
   static String version;
   static boolean hideNotRegion;
   static List<String> blackList;
   static List<String> regionNames;
   static FileConfiguration config;
   private FileConfiguration customConfig = null;
   private File customConfigFile = null;
   File messagesFile;
   FileConfiguration messages;

   public void onEnable() {
      if (this.setupActionbar()) {
         Bukkit.getPluginManager().registerEvents(this, this);
         this.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "[RegionName] " + ChatColor.GRAY + "RegionName supports this version of the game.");
      } else {
         this.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "[RegionName] " + ChatColor.RED + "RegionName does not support this version of the game.");
         Bukkit.getPluginManager().disablePlugin(this);
      }

      lang = this.getConfig().getString("lang");
      config = this.getConfig();
      this.loadCfg();
      this.saveCfg();
      Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable(), 20L, 20L);
      new MetricsLite(this);
   }

   public void OnDisable() {
      Bar.resetAllPlayersBars();
      this.saveCfg();
   }

   private boolean setupActionbar() {
      try {
         version = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
      } catch (ArrayIndexOutOfBoundsException var2) {
         return false;
      }

      if (version.equals("v1_16_R3")) {
         regionname_nms = new RegionName_1_16_R3();
      } else if (version.equals("v1_16_R2")) {
         regionname_nms = new RegionName_1_16_R2();
      } else if (version.equals("v1_16_R1")) {
         regionname_nms = new RegionName_1_16_R1();
      } else if (version.equals("v1_15_R1")) {
         regionname_nms = new RegionName_1_15_R1();
      } else if (version.equals("v1_14_R1")) {
         regionname_nms = new RegionName_1_14_R1();
      } else if (version.equals("v1_13_R2")) {
         regionname_nms = new RegionName_1_13_R2();
      } else if (version.equals("v1_12_R1")) {
         regionname_nms = new RegionName_1_12_R1();
      } else if (version.equals("v1_11_R1")) {
         regionname_nms = new RegionName_1_11_R1();
      } else if (version.equals("v1_10_R1")) {
         regionname_nms = new RegionName_1_10_R1();
      } else if (version.equals("v1_9_R1")) {
         regionname_nms = new RegionName_1_9_R1();
      } else if (version.equals("v1_9_R2")) {
         regionname_nms = new RegionName_1_9_R2();
      } else if (version.equals("v1_8_R1")) {
         regionname_nms = new RegionName_1_8_R1();
      } else if (version.equals("v1_8_R2")) {
         regionname_nms = new RegionName_1_8_R2();
      } else if (version.equals("v1_8_R3")) {
         regionname_nms = new RegionName_1_8_R3();
      } else {
         regionname_nms = new RegionName_1_16_R3();
      }

      return regionname_nms != null;
   }

   public RegionName_nms getActionbar() {
      return regionname_nms;
   }

   private void firstRun() throws Exception {
      if (!this.messagesFile.exists()) {
         this.messagesFile.getParentFile().mkdirs();
         this.copy(this.getResource(lang + ".yml"), this.messagesFile);
      }

   }

   private void copy(InputStream in, File file) {
      try {
         OutputStream out = new FileOutputStream(file);
         byte[] buf = new byte[1024];

         int len;
         while((len = in.read(buf)) > 0) {
            out.write(buf, 0, len);
         }

         out.close();
         in.close();
      } catch (Exception var6) {
         var6.printStackTrace();
      }

   }

   public void saveCfg() {
      try {
         this.messages.save(this.messagesFile);
      } catch (IOException var2) {
         var2.printStackTrace();
      }

      this.getConfig().set("region", region);
      this.getConfig().set("not_region", not_region);
      this.getConfig().set("hideNotRegion", hideNotRegion);
      this.getConfig().set("mode", mode);
      this.getConfig().set("lang", lang);
      this.getConfig().set("blackList", blackList);
      this.getConfig().set("regionNames", regionNames);
      this.saveConfig();
   }

   public void loadCfg() {
      this.messagesFile = new File(this.getDataFolder(), lang + ".yml");

      try {
         this.firstRun();
      } catch (Exception var3) {
         var3.printStackTrace();
      }

      this.messages = new YamlConfiguration();

      try {
         this.messages.load(this.messagesFile);
      } catch (Exception var2) {
         var2.printStackTrace();
      }

      region = this.getConfig().getString("region").replace('&', '§');
      not_region = this.getConfig().getString("not_region").replace('&', '§');
      hideNotRegion = this.getConfig().getBoolean("hideNotRegion");
      mode = this.getConfig().getInt("mode");
      lang = this.getConfig().getString("lang");
      blackList = this.getConfig().getStringList("blackList");
      regionNames = this.getConfig().getStringList("regionNames");
      if (mode == 2 || mode == 3) {
         this.checkPlug();
      }

   }

   public void checkPlug() {
      PluginManager pm = Bukkit.getServer().getPluginManager();
      if (pm.getPlugin("BarAPI") == null && (version.equals("v1_8_R1") || version.equals("v1_8_R2") || version.equals("v1_8_R3"))) {
         this.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "[RegionName] " + ChatColor.RED + "BarAPI must be installed!");
         this.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "[RegionName] " + ChatColor.RED + "Download it at: http://bit.ly/2xSiOd2");
         this.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "[RegionName] " + ChatColor.GRAY + "Mode is set to ActionBar (1)");
         mode = 1;
      }

   }

   public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
      if (command.getName().equalsIgnoreCase("regionname")) {
         if (sender instanceof ConsoleCommandSender) {
            this.reloadConfig();
            this.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', this.messages.getString("reload")));
            return true;
         }

         Player player = (Player)sender;
         if (!player.hasPermission("regionname.reload")) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', this.messages.getString("noPerm")));
         }

         if (args.length < 1) {
            return false;
         }

         if (args[0].equalsIgnoreCase("reload")) {
            this.reloadConfig();
            this.loadCfg();
            config = this.getConfig();
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', this.messages.getString("reload")));
            return true;
         }

         if (args.length < 2) {
            return false;
         }

         if (args[0].equalsIgnoreCase("add")) {
            blackList.add(args[1]);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', this.messages.getString("blacklist_add")));
            this.saveCfg();
            this.loadCfg();
            return true;
         }

         if (args[0].equalsIgnoreCase("remove")) {
            blackList.remove(args[1]);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', this.messages.getString("blacklist_remove")));
            this.saveCfg();
            this.loadCfg();
            return true;
         }
      }

      return false;
   }

   public void helpMessage(Player player) {
      player.sendMessage(ChatColor.translateAlternateColorCodes('&', this.messages.getString("helpTitle")));
      player.sendMessage(ChatColor.translateAlternateColorCodes('&', this.messages.getString("helpReload")));
      player.sendMessage(ChatColor.translateAlternateColorCodes('&', this.messages.getString("helpFooter")));
   }
}
