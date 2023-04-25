package me.theophobia.mythic;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;

import org.bukkit.entity.EntityType;

public class Config {
	private static Config instance = null;

	public static Config getInstance() {
		if (instance == null) {
			instance = new Config();
			instance.load();
		}
		return instance;
	}

	private boolean isMobModificationLogged = false;

	private int bossSpawnRadius = 64;

	private int bossSpawnFrequency = 10;

	private final Map<MobValue, Double> defaultMobModifiers = defaultMobValues();

	private final Map<EntityType, Map<MobValue, Double>> mobModifiers;

	private final Map<EntityType, Map<MobValue, Double>> babyMobModifiers;

	public Config() {
		this.mobModifiers = new HashMap<>();
		this.mobModifiers.put(EntityType.ZOMBIE, defaultMobValues());
		this.mobModifiers.put(EntityType.SKELETON, defaultMobValues());
		this.mobModifiers.put(EntityType.ENDER_DRAGON, defaultMobValues());

		this.babyMobModifiers = new HashMap<>();
		this.babyMobModifiers.put(EntityType.ZOMBIE, defaultMobValues());
	}

	private static Map<MobValue, Double> defaultMobValues() {
		return Map.of(MobValue.HEALTH, 1.0d,
			MobValue.DAMAGE, 1.0d,
			MobValue.MOVESPEED, 1.0d,
			MobValue.KNOCKBACK, 0.0d,
			MobValue.LOOT, 1.0d,
			MobValue.EXP, 1.0d);
	}

	public void save() {
		try {
			FileWriter fr = new FileWriter(Mythic.getConfigFile());
			try {
				(new GsonBuilder()).setPrettyPrinting().create().toJson(this, fr);
				fr.flush();
				fr.close();
			}
			catch (Throwable throwable) {
				try {
					fr.close();
				}
				catch (Throwable throwable1) {
					throwable.addSuppressed(throwable1);
				}
				throw throwable;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void load() {
		if (!Mythic.getConfigFile().exists()) {
			instance.save();
		}
		else {
			try {
				FileReader fr = new FileReader(Mythic.getConfigFile());
				try {
					instance = new Gson().fromJson(fr, instance.getClass());
					fr.close();
				}
				catch (Throwable throwable) {
					try {
						fr.close();
					}
					catch (Throwable throwable1) {
						throwable.addSuppressed(throwable1);
					}
					throw throwable;
				}
			}
			catch (Exception e) {
				Mythic.getPlugin().getLogger().log(Level.SEVERE, "Could not load configuration, loading defaults.");
				try {
					Path oldConfigPath = Mythic.getConfigFile().toPath();
					Path newConfigPath = (new File(Mythic.getConfigFile().getParentFile(), "backup-" + UUID.randomUUID() + "-" + Mythic.getConfigFile().getName())).toPath();
					Files.move(oldConfigPath, newConfigPath);
				}
				catch (IOException ioException) {
					ioException.printStackTrace();
				}
				instance = new Config();
				instance.save();
			}
		}
	}

	public String toPrettyJson() {
		return (new GsonBuilder()).setPrettyPrinting().create().toJson(this);
	}

	public Map<MobValue, Double> getModifiers(EntityType type, boolean isBaby) {
		if (isBaby) {
			return babyMobModifiers.getOrDefault(type, getDefaultMobModifiers());
		}
		else {
			return mobModifiers.getOrDefault(type, getDefaultMobModifiers());
		}
	}

	public boolean isMobModificationLogged() {
		return this.isMobModificationLogged;
	}

	public void setMobModificationLogged(boolean isMobModificationLogged) {
		this.isMobModificationLogged = isMobModificationLogged;
	}

	public int getBossSpawnRadius() {
		return this.bossSpawnRadius;
	}

	public void setBossSpawnRadius(int bossSpawnRadius) {
		this.bossSpawnRadius = bossSpawnRadius;
	}

	public int getBossSpawnFrequency() {
		return this.bossSpawnFrequency;
	}

	public void setBossSpawnFrequency(int bossSpawnFrequency) {
		this.bossSpawnFrequency = bossSpawnFrequency;
	}

	public Map<EntityType, Map<MobValue, Double>> getMobModifiers() {
		return this.mobModifiers;
	}

	public Map<EntityType, Map<MobValue, Double>> getBabyMobModifiers() {
		return babyMobModifiers;
	}

	public Map<MobValue, Double> getDefaultMobModifiers() {
		return this.defaultMobModifiers;
	}
}
