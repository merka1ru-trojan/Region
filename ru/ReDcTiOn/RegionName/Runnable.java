package ru.ReDcTiOn.RegionName;

import java.util.Iterator;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Runnable implements java.lang.Runnable {
   public void run() {
      Iterator var1 = Bukkit.getOnlinePlayers().iterator();

      while(var1.hasNext()) {
         Player p = (Player)var1.next();
         if (Main.mode == 1) {
            Utils.sendAction(p, Main.region.replace("{REGION}", Utils.getRegion(p.getLocation(), p)));
         }

         if (Main.mode == 2) {
            Utils.sendBossbar(p, Main.region.replace("{REGION}", Utils.getRegion(p.getLocation(), p)));
         }

         if (Main.mode == 3) {
            Utils.sendAction(p, Main.region.replace("{REGION}", Utils.getRegion(p.getLocation(), p)));
            Utils.sendBossbar(p, Main.region.replace("{REGION}", Utils.getRegion(p.getLocation(), p)));
         }
      }

   }
}
