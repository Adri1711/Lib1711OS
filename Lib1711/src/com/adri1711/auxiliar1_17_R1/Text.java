package com.adri1711.auxiliar1_17_R1;

import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_17_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.minecraft.EnumChatFormat;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.chat.ChatClickable;
import net.minecraft.network.chat.ChatComponentText;
import net.minecraft.network.chat.ChatHoverable;
import net.minecraft.network.chat.ChatHoverable.EnumHoverAction;
import net.minecraft.network.chat.ChatModifier;
import net.minecraft.network.chat.ChatModifier.ChatModifierSerializer;
import net.minecraft.network.chat.IChatBaseComponent;



public class Text extends ChatComponentText {

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
		return (Text) c(text);
		// return (Text) a(text);
	}

	public Text append(IChatBaseComponent node) {
		return (Text) addSibling(node);
	}

	public Text append(IChatBaseComponent... nodes) {
		for (net.minecraft.network.chat.IChatBaseComponent node : nodes) {
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
		return ChatColor.valueOf(getChatModifier().getColor().e);
	}

	public net.minecraft.network.chat.ChatClickable getChatClickable() {
		return getChatModifier().getClickEvent();
	}

	public Text setClick(ClickAction action, String value) {
		ChatClickable cc = new ChatClickable(
				action.getNMS(), value);
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(ChatModifier.class, new ChatModifierSerializer());
		Gson gson = builder.create();

		ChatModifier cm = getChatModifier().b(EnumChatFormat.e);

		JsonElement jsonElem = gson.toJsonTree(cm);
		JsonObject jsonObj = jsonElem.getAsJsonObject();
		JsonObject jsonobject1 = new JsonObject();
		jsonobject1.addProperty("action", cc.a().b());
		jsonobject1.addProperty("value", cc.b());
		jsonObj.add("clickEvent", (JsonElement) jsonobject1);

		setChatModifier(gson.fromJson(jsonObj, ChatModifier.class));

		return this;
	}

	public String getShiftClickText() {
		return getChatModifier().getInsertion();
	}

	public Text setShiftClickText(String text) {
		getChatModifier().setInsertion(text);
		return this;
	}

	public ChatHoverable getChatHoverable() {
		return getChatModifier().getHoverEvent();
	}

	public Text setHover(HoverAction action, IChatBaseComponent value) {
		return this;
	}

	public Text setHover(HoverAction action, IChatBaseComponent value,
			net.minecraft.world.item.ItemStack stack) {
		// getChatModifier().setChatHoverable(EnumHoverAction.SHOW_TEXTt)
		// value.getChatModifier().setChatHoverable(new
		// ChatHoverable(EnumHoverAction.SHOW_ITEM, ))
		// getChatModifier().setChatHoverable(new
		// net.minecraft.server.v1_16_R1.ChatHoverable(EnumHoverAction.SHOW_ITEM,
		// value.getChatModifier().getHoverEvent().a(EnumHoverAction.SHOW_ITEM)));
		ChatHoverable ch = new ChatHoverable(EnumHoverAction.b,
				new ChatHoverable.c(stack));
		getChatModifier().setChatHoverable(ch);

		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(ChatModifier.class, new ChatModifierSerializer());
		Gson gson = builder.create();

		ChatModifier cm = getChatModifier().b(EnumChatFormat.e);

		JsonElement jsonElem = gson.toJsonTree(cm);
		JsonObject jsonObj = jsonElem.getAsJsonObject();
		jsonObj.add("hoverEvent", ch.b());
		setChatModifier(gson.fromJson(jsonObj, ChatModifier.class));
		
		// chatModifierSerializer.serialize(getChatModifier(),
		// getChatModifier().getClass(), )
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
		net.minecraft.world.item.ItemStack nms = CraftItemStack.asNMSCopy(stack);
		NBTTagCompound tag = new NBTTagCompound();
		nms.save(tag);
		setHover(HoverAction.SHOW_ITEM, fromItemStack(stack), nms);
		return this;
	}

	public Text setHoverText2(List<String> text, String nombre) {
		ItemStack stack = new ItemStack(Material.DIRT);
		ItemMeta meta = stack.getItemMeta();

		List<String> lines = text;
		meta.setDisplayName(nombre);
		meta.setLore(lines);
		stack.setItemMeta(meta);
		net.minecraft.world.item.ItemStack nms = CraftItemStack.asNMSCopy(stack);
		NBTTagCompound tag = new NBTTagCompound();
		nms.save(tag);

		setHover(HoverAction.SHOW_ITEM, fromItemStack(stack), nms);
		return this;
	}

	public void send(CommandSender sender) {
		send(sender, ChatPosition.CHAT);
	}

	public void send(CommandSender sender, ChatPosition position) {
		Util.send(sender, this, position);
	}
}
