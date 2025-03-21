package ru.ReDcTiOn.RegionName.nms;

import net.minecraft.server.v1_11_R1.IChatBaseComponent;
import net.minecraft.server.v1_11_R1.PacketPlayOutChat;
import net.minecraft.server.v1_11_R1.IChatBaseComponent.ChatSerializer;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class RegionName_1_11_R1 implements RegionName_nms {
   public void sendActionbar(Player p, String msg) {
      IChatBaseComponent cbc = ChatSerializer.a("{\"text\": \"" + msg + "\"}");
      PacketPlayOutChat ppoc = new PacketPlayOutChat(cbc, (byte)2);
      ((CraftPlayer)p).getHandle().playerConnection.sendPacket(ppoc);
   }
}
