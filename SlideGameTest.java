import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.*;

/**
 * This class tests the non-GUI logic functions of SlideGame.java.
 *
 * @author Graham Girone
 */
public class SlideGameTest {
    /**
     * #1
     * Test getLogicArray() and setLogicArray()
     */
    @Test
    public void testGetAndSetLogicArray() {
        // Ensure that the testArray matches the array returned by getLogicArray()
        int[][] testArray = {
                {2, 2, 2, 2},
                {2, 2, 2, 2},
                {2, 2, 2, 2},
                {2, 2, 4, 2},
                {2, 2, 2, 2}
        };
        SlideGame.GameLogic.setLogicArray(testArray);
        assertArrayEquals(testArray, SlideGame.GameLogic.getLogicArray());
    }

    /**
     * #2
     * Test getNumRows() and setNumRows()
     */
    @Test
    public void testGetAndSetNumRows() {
        // Ensure that testValue matches the value of numRows when getNumRows() is called
        SlideGame.GameLogic.setNumRows(10);
        assertEquals(10, SlideGame.GameLogic.getNumRows());
    }

    /**
     * #3
     * Test getNumColumns() and setNumColumns()
     */
    @Test
    public void testGetAndSetNumColumns() {
        // Ensure that testValue matches the value of numColumns when getNumColumns() is called
        SlideGame.GameLogic.setNumColumns(22);
        assertEquals(22, SlideGame.GameLogic.getNumColumns());
    }

    /**
     * #4
     * Test getCanSelectRandomInt() and setCanSelectRandomInt()
     */
    @Test
    public void testGetAndSetCanSelectRandomInt() {
        SlideGame.GameLogic.setCanSelectRandomInt(false);
        assertEquals(false, SlideGame.GameLogic.getCanSelectRandomInt());
    }

    /**
     * #5
     * Test initializeLogicArray()
     */
    @Test
    public void testInitializeLogicArray() {
        // Stores boolean value representing if both test and logic arrays add up to one.
        boolean addsUpToOne = false;
        // Stores the sum of the testArray
        int sumOfTestArray = 0;
        // Stores the sum of the logicArray
        int sumOfLogicArray = 0;

        int[][] testArray = new int[4][4];

        // Initializes testArray with 0s, except one random index. Then sums the values of the indices
        for (int i = 0; i < testArray.length; i++) {
            for (int j = 0; j < testArray[i].length; j++) {
                testArray[i][j] = 0;
                testArray[3][3] = 1;
                sumOfTestArray += testArray[i][j];

            }
        }

        // Initializes the logicArray
        SlideGame.GameLogic.initializeLogicArray();

        // Sums the values of the logicArray
        for (int i = 0; i < SlideGame.GameLogic.getLogicArray().length; i++) {
            for (int j = 0; j < SlideGame.GameLogic.getLogicArray()[i].length; j++) {
                sumOfLogicArray += SlideGame.GameLogic.getLogicArray()[i][j];

            }
        }

        // Checks to see if the sums of both the testArray and logicArray add up to one.
        if (sumOfTestArray == 1 && sumOfLogicArray == 1) {
            addsUpToOne = true;
        }

        // Assert true if both arrays add up to one
        assertEquals(true, addsUpToOne);
    }

    /**
     * #6
     * Test selectRandomInt()
     */
    @Test
    public void testSelectRandomInt() {
        // Stores if the logicArray currently adds up to two or not
        boolean addsUpToTwo = false;
        // Stores the sum of the integers in the logicArray
        int sumOfBoard = 0;

        // Initialize the logicArray and add an extra random int to the board
        SlideGame.GameLogic.initializeLogicArray();
        SlideGame.GameLogic.selectRandomInt();

        // Sum the values of the logicArray
        for (int[] column : SlideGame.GameLogic.getLogicArray()) {
            for (int row : column) {
                sumOfBoard += row;
            }
        }

        // Check if the sum of the values of logicArray now equal 2
        if (sumOfBoard == 2) {
            addsUpToTwo = true;
        }

        // Test passes if the logicArray now adds up to two
        assertEquals(true, addsUpToTwo);
    }

