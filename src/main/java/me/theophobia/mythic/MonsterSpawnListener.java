package me.theophobia.mythic;

import net.kyori.adventure.text.Component;
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
			boolean isBoss = false;

			if (Mythic.getBossName().equals(le.customName())) {
				isBoss = true;
				le.customName(Component.text("isus"));
			}

			// Set entity health
			AttributeInstance hpAttr = le.getAttribute(Attribute.GENERIC_MAX_HEALTH);
			if (hpAttr != null) {
				if (isBoss) {
					hpAttr.setBaseValue(Config.getInstance().getBossHealthScale() * hpAttr.getValue());
				}
				else {
					hpAttr.setBaseValue(Config.getInstance().getHealthScale() * hpAttr.getValue());
				}
				le.setHealth(hpAttr.getValue());
			}

			// Set entity knockback
			AttributeInstance kbAttr = le.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE);
			if (kbAttr != null) {
				if (isBoss) {
					kbAttr.setBaseValue(Config.getInstance().getBossKnockback());
				}
				else {
					kbAttr.setBaseValue(Config.getInstance().getKnockback());
				}
			}

			// Set entity damage
			AttributeInstance dmgAttr = le.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE);
			if (dmgAttr != null) {
				if (isBoss) {
					dmgAttr.setBaseValue(Config.getInstance().getBossDamageScale() * dmgAttr.getValue());
				}
				else {
					dmgAttr.setBaseValue(Config.getInstance().getDamageScale() * dmgAttr.getValue());
				}
			}

			// Set entity movement speed
			AttributeInstance mvAttr = le.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED);
			if (mvAttr != null) {
				if (isBoss) {
					mvAttr.setBaseValue(Config.getInstance().getBossMovementSpeedScale() * mvAttr.getValue());
				}
				else {
					mvAttr.setBaseValue(Config.getInstance().getMovementSpeedScale() * mvAttr.getValue());
				}
			}

			if (Config.getInstance().isMobModificationLogged()) {
				Mythic.getPlugin().getLogger().log(Level.INFO, "Modified " + le.getType() + " at " + le.getLocation());
			}



//			((LivingEntity) e.getEntity()).setAI(false);
//			((LivingEntity) e.getEntity()).setNoDamageTicks(10 * 20);
		}
	}
}
