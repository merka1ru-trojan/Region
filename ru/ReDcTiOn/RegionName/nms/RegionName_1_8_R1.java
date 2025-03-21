package ru.ReDcTiOn.RegionName.nms;

import net.minecraft.server.v1_8_R1.ChatSerializer;
import net.minecraft.server.v1_8_R1.IChatBaseComponent;
import net.minecraft.server.v1_8_R1.PacketPlayOutChat;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class RegionName_1_8_R1 implements RegionName_nms {
   public void sendActionbar(Player p, String msg) {
      IChatBaseComponent cbc = ChatSerializer.a("{\"text\": \"" + msg + "\"}");
      PacketPlayOutChat ppoc = new PacketPlayOutChat(cbc, (byte)2);
      ((CraftPlayer)p).getHandle().playerConnection.sendPacket(ppoc);
   }
}
