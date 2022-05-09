package me.theophobia.mythic;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import org.bukkit.util.Vector;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class VectorUtilTest {
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

	@Test
	public void getRandomUnitVectorXZ() {
		Vector v = Util.Vectors.getRandomUnitVectorXZ();

		Assert.assertTrue(v.isNormalized());
		Assert.assertEquals(0, v.getY(), 0.0001);
	}

}
