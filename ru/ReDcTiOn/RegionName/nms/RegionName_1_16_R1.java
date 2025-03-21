package ru.ReDcTiOn.RegionName.nms;

import java.util.UUID;
import net.minecraft.server.v1_16_R1.ChatMessageType;
import net.minecraft.server.v1_16_R1.IChatBaseComponent;
import net.minecraft.server.v1_16_R1.PacketPlayOutChat;
import net.minecraft.server.v1_16_R1.IChatBaseComponent.ChatSerializer;
import org.bukkit.craftbukkit.v1_16_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class RegionName_1_16_R1 implements RegionName_nms {
   public void sendActionbar(Player p, String region) {
      UUID uuid = p.getUniqueId();
      IChatBaseComponent cbc = ChatSerializer.a("{\"text\": \"" + region + "\"}");
      PacketPlayOutChat ppoc = new PacketPlayOutChat(cbc, ChatMessageType.GAME_INFO, uuid);
      ((CraftPlayer)p).getHandle().playerConnection.sendPacket(ppoc);
   }
}
