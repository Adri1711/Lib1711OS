package com.adri1711.api;

import java.io.Reader;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle.DustOptions;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.material.MaterialData;
import org.bukkit.util.Vector;

import com.adri1711.util.FastBoard;
import com.adri1711.util.enums.AMaterials;
import com.adri1711.util.enums.Particle1711;

public abstract class AbstractAPI1711<T> {

	public AbstractAPI1711() {

	}

	public abstract void prove(String id, String pl);

	public abstract void send(CommandSender player, String messageAux, String shopName, List<String> shopOver,
			String command, String lastPart);

	public abstract void usaTitle(Player p, String title, String subtitle);
	
	public abstract FastBoard createFastBoard(Player p);
	
	

	public abstract String getInventoryName(InventoryClickEvent event);

	public abstract String toJson(Object obj);

	public abstract Object fromJson(Reader json, Class<?> clase);

	public abstract void cambiaNombre(Player p, String nuevoNombre, List<Player> players);

	public abstract Material getMaterial(AMaterials material);

	public abstract void createWorldBorder(Player p, Double size, Location center);

	public abstract void correctDirectionFireball(Fireball fireball, Vector v2);
	
	public abstract void forceBreakBlock(Player p, Double x, Double y, Double z);
	
	public abstract void convertBlock(Location l,MaterialData data);

	public abstract void spawnParticle(World world, Particle1711 particle, Location loc, int count, double offsetx,
			double offsety, double offsetz, double extra, DustOptions dust);

	public abstract Double getHorseSpeed(Horse h);
	
	public abstract Boolean isCraftHorse(Object e);

	public abstract Boolean isCraftEnderPearl(Object e);
	
	public abstract Boolean isCraftItem(Object e);
	
	public abstract Boolean isCraftBoat(Object e);

	public abstract void setHorseSpeed(Horse h, double speed);
}
