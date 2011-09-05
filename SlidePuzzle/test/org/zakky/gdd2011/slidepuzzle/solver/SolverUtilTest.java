
package org.zakky.gdd2011.slidepuzzle.solver;

import static org.junit.Assert.*;
import static org.zakky.gdd2011.slidepuzzle.solver.SolverUtil.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.zakky.gdd2011.slidepuzzle.Puzzle;

public class SolverUtilTest {
    @SuppressWarnings("unused")
    private final SolverUtilTest self = this;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testCalcManhattanDistance() {
        assertEquals(0, calcManhattanDistance(0, 0, 0, 0));
        assertEquals(0, calcManhattanDistance(100, 100, 100, 100));

        assertEquals(1, calcManhattanDistance(10, 11, 10, 10));
        assertEquals(1, calcManhattanDistance(11, 10, 10, 10));
        assertEquals(1, calcManhattanDistance(10, 10, 11, 10));
        assertEquals(1, calcManhattanDistance(10, 10, 10, 11));

        assertEquals(2, calcManhattanDistance(0, 0, 1, 1));
        assertEquals(2, calcManhattanDistance(1, 1, 0, 0));
        assertEquals(2, calcManhattanDistance(0, 1, 1, 0));
        assertEquals(2, calcManhattanDistance(1, 0, 0, 1));
        assertEquals(2, calcManhattanDistance(100, 100, 101, 101));
        assertEquals(2, calcManhattanDistance(101, 101, 100, 100));
        assertEquals(2, calcManhattanDistance(100, 101, 101, 100));
        assertEquals(2, calcManhattanDistance(101, 100, 100, 101));
    }

    @Test
    public void testCalcManhattanDistanceSum() {
        assertEquals(0, calcManhattanDistanceSum(new Puzzle(0, "3,3,123456780")));
        assertEquals(12, calcManhattanDistanceSum(new Puzzle(0, "3,3,012345678")));
    }

}
