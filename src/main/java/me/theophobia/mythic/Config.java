package me.theophobia.mythic;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;

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
	private Map<String, Object> normalMonsterProperties = Map.ofEntries(
		Map.entry("healthScale", 1.0),
		Map.entry("damageScale", 1.0),
		Map.entry("knockback", 0.0),
		Map.entry("movementSpeedScale", 1.0),
		Map.entry("lootScale", 1.0),
		Map.entry("experienceScale", 1.0)
	);
	private Map<String, Object> bossMonsterProperties = Map.ofEntries(
		Map.entry("healthScale", 1.0),
		Map.entry("damageScale", 1.0),
		Map.entry("knockback", 0.0),
		Map.entry("movementSpeedScale", 1.0),
		Map.entry("lootScale", 1.0),
		Map.entry("experienceScale", 1.0)
	);
	private int bossSpawnRadius = 64;
	private int bossSpawnFrequency = 10;

	public void save() {
		try (FileWriter fr = new FileWriter(Mythic.getConfigFile())) {
			new GsonBuilder().setPrettyPrinting().create().toJson(this, fr);
			fr.flush();
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
			try (FileReader fr = new FileReader(Mythic.getConfigFile())) {
				instance = new Gson().fromJson(fr, instance.getClass());
			}
			catch (Exception e) {
				Mythic.getPlugin().getLogger().log(Level.SEVERE, "Could not load configuration, loading defaults.");

				try {
					Path oldConfigPath = Mythic.getConfigFile().toPath();
					Path newConfigPath = new File(Mythic.getConfigFile().getParentFile(), "backup-" + UUID.randomUUID() + "-" + Mythic.getConfigFile().getName()).toPath();

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
		return new GsonBuilder().setPrettyPrinting().create().toJson(this);
	}

	public double getHealthScale() {
		return (double) normalMonsterProperties.get("healthScale");
	}

	public void setHealthScale(double healthScale) {
		normalMonsterProperties.put("healthScale", healthScale);
	}

	public double getDamageScale() {
		return (double) normalMonsterProperties.get("damageScale");
	}

	public void setDamageScale(double damageScale) {
		normalMonsterProperties.put("damageScale", damageScale);
	}

	public double getKnockback() {
		return (double) normalMonsterProperties.get("knockback");
	}

	public void setKnockback(double knockback) {
		normalMonsterProperties.put("knockback", knockback);
	}

	public boolean isMobModificationLogged() {
		return isMobModificationLogged;
	}

	public void setMobModificationLogged(boolean isMobModificationLogged) {
		this.isMobModificationLogged = isMobModificationLogged;
	}

	public double getMovementSpeedScale() {
		return (double) normalMonsterProperties.get("movementSpeedScale");
	}

	public void setMovementSpeedScale(double movementSpeedScale) {
		normalMonsterProperties.put("movementSpeedScale", movementSpeedScale);
	}

	public double getLootScale() {
		return (double) normalMonsterProperties.get("lootScale");
	}

	public void setLootScale(double lootScale) {
		normalMonsterProperties.put("lootScale", lootScale);
	}

	public double getExperienceScale() {
		return (double) normalMonsterProperties.get("experienceScale");
	}

	public void setExperienceScale(double experienceScale) {
		normalMonsterProperties.put("experienceScale", experienceScale);
	}

	//


	public double getBossHealthScale() {
		return (double) bossMonsterProperties.get("healthScale");
	}

	public void setBossHealthScale(double healthScale) {
		bossMonsterProperties.put("healthScale", healthScale);
	}

	public double getBossDamageScale() {
		return (double) bossMonsterProperties.get("damageScale");
	}

	public void setBossDamageScale(double damageScale) {
		bossMonsterProperties.put("damageScale", damageScale);
	}

	public double getBossKnockback() {
		return (double) bossMonsterProperties.get("knockback");
	}

	public void setBossKnockback(double knockback) {
		bossMonsterProperties.put("knockback", knockback);
	}

	public double getBossMovementSpeedScale() {
		return (double) bossMonsterProperties.get("movementSpeedScale");
	}

	public void setBossMovementSpeedScale(double movementSpeedScale) {
		bossMonsterProperties.put("movementSpeedScale", movementSpeedScale);
	}

	public double getBossLootScale() {
		return (double) bossMonsterProperties.get("lootScale");
	}

	public void setBossLootScale(double lootScale) {
		bossMonsterProperties.put("lootScale", lootScale);
	}

	public double getBossExperienceScale() {
		return (double) bossMonsterProperties.get("experienceScale");
	}

	public void setBossExperienceScale(double experienceScale) {
		bossMonsterProperties.put("experienceScale", experienceScale);
	}

	public int getBossSpawnRadius() {
		return bossSpawnRadius;
	}

	public void setBossSpawnRadius(int bossSpawnRadius) {
		this.bossSpawnRadius = bossSpawnRadius;
	}

	public int getBossSpawnFrequency() {
		return bossSpawnFrequency;
	}

	public void setBossSpawnFrequency(int bossSpawnFrequency) {
		this.bossSpawnFrequency = bossSpawnFrequency;
	}
}
