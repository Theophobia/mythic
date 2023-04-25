package me.theophobia.mythic.listeners;

import java.util.Map;

import me.theophobia.mythic.Config;
import me.theophobia.mythic.MobValue;
import org.bukkit.entity.Ageable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

public class MonsterDeathListener implements Listener {
	@EventHandler
	public void onMonsterDeath(EntityDeathEvent e) {
		if (e.getEntity().fromMobSpawner()) {
			return;
		}

		boolean isBaby = false;

		if (e.getEntity() instanceof Ageable a) {
			isBaby = !a.isAdult();
		}

		final Map<MobValue, Double> mobValues = Config.getInstance().getModifiers(e.getEntityType(), isBaby);
		final Double expScale = mobValues.get(MobValue.EXP);
		final Double lootScale = mobValues.get(MobValue.LOOT);

		for (ItemStack is : e.getDrops()) {
			is.setAmount((int) (lootScale * is.getAmount()));
		}

		e.setDroppedExp((int)(expScale * e.getDroppedExp()));
	}
}
