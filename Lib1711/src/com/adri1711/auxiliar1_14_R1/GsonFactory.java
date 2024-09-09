package com.adri1711.auxiliar1_14_R1;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.UUID;

import org.apache.commons.lang.StringEscapeUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.ShulkerBox;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.craftbukkit.v1_14_R1.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_14_R1.util.CraftMagicNumbers;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.FireworkEffectMeta;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.material.MaterialData;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

import com.adri1711.util.enums.XMaterial;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.Expose;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

import net.minecraft.server.v1_14_R1.MojangsonParser;
import net.minecraft.server.v1_14_R1.NBTBase;
import net.minecraft.server.v1_14_R1.NBTTagCompound;

/* *
* Created by Joshua Bell (RingOfStorms)
*
* Post explaining here: [URL]http://bukkit.org/threads/gsonfactory-gson-that-works-on-itemstack-potioneffect-location-objects.331161/[/URL]
* */
public class GsonFactory {

	@Retention(RetentionPolicy.RUNTIME)
	@Target({ ElementType.FIELD })
	public static @interface Ignore {
	}

	/*
	 * - I want to not use Bukkit parsing for most objects... it's kind of
	 * clunky - Instead... I want to start using any of Mojang's tags - They're
	 * really well documented + built into MC, and handled by them. - Rather
	 * than kill your old code, I'm going to write TypeAdapaters using Mojang's
	 * stuff.
	 */

	private static Gson g = new Gson();

	private final static String CLASS_KEY = "SERIAL-ADAPTER-CLASS-KEY";

	private static Gson prettyGson;
	private static Gson compactGson;

	/**
	 * Returns a Gson instance for use anywhere with new line pretty printing
	 * <p>
	 * Use @GsonIgnore in order to skip serialization and deserialization
	 * </p>
	 * 
	 * @return a Gson instance
	 */
	public static Gson getPrettyGson() {
		if (prettyGson == null)
			prettyGson = new GsonBuilder().addSerializationExclusionStrategy(new ExposeExlusion())
					.addDeserializationExclusionStrategy(new ExposeExlusion())
					.registerTypeHierarchyAdapter(ItemStack.class, new ItemStackGsonAdapter())
					.registerTypeAdapter(PotionEffect.class, new PotionEffectGsonAdapter())
					.registerTypeAdapter(Location.class, new LocationGsonAdapter())
					.registerTypeAdapter(Date.class, new DateGsonAdapter())
					.registerTypeAdapter(MaterialData.class, new MaterialDataGsonAdapter()).setPrettyPrinting()
					.enableComplexMapKeySerialization().disableHtmlEscaping().create();
		return prettyGson;
	}

	/**
	 * Returns a Gson instance for use anywhere with one line strings
	 * <p>
	 * Use @GsonIgnore in order to skip serialization and deserialization
	 * </p>
	 * 
	 * @return a Gson instance
	 */
	public static Gson getCompactGson() {
		if (compactGson == null)
			compactGson = new GsonBuilder().addSerializationExclusionStrategy(new ExposeExlusion())
					.addDeserializationExclusionStrategy(new ExposeExlusion())
					.registerTypeHierarchyAdapter(ItemStack.class, new ItemStackGsonAdapter())
					.registerTypeAdapter(PotionEffect.class, new PotionEffectGsonAdapter())
					.registerTypeAdapter(Location.class, new LocationGsonAdapter())
					.registerTypeAdapter(Date.class, new DateGsonAdapter())
					.registerTypeAdapter(MaterialData.class, new MaterialDataGsonAdapter())
					.enableComplexMapKeySerialization().disableHtmlEscaping().create();
		return compactGson;
	}

	/**
	 * Creates a new instance of Gson for use anywhere
	 * <p>
	 * Use @GsonIgnore in order to skip serialization and deserialization
	 * </p>
	 * 
	 * @return a Gson instance
	 */
	public static Gson getNewGson(boolean prettyPrinting) {
		GsonBuilder builder = new GsonBuilder().addSerializationExclusionStrategy(new ExposeExlusion())
				.addDeserializationExclusionStrategy(new ExposeExlusion())
				.registerTypeHierarchyAdapter(ItemStack.class, new NewItemStackAdapter()).disableHtmlEscaping();
		if (prettyPrinting)
			builder.setPrettyPrinting();
		return builder.create();
	}

	private static Map<String, Object> recursiveSerialization(ConfigurationSerializable o) {
		Map<String, Object> originalMap = o.serialize();
		Map<String, Object> map = new HashMap<String, Object>();
		for (Entry<String, Object> entry : originalMap.entrySet()) {
			Object o2 = entry.getValue();
			if (o2 instanceof ConfigurationSerializable) {
				ConfigurationSerializable serializable = (ConfigurationSerializable) o2;
				Map<String, Object> newMap = recursiveSerialization(serializable);
				newMap.put(CLASS_KEY, ConfigurationSerialization.getAlias(serializable.getClass()));
				map.put(entry.getKey(), newMap);
			}
		}
		map.put(CLASS_KEY, ConfigurationSerialization.getAlias(o.getClass()));
		return map;
	}

	private static Map<String, Object> recursiveDoubleToInteger(Map<String, Object> originalMap) {
		Map<String, Object> map = new HashMap<String, Object>();
		for (Entry<String, Object> entry : originalMap.entrySet()) {
			Object o = entry.getValue();
			if (o instanceof Double) {
				Double d = (Double) o;
				Integer i = d.intValue();
				map.put(entry.getKey(), i);
			} else if (o instanceof Map) {
				Map<String, Object> subMap = (Map<String, Object>) o;
				map.put(entry.getKey(), recursiveDoubleToInteger(subMap));
			} else {
				map.put(entry.getKey(), o);
			}
		}
		return map;
	}

	private static class ExposeExlusion implements ExclusionStrategy {
		@Override
		public boolean shouldSkipField(FieldAttributes fieldAttributes) {
			final Ignore ignore = fieldAttributes.getAnnotation(Ignore.class);
			if (ignore != null)
				return true;
			final Expose expose = fieldAttributes.getAnnotation(Expose.class);
			return expose != null && (!expose.serialize() || !expose.deserialize());
		}

		@Override
		public boolean shouldSkipClass(Class<?> aClass) {
			return false;
		}
	}

	private static String nbtToString(NBTBase base) {
		return base.toString().replace(",}", "}").replace(",]", "]").replaceAll("[0-9]+\\:", "");
	}

