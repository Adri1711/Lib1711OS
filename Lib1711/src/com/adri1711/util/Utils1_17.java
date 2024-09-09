package com.adri1711.util;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundSetSubtitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.network.protocol.game.PacketPlayOutEntityDestroy;
import net.minecraft.network.protocol.game.PacketPlayOutNamedEntitySpawn;
import net.minecraft.server.level.EntityPlayer;

public class Utils1_17 {
	public static void usaTitle(Player p, String title, String subtitle) {
		IChatBaseComponent chatTitle = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + title + "\"}");
		IChatBaseComponent chatSubTitle = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + subtitle + "\"}");
		ClientboundSetTitleTextPacket title1 = new ClientboundSetTitleTextPacket(chatTitle);

		ClientboundSetSubtitleTextPacket subtitle1 = new ClientboundSetSubtitleTextPacket(chatSubTitle);
		(((CraftPlayer) p).getHandle()).b.sendPacket(title1);
		(((CraftPlayer) p).getHandle()).b.sendPacket(subtitle1);
	}

	public static void cambiaNombre(Player player, String nuevoNombre, List<Player> players) {

		EntityPlayer ePlayer1 = ((CraftPlayer) player).getHandle();
		PacketPlayOutEntityDestroy p10 = new PacketPlayOutEntityDestroy(ePlayer1.getId());

		EntityPlayer ePlayer = ((CraftPlayer) player).getHandle();
		ePlayer.displayName = nuevoNombre;
		PacketPlayOutNamedEntitySpawn p20 = new PacketPlayOutNamedEntitySpawn(ePlayer);
		player.sendMessage(ChatColor.AQUA + "You disguise name has been set to " + ChatColor.DARK_GREEN + nuevoNombre);
		for (Player p : players) {
			if (!p.getName().equals(player.getName())) {
				((CraftPlayer) player).getHandle().b.sendPacket(p10);
				((CraftPlayer) player).getHandle().b.sendPacket(p20);
			}
		}

	}
}
