package com.adri1711.auxiliar1_20_R2;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.minecraft.EnumChatFormat;
import net.minecraft.network.chat.ChatClickable;
import net.minecraft.network.chat.ChatClickable.EnumClickAction;
import net.minecraft.network.chat.ChatHexColor;
import net.minecraft.network.chat.ChatHoverable;
import net.minecraft.network.chat.ChatModifier;
import net.minecraft.network.chat.ChatModifier.ChatModifierSerializer;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.chat.contents.TranslatableContents;

public class Trans extends TranslatableContents {

	public Trans() {
		super("");
	}

	public Trans(String string, Object... objects) {
		super(string, objects);
	}

	public static Trans fromItemStack(ItemStack stack) {
		return new Trans().append(Util.fromItemStack(stack));
	}

	public Trans append(String text) {
		try {
			Method m = getClass().getMethod("c", String.class);
			return (Trans) m.invoke(this, text);
			// return (Text) a(node);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public Trans append(IChatBaseComponent node) {
		try {
			Method m = getClass().getMethod("a", IChatBaseComponent.class);
			return (Trans) m.invoke(this, node);
			// return (Text) a(node);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public Trans append(IChatBaseComponent... nodes) {
		for (IChatBaseComponent node : nodes) {
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

	public Trans appendItem(ItemStack stack) {
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

	public Trans setBold(boolean bold) {
		try {
			Method m = getClass().getMethod("c");
			ChatModifier cm = (ChatModifier) m.invoke(this);
			Method m2 = cm.getClass().getMethod("a", Boolean.class);
			m2.invoke(bold);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// c().a(bold);
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

	public Trans setItalic(boolean italic) {
		try {
			Method m = getClass().getMethod("c");
			ChatModifier cm = (ChatModifier) m.invoke(this);
			Method m2 = cm.getClass().getMethod("b", Boolean.class);
			m2.invoke(italic);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// c().b(italic);
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

	public Trans setUnderline(boolean underline) {
		try {
			Method m = getClass().getMethod("c");
			ChatModifier cm = (ChatModifier) m.invoke(this);
			Method m2 = cm.getClass().getMethod("c", Boolean.class);
			m2.invoke(underline);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// c().c(underline);
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

	public Trans setRandom(boolean random) {
		try {
			Method m = getClass().getMethod("c");
			ChatModifier cm = (ChatModifier) m.invoke(this);
			Method m2 = cm.getClass().getMethod("e", Boolean.class);
			m2.invoke(random);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// c().e(random);
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

	public Trans setStrikethrough(boolean strikethrough) {
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

	public ChatClickable getChatClickable() {
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

	public Trans setClick(ClickAction action, String value) {
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
			
			Method m6 = getClass().getMethod("a",ChatModifier.class);
			m6.invoke(this, gson.fromJson(jsonObj, ChatModifier.class));
			//a(gson.fromJson(jsonObj, ChatModifier.class));
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

	public Trans setShiftClickText(String text) {
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

	public Trans setHover(HoverAction action, IChatBaseComponent value) {
		return this;
	}

	


}
