package me.theophobia.mythic.listeners;

import me.theophobia.mythic.Config;
import me.theophobia.mythic.Constants;
import me.theophobia.mythic.Mythic;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;

import java.util.logging.Level;

public class MonsterSpawnListener implements Listener {


	@EventHandler
	public void onMonsterSpawn(EntitySpawnEvent e) {
		if (e.getEntity() instanceof LivingEntity) {
			LivingEntity le = (LivingEntity) e.getEntity();
			Config conf = Config.getInstance();
			boolean isBoss = false;

			if (Constants.BOSS_NAME_COMPONENT.equals(le.customName())) {
				isBoss = true;
				le.customName(null);
			}

			// Set entity health
			scaleHealth(le, isBoss ? conf.getBossHealthScale() : conf.getHealthScale());

			// Set entity knockback
			setAttribute(le, Attribute.GENERIC_KNOCKBACK_RESISTANCE, isBoss ? conf.getBossKnockback() : conf.getKnockback());

			// Set entity damage
			scaleAttribute(le, Attribute.GENERIC_ATTACK_DAMAGE, isBoss ? conf.getBossDamageScale() : conf.getDamageScale());

			// Set entity movement speed
			scaleAttribute(le, Attribute.GENERIC_MOVEMENT_SPEED, isBoss ? conf.getBossMovementSpeedScale() : conf.getMovementSpeedScale());

			// Log entity modification
			if (conf.isMobModificationLogged()) {
				Mythic.getPlugin().getLogger().log(Level.INFO, "Modified " + le.getType() + " at " + le.getLocation());
			}

//			MythicMonsterRegistry.getInstance().register();
		}
	}

	private void scaleHealth(LivingEntity le, double scale) {
		AttributeInstance hpAttr = le.getAttribute(Attribute.GENERIC_MAX_HEALTH);
		if (hpAttr != null) {
			hpAttr.setBaseValue(scale * hpAttr.getValue());
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
