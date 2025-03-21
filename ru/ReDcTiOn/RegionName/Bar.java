package ru.ReDcTiOn.RegionName;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

public class Bar {
   private String name;
   private BarColor color;
   private BarStyle type;
   private BossBar b;
   private static ArrayList<Bar> bars = new ArrayList();

   public static void removeBar(String name) {
      Iterator var1 = bars.iterator();

      while(var1.hasNext()) {
         Bar bar = (Bar)var1.next();
         if (bar.getName().contains(name)) {
            bars.remove(bar);
         }
      }

   }

   public static Bar getPlayerBar(Player p) {
      Iterator var1 = bars.iterator();

      Bar bar;
      do {
         if (!var1.hasNext()) {
            return null;
         }

         bar = (Bar)var1.next();
      } while(!bar.getPlayers().contains(p));

      return bar;
   }

   public static void resetAllPlayersBars() {
      Iterator var0 = Bukkit.getOnlinePlayers().iterator();

      while(var0.hasNext()) {
         Player p = (Player)var0.next();
         getPlayerBar(p).hideFrom(p);
      }

   }

   public Bar(String name, BarColor color, BarStyle type) {
      this.name = name;
      this.color = color;
      this.type = type;
      this.b = Bukkit.createBossBar(name, color, type, new BarFlag[0]);
      bars.add(this);
   }

   public String getName() {
      return this.b.getTitle();
   }

   public BarColor getColor() {
      return this.b.getColor();
   }

   public BarStyle getType() {
      return this.b.getStyle();
   }

   public void display(Player p, boolean override) {
      if (getPlayerBar(p) != null && override) {
         getPlayerBar(p).hideFrom(p);
      }

      this.b.addPlayer(p);
      this.b.setVisible(true);
   }

   public void hideFrom(Player p) {
      this.b.removePlayer(p);
   }

   public void setProgress(double d) {
      this.b.setProgress(d);
   }

   public void setColor(BarColor c) {
      this.b.setColor(c);
   }

   public void setStyle(BarStyle s) {
      this.b.setStyle(s);
   }

   public void setName(String name) {
      this.b.setTitle(name);
   }

   public List<Player> getPlayers() {
      return this.b.getPlayers();
   }
}
