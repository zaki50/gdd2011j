
package org.zakky.gdd2011.slidepuzzle.solver;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.zakky.gdd2011.slidepuzzle.Puzzle;

public class PuzzleTest {
    @SuppressWarnings("unused")
    private final PuzzleTest self = this;

    @Test
    public void testMatches() {

        final Puzzle target = new Puzzle(0, "6,6,=74638EF9==C2KDGHI50====J=RSTUPVWXYZ");

        // 完全一致
        assertTrue(target.matches("=74638EF9==C2KDGHI50====J=RSTUPVWXYZ".toCharArray()));

        // ワイルドカード使用
        assertTrue(target.matches(" 7 6 8 F = C K G I 0 = = = S U V X Z".toCharArray()));

        // 本来壁でない所が壁
        assertFalse(target.matches("074638EF9==C2KDGHI50====J=RSTUPVWXYZ".toCharArray()));

        // 配列が短い
        assertFalse(target.matches("=74638EF9==C2KDGHI50====J=RSTUPVWXY".toCharArray()));

        // 配列が長い
        assertFalse(target.matches("=74638EF9==C2KDGHI50====J=RSTUPVWXYZZ".toCharArray()));
    }
}
