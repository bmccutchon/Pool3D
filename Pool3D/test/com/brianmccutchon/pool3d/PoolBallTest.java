package com.brianmccutchon.pool3d;

import static org.junit.Assert.*;
import geometry.Point3D;
import geometry.Tools3D;
import geometry.Triangle3D;

import java.util.Iterator;

import org.junit.Test;

public class PoolBallTest {

	@Test
	public void testWireframe() {
		PoolBall ball = new PoolBall(0, 0, 0, null, null, 0);
		for (Triangle3D tri : ball.getTriangles()) {
			assertEquals(-1, Math.signum(Tools3D.sigmaVal(tri.points[0],
					tri.points[1], tri.points[2], new Point3D(0, 0, 0))), 0.0);
		}

		assertEquals((int) (5 * Math.pow(4, PoolBall.SMOOTHNESS)),
				new PoolBall(0, 0, 0, null, null, 0)
						.getTriangles().size());
	}

	@Test
	public void testRack() {
		PoolBall[] balls = PoolBall.rack();

		assertEquals(16, balls.length);

		int counter = 0;
		for (PoolBall b : balls) {
			assertNotNull(b);

			assertEquals(counter++, b.ballNum);
		}

		assertEquals(new Point3D(0, 0, 0), balls[8].center);

		for (int i : range(0, balls.length-1))
			for (int j : range(i+1, balls.length-1))
				assertFalse(balls[i].intersects(balls[j]));
	}

	/**
	 * Python style range method. Use to iterate through the set
	 * <code>{i, i+1,..., j-1, j}</code>. There are two ways to use this:
	 * <pre>
	 * for (int i : range(1, 5))
	 *     System.out.println(i);
	 * </pre>
	 * <pre>
	 * range(1, 20).forEach(System.out::println);
	 * </pre>
	 * @param i The start of the range.
	 * @param j The end of the range.
	 * @return An Iterable whose iterator iterates over each element of the range.
	 */
	private static Iterable<Integer> range(int i, int j) {
		return () -> new Iterator<Integer>() {
			int next = i;

			@Override
			public boolean hasNext() {
				return next <= j;
			}

			@Override
			public Integer next() {
				return next++;
			}
		};
	}

}