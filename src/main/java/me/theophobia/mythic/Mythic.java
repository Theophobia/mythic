package me.theophobia.mythic;

import com.google.gson.Gson;

import me.theophobia.mythic.commands.MythicCommand;
import me.theophobia.mythic.listeners.MenuInteractListener;
import me.theophobia.mythic.listeners.MonsterDeathListener;
import me.theophobia.mythic.listeners.MonsterSpawnListener;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;
import org.bukkit.util.Vector;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Level;

public final class Mythic extends JavaPlugin {

	private static Map<TaskType, List<Integer>> tasks = new HashMap<>();
	private static Plugin plugin;
	private static File configFile;

	public Mythic() {
		super();
	}

	protected Mythic(JavaPluginLoader loader, PluginDescriptionFile description, File dataFolder, File file) {
		super(loader, description, dataFolder, file);
	}

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
		getServer().getPluginManager().registerEvents(new MenuInteractListener(), plugin);

		// Load configuration and update save it back.
		// Saving it back ensures there is no missing information in the file.
		Config.getInstance().save();
//		getLogger().log(Level.INFO, new Gson().toJson(Config.getInstance()));

//		schedulePeriodicBossSpawns();
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

		List<? extends Player> players = Mythic.getPlugin().getServer().getOnlinePlayers().stream().filter(player -> player.getWorld().getEnvironment().equals(World.Environment.NORMAL)).toList();

		// Calculate arithmetic mean coordinates
		AtomicLong accumulatedX = new AtomicLong(0L);
		AtomicLong accumulatedZ = new AtomicLong(0L);
		AtomicLong count = new AtomicLong(0L);

		players.forEach(player -> {
			accumulatedX.addAndGet(player.getLocation().getBlockX());
			accumulatedZ.addAndGet(player.getLocation().getBlockZ());
			count.incrementAndGet();
		});

		long x = accumulatedX.get() / count.get();
		long z = accumulatedZ.get() / count.get();

		// Calculate vector to fastest
		Vector escapeVector = new Vector();
		players.forEach(player -> {
			escapeVector.add(player.getLocation().subtract(x, 0, z).toVector());
		});
		escapeVector.normalize().multiply(-1);

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
}
