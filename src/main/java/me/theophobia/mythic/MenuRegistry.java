package me.theophobia.mythic;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.function.Consumer;

public class MenuRegistry {

	private static final MenuRegistry instance = new MenuRegistry();

	public static MenuRegistry getInstance() {
		return instance;
	}

	private Map<Inventory, Consumer<InventoryClickEvent>> map = new HashMap<>();

	public void register(Inventory inv, Consumer<InventoryClickEvent> cons) {
		if (!map.containsKey(inv)) {
			map.put(inv, cons);
		}
	}

	public void removeNotViewed() {
		map.entrySet().removeIf(entry -> entry.getKey().getViewers().isEmpty());
	}

	public Consumer<InventoryClickEvent> getInventoryClickConsumer(Inventory inv) {
		if (map.containsKey(inv)) {
			return map.get(inv);
		}

		throw new NoSuchElementException();
	}
}
