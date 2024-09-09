package com.adri1711.auxiliar1_21_R1;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_21_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_21_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_21_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.adri1711.pl.Lib1711;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.chat.ChatHoverable;
import net.minecraft.network.chat.ChatModifier;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundInitializeBorderPacket;
import net.minecraft.network.protocol.game.ClientboundSystemChatPacket;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.network.PlayerConnection;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.border.WorldBorder;

public class Util {
	private static Lib1711 plugin = (Lib1711) Bukkit.getPluginManager().getPlugin("Lib1711");

	public static Boolean getPatchPotion() {
		Boolean patchPotions = Boolean.TRUE;
		if (plugin != null) {
			patchPotions = plugin.getPatchPotions();
		}
		return patchPotions;
	}

	public static String getName(net.minecraft.world.item.ItemStack stack) {

		try {
			Method m = stack.getClass().getMethod("v");
			NBTTagCompound tag = (NBTTagCompound) m.invoke(stack);
			// NBTTagCompound tag = stack.s();
			Method m2 = tag.getClass().getMethod("b", String.class, int.class);
			if ((tag != null) && ((boolean) m2.invoke(tag, "display", 10))) {
				Method mm2 = tag.getClass().getMethod("p", String.class);
				NBTTagCompound nbttagcompound = (NBTTagCompound) mm2.invoke(tag, "display");

//				NBTTagCompound nbttagcompound = tag.p("display");

				if ((boolean) m2.invoke(nbttagcompound, "Name", 8)) {
					Method m3 = nbttagcompound.getClass().getMethod("l", String.class);

					return (String) m3.invoke(nbttagcompound, "Name");
				}
			}
			Method mm = stack.getClass().getMethod("d");
			net.minecraft.world.item.Item item = (Item) mm.invoke(stack);
			Method mm2 = item.getClass().getMethod("a");
			return (String) mm2.invoke(item);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * Creates a new chat base component object from a Bukkit ItemStack.
	 *
	 * @param stack the stack to create from
	 * @return the created chat base component
	 */
	public static IChatBaseComponent fromItemStack(ItemStack stack) {
		net.minecraft.world.item.ItemStack nms = CraftItemStack.asNMSCopy(stack);
		NBTTagCompound tag = new NBTTagCompound();

		try {
			Method m = nms.getClass().getMethod("b", NBTTagCompound.class);

			m.invoke(nms, tag);
			// nms.b(tag);

//			LiteralContents text = new LiteralContents(getName(nms));
			IChatBaseComponent text = IChatBaseComponent.a(getName(nms));
			ChatModifier modifier = ChatModifier.a;
			Method m3 = modifier.getClass().getMethod("a", ChatHoverable.class);
			m3.invoke(modifier,
					new ChatHoverable(HoverAction.SHOW_ITEM.getNMS(), IChatBaseComponent.a(tag.toString())));

//			ChatComponentText text = new ChatComponentText(getName(nms));
//			Method m2 = text.getClass().getMethod("c");
//
//			ChatModifier modifier = (ChatModifier) m2.invoke(text);
//			Method m3 = modifier.getClass().getMethod("a", ChatHoverable.class);
//			m3.invoke(modifier,
//					new ChatHoverable(HoverAction.SHOW_ITEM.getNMS(), new ChatComponentText(tag.toString())));

			// modifier.a(new ChatHoverable(HoverAction.SHOW_ITEM.getNMS(), new
			// ChatComponentText(tag.toString())));
			return text;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static void send(CommandSender sender, IChatBaseComponent text, ChatPosition position) {
		try {
			if (sender instanceof Player) {
				Player player = (Player) sender;

				ClientboundSystemChatPacket packet = new ClientboundSystemChatPacket(text, 0);

				EntityPlayer ep = ((CraftPlayer) player).getHandle();

				Field f = ep.getClass().getDeclaredField("b");

				f.setAccessible(true);
				PlayerConnection pc = (PlayerConnection) f.get(ep);
				Method m = pc.getClass().getMethod("a", Packet.class);
				m.invoke(pc, packet);
				// ((CraftPlayer) player).getHandle().b.a(packet);
			} else {
				Method m = text.getClass().getMethod("a");

				sender.sendMessage((String) m.invoke(text));
				// sender.sendMessage(text.a());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void createWorldBorder(Player p, Double size, Location center) {
		WorldBorder worldBorder = new WorldBorder();
		worldBorder.world = ((CraftWorld) center.getWorld()).getHandle();

		try {
			Method m = worldBorder.getClass().getMethod("a", double.class);

			m.invoke(worldBorder, size);
			Method m2 = worldBorder.getClass().getMethod("c", double.class, double.class);
			m2.invoke(worldBorder, center.getX(), center.getZ());

			// worldBorder.a(size);
			// worldBorder.c(center.getX(), center.getZ());

			ClientboundInitializeBorderPacket packetPlayOutWorldBorder = new ClientboundInitializeBorderPacket(
					worldBorder);
			EntityPlayer ep = ((CraftPlayer) p).getHandle();

			Field f = ep.getClass().getDeclaredField("c");

			f.setAccessible(true);
			PlayerConnection pc = (PlayerConnection) f.get(ep);
			Method m3 = pc.getClass().getMethod("a", Packet.class);
			m3.invoke(pc, packetPlayOutWorldBorder);
			// ((CraftPlayer) p).getHandle().b.a(packetPlayOutWorldBorder);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