	private static net.minecraft.server.v1_14_R1.ItemStack removeSlot(ItemStack item) {
		if (item == null)
			return null;
		net.minecraft.server.v1_14_R1.ItemStack nmsi = CraftItemStack.asNMSCopy(item);
		if (nmsi == null)
			return null;
		NBTTagCompound nbtt = nmsi.getTag();
		if (nbtt != null) {
			nbtt.remove("Slot");
			nmsi.setTag(nbtt);
		}
		return nmsi;
	}

	private static ItemStack removeSlotNBT(ItemStack item) {
		if (item == null)
			return null;
		net.minecraft.server.v1_14_R1.ItemStack nmsi = CraftItemStack.asNMSCopy(item);
		if (nmsi == null)
			return null;
		NBTTagCompound nbtt = nmsi.getTag();
		if (nbtt != null) {
			nbtt.remove("Slot");
			nmsi.setTag(nbtt);
		}
		return CraftItemStack.asBukkitCopy(nmsi);
	}

	private static class NewItemStackAdapter extends TypeAdapter<ItemStack> {
		@Override
		public void write(JsonWriter jsonWriter, ItemStack itemStack) throws IOException {
			if (itemStack == null) {
				jsonWriter.nullValue();
				return;
			}
			net.minecraft.server.v1_14_R1.ItemStack item = removeSlot(itemStack);
			if (item == null) {
				jsonWriter.nullValue();
				return;
			}
			try {
				jsonWriter.beginObject();
				jsonWriter.name("type");

				jsonWriter.value(itemStack.getType().toString()); // I hate
																	// using
																	// this -
																	// but
				jsonWriter.name("amount");

				jsonWriter.value(itemStack.getAmount());
				jsonWriter.name("data");

				jsonWriter.value(itemStack.getDurability());
				jsonWriter.name("tag");

				if (item != null && item.getTag() != null) {
					jsonWriter.value(nbtToString(item.getTag()));
				} else
					jsonWriter.value("");
				jsonWriter.endObject();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		@Override
		public ItemStack read(JsonReader jsonReader) throws IOException {
			if (jsonReader.peek() == JsonToken.NULL) {
				return null;
			}
			jsonReader.beginObject();
			jsonReader.nextName();
			Material type = Material.getMaterial(jsonReader.nextString());
			jsonReader.nextName();
			int amount = jsonReader.nextInt();
			jsonReader.nextName();
			int data = jsonReader.nextInt();
			net.minecraft.server.v1_14_R1.ItemStack item = new net.minecraft.server.v1_14_R1.ItemStack(
					CraftMagicNumbers.getItem(type), amount);
			jsonReader.nextName();
			String next = jsonReader.nextString();
			if (next.startsWith("{")) {
				NBTTagCompound compound = null;
				try {
					compound = MojangsonParser.parse(ChatColor.translateAlternateColorCodes('&', next));
				} catch (Exception e) {
					e.printStackTrace();
				}
				item.setTag(compound);
			}
			jsonReader.endObject();
			return CraftItemStack.asBukkitCopy(item);
		}
	}

	private static class ItemStackGsonAdapter extends TypeAdapter<ItemStack> {

		private static Type seriType = new TypeToken<Map<String, Object>>() {
		}.getType();

		@Override
		public void write(JsonWriter jsonWriter, ItemStack itemStack) throws IOException {
			if (itemStack == null) {
				jsonWriter.nullValue();
				return;
			}
			jsonWriter.value(getRaw(removeSlotNBT(itemStack)));
		}

		@Override
		public ItemStack read(JsonReader jsonReader) throws IOException {
			if (jsonReader.peek() == JsonToken.NULL) {
				jsonReader.nextNull();
				return null;
			}
			return fromRaw(jsonReader.nextString());
		}

		private static final String[] BYPASS_CLASS = { "CraftMetaBlockState", "CraftMetaItem"
				/* Glowstone Support */ , "GlowMetaItem" };

		/**
		 * Parse the {@link ItemStack} to JSON
		 *
		 * @param itemStack
		 *            The {@link ItemStack} instance
		 * @return The JSON string
		 */
		public static String getRaw(ItemStack itemStack) {

			Gson gson = new Gson();
			JsonObject itemJson = new JsonObject();

			String name = itemStack.getType().name();
			if (name == null || name.equalsIgnoreCase("AIR")) {
				name = XMaterial.matchXMaterial(itemStack).toString();

			}

			net.minecraft.server.v1_14_R1.ItemStack nmsStack = CraftItemStack.asNMSCopy(itemStack);
			if (nmsStack != null && nmsStack.getTag() != null && nmsStack.getTag().getKeys() != null
					&& !nmsStack.getTag().getKeys().isEmpty()) {
				JsonObject nbtInt = new JsonObject();
				JsonObject nbtShort = new JsonObject();
				JsonObject nbtByte = new JsonObject();
				JsonObject nbtDouble = new JsonObject();
				JsonObject nbtString = new JsonObject();
				JsonObject nbtLong = new JsonObject();
				JsonObject nbtFloat = new JsonObject();
				// JsonObject nbtList = new JsonObject();
				// JsonObject nbtByteArray = new JsonObject();
				// JsonObject nbtIntArray = new JsonObject();
				for (String key : nmsStack.getTag().getKeys()) {
					if (nmsStack.getTag().get(key) instanceof net.minecraft.server.v1_14_R1.NBTTagByte) {
						nbtByte.addProperty(key, nmsStack.getTag().get(key).toString());
					} else if (nmsStack.getTag().get(key) instanceof net.minecraft.server.v1_14_R1.NBTTagDouble) {
						nbtDouble.addProperty(key, nmsStack.getTag().get(key).toString());
					} else if (nmsStack.getTag().get(key) instanceof net.minecraft.server.v1_14_R1.NBTTagFloat) {
						nbtFloat.addProperty(key, nmsStack.getTag().get(key).toString());
					} else if (nmsStack.getTag().get(key) instanceof net.minecraft.server.v1_14_R1.NBTTagInt) {
						nbtInt.addProperty(key, nmsStack.getTag().get(key).toString());
					} else if (nmsStack.getTag().get(key) instanceof net.minecraft.server.v1_14_R1.NBTTagLong) {
						nbtLong.addProperty(key, nmsStack.getTag().get(key).toString());
					} else if (nmsStack.getTag().get(key) instanceof net.minecraft.server.v1_14_R1.NBTTagShort) {
						nbtShort.addProperty(key, nmsStack.getTag().get(key).toString());
					} else if (nmsStack.getTag().get(key) instanceof net.minecraft.server.v1_14_R1.NBTTagString) {

						nbtString.addProperty(key, StringEscapeUtils.unescapeJava(nmsStack.getTag().get(key).toString())
								.replaceAll("\"", ""));
					}

				}

				itemJson.add("nbtInt", nbtInt);
				itemJson.add("nbtShort", nbtShort);
				itemJson.add("nbtByte", nbtByte);
				itemJson.add("nbtDouble", nbtDouble);
				itemJson.add("nbtString", nbtString);
				itemJson.add("nbtLong", nbtLong);
				itemJson.add("nbtFloat", nbtFloat);

				ByteArrayOutputStream out = new ByteArrayOutputStream();

				try {
					net.minecraft.server.v1_14_R1.NBTCompressedStreamTools.a(nmsStack.getTag(), out);

					String s = "";
					for (byte b : out.toByteArray()) {
						s += b + ";";
					}

					String dataNBT = s.substring(0, s.length() - 1);
					// StringEscapeUtils.unescapeJava(out.toString("UTF-8")).replaceAll("\"",
					// "");

					itemJson.add("compressedNBT", new JsonPrimitive(dataNBT));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			itemJson.addProperty("type", itemStack.getType().name());

			if (itemStack.getDurability() > 0)
				itemJson.addProperty("data", itemStack.getDurability());
			if (itemStack.getAmount() != 1)
				itemJson.addProperty("amount", itemStack.getAmount());

			if (itemStack.hasItemMeta()) {
				JsonObject metaJson = new JsonObject();

				ItemMeta meta = itemStack.getItemMeta();

				if (meta.hasDisplayName()) {
					metaJson.addProperty("displayname", meta.getDisplayName());
				}
				if (meta.hasLore()) {
					JsonArray lore = new JsonArray();
					meta.getLore().forEach(str -> lore.add(new JsonPrimitive(str)));
					metaJson.add("lore", lore);
				}
				if (meta.hasEnchants()) {
					JsonArray enchants = new JsonArray();
					meta.getEnchants().forEach((enchantment, integer) -> {
						enchants.add(new JsonPrimitive(enchantment.getName() + ":" + integer));
					});
					metaJson.add("enchants", enchants);
				}
				if (!meta.getItemFlags().isEmpty()) {
					JsonArray flags = new JsonArray();
					meta.getItemFlags().stream().map(ItemFlag::name).forEach(str -> flags.add(new JsonPrimitive(str)));
					metaJson.add("flags", flags);
				}

				for (String clazz : BYPASS_CLASS) {
					if (meta.getClass().getSimpleName().equals(clazz)) {
						itemJson.add("item-meta", metaJson);
						return gson.toJson(itemJson);
					}
				}

				if (meta instanceof SkullMeta) {
					SkullMeta skullMeta = (SkullMeta) meta;
					if (skullMeta.hasOwner()) {
						JsonObject extraMeta = new JsonObject();
						extraMeta.addProperty("owner", skullMeta.getOwner());
						metaJson.add("extra-meta", extraMeta);
					}

					Field profileField = null;
					try {
						JsonObject extraMeta = new JsonObject();

						profileField = meta.getClass().getDeclaredField("profile");
						profileField.setAccessible(true);
						GameProfile profile = (GameProfile) profileField.get(meta);
						if (profile != null) {
							extraMeta.addProperty("uuid", profile.getId().toString());

							Collection<Property> properties = profile.getProperties().get("textures");
							if (properties != null && !properties.isEmpty()) {
								Property prop = null;
								for (Property pr : properties) {
									prop = pr;
								}
								extraMeta.addProperty("value", prop.getValue());
								extraMeta.addProperty("name", prop.getName());
								extraMeta.addProperty("signature", prop.getSignature());

							}
							metaJson.add("game-profile", extraMeta);
						}
					} catch (Exception exception) {
						// exception.printStackTrace();
					}
				} else if (meta instanceof BannerMeta) {
					BannerMeta bannerMeta = (BannerMeta) meta;
					JsonObject extraMeta = new JsonObject();
					if(bannerMeta.getBaseColor()!=null)
					extraMeta.addProperty("base-color", bannerMeta.getBaseColor().name());

					if (bannerMeta.numberOfPatterns() > 0) {
						JsonArray patterns = new JsonArray();
						bannerMeta.getPatterns().stream()
								.map(pattern -> pattern.getColor().name() + ":" + pattern.getPattern().getIdentifier())
								.forEach(str -> patterns.add(new JsonPrimitive(str)));
						extraMeta.add("patterns", patterns);
					}

					metaJson.add("extra-meta", extraMeta);
				} else if (meta instanceof EnchantmentStorageMeta) {
					EnchantmentStorageMeta esmeta = (EnchantmentStorageMeta) meta;
					if (esmeta.hasStoredEnchants()) {
						JsonObject extraMeta = new JsonObject();
						JsonArray storedEnchants = new JsonArray();
						esmeta.getStoredEnchants().forEach((enchantment, integer) -> {
							storedEnchants.add(new JsonPrimitive(enchantment.getName() + ":" + integer));
						});
						extraMeta.add("stored-enchants", storedEnchants);
						metaJson.add("extra-meta", extraMeta);
					}
				} else if (meta instanceof LeatherArmorMeta) {
					LeatherArmorMeta lameta = (LeatherArmorMeta) meta;
					JsonObject extraMeta = new JsonObject();
					extraMeta.addProperty("color", Integer.toHexString(lameta.getColor().asRGB()));
					metaJson.add("extra-meta", extraMeta);
				} else if (meta instanceof BookMeta) {
					BookMeta bmeta = (BookMeta) meta;
					if (bmeta.hasAuthor() || bmeta.hasPages() || bmeta.hasTitle()) {
						JsonObject extraMeta = new JsonObject();
						if (bmeta.hasTitle()) {
							extraMeta.addProperty("title", bmeta.getTitle());
						}
						if (bmeta.hasAuthor()) {
							extraMeta.addProperty("author", bmeta.getAuthor());
						}
						if (bmeta.hasPages()) {
							JsonArray pages = new JsonArray();
							bmeta.getPages().forEach(str -> pages.add(new JsonPrimitive(str)));
							extraMeta.add("pages", pages);
						}
						metaJson.add("extra-meta", extraMeta);
					}
				} else if (meta instanceof PotionMeta) {
					PotionMeta pmeta = (PotionMeta) meta;

					if (pmeta.hasCustomEffects()) {
						JsonObject extraMeta = new JsonObject();

						JsonArray customEffects = new JsonArray();
						pmeta.getCustomEffects().forEach(potionEffect -> {
							customEffects.add(new JsonPrimitive(potionEffect.getType().getName() + ":"
									+ potionEffect.getAmplifier() + ":" + potionEffect.getDuration() / 20));
						});
						extraMeta.add("custom-effects", customEffects);

						metaJson.add("extra-meta", extraMeta);
					} else {
						try {

							Method metodo = pmeta.getClass().getMethod("getBasePotionData");
							metodo.setAccessible(true);
							PotionData data = (PotionData) metodo.invoke(pmeta);
							Method metodo2 = data.getClass().getMethod("getType");
							metodo2.setAccessible(true);
							PotionType potType = (PotionType) metodo2.invoke(data);

							JsonObject potMeta = new JsonObject();
							potMeta.add("type", new JsonPrimitive(potType.toString()));
							potMeta.add("extended", new JsonPrimitive(data.isExtended()));
							potMeta.add("upgraded", new JsonPrimitive(data.isUpgraded()));
							metaJson.add("potion-meta", potMeta);

						} catch (NoSuchMethodException | SecurityException | IllegalAccessException
								| IllegalArgumentException | InvocationTargetException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}

				} else if (meta instanceof FireworkEffectMeta) {
					FireworkEffectMeta femeta = (FireworkEffectMeta) meta;
					if (femeta.hasEffect()) {
						FireworkEffect effect = femeta.getEffect();
						JsonObject extraMeta = new JsonObject();

						extraMeta.addProperty("type", effect.getType().name());
						if (effect.hasFlicker())
							extraMeta.addProperty("flicker", true);
						if (effect.hasTrail())
							extraMeta.addProperty("trail", true);

						if (!effect.getColors().isEmpty()) {
							JsonArray colors = new JsonArray();
							effect.getColors().forEach(
									color -> colors.add(new JsonPrimitive(Integer.toHexString(color.asRGB()))));
							extraMeta.add("colors", colors);
						}

						if (!effect.getFadeColors().isEmpty()) {
							JsonArray fadeColors = new JsonArray();
							effect.getFadeColors().forEach(
									color -> fadeColors.add(new JsonPrimitive(Integer.toHexString(color.asRGB()))));
							extraMeta.add("fade-colors", fadeColors);
						}

						metaJson.add("extra-meta", extraMeta);
					}
				} else if (meta instanceof FireworkMeta) {
					FireworkMeta fmeta = (FireworkMeta) meta;

					JsonObject extraMeta = new JsonObject();

					extraMeta.addProperty("power", fmeta.getPower());

					if (fmeta.hasEffects()) {
						JsonArray effects = new JsonArray();
						fmeta.getEffects().forEach(effect -> {
							JsonObject jsonObject = new JsonObject();

							jsonObject.addProperty("type", effect.getType().name());
							if (effect.hasFlicker())
								jsonObject.addProperty("flicker", true);
							if (effect.hasTrail())
								jsonObject.addProperty("trail", true);

							if (!effect.getColors().isEmpty()) {
								JsonArray colors = new JsonArray();
								effect.getColors().forEach(
										color -> colors.add(new JsonPrimitive(Integer.toHexString(color.asRGB()))));
								jsonObject.add("colors", colors);
							}

							if (!effect.getFadeColors().isEmpty()) {
								JsonArray fadeColors = new JsonArray();
								effect.getFadeColors().forEach(
										color -> fadeColors.add(new JsonPrimitive(Integer.toHexString(color.asRGB()))));
								jsonObject.add("fade-colors", fadeColors);
							}

							effects.add(jsonObject);
						});
						extraMeta.add("effects", effects);
					}
					metaJson.add("extra-meta", extraMeta);
				} else if (meta instanceof MapMeta) {
					MapMeta mmeta = (MapMeta) meta;
					JsonObject extraMeta = new JsonObject();

					/*
					 * 1.11 if(mmeta.hasLocationName()) {
					 * extraMeta.addProperty("location-name",
					 * mmeta.getLocationName()); } if(mmeta.hasColor()) {
					 * extraMeta.addProperty("color",
					 * Integer.toHexString(mmeta.getColor().asRGB())); }
					 */
					extraMeta.addProperty("scaling", mmeta.isScaling());

					metaJson.add("extra-meta", extraMeta);
				}

				itemJson.add("item-meta", metaJson);
			}
			return gson.toJson(itemJson);
		}

		/**
		 * Parse a JSON to {@link ItemStack}
		 *
		 * @param string
		 *            The JSON string
		 * @return The {@link ItemStack} or null if not succeed
		 */
		public static ItemStack fromRaw(String string) {
			JsonParser parser = new JsonParser();
			JsonElement element = parser.parse(string);
			if (element.isJsonObject()) {
				JsonObject itemJson = element.getAsJsonObject();

				JsonElement typeElement = itemJson.get("type");
				JsonElement dataElement = itemJson.get("data");
				JsonElement amountElement = itemJson.get("amount");

				if (typeElement.isJsonPrimitive()) {

					String type = typeElement.getAsString();
					short data = dataElement != null ? dataElement.getAsShort() : 0;
					int amount = amountElement != null ? amountElement.getAsInt() : 1;

					Material mat = null;
					mat = Material.getMaterial(type);
					if (mat == null || mat.equals(XMaterial.AIR.parseMaterial())) {
						XMaterial opt = XMaterial.valueOf(type);
						if (opt != null) {
							mat = opt.parseMaterial();
						}
					}

					ItemStack itemStack = new ItemStack(mat);
					if (itemJson.has("nbtInt")) {
						itemStack = getItemWithNBT(itemStack, itemJson);
					}
					itemStack.setDurability(data);
					itemStack.setAmount(amount);

					JsonElement itemMetaElement = itemJson.get("item-meta");

					if (itemMetaElement != null && itemMetaElement.isJsonObject()) {

						ItemMeta meta = itemStack.getItemMeta();
						JsonObject metaJson = itemMetaElement.getAsJsonObject();

						JsonElement displaynameElement = metaJson.get("displayname");
						JsonElement loreElement = metaJson.get("lore");
						JsonElement enchants = metaJson.get("enchants");
						JsonElement flagsElement = metaJson.get("flags");

						if (displaynameElement != null && displaynameElement.isJsonPrimitive()) {
							meta.setDisplayName(displaynameElement.getAsString().replaceAll("\\?0", "§0")
									.replaceAll("\\?1", "§1").replaceAll("\\?2", "§2").replaceAll("\\?3", "§3")
									.replaceAll("\\?4", "§4").replaceAll("\\?5", "§5").replaceAll("\\?6", "§6")
									.replaceAll("\\?4", "§4").replaceAll("\\?7", "§7").replaceAll("\\?8", "§8")
									.replaceAll("\\?9", "§9").replaceAll("\\?a", "§a").replaceAll("\\?b", "§b")
									.replaceAll("\\?c", "§c").replaceAll("\\?d", "§d").replaceAll("\\?e", "§e")
									.replaceAll("\\?f", "§f").replaceAll("\\?g", "§g").replaceAll("\\?k", "§k")
									.replaceAll("\\?l", "§l").replaceAll("\\?m", "§m").replaceAll("\\?n", "§n")
									.replaceAll("\\?o", "§o").replaceAll("\\?r", "§r").replaceAll("\\?A", "§A")
									.replaceAll("\\?B", "§B").replaceAll("\\?C", "§C").replaceAll("\\?D", "§D")
									.replaceAll("\\?E", "§E").replaceAll("\\?F", "§F").replaceAll("\\?x", "§x"));
						}

						if (loreElement != null && loreElement.isJsonArray()) {
							JsonArray jarray = loreElement.getAsJsonArray();
							List<String> lore = new ArrayList<>(jarray.size());
							jarray.forEach(jsonElement -> {
								if (jsonElement.isJsonPrimitive())
									lore.add(jsonElement.getAsString().replaceAll("\\?0", "§0").replaceAll("\\?1", "§1")
											.replaceAll("\\?2", "§2").replaceAll("\\?3", "§3").replaceAll("\\?4", "§4")
											.replaceAll("\\?5", "§5").replaceAll("\\?6", "§6").replaceAll("\\?4", "§4")
											.replaceAll("\\?7", "§7").replaceAll("\\?8", "§8").replaceAll("\\?9", "§9")
											.replaceAll("\\?a", "§a").replaceAll("\\?b", "§b").replaceAll("\\?c", "§c")
											.replaceAll("\\?d", "§d").replaceAll("\\?e", "§e").replaceAll("\\?f", "§f")
											.replaceAll("\\?g", "§g").replaceAll("\\?k", "§k").replaceAll("\\?l", "§l")
											.replaceAll("\\?m", "§m").replaceAll("\\?n", "§n").replaceAll("\\?o", "§o")
											.replaceAll("\\?r", "§r").replaceAll("\\?A", "§A").replaceAll("\\?B", "§B")
											.replaceAll("\\?C", "§C").replaceAll("\\?D", "§D").replaceAll("\\?E", "§E")
											.replaceAll("\\?F", "§F").replaceAll("\\?x", "§x"));
							});
							meta.setLore(lore);
						}

						if (enchants != null && enchants.isJsonArray()) {
							JsonArray jarray = enchants.getAsJsonArray();
							jarray.forEach(jsonElement -> {
								if (jsonElement.isJsonPrimitive()) {
									String enchantString = jsonElement.getAsString();
									if (enchantString.contains(":")) {
										try {
											String[] splitEnchant = enchantString.split(":");
											Enchantment enchantment = Enchantment.getByName(splitEnchant[0]);
											int level = Integer.parseInt(splitEnchant[1]);
											if (enchantment != null && level > 0) {
												meta.addEnchant(enchantment, level, true);
											}
										} catch (NumberFormatException ex) {
										}
									}
								}
							});
						}

						if (flagsElement != null && flagsElement.isJsonArray()) {
							JsonArray jarray = flagsElement.getAsJsonArray();
							jarray.forEach(jsonElement -> {
								if (jsonElement.isJsonPrimitive()) {
									for (ItemFlag flag : ItemFlag.values()) {
										if (flag.name().equalsIgnoreCase(jsonElement.getAsString())) {
											meta.addItemFlags(flag);
											break;
										}
									}
								}
							});
						}
						if (meta instanceof PotionMeta) {
							JsonObject potionMetaElement = metaJson.getAsJsonObject("potion-meta");

							PotionMeta pmeta = (PotionMeta) meta;

							if (potionMetaElement != null) {
								try {
									Method metodo = pmeta.getClass().getMethod("setBasePotionData", PotionData.class);
									metodo.setAccessible(true);
									PotionData potData = new PotionData(
											PotionType.valueOf(potionMetaElement.get("type").getAsString()),
											potionMetaElement.get("extended").getAsBoolean(),
											potionMetaElement.get("upgraded").getAsBoolean());
									metodo.invoke(pmeta, potData);
								} catch (Exception e) {
									System.out.println("Failed to parse potion");
								}
							}
							itemStack.setItemMeta(pmeta);

						}
						for (String clazz : BYPASS_CLASS) {
							if (meta.getClass().getSimpleName().equals(clazz)) {
								itemStack.setItemMeta(meta);
								return itemStack;
							}
						}

						JsonElement extrametaElement = metaJson.get("extra-meta");

						if (extrametaElement != null && extrametaElement.isJsonObject()) {
							try {
								JsonObject extraJson = extrametaElement.getAsJsonObject();
								if (meta instanceof SkullMeta) {
									JsonElement ownerElement = extraJson.get("owner");
									if (ownerElement != null && ownerElement.isJsonPrimitive()) {
										SkullMeta smeta = (SkullMeta) meta;
										smeta.setOwner(ownerElement.getAsString());
										JsonElement gameprofileElement = metaJson.get("game-profile");
										if (gameprofileElement != null && gameprofileElement.isJsonObject()) {
											JsonObject gameprofileJson = gameprofileElement.getAsJsonObject();

											JsonElement uuiidElement = gameprofileJson.get("uuid");
											if (uuiidElement != null && uuiidElement.isJsonPrimitive()) {

												GameProfile gp = new GameProfile(
														UUID.fromString(uuiidElement.getAsString()), null);
												JsonElement valueElement = gameprofileJson.get("value");
												JsonElement signatureElement = gameprofileJson.get("signature");
												String value = null;
												String signature = null;
												if (valueElement != null && valueElement.isJsonPrimitive()) {
													value = valueElement.getAsString();
												}
												if (signatureElement != null && signatureElement.isJsonPrimitive()) {
													signature = signatureElement.getAsString();
												}
												if (value != null || signature != null) {
													gp.getProperties().put("textures",
															new Property("textures", value, signature));

													Field profileField = null;
													try {
														profileField = smeta.getClass().getDeclaredField("profile");
														profileField.setAccessible(true);
														profileField.set(smeta, gp);
													} catch (NoSuchFieldException | IllegalArgumentException
															| IllegalAccessException exception) {
														exception.printStackTrace();
													}
												}
											}

										}
									}
								} else if (meta instanceof BannerMeta) {
									JsonElement baseColorElement = extraJson.get("base-color");
									JsonElement patternsElement = extraJson.get("patterns");
									BannerMeta bmeta = (BannerMeta) meta;
									if (baseColorElement != null && baseColorElement.isJsonPrimitive()) {
										try {
											Optional<DyeColor> color = Arrays.stream(DyeColor.values())
													.filter(dyeColor -> dyeColor.name()
															.equalsIgnoreCase(baseColorElement.getAsString()))
													.findFirst();
											if (color.isPresent()) {
												bmeta.setBaseColor(color.get());
											}
										} catch (NumberFormatException ex) {
										}
									}
									if (patternsElement != null && patternsElement.isJsonArray()) {
										JsonArray jarray = patternsElement.getAsJsonArray();
										List<Pattern> patterns = new ArrayList<>(jarray.size());
										jarray.forEach(jsonElement -> {
											String patternString = jsonElement.getAsString();
											if (patternString.contains(":")) {
												String[] splitPattern = patternString.split(":");
												Optional<DyeColor> color = Arrays.stream(DyeColor.values()).filter(
														dyeColor -> dyeColor.name().equalsIgnoreCase(splitPattern[0]))
														.findFirst();
												PatternType patternType = PatternType.getByIdentifier(splitPattern[1]);
												if (color.isPresent() && patternType != null) {
													patterns.add(new Pattern(color.get(), patternType));
												}
											}
										});
										if (!patterns.isEmpty())
											bmeta.setPatterns(patterns);
									}
								} else if (meta instanceof EnchantmentStorageMeta) {
									JsonElement storedEnchantsElement = extraJson.get("stored-enchants");
									if (storedEnchantsElement != null && storedEnchantsElement.isJsonArray()) {
										EnchantmentStorageMeta esmeta = (EnchantmentStorageMeta) meta;
										JsonArray jarray = storedEnchantsElement.getAsJsonArray();
										jarray.forEach(jsonElement -> {
											if (jsonElement.isJsonPrimitive()) {
												String enchantString = jsonElement.getAsString();
												if (enchantString.contains(":")) {
													try {
														String[] splitEnchant = enchantString.split(":");
														Enchantment enchantment = Enchantment
																.getByName(splitEnchant[0]);
														int level = Integer.parseInt(splitEnchant[1]);
														if (enchantment != null && level > 0) {
															esmeta.addStoredEnchant(enchantment, level, true);
														}
													} catch (NumberFormatException ex) {
													}
												}
											}
										});
									}
								} else if (meta instanceof LeatherArmorMeta) {
									JsonElement colorElement = extraJson.get("color");
									if (colorElement != null && colorElement.isJsonPrimitive()) {
										LeatherArmorMeta lameta = (LeatherArmorMeta) meta;
										try {
											lameta.setColor(
													Color.fromRGB(Integer.parseInt(colorElement.getAsString(), 16)));
										} catch (NumberFormatException ex) {
										}
									}
								} else if (meta instanceof BookMeta) {
									JsonElement titleElement = extraJson.get("title");
									JsonElement authorElement = extraJson.get("author");
									JsonElement pagesElement = extraJson.get("pages");

									BookMeta bmeta = (BookMeta) meta;
									if (titleElement != null && titleElement.isJsonPrimitive()) {
										bmeta.setTitle(titleElement.getAsString());
									}
									if (authorElement != null && authorElement.isJsonPrimitive()) {
										bmeta.setAuthor(authorElement.getAsString());
									}
									if (pagesElement != null && pagesElement.isJsonArray()) {
										JsonArray jarray = pagesElement.getAsJsonArray();
										List<String> pages = new ArrayList<>(jarray.size());
										jarray.forEach(jsonElement -> {
											if (jsonElement.isJsonPrimitive())
												pages.add(jsonElement.getAsString());
										});
										bmeta.setPages(pages);
									}

								} else if (meta instanceof PotionMeta) {
									JsonElement customEffectsElement = extraJson.get("custom-effects");
									JsonObject potionMetaElement = metaJson.getAsJsonObject("potion-meta");

									PotionMeta pmeta = (PotionMeta) meta;
									if (customEffectsElement != null && customEffectsElement.isJsonArray()) {
										JsonArray jarray = customEffectsElement.getAsJsonArray();
										jarray.forEach(jsonElement -> {
											if (jsonElement.isJsonPrimitive()) {
												String enchantString = jsonElement.getAsString();
												if (enchantString.contains(":")) {
													try {
														String[] splitPotions = enchantString.split(":");
														PotionEffectType potionType = PotionEffectType
																.getByName(splitPotions[0]);
														int amplifier = Integer.parseInt(splitPotions[1]);
														int duration = Integer.parseInt(splitPotions[2]) * 20;
														if (potionType != null) {
															pmeta.addCustomEffect(
																	new PotionEffect(potionType, duration, amplifier),
																	true);
														}
													} catch (NumberFormatException ex) {
													}
												}
											}
										});
									}
									if (potionMetaElement != null) {
										Method metodo = pmeta.getClass().getMethod("setBasePotionData",
												PotionData.class);
										metodo.setAccessible(true);
										PotionData potData = new PotionData(
												PotionType.valueOf(potionMetaElement.get("type").getAsString()),
												potionMetaElement.get("extended").getAsBoolean(),
												potionMetaElement.get("upgraded").getAsBoolean());
										metodo.invoke(pmeta, potData);
									}
									itemStack.setItemMeta(pmeta);

								} else if (meta instanceof FireworkEffectMeta) {
								} else if (meta instanceof FireworkMeta) {
								} else if (meta instanceof MapMeta) {
									MapMeta mmeta = (MapMeta) meta;

									JsonElement scalingElement = extraJson.get("scaling");
									if (scalingElement != null && scalingElement.isJsonPrimitive()) {
										mmeta.setScaling(scalingElement.getAsBoolean());
									}

									/*
									 * 1.11 JsonElement locationNameElement =
									 * extraJson.get("location-name");
									 * if(locationNameElement != null &&
									 * locationNameElement.isJsonPrimitive()) {
									 * mmeta.setLocationName(locationNameElement
									 * .getAsString()); } JsonElement
									 * colorElement = extraJson.get("color");
									 * if(colorElement != null &&
									 * colorElement.isJsonPrimitive()) {
									 * mmeta.setColor(Color.fromRGB(Integer.
									 * parseInt(colorElement.getAsString(),
									 * 16))); }
									 */
								}
							} catch (Exception e) {
								System.out.println("Error parsing ItemMeta");
								return null;
							}
						}

						itemStack.setItemMeta(meta);
					}
					return itemStack;
				} else
					return null;
			} else
				return null;
		}

		private static ItemStack getItemWithNBT(ItemStack itemStack, JsonObject itemJson) {
			net.minecraft.server.v1_14_R1.ItemStack nmsStack = CraftItemStack.asNMSCopy(itemStack);
			Boolean enchanted = Boolean.FALSE;
			JsonElement nbtInt = itemJson.get("nbtInt");
			JsonElement nbtShort = itemJson.get("nbtShort");
			JsonElement nbtByte = itemJson.get("nbtByte");
			JsonElement nbtDouble = itemJson.get("nbtDouble");
			JsonElement nbtString = itemJson.get("nbtString");
			JsonElement nbtLong = itemJson.get("nbtLong");
			JsonElement nbtFloat = itemJson.get("nbtFloat");
			JsonElement compressedNBT = itemJson.get("compressedNBT");
			// JsonElement nbtList = itemJson.get("nbtList");
			// JsonElement nbtByteArray = itemJson.get("nbtByteArray");
			// JsonElement nbtIntArray = itemJson.get("nbtIntArray");

			if (nmsStack != null) {
				NBTTagCompound tag = nmsStack.getTag();
				if (tag == null) {
					tag = new NBTTagCompound();
				}
				if (nbtInt != null && nbtInt.isJsonObject()) {
					for (Entry<String, JsonElement> entrada : nbtInt.getAsJsonObject().entrySet()) {
						if (tag != null && tag.getKeys() != null && !tag.getKeys().contains(entrada.getKey())) {
							if (entrada.getKey().contains("enchant")) {
								enchanted = Boolean.TRUE;
							}
							tag.setInt(entrada.getKey(), Integer.valueOf(entrada.getValue().getAsString()));
						}
					}
				}
				if (nbtShort != null && nbtShort.isJsonObject()) {
					for (Entry<String, JsonElement> entrada : nbtShort.getAsJsonObject().entrySet()) {
						if (tag != null && tag.getKeys() != null && !tag.getKeys().contains(entrada.getKey())) {
							if (entrada.getKey().contains("enchant")) {
								enchanted = Boolean.TRUE;
							}
							tag.setShort(entrada.getKey(), Short.valueOf(entrada.getValue().getAsString()));
						}
					}
				}
				if (nbtByte != null && nbtByte.isJsonObject()) {
					for (Entry<String, JsonElement> entrada : nbtByte.getAsJsonObject().entrySet()) {
						if (tag != null && tag.getKeys() != null && !tag.getKeys().contains(entrada.getKey())) {
							if (entrada.getKey().contains("enchant")) {
								enchanted = Boolean.TRUE;
							}
							tag.setByte(entrada.getKey(),
									Byte.valueOf(entrada.getValue().getAsString().replaceAll("b", "")));
						}
					}
				}
				if (nbtDouble != null && nbtDouble.isJsonObject()) {
					for (Entry<String, JsonElement> entrada : nbtDouble.getAsJsonObject().entrySet()) {
						if (tag != null && tag.getKeys() != null && !tag.getKeys().contains(entrada.getKey())) {
							if (entrada.getKey().contains("enchant")) {
								enchanted = Boolean.TRUE;
							}
							tag.setDouble(entrada.getKey(), Double.valueOf(entrada.getValue().getAsString()));
						}
					}
				}
				if (nbtString != null && nbtString.isJsonObject()) {
					for (Entry<String, JsonElement> entrada : nbtString.getAsJsonObject().entrySet()) {
						if (tag != null && tag.getKeys() != null && !tag.getKeys().contains(entrada.getKey())) {
							if (entrada.getKey().contains("enchant")) {
								enchanted = Boolean.TRUE;
							}
							tag.setString(entrada.getKey(), entrada.getValue().getAsString());
						}
					}
				}
				if (nbtLong != null && nbtLong.isJsonObject()) {
					for (Entry<String, JsonElement> entrada : nbtLong.getAsJsonObject().entrySet()) {
						if (tag != null && tag.getKeys() != null && !tag.getKeys().contains(entrada.getKey())) {
							if (entrada.getKey().contains("enchant")) {
								enchanted = Boolean.TRUE;
							}
							tag.setLong(entrada.getKey(), Long
									.valueOf(entrada.getValue().getAsString().replaceAll("l", "").replaceAll("L", "")));
						}
					}
				}
				if (nbtFloat != null && nbtFloat.isJsonObject()) {
					for (Entry<String, JsonElement> entrada : nbtFloat.getAsJsonObject().entrySet()) {
						if (tag != null && tag.getKeys() != null && !tag.getKeys().contains(entrada.getKey())) {
							if (entrada.getKey().contains("enchant")) {
								enchanted = Boolean.TRUE;
							}
							tag.setFloat(entrada.getKey(), Float.valueOf(entrada.getValue().getAsString()));
						}
					}
				}

				// if (nbtList != null && nbtList.isJsonObject()) {
				// for (Entry<String, JsonElement> entrada :
				// nbtList.getAsJsonObject().entrySet()) {
				// if (tag != null && tag.getKeys() != null &&
				// !tag.getKeys().contains(entrada.getKey())) {
				// tag.setFloat(entrada.getKey(),
				// Float.valueOf(entrada.getValue().getAsString()));
				// NBTTagList tagList = new NBTTagList();
				// String valueList = entrada.getValue().getAsString();
				// String[] valueParts = valueList.split(";");
				// for (String part : valueParts) {
				// String[] entryValue = part.split("|");
				// switch (entryValue[0]) {
				// case "byte":
				//
				// tagList.add(new NBTTag(
				// Byte.valueOf(entrada.getValue().getAsString().replaceAll("b",
				// ""))));
				// break;
				// case "double":
				// break;
				// case "float":
				// break;
				// case "int":
				// break;
				// case "long":
				// break;
				// case "short":
				// break;
				// case "string":
				// break;
				// default:
				// break;
				// }
				// }
				//
				//
				// tagList.add(nbtbase);
				// }
				// }
				// }

				if (enchanted) {
					tag.set("ench", new net.minecraft.server.v1_14_R1.NBTTagList());
				}

				if (compressedNBT != null && compressedNBT.isJsonPrimitive()) {
					ByteArrayInputStream is = null;
					String strStream = "";
					try {

						strStream = new String(compressedNBT.getAsString());
						String[] splitted = strStream.split(";");
						byte[] bytes = new byte[splitted.length];
						for (int i = 0; i < splitted.length; i++) {
							bytes[i] = Byte.valueOf(splitted[i]).byteValue();
						}

						is = new ByteArrayInputStream(bytes);

						tag = net.minecraft.server.v1_14_R1.NBTCompressedStreamTools.a(is);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

				nmsStack.setTag(tag);
				itemStack = CraftItemStack.asBukkitCopy(nmsStack);
			}
			return itemStack;
		}

	}

	private static class PotionEffectGsonAdapter extends TypeAdapter<PotionEffect> {

		private static Type seriType = new TypeToken<Map<String, Object>>() {
		}.getType();

		private static String TYPE = "effect";
		private static String DURATION = "duration";
		private static String AMPLIFIER = "amplifier";
		private static String AMBIENT = "ambient";

		@Override
		public void write(JsonWriter jsonWriter, PotionEffect potionEffect) throws IOException {
			if (potionEffect == null) {
				jsonWriter.nullValue();
				return;
			}
			jsonWriter.value(getRaw(potionEffect));
		}

		@Override
		public PotionEffect read(JsonReader jsonReader) throws IOException {
			if (jsonReader.peek() == JsonToken.NULL) {
				jsonReader.nextNull();
				return null;
			}
			return fromRaw(jsonReader.nextString());
		}

		private String getRaw(PotionEffect potion) {
			Map<String, Object> serial = potion.serialize();

			return g.toJson(serial);
		}

		private PotionEffect fromRaw(String raw) {
			Map<String, Object> keys = g.fromJson(raw, seriType);
			return new PotionEffect(PotionEffectType.getById(((Double) keys.get(TYPE)).intValue()),
					((Double) keys.get(DURATION)).intValue(), ((Double) keys.get(AMPLIFIER)).intValue(),
					(Boolean) keys.get(AMBIENT));
		}
	}

	private static class LocationGsonAdapter extends TypeAdapter<Location> {

		private static Type seriType = new TypeToken<Map<String, Object>>() {
		}.getType();

		private static String UUID = "uuid";
		private static String WORLD_NAME = "wname";
		private static String X = "x";
		private static String Y = "y";
		private static String Z = "z";
		private static String YAW = "yaw";
		private static String PITCH = "pitch";

		@Override
		public void write(JsonWriter jsonWriter, Location location) throws IOException {
			if (location == null) {
				jsonWriter.nullValue();
				return;
			}
			jsonWriter.value(getRaw(location));
		}

		@Override
		public Location read(JsonReader jsonReader) throws IOException {
			if (jsonReader.peek() == JsonToken.NULL) {
				jsonReader.nextNull();
				return null;
			}
			return fromRaw(jsonReader.nextString());
		}

		private String getRaw(Location location) {
			Map<String, Object> serial = new HashMap<String, Object>();
			serial.put(UUID, location.getWorld().getUID().toString());
			serial.put(WORLD_NAME, location.getWorld().getName());
			serial.put(X, Double.toString(location.getX()));
			serial.put(Y, Double.toString(location.getY()));
			serial.put(Z, Double.toString(location.getZ()));
			serial.put(YAW, Float.toString(location.getYaw()));
			serial.put(PITCH, Float.toString(location.getPitch()));
			return g.toJson(serial);
		}

		private Location fromRaw(String raw) {
			Map<String, Object> keys = g.fromJson(raw, seriType);
			World w = Bukkit.getWorld(java.util.UUID.fromString((String) keys.get(UUID)));
			if (w == null) {
				w = Bukkit.getWorld((String) keys.get(WORLD_NAME));
			}
			return new Location(w, Double.parseDouble((String) keys.get(X)), Double.parseDouble((String) keys.get(Y)),
					Double.parseDouble((String) keys.get(Z)), Float.parseFloat((String) keys.get(YAW)),
					Float.parseFloat((String) keys.get(PITCH)));
		}
	}

	private static class MaterialDataGsonAdapter extends TypeAdapter<MaterialData> {

		private static Type seriType = new TypeToken<Map<String, Object>>() {
		}.getType();

		private static String type = "type";
		private static String data = "data";

		@Override
		public void write(JsonWriter jsonWriter, MaterialData location) throws IOException {
			if (location == null) {
				jsonWriter.nullValue();
				return;
			}
			jsonWriter.value(getRaw(location));
		}

		@Override
		public MaterialData read(JsonReader jsonReader) throws IOException {
			if (jsonReader.peek() == JsonToken.NULL) {
				jsonReader.nextNull();
				return null;
			}
			return fromRaw(jsonReader.nextString());
		}

		private String getRaw(MaterialData md) {
			Map<String, Object> serial = new HashMap<String, Object>();
			serial.put(type, "" + md.getItemType().toString());

			serial.put(data, "" + md.getData());

			return g.toJson(serial);
		}

		private MaterialData fromRaw(String raw) {
			Map<String, Object> keys = g.fromJson(raw, seriType);
			return new MaterialData(Material.valueOf(keys.get(type).toString()),
					Byte.valueOf(keys.get(data).toString()));
			// return new
			// MaterialData(Integer.valueOf(keys.get(type).toString()),Byte.valueOf(keys.get(data).toString()));

		}
	}

	private static class DateGsonAdapter extends TypeAdapter<Date> {
		@Override
		public void write(JsonWriter jsonWriter, Date date) throws IOException {
			if (date == null) {
				jsonWriter.nullValue();
				return;
			}
			jsonWriter.value(date.getTime());
		}

		@Override
		public Date read(JsonReader jsonReader) throws IOException {
			if (jsonReader.peek() == JsonToken.NULL) {
				jsonReader.nextNull();
				return null;
			}
			return new Date(jsonReader.nextLong());
		}
	}

}
