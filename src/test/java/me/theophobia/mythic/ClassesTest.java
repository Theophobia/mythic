package me.theophobia.mythic;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import me.theophobia.mythic.classes.IClass;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ClassesTest {
	private ServerMock server;
	private Mythic mythic;

	@Before
	public void setUp() {
		server = MockBukkit.mock();
		mythic = MockBukkit.load(Mythic.class);
	}

	@After
	public void tearDown() {
		MockBukkit.unmock();
	}

//	@Test
//	public void allClasses() {
//		System.out.println(IClass.getClasses());
//	}

}