    /**
     * #7
     * Test displayGameBoard()
     */
    @Test
    public void testDisplayGameBoard() {
        // Captures the output to the console
        final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));

        // Stores the expected output to the console based on the testArray
        String expectedOutput = "2 4 6 8 \n8 6 4 2 \n2 4 6 8 \n8 6 4 2 \n2 4 6 8 \n";

        int[][] testArray = {
                {2, 4, 6, 8},
                {8, 6, 4, 2},
                {2, 4, 6, 8},
                {8, 6, 4, 2},
                {2, 4, 6, 8}
        };

        SlideGame.GameLogic.setLogicArray(testArray);
        SlideGame.GameLogic.displayGameBoard();
        assertEquals(expectedOutput, outputStreamCaptor.toString());
    }

    /**
     * #8
     * Test canMergeLeft()
     */
    @Test
    public void testCanMergeLeft() {
        int[][] canBeMerged = {
                {2, 1, 0, 0},
                {2, 1, 0, 0},
                {2, 1, 0, 0},
                {2, 1, 0, 0},
                {2, 1, 1, 0}
        };
        int[][] canNotBeMerged = {
                {2, 1, 0, 0},
                {2, 1, 0, 0},
                {2, 1, 0, 0},
                {2, 1, 0, 0},
                {2, 1, 0, 0}
        };
        SlideGame.GameLogic.setLogicArray(canBeMerged);
        assertEquals(true, SlideGame.GameLogic.canMergeLeft());

        SlideGame.GameLogic.setLogicArray(canNotBeMerged);
        assertEquals(false, SlideGame.GameLogic.canMergeLeft());
    }

    /**
     * #9
     * Test mergeLeft()
     */
    @Test
    public void testMergeLeft() {
        int[][] beforeMerge = {
                {4, 1, 1, 1},
                {8, 4, 4, 0},
                {2, 1, 1, 0},
                {4, 1, 0, 0},
                {8, 4, 2, 2}
        };

        int[][] afterMerge = {
                {4, 2, 1, 0},
                {16, 0, 0, 0},
                {4, 0, 0, 0},
                {4, 1, 0, 0},
                {16, 0, 0, 0}
        };

        SlideGame.GameLogic.setLogicArray(beforeMerge);
        SlideGame.GameLogic.mergeLeft();

        assertArrayEquals(afterMerge, SlideGame.GameLogic.getLogicArray());
    }

    /**
     * #10
     * Test canMergeRight()
     */
    @Test
    public void testCanMergeRight() {
        int[][] canBeMerged = {
                {2, 1, 0, 0},
                {2, 1, 0, 0},
                {2, 1, 0, 0},
                {2, 1, 0, 0},
                {2, 1, 1, 0}
        };
        int[][] canNotBeMerged = {
                {0, 0, 1, 2},
                {0, 0, 1, 2},
                {0, 0, 1, 2},
                {0, 0, 1, 2},
                {0, 0, 1, 2}
        };
        SlideGame.GameLogic.setLogicArray(canBeMerged);
        assertEquals(true, SlideGame.GameLogic.canMergeRight());

        SlideGame.GameLogic.setLogicArray(canNotBeMerged);
        assertEquals(false, SlideGame.GameLogic.canMergeRight());
    }

    /**
     * #11
     * Test mergeRight()
     */
    @Test
    public void testMergeRight() {
        int[][] beforeMerge = {
                {4, 1, 1, 1},
                {8, 4, 4, 0},
                {2, 1, 1, 0},
                {4, 1, 0, 0},
                {8, 4, 2, 2}
        };

        int[][] afterMerge = {
                {0, 4, 1, 2},
                {0, 0, 0, 16},
                {0, 0, 0, 4},
                {0, 0, 4, 1},
                {0, 0, 0, 16}
        };

        SlideGame.GameLogic.setLogicArray(beforeMerge);
        SlideGame.GameLogic.mergeRight();

        assertArrayEquals(afterMerge, SlideGame.GameLogic.getLogicArray());
    }

    /**
     * #12
     * Test canMergeUp()
     */
    @Test
    public void testCanMergeUp() {
        int[][] canBeMerged = {
                {2, 1, 4, 2},
                {3, 2, 0, 0},
                {3, 1, 0, 2},
                {4, 1, 1, 0},
                {4, 0, 1, 0}
        };
        int[][] canNotBeMerged = {
                {2, 2, 2, 2},
                {1, 1, 1, 1},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
        };
        SlideGame.GameLogic.setLogicArray(canBeMerged);
        assertEquals(true, SlideGame.GameLogic.canMergeUp());

        SlideGame.GameLogic.setLogicArray(canNotBeMerged);
        assertEquals(false, SlideGame.GameLogic.canMergeUp());
    }

    /**
     * #13
     * Test mergeUp()
     */
    @Test
    public void testMergeUp() {
        int[][] beforeMerge = {
                {2, 1, 4, 2},
                {3, 2, 0, 0},
                {3, 1, 0, 2},
                {4, 1, 1, 0},
                {4, 0, 1, 0}
        };
        int[][] afterMerge = {
                {2, 1, 4, 4},
                {6, 4, 2, 0},
                {8, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
        };
        SlideGame.GameLogic.setLogicArray(beforeMerge);
        SlideGame.GameLogic.mergeUp();

        assertArrayEquals(afterMerge, SlideGame.GameLogic.getLogicArray());
    }

    /**
     * #14
     * Test canMergeDown()
     */
    @Test
    public void testCanMergeDown() {
        int[][] canBeMerged = {
                {2, 1, 4, 2},
                {3, 2, 0, 0},
                {3, 1, 0, 2},
                {4, 1, 1, 0},
                {4, 0, 1, 0}
        };
        int[][] canNotBeMerged = {
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {1, 1, 1, 1},
                {2, 2, 2, 2}
        };
        SlideGame.GameLogic.setLogicArray(canBeMerged);
        assertEquals(true, SlideGame.GameLogic.canMergeDown());

        SlideGame.GameLogic.setLogicArray(canNotBeMerged);
        assertEquals(false, SlideGame.GameLogic.canMergeDown());
    }

    /**
     * #15
     * Test mergeDown()
     */
    @Test
    public void testMergeDown() {
        int[][] beforeMerge = {
                {2, 1, 4, 2},
                {3, 2, 0, 0},
                {3, 1, 0, 2},
                {4, 1, 1, 0},
                {4, 0, 1, 0}
        };
        int[][] afterMerge = {
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {2, 0, 0, 0},
                {6, 1, 4, 0},
                {8, 4, 2, 4}
        };
        SlideGame.GameLogic.setLogicArray(beforeMerge);
        SlideGame.GameLogic.mergeDown();

        assertArrayEquals(afterMerge, SlideGame.GameLogic.getLogicArray());
    }

    /**
     * #16
     * Test mergeUpLeft()
     */
    @Test
    public void testMergeUpLeft() {
        int[][] beforeMerge = {
                {2, 1, 4, 2},
                {3, 2, 0, 0},
                {3, 1, 0, 2},
                {4, 1, 1, 0},
                {4, 0, 1, 0}
        };
        int[][] afterMerge = {
                {2, 1, 8, 0},
                {6, 4, 2, 0},
                {8, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
        };
        SlideGame.GameLogic.setLogicArray(beforeMerge);
        SlideGame.GameLogic.mergeUpLeft();

        assertArrayEquals(afterMerge, SlideGame.GameLogic.getLogicArray());
    }

    /**
     * #17
     * Test mergeUpLeft()
     */
    @Test
    public void testMergeUpRight() {
        int[][] beforeMerge = {
                {2, 1, 4, 2},
                {3, 2, 0, 0},
                {3, 1, 0, 2},
                {4, 1, 1, 0},
                {4, 0, 1, 0}
        };
        int[][] afterMerge = {
                {0, 2, 1, 8},
                {0, 6, 4, 2},
                {0, 0, 0, 8},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
        };
        SlideGame.GameLogic.setLogicArray(beforeMerge);
        SlideGame.GameLogic.mergeUpRight();

        assertArrayEquals(afterMerge, SlideGame.GameLogic.getLogicArray());
    }

    /**
     * #18
     * Test mergeDownLeft()
     */
    @Test
    public void testMergeDownLeft() {
        int[][] beforeMerge = {
                {2, 1, 4, 2},
                {3, 2, 0, 0},
                {3, 1, 0, 2},
                {4, 1, 1, 0},
                {4, 0, 1, 0}
        };
        int[][] afterMerge = {
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {2, 0, 0, 0},
                {6, 1, 4, 0},
                {8, 4, 2, 4}
        };
        SlideGame.GameLogic.setLogicArray(beforeMerge);
        SlideGame.GameLogic.mergeDownLeft();

        assertArrayEquals(afterMerge, SlideGame.GameLogic.getLogicArray());
    }

    /**
     * #19
     * Test mergeDownRight()
     */
    @Test
    public void testMergeDownRight() {
        int[][] beforeMerge = {
                {2, 1, 4, 2},
                {3, 2, 0, 0},
                {3, 1, 0, 2},
                {4, 1, 1, 0},
                {4, 0, 1, 0}
        };
        int[][] afterMerge = {
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 2},
                {0, 6, 1, 4},
                {8, 4, 2, 4}
        };
        SlideGame.GameLogic.setLogicArray(beforeMerge);
        SlideGame.GameLogic.mergeDownRight();

        assertArrayEquals(afterMerge, SlideGame.GameLogic.getLogicArray());
    }

    /**
     * #20
     * Test isGameOver()
     */
    @Test
    public void testIsGameOver() {
        // Check for empty (0) indices. Should return false
        int[][] testEmptyArray = {
                {2, 1, 4, 2},
                {0, 2, 1, 1},
                {1, 1, 2, 2},
                {4, 1, 1, 1},
                {4, 2, 1, 1}
        };
        SlideGame.GameLogic.setLogicArray(testEmptyArray);
        assertEquals(false, SlideGame.GameLogic.isGameOver());

        // Check for no empty indices, but at least one merge horizontally. Should return true
        int[][] testOneMergeHorizontal = {
                {10, 9, 8, 7},
                {9, 8, 7, 6},
                {8, 7, 6, 5},
                {7, 6, 5, 4},
                {6, 5, 4, 4}
        };
        SlideGame.GameLogic.setLogicArray(testOneMergeHorizontal);
        assertEquals(false, SlideGame.GameLogic.isGameOver());

        // Check for no empty indices, but at least one merge vertically
        int[][] testOneMergeVertically = {
                {10, 9, 8, 7},
                {10, 8, 7, 6},
                {8, 7, 6, 5},
                {7, 6, 5, 4},
                {6, 5, 4, 3}
        };
        SlideGame.GameLogic.setLogicArray(testOneMergeVertically);
        assertEquals(false, SlideGame.GameLogic.isGameOver());

        // Check for no empty indices and no merges
        int[][] testNoEmptyIndicesOrMerges = {
                {10, 9, 8, 7},
                {9, 8, 7, 6},
                {8, 7, 6, 5},
                {7, 6, 5, 4},
                {6, 5, 4, 3}
        };
        SlideGame.GameLogic.setLogicArray(testNoEmptyIndicesOrMerges);
        assertEquals(true, SlideGame.GameLogic.isGameOver());
    }
}


