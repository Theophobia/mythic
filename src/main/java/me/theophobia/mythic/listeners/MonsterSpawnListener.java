package me.theophobia.mythic.listeners;

import me.theophobia.mythic.Config;
import me.theophobia.mythic.MobValue;
import me.theophobia.mythic.Mythic;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Ageable;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;

import java.util.Map;
import java.util.logging.Level;

public class MonsterSpawnListener implements Listener {
	@EventHandler
	public void onMonsterSpawn(EntitySpawnEvent e) {
		if (e.getEntity() instanceof LivingEntity le) {
			boolean isBaby = false;

			if (e.getEntity() instanceof Ageable a) {
				isBaby = !a.isAdult();
			}
			final Map<MobValue, Double> mobValues = Config.getInstance().getModifiers(e.getEntityType(), isBaby);

			scaleHealth(le, mobValues.get(MobValue.HEALTH));
			scaleAttribute(le, Attribute.GENERIC_ATTACK_DAMAGE, mobValues.get(MobValue.DAMAGE));
			scaleAttribute(le, Attribute.GENERIC_MOVEMENT_SPEED, mobValues.get(MobValue.MOVESPEED));
			scaleAttribute(le, Attribute.GENERIC_KNOCKBACK_RESISTANCE, mobValues.get(MobValue.KNOCKBACK));

			if (le.getHealth() > 500.0 || Config.getInstance().isMobModificationLogged()) {
				Mythic.getPlugin().getLogger().log(Level.INFO, "Modified " + le.getType() + " at " + le.getLocation() + " " + le.getHealth());
			}
		}
	}

	private void scaleHealth(LivingEntity le, double scale) {
		AttributeInstance hpAttr = le.getAttribute(Attribute.GENERIC_MAX_HEALTH);
		if (hpAttr != null) {
			hpAttr.setBaseValue((int) scale * hpAttr.getBaseValue());
			le.setHealth(hpAttr.getValue());
		}
	}

	private void scaleAttribute(LivingEntity le, Attribute attr, double scale) {
		AttributeInstance ai = le.getAttribute(attr);
		if (ai != null) {
			ai.setBaseValue(scale * ai.getValue());
		}
	}

	private void setAttribute(LivingEntity le, Attribute attr, double value) {
		AttributeInstance ai = le.getAttribute(attr);
		if (ai != null) {
			ai.setBaseValue(value);
		}
	}
}
