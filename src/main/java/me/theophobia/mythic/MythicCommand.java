package me.theophobia.mythic;

import com.google.gson.Gson;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.*;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MythicCommand implements CommandExecutor, TabCompleter {

	@Override
	public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
		if (args.length == 0) {

		}
		else if (args.length == 1) {
			return List.of("reload", "config");
		}

		return List.of();
	}

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
		if (args.length == 0) {

		}
		else if (args.length == 1) {
			if ("reload".equalsIgnoreCase(args[0])) {
				Config.getInstance().load();

				Mythic.cancelPeriodicBossSpawns();
				Mythic.schedulePeriodicBossSpawns();

				sender.sendMessage("Reloaded configuration!");
			}
			else if ("config".equalsIgnoreCase(args[0])) {
				sender.sendMessage(Config.getInstance().toPrettyJson());
			}
		}

		return true;
	}
}
