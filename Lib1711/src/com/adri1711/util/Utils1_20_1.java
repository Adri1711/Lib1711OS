package com.adri1711.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_20_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundSetSubtitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.network.protocol.game.PacketPlayOutEntityDestroy;
import net.minecraft.network.protocol.game.PacketPlayOutNamedEntitySpawn;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.network.PlayerConnection;

public class Utils1_20_1 {
	public static void usaTitle(Player p, String title, String subtitle) {
		IChatBaseComponent chatTitle = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + title + "\"}");
		IChatBaseComponent chatSubTitle = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + subtitle + "\"}");
		ClientboundSetTitleTextPacket title1 = new ClientboundSetTitleTextPacket(chatTitle);

		ClientboundSetSubtitleTextPacket subtitle1 = new ClientboundSetSubtitleTextPacket(chatSubTitle);
		try {
			EntityPlayer ep = ((CraftPlayer) p).getHandle();

			Field f = ep.getClass().getDeclaredField("c");

			f.setAccessible(true);
			PlayerConnection pc = (PlayerConnection) f.get(ep);

			Method m3 = pc.getClass().getMethod("a", Packet.class);

			m3.invoke(pc, title1);
			m3.invoke(pc, subtitle1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void cambiaNombre(Player player, String nuevoNombre, List<Player> players) {
		try {
			EntityPlayer ePlayer1 = ((CraftPlayer) player).getHandle();
			int ae = (int) ePlayer1.getClass().getMethod("ae").invoke(ePlayer1);
			PacketPlayOutEntityDestroy p10 = new PacketPlayOutEntityDestroy(ae);

			EntityPlayer ePlayer = ((CraftPlayer) player).getHandle();
			ePlayer.displayName = nuevoNombre;
			PacketPlayOutNamedEntitySpawn p20 = new PacketPlayOutNamedEntitySpawn(ePlayer);
			player.sendMessage(
					ChatColor.AQUA + "You disguise name has been set to " + ChatColor.DARK_GREEN + nuevoNombre);
			for (Player p : players) {
				if (!p.getName().equals(player.getName())) {
					EntityPlayer ep = ((CraftPlayer) player).getHandle();

					Field f = ep.getClass().getDeclaredField("c");

					f.setAccessible(true);
					PlayerConnection pc = (PlayerConnection) f.get(ep);

					Method m3 = pc.getClass().getMethod("a", Packet.class);

					m3.invoke(pc, p10);
					m3.invoke(pc, p20);
					// ((CraftPlayer) player).getHandle().b.a(p10);
					// ((CraftPlayer) player).getHandle().b.a(p20);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
