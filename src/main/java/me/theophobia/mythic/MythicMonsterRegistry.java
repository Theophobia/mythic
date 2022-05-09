package me.theophobia.mythic;

import org.bukkit.entity.LivingEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MythicMonsterRegistry {

	private static final MythicMonsterRegistry instance = new MythicMonsterRegistry();

	public static MythicMonsterRegistry getInstance() {
		return instance;
	}

	private Map<UUID, MythicMonster> map = new HashMap<>();

	public void register(MythicMonster mm) {
		if (map.containsKey(mm.getEntity().getUniqueId())) {
			throw new IllegalStateException("UUID of entity already exists");
		}

		map.put(mm.getEntity().getUniqueId(), mm);
	}
}
