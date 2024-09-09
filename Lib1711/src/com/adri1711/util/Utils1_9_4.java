package com.adri1711.util;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_9_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_9_R2.EntityPlayer;
import net.minecraft.server.v1_9_R2.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_9_R2.PacketPlayOutNamedEntitySpawn;
import net.minecraft.server.v1_9_R2.IChatBaseComponent;
import net.minecraft.server.v1_9_R2.Packet;
import net.minecraft.server.v1_9_R2.PacketPlayOutTitle;

public class Utils1_9_4 {
  public static void usaTitle(Player p, String title, String subtitle) {
    IChatBaseComponent chatTitle = 
      IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + title + "\"}");
    IChatBaseComponent chatSubTitle = 
      IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + subtitle + "\"}");
    PacketPlayOutTitle title1 = new PacketPlayOutTitle(
        PacketPlayOutTitle.EnumTitleAction.TITLE, 
        chatTitle);
    PacketPlayOutTitle times1 = new PacketPlayOutTitle(
        PacketPlayOutTitle.EnumTitleAction.TIMES, 
        chatTitle, 20, 60, 20);
    PacketPlayOutTitle subtitle1 = new PacketPlayOutTitle(
        PacketPlayOutTitle.EnumTitleAction.SUBTITLE, 
        chatSubTitle);
    (((CraftPlayer)p).getHandle()).playerConnection
      .sendPacket((Packet)times1);
    (((CraftPlayer)p).getHandle()).playerConnection
      .sendPacket((Packet)title1);
    (((CraftPlayer)p).getHandle()).playerConnection
      .sendPacket((Packet)subtitle1);
  }
  
  public static void cambiaNombre(Player player, String nuevoNombre, List<Player> players){

	  EntityPlayer ePlayer1 = ((CraftPlayer) player).getHandle();
	  PacketPlayOutEntityDestroy p10 = new PacketPlayOutEntityDestroy(ePlayer1.getId());
	 

	  
	  EntityPlayer ePlayer = ((CraftPlayer) player).getHandle();
      ePlayer.displayName = nuevoNombre;
      PacketPlayOutNamedEntitySpawn p20 = new PacketPlayOutNamedEntitySpawn(ePlayer);
      player.sendMessage(ChatColor.AQUA + "You disguise name has been set to " + ChatColor.DARK_GREEN + nuevoNombre);
      for (Player p : players) {
          if (!p.getName().equals(player.getName())) {
              ((CraftPlayer) player).getHandle().playerConnection.sendPacket(p10);
              ((CraftPlayer) player).getHandle().playerConnection.sendPacket(p20);
          }
      }
	  
  }
}
