package com.adri1711.auxiliar1_16_R2;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_16_R2.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_16_R2.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.adri1711.pl.Lib1711;

import net.minecraft.server.v1_16_R2.ChatMessageType;

public class Util {
	private static Lib1711 plugin = (Lib1711) Bukkit.getPluginManager().getPlugin("Lib1711");
	public static Boolean getPatchPotion(){
		Boolean patchPotions=Boolean.TRUE;
		if(plugin!=null){
			patchPotions= plugin.getPatchPotions();
		}
		return patchPotions;
	}
    public static String getName(net.minecraft.server.v1_16_R2.ItemStack stack) {
    	net.minecraft.server.v1_16_R2.NBTTagCompound tag = stack.getTag();

        if ((tag != null) && (tag.hasKeyOfType("display", 10))) {
        	net.minecraft.server.v1_16_R2.NBTTagCompound nbttagcompound = tag.getCompound("display");

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
    public static net.minecraft.server.v1_16_R2.IChatBaseComponent fromItemStack(ItemStack stack) {
        net.minecraft.server.v1_16_R2.ItemStack nms = CraftItemStack.asNMSCopy(stack);
        net.minecraft.server.v1_16_R2.NBTTagCompound tag = new net.minecraft.server.v1_16_R2.NBTTagCompound();
        nms.save(tag);

        net.minecraft.server.v1_16_R2.ChatComponentText text = new net.minecraft.server.v1_16_R2.ChatComponentText(getName(nms));
        net.minecraft.server.v1_16_R2.ChatModifier modifier = text.getChatModifier();
        modifier.setChatHoverable(new net.minecraft.server.v1_16_R2.ChatHoverable(HoverAction.SHOW_ITEM.getNMS(), new net.minecraft.server.v1_16_R2.ChatComponentText(tag.toString())));
        return text;
    }

    public static void send(CommandSender sender, net.minecraft.server.v1_16_R2.IChatBaseComponent text, ChatPosition position) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            net.minecraft.server.v1_16_R2.PacketPlayOutChat packet = new net.minecraft.server.v1_16_R2.PacketPlayOutChat(text, ChatMessageType.CHAT, player.getUniqueId());
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
        } else {
            sender.sendMessage(text.getText());
        }
    }
    
    public static void createWorldBorder(Player p, Double size, Location center) {
		net.minecraft.server.v1_16_R2.WorldBorder worldBorder = new net.minecraft.server.v1_16_R2.WorldBorder();
		worldBorder.world = ((org.bukkit.craftbukkit.v1_16_R2.CraftWorld) center.getWorld()).getHandle();
		worldBorder.setSize(size);
		worldBorder.setCenter(center.getX(), center.getZ());

		net.minecraft.server.v1_16_R2.PacketPlayOutWorldBorder packetPlayOutWorldBorder = new net.minecraft.server.v1_16_R2.PacketPlayOutWorldBorder(
				worldBorder, net.minecraft.server.v1_16_R2.PacketPlayOutWorldBorder.EnumWorldBorderAction.INITIALIZE);
		((CraftPlayer) p).getHandle().playerConnection.sendPacket(packetPlayOutWorldBorder);
	}
}
