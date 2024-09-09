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
import org.bukkit.craftbukkit.v1_9_R1.entity.CraftFireball;
import org.bukkit.craftbukkit.v1_9_R1.entity.CraftLivingEntity;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.material.MaterialData;
import org.bukkit.util.Vector;

import com.adri1711.auxiliar1_9_R1.ChatPosition;
import com.adri1711.auxiliar1_9_R1.ClickAction;
import com.adri1711.auxiliar1_9_R1.GsonFactory;
import com.adri1711.auxiliar1_9_R1.Text;
import com.adri1711.auxiliar1_9_R1.Util;
import com.adri1711.util.FastBoard;
import com.adri1711.util.Util1;
import com.adri1711.util.Utils1_9;
import com.adri1711.util.enums.AMaterials;
import com.adri1711.util.enums.Particle1711;
import com.google.gson.Gson;

import net.minecraft.server.v1_9_R1.AttributeInstance;
import net.minecraft.server.v1_9_R1.EntityFireball;
import net.minecraft.server.v1_9_R1.EntityHorse;
import net.minecraft.server.v1_9_R1.GenericAttributes;
import net.minecraft.server.v1_9_R1.IChatBaseComponent;

public class API1711v1_9_R1 extends AbstractAPI1711<Text> {
	private String id;
	private Gson gson;

	public API1711v1_9_R1(String id, String pl) {
		super();
		this.id = id;
		this.pl = pl;
		this.gson=GsonFactory.getPrettyGson();
		prove(id, pl);
	}

	private String pl;
	@Override
	public FastBoard createFastBoard(Player p) {
		
		return new com.adri1711.util.FastBoard(p);
	}
	@Override
	public Double getHorseSpeed(Horse h){   
        AttributeInstance attributes = ((EntityHorse)((CraftLivingEntity)h).getHandle()).getAttributeInstance(GenericAttributes.MOVEMENT_SPEED);
        return attributes.getValue();
    }
	@Override
    public void setHorseSpeed(Horse h,double speed){   
        // use about  2.25 for normalish speed  
        AttributeInstance attributes = ((EntityHorse)((CraftLivingEntity)h).getHandle()).getAttributeInstance(GenericAttributes.MOVEMENT_SPEED);
        attributes.setValue(speed);
    }
	
	@Override
	public  Boolean isCraftHorse(Object e) {
		return e instanceof org.bukkit.craftbukkit.v1_9_R1.entity.CraftHorse;
	}
	@Override
	public  Boolean isCraftEnderPearl(Object e) {
		return e instanceof org.bukkit.craftbukkit.v1_9_R1.entity.CraftEnderPearl;
	}
	@Override
	public  Boolean isCraftItem(Object e) {
		return e instanceof org.bukkit.craftbukkit.v1_9_R1.entity.CraftItem;

	}
	@Override
	public  Boolean isCraftBoat(Object e) {
		return e instanceof org.bukkit.craftbukkit.v1_9_R1.entity.CraftBoat;
	}
	
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
	public void forceBreakBlock(Player p, Double x, Double y, Double z) {
		((org.bukkit.craftbukkit.v1_9_R1.entity.CraftPlayer) p).getHandle().playerInteractManager
				.breakBlock(new net.minecraft.server.v1_9_R1.BlockPosition(x, y, z));
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
	public void convertBlock(Location l, MaterialData data) {
		l.getBlock().setType(data.getItemType());
		try {
			l.getBlock().setData(data.getData());
		} catch (Throwable e) {

		}

	}

	@Override
	public void usaTitle(Player p, String title, String subtitle) {
		Utils1_9.usaTitle(p, title, subtitle);
	}
	@Override
	public String getInventoryName(InventoryClickEvent event) {
		return event.getInventory().getName();	
	}
	@Override
	public String toJson(Object obj) {
		return gson.toJson(obj);

	}

	@Override
	public Object fromJson(Reader json, Class<?> clase) {
		return gson.fromJson(json,clase);
	}

	@Override
	public  void cambiaNombre(Player p,String nuevoNombre, List<Player> players){
		Utils1_9.cambiaNombre(p, nuevoNombre, players);
	}
	@Override
	public Material getMaterial(AMaterials material){
		return material.getMaterial1_9();
	}
	@Override
	public void createWorldBorder(Player p, Double size, Location center) {
		Util.createWorldBorder(p, size, center);
	}
	@Override
	public void correctDirectionFireball(Fireball fireball, Vector v2) {
		EntityFireball nms = ((CraftFireball) fireball).getHandle();
		double x = v2.getX();
		double y = v2.getY();
		double z = v2.getZ();
		double length = Math.sqrt(x * x + y * y + z * z);

		nms.dirX = x / length * 0.1D;
		nms.dirY = y / length * 0.1D;
		nms.dirZ = z / length * 0.1D;
	}
	
	@Override
	public void spawnParticle(World world, Particle1711 particle, Location loc, int count, double offsetx,
			double offsety, double offsetz, double extra, DustOptions dust) {

		try {
			if (dust != null) {
				Method method = world.getClass().getMethod("spawnParticle", Particle.class, Location.class, int.class,
						double.class, double.class, double.class, double.class, Object.class);
				method.invoke(world, Particle.valueOf(particle.getParticle()), loc, count, offsetx, offsety, offsetz, extra, dust);
			} else {
				Method method = world.getClass().getMethod("spawnParticle", Particle.class, Location.class, int.class,
						double.class, double.class, double.class, double.class);
				method.invoke(world, Particle.valueOf(particle.getParticle()), loc, count, offsetx, offsety, offsetz, extra);
			}

		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
		}

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
