package ru.ReDcTiOn.RegionName;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import me.confuser.barapi.BarAPI;
import net.md_5.bungee.api.ChatColor;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Location;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

public class Utils {
   public static String getRegion(Location l, Player p) {
      RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
      RegionQuery query = container.createQuery();
      ApplicableRegionSet set = query.getApplicableRegions(BukkitAdapter.adapt(l));
      List<String> names = new ArrayList();
      Iterator var6 = set.getRegions().iterator();

      while(var6.hasNext()) {
         ProtectedRegion pr = (ProtectedRegion)var6.next();
         String name = ChatColor.translateAlternateColorCodes('&', Main.config.getString("regionNames" + pr.getId(), pr.getId()));
         names.add(name);
      }

      return names.isEmpty() ? Main.not_region : StringUtils.join(names, ", ");
   }

   @EventHandler
   public static void sendAction(Player p, String msg) {
      if (!Main.blackList.contains(msg)) {
         if (!msg.equals(Main.not_region) || !Main.hideNotRegion) {
            if (p.hasPermission("regionname.use")) {
               Main.regionname_nms.sendActionbar(p.getPlayer(), msg);
            }

         }
      }
   }

   public static void sendBossbar(Player p, String msg) {
      Bar bar = new Bar(msg, BarColor.GREEN, BarStyle.SOLID);
      if (p.hasPermission("regionname.use")) {
         if (Main.blackList.contains(msg)) {
            bar.hideFrom(p.getPlayer());
         }

         if (msg.equals(Main.not_region) && Main.hideNotRegion) {
            return;
         }

         if (!Main.version.equals("v1_8_R1") && !Main.version.equals("v1_8_R2") && !Main.version.equals("v1_8_R3")) {
            if (msg.equals(Main.not_region)) {
               bar.display(p, true);
               bar.setColor(BarColor.YELLOW);
            } else {
               bar.display(p, true);
               bar.setColor(BarColor.GREEN);
            }
         } else {
            BarAPI.setMessage(p, msg, 1);
         }
      }

   }
}
