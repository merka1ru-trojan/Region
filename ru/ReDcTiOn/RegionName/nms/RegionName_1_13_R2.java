package ru.ReDcTiOn.RegionName.nms;

import net.minecraft.server.v1_13_R2.ChatMessageType;
import net.minecraft.server.v1_13_R2.IChatBaseComponent;
import net.minecraft.server.v1_13_R2.PacketPlayOutChat;
import net.minecraft.server.v1_13_R2.IChatBaseComponent.ChatSerializer;
import org.bukkit.craftbukkit.v1_13_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class RegionName_1_13_R2 implements RegionName_nms {
   public void sendActionbar(Player p, String msg) {
      IChatBaseComponent cbc = ChatSerializer.a("{\"text\": \"" + msg + "\"}");
      PacketPlayOutChat ppoc = new PacketPlayOutChat(cbc, ChatMessageType.GAME_INFO);
      ((CraftPlayer)p).getHandle().playerConnection.sendPacket(ppoc);
   }
}
