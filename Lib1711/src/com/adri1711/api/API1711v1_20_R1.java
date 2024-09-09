package com.adri1711.api;

import java.io.Reader;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Particle.DustOptions;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_20_R1.block.CraftBlock;
import org.bukkit.craftbukkit.v1_20_R1.block.CraftBlockState;
import org.bukkit.craftbukkit.v1_20_R1.entity.CraftFireball;
import org.bukkit.craftbukkit.v1_20_R1.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_20_R1.entity.CraftPlayer;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.material.MaterialData;
import org.bukkit.util.Vector;

import com.adri1711.auxiliar1_20_R1.ChatPosition;
import com.adri1711.auxiliar1_20_R1.ClickAction;
import com.adri1711.auxiliar1_20_R1.GsonFactory;
import com.adri1711.auxiliar1_20_R1.Util;
import com.adri1711.auxiliar1_20_R1.Text;
import com.adri1711.util.FastBoard;
import com.adri1711.util.Util1;
import com.adri1711.util.Utils1_20_1;
import com.adri1711.util.enums.AMaterials;
import com.adri1711.util.enums.Particle1711;
import com.google.gson.Gson;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.core.BlockPosition;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.level.PlayerInteractManager;
import net.minecraft.world.entity.ai.attributes.AttributeBase;
import net.minecraft.world.entity.ai.attributes.AttributeModifiable;
import net.minecraft.world.entity.ai.attributes.GenericAttributes;
import net.minecraft.world.entity.animal.horse.EntityHorse;
import net.minecraft.world.entity.projectile.EntityFireball;

public class API1711v1_20_R1 extends AbstractAPI1711<Text> {
	private String id; 
	private Gson gson;

	public API1711v1_20_R1(String id, String pl) {
		this.id = id;
		this.pl = pl;
		this.gson = GsonFactory.getPrettyGson();
		prove(id, pl);
	}

	private String pl;

	@Override
	public void prove(String id, String pl) {
		if (id != null) {
			if (id == "") {
				Bukkit.getPluginManager()
						.disablePlugin(Bukkit.getPluginManager().getPlugin(com.adri1711.util.Util.dec("TGliMTcxMQ==")));
				Bukkit.getPluginManager().disablePlugin(Bukkit.getPluginManager().getPlugin(pl));
			} else if (!Util1.prove(id, "")) {
				Bukkit.getPluginManager()
						.disablePlugin(Bukkit.getPluginManager().getPlugin(com.adri1711.util.Util.dec("TGliMTcxMQ==")));
				Bukkit.getPluginManager().disablePlugin(Bukkit.getPluginManager().getPlugin(pl));
			}
		} else {

			Bukkit.getPluginManager()
					.disablePlugin(Bukkit.getPluginManager().getPlugin(com.adri1711.util.Util.dec("TGliMTcxMQ==")));
			Bukkit.getPluginManager().disablePlugin(Bukkit.getPluginManager().getPlugin(pl));
		}
	}

	@Override
	public void send(CommandSender player, String messageAux, String shopName, List<String> shopOver, String command,
			String lastPart) {
		TextComponent firstText = new TextComponent(messageAux);
		TextComponent tienda = new TextComponent(shopName);
		String over = "";
		for (String s : shopOver) {
			over += s + '\n';
		}
		if (!over.isEmpty()) {
			over = over.substring(0, over.length() - 1);
		}
		tienda.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(over).create()));

//		tienda.setHoverText2(shopOver, shopName);
		if (command != null)
			tienda.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, command));

		TextComponent lastText = new TextComponent(lastPart);
		firstText.addExtra(tienda);
		firstText.addExtra(lastText);
//		player.getServer().spigot().sendMessage(firstText);
		if (player instanceof Player) {
			Player p = (Player) player;
			p.spigot().sendMessage(firstText);
		}
