package me.theophobia.mythic.classes;

import me.theophobia.mythic.skills.ISkill;
import org.reflections.Reflections;

import java.util.Set;

public interface IClass {
	Set<ISkill> getClassSkills();

	static Set<Class<? extends IClass>> getClasses() {
		Reflections reflections = new Reflections("me.theophobia.mythic");
		return reflections.getSubTypesOf(IClass.class);
	}
}
