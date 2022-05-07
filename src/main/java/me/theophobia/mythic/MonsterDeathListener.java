package me.theophobia.mythic;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

public class MonsterDeathListener implements Listener {
	@EventHandler
	public void onMonsterDeath(EntityDeathEvent e) {
		for (ItemStack is : e.getDrops()) {
			is.setAmount((int) (Config.getInstance().getLootScale() * is.getAmount()));
		}

		e.setDroppedExp((int) (Config.getInstance().getExperienceScale() * e.getDroppedExp()));
	}
}
