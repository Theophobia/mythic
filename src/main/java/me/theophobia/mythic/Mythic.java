package me.theophobia.mythic;

import com.google.gson.Gson;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Level;
import java.util.stream.Stream;

public final class Mythic extends JavaPlugin {

	private static final Component BOSS_NAME = Component.text("5e426809-82ad-4b05-b218-f8fd7ae0042e");

	private static Map<TaskType, List<Integer>> tasks = new HashMap<>();
	private static Plugin plugin;
	private static File configFile;

	private static final Runnable task = () -> {
		Location spawnLoc = getRandomSpawnLocation();
		if (spawnLoc == null) {
			return;
		}
		spawnLoc = spawnLoc.add(0, 1, 0);

		Bukkit.getServer().broadcast(Component.text("Spawning boss mob at [" + spawnLoc.getBlockX() + ", " + spawnLoc.getBlockY() + ", " + spawnLoc.getBlockZ() + "]", TextColor.color(255, 255, 0)));

		spawnLoc.getWorld().spawnEntity(spawnLoc, EntityType.WITHER_SKELETON, CreatureSpawnEvent.SpawnReason.COMMAND, entity -> {
			entity.customName(BOSS_NAME);

//			LivingEntity le = ((LivingEntity) entity);
//			le.setAI(false);
//			le.setNoDamageTicks(10 * 20);
//
//			int taskId = Mythic.getPlugin().getServer().getScheduler().scheduleSyncDelayedTask(Mythic.getPlugin(), () -> {
//				le.setAI(true);
//			}, 10L * 20L);
//
//			List<Integer> freezeTasks = tasks.getOrDefault(TaskType.BOSS_FREEZE_TASK, new ArrayList<>());
//			freezeTasks.add(taskId);
//			tasks.put(TaskType.BOSS_FREEZE_TASK, freezeTasks);
		});
	};

	@Override
	public void onEnable() {
		plugin = this;
		configFile = new File(getDataFolder(), "config.json");

		// Load command executors
		getCommand("mythic").setExecutor(new MythicCommand());
		getCommand("mythic").setTabCompleter(new MythicCommand());

		// Load event listeners
		getServer().getPluginManager().registerEvents(new MonsterSpawnListener(), plugin);
		getServer().getPluginManager().registerEvents(new MonsterDeathListener(), plugin);

		// Load configuration and update save it back.
		// Saving it back ensures there is no missing information in the file.
		Config.getInstance().save();
		getLogger().log(Level.INFO, new Gson().toJson(Config.getInstance()));

//		schedulePeriodicBossSpawns();
	}

	public static void schedulePeriodicBossSpawns() {
		int taskId = Mythic.getPlugin().getServer().getScheduler().scheduleSyncRepeatingTask(plugin, task, 20L * 60L * Config.getInstance().getBossSpawnFrequency(), 20L * 60L * Config.getInstance().getBossSpawnFrequency());
		List<Integer> bossRepeatingTaskIds = tasks.getOrDefault(TaskType.BOSS_REPEATING_TASK, new ArrayList<>());
		bossRepeatingTaskIds.add(taskId);
		tasks.put(TaskType.BOSS_REPEATING_TASK, bossRepeatingTaskIds);
	}

	public static void cancelPeriodicBossSpawns() {
		tasks.getOrDefault(TaskType.BOSS_REPEATING_TASK, new ArrayList<>()).forEach(id -> Mythic.getPlugin().getServer().getScheduler().cancelTask(id));
		tasks.put(TaskType.BOSS_REPEATING_TASK, new ArrayList<>());
	}

	@Override
	public void onDisable() {
		plugin = null;
		configFile = null;
	}

	public static Plugin getPlugin() {
		return plugin;
	}

	public static File getConfigFile() {
		return configFile;
	}

	public static Location getRandomSpawnLocation() {
		Optional<? extends Player> randomPlayer = Mythic.getPlugin().getServer().getOnlinePlayers().stream().findFirst();
		if (randomPlayer.isEmpty()) {
			return null;
		}

		World w = randomPlayer.get().getWorld();

		Stream<? extends Player> s = Mythic.getPlugin().getServer().getOnlinePlayers().stream().filter(player -> player.getWorld().getEnvironment().equals(World.Environment.NORMAL));

		AtomicLong accumulatedX = new AtomicLong(0L);
		AtomicLong accumulatedZ = new AtomicLong(0L);
		AtomicLong count = new AtomicLong(0L);

		s.forEach(player -> {
			accumulatedX.addAndGet(player.getLocation().getBlockX());
			accumulatedZ.addAndGet(player.getLocation().getBlockZ());
			count.incrementAndGet();
		});

		long x = accumulatedX.get() / count.get();
		long z = accumulatedZ.get() / count.get();

		Location loc = new Location(w, x, 256, z);
		while (!loc.getBlock().isSolid()) {
			loc.add(0, -1, 0);
		}

		if (loc.getBlock().isSolid()) {
			return loc;
		}
		else {
			return null;
		}
	}

	public static Component getBossName() {
		return BOSS_NAME;
	}
}
