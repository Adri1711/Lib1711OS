package com.adri1711.api;

import java.io.Reader;
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
import org.bukkit.craftbukkit.v1_17_R1.block.CraftBlock;
import org.bukkit.craftbukkit.v1_17_R1.block.CraftBlockState;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftFireball;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftPlayer;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.material.MaterialData;
import org.bukkit.util.Vector;

import com.adri1711.auxiliar1_17_R1.ChatPosition;
import com.adri1711.auxiliar1_17_R1.ClickAction;
import com.adri1711.auxiliar1_17_R1.GsonFactory;
import com.adri1711.auxiliar1_17_R1.Text;
import com.adri1711.auxiliar1_17_R1.Util;
import com.adri1711.util.FastBoard;
import com.adri1711.util.Util1;
import com.adri1711.util.Utils1_17;
import com.adri1711.util.enums.AMaterials;
import com.adri1711.util.enums.Particle1711;
import com.google.gson.Gson;

import net.minecraft.core.BlockPosition;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.ai.attributes.AttributeModifiable;
import net.minecraft.world.entity.ai.attributes.GenericAttributes;
import net.minecraft.world.entity.ambient.EntityAmbient;
import net.minecraft.world.entity.animal.horse.EntityHorse;
import net.minecraft.world.entity.projectile.EntityFireball;

public class API1711v1_17_R1 extends AbstractAPI1711<Text> {
	private String id;
	private Gson gson;

	public API1711v1_17_R1(String id, String pl) {
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
	public  Boolean isCraftHorse(Object e) {
		return e instanceof org.bukkit.craftbukkit.v1_17_R1.entity.CraftHorse;
	}
	@Override
	public  Boolean isCraftEnderPearl(Object e) {
		return e instanceof org.bukkit.craftbukkit.v1_17_R1.entity.CraftEnderPearl;
	}
	@Override
	public  Boolean isCraftItem(Object e) {
		return e instanceof org.bukkit.craftbukkit.v1_17_R1.entity.CraftItem;

	}
	@Override
	public  Boolean isCraftBoat(Object e) {
		return e instanceof org.bukkit.craftbukkit.v1_17_R1.entity.CraftBoat;
	}
	@Override
	public Double getHorseSpeed(Horse h){   
		AttributeModifiable attributes = ((EntityHorse)((CraftLivingEntity)h).getHandle()).getAttributeInstance(GenericAttributes.d);
        return attributes.getValue();
    }
	@Override
    public void setHorseSpeed(Horse h,double speed){   
        // use about  2.25 for normalish speed  
		AttributeModifiable attributes = ((EntityHorse)((CraftLivingEntity)h).getHandle()).getAttributeInstance(GenericAttributes.d);
        attributes.setValue(speed);
    }
	@Override
	public void send(CommandSender player, String messageAux, String shopName, List<String> shopOver, String command,
			String lastPart) {
		Text firstText = new Text(messageAux);
		Text tienda = new Text(shopName);

		tienda.setHoverText2(shopOver, shopName);
		if (command != null)
			tienda.setClick(ClickAction.RUN_COMMAND, command);
		Text lastText = new Text(lastPart);
		firstText.append((IChatBaseComponent) tienda).append((IChatBaseComponent) lastText);
		Util.send(player, (IChatBaseComponent) firstText, ChatPosition.CHAT);
	}

	@Override
	public void forceBreakBlock(Player p, Double x, Double y, Double z) {
		((CraftPlayer) p).getHandle().d.breakBlock(new BlockPosition(x, y, z));
	}

	@Override
	public void convertBlock(Location l, MaterialData data) {
		try {

			l.getBlock().setType(data.getItemType());

		} catch (Throwable e1) {

		}
		if (data.getData() != Byte.valueOf("0").byteValue()) {

			try {
				CraftBlockState cbs = new CraftBlockState(l.getBlock());
				cbs.setData(data);

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
		Utils1_17.usaTitle(p, title, subtitle);
	}
	@Override
	public FastBoard createFastBoard(Player p) {
		
		return new com.adri1711.util.FastBoard(p);
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
		Utils1_17.cambiaNombre(p, nuevoNombre, players);
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
