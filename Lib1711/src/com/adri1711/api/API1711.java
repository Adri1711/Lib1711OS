package com.adri1711.api;

import java.io.Reader;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftFireball;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.material.MaterialData;
import org.bukkit.util.Vector;

import com.adri1711.pl.Lib1711;
import com.adri1711.util.FastBoard;
import com.adri1711.util.enums.AMaterials;
import com.adri1711.util.enums.Particle1711;
import com.adri1711.util.enums.SpigotVersion;

import net.minecraft.server.v1_8_R3.EntityFireball;

@SuppressWarnings("rawtypes")
public class API1711 {

	private String id;
	private String pl;
	private String sVersion;

	private AbstractAPI1711 apiVersionada;

	private Lib1711 plugin;

	public API1711(String id, String pl) {
		if (Bukkit.getPluginManager().isPluginEnabled("Lib1711")) {
			plugin = (Lib1711) Bukkit.getPluginManager().getPlugin("Lib1711");
		}

		try {
			this.sVersion = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
		} catch (ArrayIndexOutOfBoundsException whatVersionAreYouUsingException) {
			this.sVersion = "";
		}

		
		SpigotVersion version = SpigotVersion.getValueOf(sVersion);

		if (version != null) {
			switch (version) {
			case V1_8:
				apiVersionada = new API1711v1_8_R3(id, pl);
				break;
			case V1_9_R1:
				apiVersionada = new API1711v1_9_R1(id, pl);
				break;
			case V1_9_R2:
				apiVersionada = new API1711v1_9_R2(id, pl);
				break;
			case V1_10:
				apiVersionada = new API1711v1_10_R1(id, pl);
				break;
			case V1_11:
				apiVersionada = new API1711v1_11_R1(id, pl);
				break;
			case V1_12:
				apiVersionada = new API1711v1_12_R1(id, pl);
				break;
			case V1_13_R2:
				apiVersionada = new API1711v1_13_R2(id, pl);
				break;
			case V1_13_R1:
				apiVersionada = new API1711v1_13_R1(id, pl);
				break;
			case V1_14:
				apiVersionada = new API1711v1_14_R1(id, pl);
				break;
			case V1_15:
				apiVersionada = new API1711v1_15_R1(id, pl);
				break;
			case V1_16_3:
				apiVersionada = new API1711v1_16_R3(id, pl);
				break;
			case V1_16_2:
				apiVersionada = new API1711v1_16_R2(id, pl);
				break;
			case V1_16:
				apiVersionada = new API1711v1_16_R1(id, pl);
				break;
			case V1_17_1:
				apiVersionada = new API1711v1_17_R1(id, pl);

				break;
			case V1_18_1:
			
				apiVersionada = new API1711v1_18_R1(id, pl);
				break;
			case V1_18_2:
			case V1_18_3:
				apiVersionada = new API1711v1_18_R2(id, pl);

				break;
			case V1_19_1:
				apiVersionada = new API1711v1_19_R1(id, pl);
				break;
			case V1_19_2:
				apiVersionada = new API1711v1_19_R2(id, pl);
				break;
			case V1_19_3:
				apiVersionada = new API1711v1_19_R3(id, pl);
				break;
			case V1_20_1:
				apiVersionada = new API1711v1_20_R1(id, pl);
				break;
			case V1_20_2:
				apiVersionada = new API1711v1_20_R2(id, pl);
				break;
			case V1_20_3:
				apiVersionada = new API1711v1_20_R3(id, pl);
				break;
			case V1_20_4:
				apiVersionada = new API1711v1_20_R4(id, pl);
				break;
			case V1_21_1:
				apiVersionada = new API1711v1_21_R1(id, pl);
				break;
			default:
				break;
			}
		}

	}

	public API1711() {
	}

	public void prove(String id, String pl) {
//		Bukkit.getScheduler().runTaskAsynchronously(getPlugin(), new Runnable() {
//
//			@Override
//			public void run() {
//				apiVersionada.prove(id, pl);
//			}
//
//		});

	}

	public FastBoard createFastBoard(Player p) {
		return apiVersionada.createFastBoard(p);
	}

	public void send(CommandSender player, String messageAux, String shopName, List<String> shopOver, String command,
			String lastPart) {
		apiVersionada.send(player, messageAux, shopName, shopOver, command, lastPart);
	}

	public void usaTitle(Player p, String title, String subtitle) {
		apiVersionada.usaTitle(p, title, subtitle);
	}

	public String getInventoryName(InventoryClickEvent event) {
		return apiVersionada.getInventoryName(event);
	}

	public String toJson(Object obj) {
		return apiVersionada.toJson(obj);
	}

	public Object fromJson(Reader json, Class<?> clase) {
		return apiVersionada.fromJson(json, clase);
	}

	public void cambiaNombre(Player p, String nuevoNombre, List<Player> players) {
		apiVersionada.cambiaNombre(p, nuevoNombre, players);
	}

	public Material getMaterial(AMaterials material) {
		return apiVersionada.getMaterial(material);

	}

	public  Boolean isCraftHorse(Object e) {
		return apiVersionada.isCraftHorse(e);
	}

	public  Boolean isCraftEnderPearl(Object e) {
		return apiVersionada.isCraftEnderPearl(e);
	}
	
	public  Boolean isCraftItem(Object e) {
		return apiVersionada.isCraftItem(e);
	}
	
	public  Boolean isCraftBoat(Object e) {
		return apiVersionada.isCraftBoat(e);
	}
	
	public void createWorldBorder(Player p, Double size, Location center) {
		apiVersionada.createWorldBorder(p, size, center);
	}

	public void spawnParticle(World world, Particle1711 particle, Location loc, int count, double offsetx,
			double offsety, double offsetz, double extra, org.bukkit.Particle.DustOptions dust) {
		apiVersionada.spawnParticle(world, particle, loc, count, offsetx, offsety, offsetz, extra, dust);

	}

	public Double getHorseSpeed(Horse h) {
		return apiVersionada.getHorseSpeed(h);
	}

	public void setHorseSpeed(Horse h, double speed) {
		apiVersionada.setHorseSpeed(h, speed);
	}

	public void forceBreakBlock(Player p, Double x, Double y, Double z) {
		apiVersionada.forceBreakBlock(p, x, y, z);
	}

	public void convertBlock(Location l, MaterialData data) {
		apiVersionada.convertBlock(l, data);
	}

	public void correctDirectionFireball(Fireball fireball, Vector v2) {
		apiVersionada.correctDirectionFireball(fireball, v2);
	}

	public String getId() {
		return id;
	}

	public String getPl() {
		return pl;
	}

	public String getsVersion() {
		return sVersion;
	}

	public AbstractAPI1711 getApiVersionada() {
		return apiVersionada;
	}

	public void setApiVersionada(AbstractAPI1711 apiVersionada) {
		this.apiVersionada = apiVersionada;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setPl(String pl) {
		this.pl = pl;
	}

	public void setsVersion(String sVersion) {
		this.sVersion = sVersion;
	}

	public Lib1711 getPlugin() {
		return plugin;
	}

	public void setPlugin(Lib1711 plugin) {
		this.plugin = plugin;
	}

}
