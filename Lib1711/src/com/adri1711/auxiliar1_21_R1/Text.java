package com.adri1711.auxiliar1_21_R1;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_21_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.minecraft.EnumChatFormat;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.chat.ChatClickable;
import net.minecraft.network.chat.ChatClickable.EnumClickAction;
import net.minecraft.network.chat.ChatHexColor;
import net.minecraft.network.chat.ChatHoverable;
import net.minecraft.network.chat.ChatHoverable.EnumHoverAction;
import net.minecraft.network.chat.ChatModifier;
import net.minecraft.network.chat.ChatModifier.ChatModifierSerializer;
import net.minecraft.network.chat.ComponentContents;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.chat.IChatMutableComponent;

public class Text implements ComponentContents {
	private String e;

	private IChatMutableComponent cmc;

	public Text() {
		this.e = "";
		Method m;
		try {
			m = IChatMutableComponent.class.getMethod("a", ComponentContents.class);
			this.cmc = (IChatMutableComponent) m.invoke(null, this);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	public Text(String string) {

		this.e = string;
		Method m;
		try {
			m = IChatMutableComponent.class.getMethod("a", ComponentContents.class);
			this.cmc = (IChatMutableComponent) m.invoke(null, this);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public String getE() {
		return e;
	}

	public IChatMutableComponent getCmc() {
		return cmc;
	}

	public static Text fromItemStack(ItemStack stack) {
		return new Text().append(Util.fromItemStack(stack));
	}

//	public Text append(String text) {
//		return (Text) c(text);
//		// return (Text) a(text);
//	}

	public Text append(IChatBaseComponent node) {
		try {
			Method m = getClass().getMethod("a", IChatBaseComponent.class);
			return (Text) m.invoke(this, node);
			// return (Text) a(node);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public Text append(IChatBaseComponent... nodes) {
		for (net.minecraft.network.chat.IChatBaseComponent node : nodes) {
			try {
				Method m = getClass().getMethod("a", IChatBaseComponent.class);
				m.invoke(this, node);
				// return (Text) a(node);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return this;
	}

	public Text appendItem(ItemStack stack) {
		return append(Util.fromItemStack(stack));
	}

	public boolean isBold() {
		try {
			Method m = getClass().getMethod("c");
			ChatModifier cm = (ChatModifier) m.invoke(this);
			Method m2 = cm.getClass().getMethod("b");
			return (boolean) m2.invoke(cm);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public Text setBold(boolean bold) {
		try {
			Method m = getClass().getMethod("c");
			ChatModifier cm = (ChatModifier) m.invoke(this);
			Method m2 = cm.getClass().getMethod("a", Boolean.class);
			m2.invoke(bold);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		c().a(bold);
		return this;
	}

	public boolean isItalic() {
		try {
			Method m = getClass().getMethod("c");
			ChatModifier cm = (ChatModifier) m.invoke(this);
			Method m2 = cm.getClass().getMethod("d");
			return (boolean) m2.invoke(cm);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public Text setItalic(boolean italic) {
		try {
			Method m = getClass().getMethod("c");
			ChatModifier cm = (ChatModifier) m.invoke(this);
			Method m2 = cm.getClass().getMethod("b", Boolean.class);
			m2.invoke(italic);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		c().b(italic);
		return this;
	}

	public boolean isUnderlined() {
		try {
			Method m = getClass().getMethod("c");
			ChatModifier cm = (ChatModifier) m.invoke(this);
			Method m2 = cm.getClass().getMethod("d");
			return (boolean) m2.invoke(cm);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public Text setUnderline(boolean underline) {
		try {
			Method m = getClass().getMethod("c");
			ChatModifier cm = (ChatModifier) m.invoke(this);
			Method m2 = cm.getClass().getMethod("c", Boolean.class);
			m2.invoke(underline);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		c().c(underline);
		return this;
	}

	public boolean isRandom() {
		try {
			Method m = getClass().getMethod("c");
			ChatModifier cm = (ChatModifier) m.invoke(this);
			Method m2 = cm.getClass().getMethod("f");
			return (boolean) m2.invoke(cm);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public Text setRandom(boolean random) {
		try {
			Method m = getClass().getMethod("c");
			ChatModifier cm = (ChatModifier) m.invoke(this);
			Method m2 = cm.getClass().getMethod("e", Boolean.class);
			m2.invoke(random);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		c().e(random);
		return this;
	}

	public boolean isStrikethrough() {
		try {
			Method m = getClass().getMethod("c");
			ChatModifier cm = (ChatModifier) m.invoke(this);
			Method m2 = cm.getClass().getMethod("e");
			return (boolean) m2.invoke(cm);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public Text setStrikethrough(boolean strikethrough) {
		try {
			Method m = getClass().getMethod("c");
			ChatModifier cm = (ChatModifier) m.invoke(this);
			Method m2 = cm.getClass().getMethod("d", Boolean.class);
			m2.invoke(strikethrough);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// c().d(strikethrough);
		return this;
	}

	public ChatColor getColor() {
		try {
			Method m = getClass().getMethod("c");
			ChatModifier cm = (ChatModifier) m.invoke(this);
			Method m2 = cm.getClass().getMethod("a");
			ChatHexColor chc = (ChatHexColor) m2.invoke(cm);
			Field m3 = cm.getClass().getDeclaredField("e");
			m3.setAccessible(true);
			return ChatColor.valueOf((String) m3.get(chc));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		// return ChatColor.valueOf(c().a().e);
	}

	public net.minecraft.network.chat.ChatClickable getChatClickable() {
		try {
			Method m = getClass().getMethod("c");
			ChatModifier cm = (ChatModifier) m.invoke(this);
			Method m2 = cm.getClass().getMethod("h");
			return (ChatClickable) m2.invoke(cm);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public Text setClick(ClickAction action, String value) {
		ChatClickable cc = new ChatClickable(action.getNMS(), value);
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(ChatModifier.class, new ChatModifierSerializer());
		Gson gson = builder.create();
		try {
			Method m = getClass().getMethod("c");
			ChatModifier cm2 = (ChatModifier) m.invoke(this);
			Method m2 = cm2.getClass().getMethod("b", EnumChatFormat.class);
			ChatModifier cm = (ChatModifier) m2.invoke(cm2, EnumChatFormat.e);

			Method m3 = cc.getClass().getMethod("a");
			EnumClickAction eca = (EnumClickAction) m3.invoke(cc);
			Method m4 = eca.getClass().getMethod("b");
			Method m5 = cc.getClass().getMethod("b");

			// ChatModifier cm = c().b(EnumChatFormat.e);

			JsonElement jsonElem = gson.toJsonTree(cm);
			JsonObject jsonObj = jsonElem.getAsJsonObject();
			JsonObject jsonobject1 = new JsonObject();
			jsonobject1.addProperty("action", (String) m4.invoke(eca));
			jsonobject1.addProperty("value", (String) m5.invoke(cc));
			jsonObj.add("clickEvent", (JsonElement) jsonobject1);

			Method m6 = getClass().getMethod("a", ChatModifier.class);
			m6.invoke(this, gson.fromJson(jsonObj, ChatModifier.class));
			// a(gson.fromJson(jsonObj, ChatModifier.class));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return this;
	}

	public String getShiftClickText() {
		try {
			Method m = getClass().getMethod("c");
			ChatModifier cm = (ChatModifier) m.invoke(this);
			Method m2 = cm.getClass().getMethod("j");
			return (String) m2.invoke(cm);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public Text setShiftClickText(String text) {
		try {
			Method m = getClass().getMethod("c");
			ChatModifier cm = (ChatModifier) m.invoke(this);
			Method m2 = cm.getClass().getMethod("a", String.class);
			m2.invoke(cm, text);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return this;
	}

	public ChatHoverable getChatHoverable() {
		try {
			Method m = getClass().getMethod("c");
			ChatModifier cm = (ChatModifier) m.invoke(this);
			Method m2 = cm.getClass().getMethod("i");
			return (ChatHoverable) m2.invoke(cm);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public Text setHover(HoverAction action, IChatBaseComponent value) {
		return this;
	}

	public Text setHover(HoverAction action, IChatBaseComponent value, net.minecraft.world.item.ItemStack stack) {
		// c().a(EnumHoverAction.SHOW_TEXTt)
		// value.c().a(new
		// ChatHoverable(EnumHoverAction.SHOW_ITEM, ))
		// c().a(new
		// net.minecraft.server.v1_16_R4.ChatHoverable(EnumHoverAction.SHOW_ITEM,
		// value.c().i().a(EnumHoverAction.SHOW_ITEM)));
		ChatHoverable ch = new ChatHoverable(EnumHoverAction.b, new ChatHoverable.c(stack));

		try {
			Method m = getClass().getMethod("c");

			ChatModifier cm2 = (ChatModifier) m.invoke(this);
			Method mm = cm2.getClass().getMethod("a", ChatHoverable.class);
			mm.invoke(cm2, ch);

			// c().a(ch);

			GsonBuilder builder = new GsonBuilder();
			builder.registerTypeAdapter(ChatModifier.class, new ChatModifierSerializer());
			Gson gson = builder.create();

			Method m2 = cm2.getClass().getMethod("b", EnumChatFormat.class);
			ChatModifier cm = (ChatModifier) m2.invoke(cm2, EnumChatFormat.e);
			Method m3 = ch.getClass().getMethod("b");

			JsonElement jsonElem = gson.toJsonTree(cm);
			JsonObject jsonObj = jsonElem.getAsJsonObject();
			jsonObj.add("hoverEvent", (JsonObject) m3.invoke(ch));

			// jsonObj.add("hoverEvent", ch.b());

			Method m4 = getClass().getMethod("a", ChatModifier.class);
			m4.invoke(this, gson.fromJson(jsonObj, ChatModifier.class));
			// a(gson.fromJson(jsonObj, ChatModifier.class));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// chatModifierSerializer.serialize(c(),
		// c().getClass(), )
		return this;
	}

	public Text setHoverText(String text) {
		return setHover(HoverAction.SHOW_TEXT, new Text(text).getCmc());
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
		try {
			Method m = nms.getClass().getMethod("b", NBTTagCompound.class);
			m.invoke(nms, tag);
			setHover(HoverAction.SHOW_ITEM, fromItemStack(stack).getCmc(), nms);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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
		try {
			Method m = nms.getClass().getMethod("b", NBTTagCompound.class);
			m.invoke(nms, tag);
			setHover(HoverAction.SHOW_ITEM, fromItemStack(stack).getCmc(), nms);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return this;
	}

	public void send(CommandSender sender) {
		send(sender, ChatPosition.CHAT);
	}

	public void send(CommandSender sender, ChatPosition position) {
		Util.send(sender, this.getCmc(), position);
	}
}
