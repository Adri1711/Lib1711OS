package com.adri1711.auxiliar1_17_R1;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_17_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_17_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.adri1711.pl.Lib1711;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.chat.ChatComponentText;
import net.minecraft.network.chat.ChatHoverable;
import net.minecraft.network.chat.ChatMessageType;
import net.minecraft.network.chat.ChatModifier;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.protocol.game.ClientboundInitializeBorderPacket;
import net.minecraft.network.protocol.game.PacketPlayOutChat;
import net.minecraft.world.level.border.WorldBorder;

public class Util {
	private static Lib1711 plugin = (Lib1711) Bukkit.getPluginManager().getPlugin("Lib1711");
	public static Boolean getPatchPotion(){
		Boolean patchPotions=Boolean.TRUE;
		if(plugin!=null){
			patchPotions= plugin.getPatchPotions();
		}
		return patchPotions;
	}
    public static String getName(net.minecraft.world.item.ItemStack stack) {
    	NBTTagCompound tag = stack.getTag();

        if ((tag != null) && (tag.hasKeyOfType("display", 10))) {
        	NBTTagCompound nbttagcompound = tag.getCompound("display");

            if (nbttagcompound.hasKeyOfType("Name", 8)) {
                return nbttagcompound.getString("Name");
            }
        }
        
        return stack.getItem().getName();
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
        nms.save(tag);

        ChatComponentText text = new ChatComponentText(getName(nms));
        ChatModifier modifier = text.getChatModifier();
        modifier.setChatHoverable(new ChatHoverable(HoverAction.SHOW_ITEM.getNMS(), new ChatComponentText(tag.toString())));
        return text;
    }

    public static void send(CommandSender sender, IChatBaseComponent text, ChatPosition position) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            PacketPlayOutChat packet = new PacketPlayOutChat(text, ChatMessageType.a, player.getUniqueId());
            ((CraftPlayer) player).getHandle().b.sendPacket(packet);
        } else {
            sender.sendMessage(text.getText());
        }
    }
    
    public static void createWorldBorder(Player p, Double size, Location center) {
		WorldBorder worldBorder = new WorldBorder();
		worldBorder.world = ((CraftWorld) center.getWorld()).getHandle();
		worldBorder.setSize(size);
		worldBorder.setCenter(center.getX(), center.getZ());

		ClientboundInitializeBorderPacket packetPlayOutWorldBorder = new ClientboundInitializeBorderPacket(
				worldBorder);
		((CraftPlayer) p).getHandle().b.sendPacket(packetPlayOutWorldBorder);
	}
}
