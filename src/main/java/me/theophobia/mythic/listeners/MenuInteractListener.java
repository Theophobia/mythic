package me.theophobia.mythic.listeners;

import me.theophobia.mythic.MenuRegistry;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.function.Consumer;

public class MenuInteractListener implements Listener {

	@EventHandler
	public void onInventoryInteract(InventoryClickEvent e) {
		if (e.isCancelled()) {
			return;
		}

		Inventory topInv = e.getView().getTopInventory();
		try {
			Consumer<InventoryClickEvent> consumer = MenuRegistry.getInstance().getInventoryClickConsumer(topInv);
			consumer.accept(e);
			e.setCancelled(true);
		}
		catch (Exception ex) {
			// Key does not exist
		}

	}
}