//		firstText.append((IChatBaseComponent) tienda).append((IChatBaseComponent) lastText);
//		Util.send(player, (IChatBaseComponent) firstText, ChatPosition.CHAT);
	}
	@Override
	public  Boolean isCraftHorse(Object e) {
		return e instanceof org.bukkit.craftbukkit.v1_20_R1.entity.CraftHorse;
	}
	@Override
	public  Boolean isCraftEnderPearl(Object e) {
		return e instanceof org.bukkit.craftbukkit.v1_20_R1.entity.CraftEnderPearl;
	}
	@Override
	public  Boolean isCraftItem(Object e) {
		return e instanceof org.bukkit.craftbukkit.v1_20_R1.entity.CraftItem;

	}
	@Override
	public  Boolean isCraftBoat(Object e) {
		return e instanceof org.bukkit.craftbukkit.v1_20_R1.entity.CraftBoat;
	}
	@Override
	public void forceBreakBlock(Player p, Double x, Double y, Double z) {
		// AQUI
		EntityPlayer ep = ((CraftPlayer) p).getHandle();

		Field f;
		try {
			f = ep.getClass().getDeclaredField("d");

			f.setAccessible(true);
			PlayerInteractManager pc = (PlayerInteractManager) f.get(ep);
			Method m2 = pc.getClass().getMethod("a", BlockPosition.class);
			m2.invoke(pc, new BlockPosition(x, y, z));

			// ((CraftPlayer) p).getHandle().d.a(new BlockPosition(x, y, z));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void convertBlock(Location l, MaterialData data) {
		try {

			l.getBlock().setType(data.getItemType());

		} catch (Throwable e1) {

		}
		if (data.getData() != Byte.valueOf("0").byteValue()) {

			try {
				Constructor<CraftBlockState> c = CraftBlockState.class.getConstructor(l.getBlock().getClass());
				if (c != null) {
					CraftBlockState cbs = c.newInstance(l.getBlock());
					cbs.setData(data);
				}

			} catch (Throwable e) {

			}
			try {
				CraftBlock cb = (CraftBlock) l.getBlock();

				try {

					cb.setData(data.getData());
				} catch (Throwable e) {

				}
			} catch (Throwable e1) {

			}
		}

	}

	@Override
	public void usaTitle(Player p, String title, String subtitle) {
		Utils1_20_1.usaTitle(p, title, subtitle);
	}

	@Override
	public FastBoard createFastBoard(Player p) {

		return new com.adri1711.util.FastBoard(p);
	}

	@Override
	public Double getHorseSpeed(Horse h) {
		try {
			EntityHorse e = ((EntityHorse) ((CraftLivingEntity) h).getHandle());
			Method m = e.getClass().getMethod("a", AttributeBase.class);
			AttributeModifiable attributes = (AttributeModifiable) m.invoke(e, GenericAttributes.d);
			Method m2 = attributes.getClass().getMethod("b");

			return (Double) m2.invoke(attributes);
		} catch (Exception e) {
			return 1.0;
		}
	}

	@Override
	public void setHorseSpeed(Horse h, double speed) {
		// use about 2.25 for normalish speed
		try {
			EntityHorse e = ((EntityHorse) ((CraftLivingEntity) h).getHandle());
			Method m = e.getClass().getMethod("a", AttributeBase.class);
			AttributeModifiable attributes = (AttributeModifiable) m.invoke(e, GenericAttributes.d);
			Method m2 = attributes.getClass().getMethod("a", double.class);
			m2.invoke(attributes, speed);
		} catch (Exception e) {

		}
	}

	@Override
	public String getInventoryName(InventoryClickEvent event) {
		return event.getView().getTitle();
	}

	@Override
	public String toJson(Object obj) {
		return gson.toJson(obj);

	}

	@Override
	public Object fromJson(Reader json, Class<?> clase) {
		return gson.fromJson(json, clase);
	}

	@Override
	public void cambiaNombre(Player p, String nuevoNombre, List<Player> players) {
		Utils1_20_1.cambiaNombre(p, nuevoNombre, players);
	}

	@Override
	public Material getMaterial(AMaterials material) {
		return material.getMaterial1_16_3();
	}

	@Override
	public void createWorldBorder(Player p, Double size, Location center) {
		Util.createWorldBorder(p, size, center);
	}

	@Override
	public void spawnParticle(World world, Particle1711 particle, Location loc, int count, double offsetx,
			double offsety, double offsetz, double extra, DustOptions dust) {

		try {
			if (dust != null) {
				Method method = world.getClass().getMethod("spawnParticle", Particle.class, Location.class, int.class,
						double.class, double.class, double.class, double.class, Object.class);
				method.invoke(world, Particle.valueOf(particle.getParticle()), loc, count, offsetx, offsety, offsetz,
						extra, dust);
			} else {
				Method method = world.getClass().getMethod("spawnParticle", Particle.class, Location.class, int.class,
						double.class, double.class, double.class, double.class);
				method.invoke(world, Particle.valueOf(particle.getParticle()), loc, count, offsetx, offsety, offsetz,
						extra);
			}

		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void correctDirectionFireball(Fireball fireball, Vector v2) {
		EntityFireball nms = ((CraftFireball) fireball).getHandle();
		double x = v2.getX();
		double y = v2.getY();
		double z = v2.getZ();
		double length = Math.sqrt(x * x + y * y + z * z);

		nms.b = x / length * 0.1D;
		nms.c = y / length * 0.1D;
		nms.d = z / length * 0.1D;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPl() {
		return this.pl;
	}

	public void setPl(String pl) {
		this.pl = pl;
	}

}
