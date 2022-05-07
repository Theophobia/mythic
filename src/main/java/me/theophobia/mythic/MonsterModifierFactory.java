//package me.theophobia.mythic;
//
//import org.bukkit.Bukkit;
//import org.bukkit.Location;
//import org.bukkit.attribute.Attribute;
//import org.bukkit.attribute.AttributeInstance;
//import org.bukkit.entity.Entity;
//import org.bukkit.entity.EntityType;
//import org.bukkit.entity.LivingEntity;
//import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
//
//import java.util.logging.Level;
//
//public class MonsterModifierFactory {
//
//	private int maxHealth;
//	private boolean isMaxHealthSet = false;
//
//	private double knockbackResist;
//	private boolean isKnockbackResistSet = false;
//
//	public void withMaxHealth(int maxHealth) {
//		this.maxHealth = maxHealth;
//		isMaxHealthSet = true;
//	}
//
//	public void withKnockbackResist(double knockbackResist) {
//		this.knockbackResist = knockbackResist;
//		isKnockbackResistSet = true;
//	}
//
//
//	public void spawn(Location location, EntityType entityType, SpawnReason reason) {
//		location.getWorld().spawnEntity(location, entityType, reason, this::apply);
//	}
//
//	public void apply(Entity entity) {
////		if (!isComplete()) {
////			Bukkit.getLogger().log(Level.SEVERE, "MonsterFactory is not complete");
////			return;
////		}
//
//		LivingEntity le = ((LivingEntity) entity);
//
//		// Set mob health
//		if (isMaxHealthSet) {
//			AttributeInstance hpAttr = le.getAttribute(Attribute.GENERIC_MAX_HEALTH);
//			if (hpAttr == null) {
//				Bukkit.getLogger().log(Level.SEVERE, "Entity does not have Attribute.GENERIC_MAX_HEALTH");
//				return;
//			}
//			hpAttr.setBaseValue(maxHealth);
//			le.setHealth(hpAttr.getValue());
//		}
//
//		// Set mob knockback resistance
//		if (isKnockbackResistSet) {
//			AttributeInstance kbAttr = le.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE);
//			if (kbAttr == null) {
//				Bukkit.getLogger().log(Level.SEVERE, "Entity does not have Attribute.GENERIC_KNOCKBACK_RESISTANCE");
//				return;
//			}
//			kbAttr.setBaseValue(knockbackResist);
//		}
//	}
//}
