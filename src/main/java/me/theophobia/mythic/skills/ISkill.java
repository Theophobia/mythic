package me.theophobia.mythic.skills;

import org.reflections.Reflections;

import java.util.Set;

public interface ISkill {

	static Set<Class<? extends ISkill>> getSkills() {
		Reflections reflections = new Reflections("me.theophobia.mythic");
		return reflections.getSubTypesOf(ISkill.class);
	}
}
