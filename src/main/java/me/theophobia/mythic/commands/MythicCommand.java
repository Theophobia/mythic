package me.theophobia.mythic.commands;

import me.theophobia.mythic.Config;
import me.theophobia.mythic.MenuRegistry;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public class MythicCommand implements CommandExecutor, TabCompleter {

	@Override
	public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
		if (args.length == 0) {

		}
		else if (args.length == 1) {
			return List.of("reload", "config", "profile");
		}

		return List.of();
	}

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
		if (args.length == 0) {
			return true;
		}

		if ("reload".equalsIgnoreCase(args[0])) {
			Config.getInstance().load();

//				Mythic.cancelPeriodicBossSpawns();
//				Mythic.schedulePeriodicBossSpawns();

			sender.sendMessage("Reloaded configuration!");
		}
		else if ("config".equalsIgnoreCase(args[0])) {
			sender.sendMessage(Config.getInstance().toPrettyJson());
		}
		else if ("profile".equalsIgnoreCase(args[0])) {
			if (!(sender instanceof Player player)) {
				sender.sendMessage("Sender must be a player");
				return true;
			}

			Inventory inv = Bukkit.getServer().createInventory(null, 45, Component.text(UUID.randomUUID().toString()));

			MenuRegistry.getInstance().removeNotViewed();
			MenuRegistry.getInstance().register(inv, e -> {
				e.getWhoClicked().sendMessage("Click!");
			});

			player.openInventory(inv);
		}

		return true;
	}
}
