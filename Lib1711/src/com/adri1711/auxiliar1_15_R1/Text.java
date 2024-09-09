package com.adri1711.auxiliar1_15_R1;

import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_15_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.adri1711.auxiliar1_15_R1.HoverAction;

import net.minecraft.server.v1_15_R1.ChatHoverable.EnumHoverAction;

public class Text extends net.minecraft.server.v1_15_R1.ChatComponentText {

	public Text() {
		super("");
	}

	public Text(String string) {
		super(string);
	}

	public static Text fromItemStack(ItemStack stack) {
		return new Text().append(Util.fromItemStack(stack));
	}

	public Text append(String text) {
		return (Text) a(text);
	}

	public Text append(net.minecraft.server.v1_15_R1.IChatBaseComponent node) {
		return (Text) addSibling(node);
	}

	public Text append(net.minecraft.server.v1_15_R1.IChatBaseComponent... nodes) {
		for (net.minecraft.server.v1_15_R1.IChatBaseComponent node : nodes) {
			addSibling(node);
		}

		return this;
	}

	public Text appendItem(ItemStack stack) {
		return append(Util.fromItemStack(stack));
	}

	public boolean isBold() {
		return getChatModifier().isBold();
	}

	public Text setBold(boolean bold) {
		getChatModifier().setBold(bold);
		return this;
	}

	public boolean isItalic() {
		return getChatModifier().isBold();
	}

	public Text setItalic(boolean italic) {
		getChatModifier().setItalic(italic);
		return this;
	}

	public boolean isUnderlined() {
		return getChatModifier().isUnderlined();
	}

	public Text setUnderline(boolean underline) {
		getChatModifier().setUnderline(underline);
		return this;
	}

	public boolean isRandom() {
		return getChatModifier().isRandom();
	}

	public Text setRandom(boolean random) {
		getChatModifier().setRandom(random);
		return this;
	}

	public boolean isStrikethrough() {
		return getChatModifier().isStrikethrough();
	}

	public Text setStrikethrough(boolean strikethrough) {
		getChatModifier().setStrikethrough(strikethrough);
		return this;
	}

	public ChatColor getColor() {
		return ChatColor.valueOf(getChatModifier().getColor().name());
	}

	public net.minecraft.server.v1_15_R1.ChatClickable getChatClickable() {
		return getChatModifier().getClickEvent();
	}

	public Text setClick(ClickAction action, String value) {
		getChatModifier().setChatClickable(new net.minecraft.server.v1_15_R1.ChatClickable(action.getNMS(), value));
		return this;
	}

	public String getShiftClickText() {
		return getChatModifier().getInsertion();
	}

	public Text setShiftClickText(String text) {
		getChatModifier().setInsertion(text);
		return this;
	}

	public net.minecraft.server.v1_15_R1.ChatHoverable getChatHoverable() {
		return getChatModifier().getHoverEvent();
	}

	public Text setHover(HoverAction action, net.minecraft.server.v1_15_R1.IChatBaseComponent value) {
		getChatModifier()
				.setChatHoverable(new net.minecraft.server.v1_15_R1.ChatHoverable(EnumHoverAction.SHOW_ITEM, value));
		return this;
	}

	public Text setHoverText(String text) {
		return setHover(HoverAction.SHOW_TEXT, new Text(text));
	}

	public Text setHoverText2(String[] text, String nombre) {
		ItemStack stack = new ItemStack(Material.DIRT);
		ItemMeta meta = stack.getItemMeta();

		List<String> lines = Arrays.asList(text);
		meta.setDisplayName(nombre);
		meta.setLore(lines);
		stack.setItemMeta(meta);
		net.minecraft.server.v1_15_R1.ItemStack nms = CraftItemStack.asNMSCopy(stack);
		net.minecraft.server.v1_15_R1.NBTTagCompound tag = new net.minecraft.server.v1_15_R1.NBTTagCompound();
		nms.save(tag);
		setHover(HoverAction.SHOW_ITEM, new net.minecraft.server.v1_15_R1.ChatComponentText(tag.toString()));
		return this;
	}

	public Text setHoverText2(List<String> text, String nombre) {
		ItemStack stack = new ItemStack(Material.DIRT);
		ItemMeta meta = stack.getItemMeta();

		List<String> lines = text;
		meta.setDisplayName(nombre);
		meta.setLore(lines);
		stack.setItemMeta(meta);
		net.minecraft.server.v1_15_R1.ItemStack nms = CraftItemStack.asNMSCopy(stack);
		net.minecraft.server.v1_15_R1.NBTTagCompound tag = new net.minecraft.server.v1_15_R1.NBTTagCompound();
		nms.save(tag);
		setHover(HoverAction.SHOW_ITEM, new net.minecraft.server.v1_15_R1.ChatComponentText(tag.toString()));
		return this;
	}

	public void send(CommandSender sender) {
		send(sender, ChatPosition.CHAT);
	}

	public void send(CommandSender sender, ChatPosition position) {
		Util.send(sender, this, position);
	}
}
