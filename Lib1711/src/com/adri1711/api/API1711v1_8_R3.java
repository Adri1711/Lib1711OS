package com.adri1711.api;

import java.io.Reader;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle.DustOptions;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftFireball;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftLivingEntity;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.material.MaterialData;
import org.bukkit.util.Vector;

import com.adri1711.auxiliar1_8_R3.ChatPosition;
import com.adri1711.auxiliar1_8_R3.ClickAction;
import com.adri1711.auxiliar1_8_R3.GsonFactory;
import com.adri1711.auxiliar1_8_R3.Text;
import com.adri1711.auxiliar1_8_R3.Util;
import com.adri1711.util.FastBoard;
import com.adri1711.util.Util1;
import com.adri1711.util.Utils1_8;
import com.adri1711.util.enums.AMaterials;
import com.adri1711.util.enums.Particle1711;
import com.google.gson.Gson;

import net.minecraft.server.v1_8_R3.AttributeInstance;
import net.minecraft.server.v1_8_R3.EntityFireball;
import net.minecraft.server.v1_8_R3.EntityHorse;
import net.minecraft.server.v1_8_R3.GenericAttributes;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;

public class API1711v1_8_R3 extends AbstractAPI1711<Text> {
	private String id;
	private String pl;

	private Gson gson;

	public API1711v1_8_R3(String id, String pl) {
		super();
		this.id = id;
		this.pl = pl;
		this.gson = GsonFactory.getPrettyGson();
		prove(id, pl);
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
		((org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer) p).getHandle().playerInteractManager
				.breakBlock(new net.minecraft.server.v1_8_R3.BlockPosition(x, y, z));
	}
	@Override
	public  Boolean isCraftHorse(Object e) {
		return e instanceof org.bukkit.craftbukkit.v1_8_R3.entity.CraftHorse;
	}
	@Override
	public  Boolean isCraftEnderPearl(Object e) {
		return e instanceof org.bukkit.craftbukkit.v1_8_R3.entity.CraftEnderPearl;
	}
	@Override
	public  Boolean isCraftItem(Object e) {
		return e instanceof org.bukkit.craftbukkit.v1_8_R3.entity.CraftItem;

	}
	@Override
	public  Boolean isCraftBoat(Object e) {
		return e instanceof org.bukkit.craftbukkit.v1_8_R3.entity.CraftBoat;
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
	public void usaTitle(Player p, String title, String subtitle) {
		Utils1_8.usaTitle(p, title, subtitle);
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
	public void convertBlock(Location l, MaterialData data) {
		l.getBlock().setType(data.getItemType());
		try {
			l.getBlock().setData(data.getData());
		} catch (Throwable e) {

		}

	}

	@Override
	public Object fromJson(Reader json, Class<?> clase) {
		return gson.fromJson(json, clase);
	}

	@Override
	public void cambiaNombre(Player p, String nuevoNombre, List<Player> players) {
		Utils1_8.cambiaNombre(p, nuevoNombre, players);
	}

	@Override
	public Material getMaterial(AMaterials material) {
		return material.getMaterial1_8();
	}

	@Override
	public void createWorldBorder(Player p, Double size, Location center) {
		Util.createWorldBorder(p, size, center);
	}

	@Override
	public void spawnParticle(World world, Particle1711 particle, Location loc, int count, double offsetx,
			double offsety, double offsetz, double extra, DustOptions dust) {

		world.playEffect(loc, Effect.valueOf(particle.getEffect()), count);

	}
	
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

	public Gson getGson() {
		return gson;
	}

	public void setGson(Gson gson) {
		this.gson = gson;
	}



}
