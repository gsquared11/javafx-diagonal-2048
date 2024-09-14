import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.Optional;
import java.util.Random;

/**
 * SlideGame.java contains the JavaFX elements and corresponding game logic, located in the nested GameLogic class,
 * to create a 2048-like game, where a player can slide tiles on a board to merge their values.
 *
 * @author Graham Girone
 */
public class SlideGameUI extends Application {
    // Stores the JavaFX buttons for the game board GUI
    private static Button[][] buttonArray;
    // Stores the JavaFX GridPane which contains the game board GUI gadgets
    private static final GridPane gameBoard = new GridPane();

    /**
     * Returns the 2D buttonArray
     *
     * @return A 2D array of button objects
     */
    public static Button[][] getButtonArray() {
        return buttonArray;
    }

    /**
     * Sets the value of the 2D buttonArray
     *
     * @param array A 2D array of button objects
     */
    public static void setButtonArray(Button[][] array) {
        buttonArray = array;
    }

    /**
     * Returns the GUI gameBoard
     *
     * @return A GridPane object
     */
    public static GridPane getGameBoard() {
        return gameBoard;
    }

    /**
     * Creates the buttons for the GUI gameBoard and assigns appropriate shifting behavior to the
     * respective buttons.
     */
    public static void initializeGameBoardGUI() {
        // Loops through the buttonArray to assign each button an appropriate action behavior and adds it to the gameBoard
        for (int i = 0; i < getButtonArray().length; i++) {
            for (int j = 0; j < getButtonArray()[i].length; j++) {
                // Initializes new button instance to each index
                getButtonArray()[i][j] = new Button();
                getButtonArray()[i][j].setPrefSize(50, 50);
                getButtonArray()[i][j].setFocusTraversable(false);
                // Assigns shift up-left to top-left corner
                if (i == 0 && j == 0) {
                    getButtonArray()[i][j].setOnAction(shiftGUIUpLeft());
                }
                // Assigns shift up-right to top-right corner
                if (i == 0 && j == getButtonArray()[i].length - 1) {
                    getButtonArray()[i][j].setOnAction(shiftGUIUpRight());
                }
                // Assigns shift down-left to bottom-left corner
                if (i == getButtonArray().length - 1 && j == 0) {
                    getButtonArray()[i][j].setOnAction(shiftGUIDownLeft());
                }
                // Assigns shift down-right to bottom-right corner
                if (i == getButtonArray().length - 1 && j == getButtonArray()[i].length - 1) {
                    getButtonArray()[i][j].setOnAction(shiftGUIDownRight());
                }
                // Ensures that all the buttons in the left-most column, except corners, are given shiftGUILeft() functionality
                if ((j == 0) && ((i != 0) && (i != getButtonArray().length - 1))) {
                    getButtonArray()[i][j].setOnAction(shiftGUILeft());
                }
                // Ensures that all the buttons in the right-most column, except corners, are given shiftGUIRight() functionality
                if ((j == getButtonArray()[i].length - 1) && ((i != 0) && (i != getButtonArray().length - 1))) {
                    getButtonArray()[i][j].setOnAction(shiftGUIRight());
                }
                // Ensures that all the buttons in the up-most row, except corners, are given shiftGUIUp() functionality
                if ((i == 0) && ((j != 0) && (j != getButtonArray()[i].length - 1))) {
                    getButtonArray()[i][j].setOnAction(shiftGUIUp());
                }
                // Ensures that all the buttons in the bottom-most row, except corners, are given shiftGUIDown() functionality
                if ((i == getButtonArray().length - 1) && ((j != 0) && (j != getButtonArray()[i].length - 1))) {
                    getButtonArray()[i][j].setOnAction(shiftGUIDown());
                }
                getGameBoard().add(getButtonArray()[i][j], j, i);
            }
        }
        updateGUI();
    }

    /**
     * Gathers the most recent logicArray data and updates the gameBoard buttons appropriately.
     */
    public static void updateGUI() {
        // Loops through the buttonArray to update the text of buttons to the corresponding value in logicArray
        for (int i = 0; i < getButtonArray().length; i++) {
            for (int j = 0; j < getButtonArray()[i].length; j++) {
                if (GameLogic.getLogicArray()[i][j] == 0) {
                    getButtonArray()[i][j].setText("");
                } else {
                    getButtonArray()[i][j].setText("" + GameLogic.getLogicArray()[i][j]);
                }
            }
        }
        // Brings focus back to the keyboard gadget to allow for simultaneous button/key input
        getGameBoard().requestFocus();
        // Checks if the random int can be selected
        if (GameLogic.getCanSelectRandomInt()) {
            GameLogic.selectRandomInt();
            // Sets the random int to false, preventing an index from being selected if there was no movement on the board
            GameLogic.setCanSelectRandomInt(false);
        }
    }

    /**
     * Shows a JavaFX Alert panel to the user informing them that the game is over and providing
     * them the option to play again.
     */
    public static void showGameOverDialogBox() {
        // Stores and initializes an information alert GUI gadget
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Game Over");
        alert.setHeaderText(null);
        alert.setContentText("Game over! You're out of moves :(\nClick \"OK\" to play again.");
        // Stores an object that waits for the user's response to start a new game
        Optional<ButtonType> userInput = alert.showAndWait();
        if (userInput.isPresent() && userInput.get() == ButtonType.OK) {
            GameLogic.initializeLogicArray();
            initializeGameBoardGUI();
        }
    }

    /**
     * Shows a JavaFX Alert panel with instructions for the game.
     */
    public static void showInstructionsDialogBox() {
        // Stores and initializes an information alert GUI gadget
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Instructions");
        alert.setHeaderText("How to Play");
        alert.setContentText("""
                Welcome to SlideGame! (can't afford the rights to 2048 :/)

                The objective is to merge tiles with the same numbers by sliding them together to create higher-value tiles and achieve a high score.

                Game Mechanics:
                1. The game starts with one tile with a value of 1 on the board.
                2. Each slide (move) adds another tile with a value of 1 to the board.
                3. You can merge tiles with the same value by sliding them in the same direction. For example, two '1' tiles will merge into a '2' tile.
                4. As tiles merge, their values add up, contributing to your score.
                5. The game continues until there are no more possible moves.

                Controls:
                - Click the tiles on the top-most, bottom-most, left-most, right-most rows and columns to move up, down, left, or right.
                - Click the tiles in the corners to move up-left, up-right, down-left, down-right.
                
                OR
                
                - Use the arrow keys or W/A/S/D to slide the tiles up, left, down, or right.
                - Use Q/E to slide the tiles up-left or up-right.
                - Use X/C to slide the tiles down-left or down-right.

                Click "OK" to start the game. Good luck!""");
        // Displays the alert and waits for the user to acknowledge
        alert.showAndWait();
    }

    /**
     * Shows a JavaFX input dialog box that gets and sets the number of rows on the GameBoard based on user's input.
     */
    public static void showNumRowsInputDialogBox() {
        // Stores an initializes a user input box for the number of rows, with a default value of 4
        TextInputDialog rowsInputBox = new TextInputDialog("4");
        rowsInputBox.setTitle("Board Creation");
        rowsInputBox.setHeaderText("Board Creation\n\nBy default, the board is set to 4 x 4 (4 rows and 4 columns)");
        rowsInputBox.setContentText("Enter the number of rows (greater than 1, less than 101) for the board:");
        // Stores an object that awaits the user's row input
        Optional<String> rowsInput = rowsInputBox.showAndWait();
        rowsInput.ifPresent(string -> {
            try {
                if (Integer.parseInt(string) < 2 || Integer.parseInt(string) > 100) {
                    throw new UnsupportedOperationException("Enter a number that is greater than 1, less than 101");
                }
                GameLogic.setNumRows(Integer.parseInt(string));
            }
            catch (Exception e) {
                showInvalidInputError();
                showNumRowsInputDialogBox();
            }
        });
    }

    /**
     * Shows a JavaFX input dialog box that gets and sets the number of columns on the GameBoard based on user's input.
     */
    public static void showNumColumnsInputDialogBox() {
        // Stores an initializes a user input box for the number of columns, with a default value of 4
        TextInputDialog colsInputBox = new TextInputDialog("4");
        colsInputBox.setTitle("Board Creation");
        colsInputBox.setHeaderText("Board Creation\n\nBy default, the board is set to a 4 x 4 (4 rows and 4 columns)");
        colsInputBox.setContentText("Enter the number of columns (greater than 1, less than 101) for the board:");
        // Stores an object that awaits the user's column input
        Optional<String> rowsInput = colsInputBox.showAndWait();
        rowsInput.ifPresent(string -> {
            try {
                if (Integer.parseInt(string) < 2 || Integer.parseInt(string) > 100) {
                    throw new UnsupportedOperationException("Enter a number that is greater than 1 or less than 101");
                }
                GameLogic.setNumColumns(Integer.parseInt(string));
            }
            catch (Exception e) {
                showInvalidInputError();
                showNumColumnsInputDialogBox();
            }
        });
    }

    /**
     * Show an JavaFX Alert signifying that a given input is invalid
     */
    public static void showInvalidInputError() {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Invalid Input");
        alert.setHeaderText("Invalid input: Enter a number that is greater than 1 or less than 101");
        Optional<ButtonType> userInput = alert.showAndWait();
    }

    /**
     * Shifts the values of the buttons on the GUI to the left
     *
     * @return An ActionEvent that performs the mergeLeft() function and updates the GUI
     */
    public static EventHandler<ActionEvent> shiftGUILeft() {
        return e -> {
            if (!GameLogic.isGameOver()) {
                GameLogic.mergeLeft();
                updateGUI();
            } else {
                showGameOverDialogBox();
            }
        };
    }

    /**
     * Shifts the values of the buttons on the GUI to the right
     *
     * @return An ActionEvent that performs the mergeRight() function and updates the GUI
     */
    public static EventHandler<ActionEvent> shiftGUIRight() {
        return e -> {
            if (!GameLogic.isGameOver()) {
                GameLogic.mergeRight();
                updateGUI();
            } else {
                showGameOverDialogBox();
            }
        };
    }

    /**
     * Shifts the values of the buttons on the GUI up
     *
     * @return An ActionEvent that performs the mergeUp() function and updates the GUI
     */
    public static EventHandler<ActionEvent> shiftGUIUp() {
        return e -> {
            if (!GameLogic.isGameOver()) {
                GameLogic.mergeUp();
                updateGUI();
            } else {
                showGameOverDialogBox();
            }
        };
    }

    /**
     * Shifts the values of the buttons on the GUI down
     *
     * @return An ActionEvent that performs the mergeDown() function and updates the GUI
     */
    public static EventHandler<ActionEvent> shiftGUIDown() {
        return e -> {
            if (!GameLogic.isGameOver()) {
                GameLogic.mergeDown();
                updateGUI();
            } else {
                showGameOverDialogBox();
            }
        };
    }

    /**
     * Shifts the values of the buttons on the GUI up and to the left
     *
     * @return An ActionEvent that performs the mergeUpLeft() function and updates the GUI
     */
    public static EventHandler<ActionEvent> shiftGUIUpLeft() {
        return e -> {
            if (!GameLogic.isGameOver()) {
                GameLogic.mergeUpLeft();
                updateGUI();
            } else {
                showGameOverDialogBox();
            }
        };
    }

    /**
     * Shifts the values of the buttons on the GUI up and to the right
     *
     * @return An ActionEvent that performs the mergeUpRight() function and updates the GUI
     */
    public static EventHandler<ActionEvent> shiftGUIUpRight() {
        return e -> {
            if (!GameLogic.isGameOver()) {
                GameLogic.mergeUpRight();
                updateGUI();
            } else {
                showGameOverDialogBox();
            }
        };
    }

    /**
     * Shifts the values of the buttons on the GUI down and to the left
     *
     * @return An ActionEvent that performs the mergeDownLeft() function and updates the GUI
     */
    public static EventHandler<ActionEvent> shiftGUIDownLeft() {
        return e -> {
            if (!GameLogic.isGameOver()) {
                GameLogic.mergeDownLeft();
                updateGUI();
            } else {
                showGameOverDialogBox();
            }
        };
    }

    /**
     * Shifts the values of the buttons on the GUI down and to the right
     *
     * @return An ActionEvent that performs the mergeDownRight() function and updates the GUI
     */
    public static EventHandler<ActionEvent> shiftGUIDownRight() {
        return e -> {
            if (!GameLogic.isGameOver()) {
                GameLogic.mergeDownRight();
                updateGUI();
            } else {
                showGameOverDialogBox();
            }
        };
    }

    /**
     * Shifts the values of the buttons on the GUI in the appropriate direction
     * depending on keypad or keyboard input
     *
     * @return An ActionEvent that performs the appropriate GameLogic merge function based on the key press
     */
    public static EventHandler<KeyEvent> shiftWithKeyboard() {
        return e -> {
            // Checks if the came is over and, if true, display the game over dialog box
            if (!GameLogic.isGameOver()) {
                // If the left arrow key or the A key is clicked, shift left
                if (e.getCode() == KeyCode.LEFT || e.getCode() == KeyCode.A) {
                    GameLogic.mergeLeft();
                    updateGUI();
                }
                // If the right arrow key or the D key is clicked, shift right
                if (e.getCode() == KeyCode.RIGHT || e.getCode() == KeyCode.D) {
                    GameLogic.mergeRight();
                    updateGUI();
                }
                // If the up arrow key or the W key is clicked, shift up
                if (e.getCode() == KeyCode.UP || e.getCode() == KeyCode.W) {
                    GameLogic.mergeUp();
                    updateGUI();
                }
                // If the down arrow key or the S key is clicked, shift down
                if (e.getCode() == KeyCode.DOWN || e.getCode() == KeyCode.S) {
                    GameLogic.mergeDown();
                    updateGUI();
                }
                // If the Q key is clicked, shift up and to the left
                if (e.getCode() == KeyCode.Q) {
                    GameLogic.mergeUpLeft();
                    updateGUI();
                }
                // If the E key is clicked, shift up and to the right
                if (e.getCode() == KeyCode.E) {
                    GameLogic.mergeUpRight();
                    updateGUI();
                }
                // If the X key is clicked, shift down and to the left
                if (e.getCode() == KeyCode.X) {
                    GameLogic.mergeDownLeft();
                    updateGUI();
                }
                // If the C key is clicked, shift down and to the right
                if (e.getCode() == KeyCode.C) {
                    GameLogic.mergeDownRight();
                    updateGUI();
                }
            } else {
                showGameOverDialogBox();
            }
        };
    }

    /**
     * Executes the enclosed methods when the JavaFX application is launched
     *
     * @param primaryStage The screen (window) that will be displayed to the user
     */
    @SuppressWarnings("DuplicatedCode")
    public void start(Stage primaryStage) {
        showInstructionsDialogBox();
        showNumRowsInputDialogBox();
        showNumColumnsInputDialogBox();
        GameLogic.setLogicArray(new int[GameLogic.getNumRows()][GameLogic.getNumColumns()]);
        setButtonArray(new Button[GameLogic.getNumRows()][GameLogic.getNumColumns()]);
        GameLogic.initializeLogicArray();
        initializeGameBoardGUI();
        // Stores and initializes the object that will be displayed on the stage
        Scene scene = new Scene(getGameBoard());
        scene.setOnKeyPressed(shiftWithKeyboard());
        primaryStage.setTitle("Slide Game");
        primaryStage.setScene(scene);
        primaryStage.show();
        // Transfers gadget focus back to the keyboard to allow for simultaneous button/keyboard inputs
        getGameBoard().requestFocus();
    }

    /**
     * Launches the JavaFX application and takes in arguments representing the number of rows and columns
     *
     * @param args String with command line arguments. The first argument (index) will be used as the number of rows on the board
     *             and the second will be used as the number on columns on the game board.
     */
    public static void main(String[] args) {
        // Checks if there is command line input and launches the application with the appropriate row/column args for the game board
        try {
            Application.launch(args[0], args[1]);
            // If no command line input, launch with no specific arguments and default (4 row x 4 column) board size will be used
        } catch (ArrayIndexOutOfBoundsException e) {
            Application.launch(args);
        }
    }

    /**
     * This class stores the core game logic for the slide game that will be translated to the GUI components of the
     * SlideGame class.
     */
    public static class GameLogic {
        // Stores the number of rows in the 2D logicArray
        private static int numRows = 4;
        // Stores the number of columns in the 2D logicArray
        private static int numColumns = 4;
        // Stores the 2D array that holds the current data for the slide game
        private static int[][] logicArray = new int[getNumRows()][getNumColumns()];
        // Stores if the random int can now be selected (first move is always true)
        private static boolean canSelectRandomInt = true;

        /**
         * Returns the logicArray
         *
         * @return An int[][] array
         */
        public static int[][] getLogicArray() {
            return logicArray;
        }

        /**
         * Sets the value of the logicArray
         *
         * @param array A 2D array of integers
         */
        public static void setLogicArray(int[][] array) {
            logicArray = array;
        }

        /**
         * Returns the number of rows in the logic array
         *
         * @return An int with the number of rows in the logic array
         */
        public static int getNumRows() {
            return numRows;
        }

        /**
         * Sets the number of rows in the logicArray
         *
         * @param rows Desired number of rows in the logicArray
         */
        public static void setNumRows(int rows) {
            numRows = rows;
        }

        /**
         * Returns the number of columns in the logic array
         *
         * @return An int with the number of columns in the logic array
         */
        public static int getNumColumns() {
            return numColumns;
        }

        /**
         * Sets the number of columns in the logicArray
         *
         * @param columns Desired number of columns in the logicArray
         */
        public static void setNumColumns(int columns) {
            numColumns = columns;
        }

        /**
         * Returns the boolean canSelectRandomInt
         *
         * @return the boolean canSelectRandomInt
         */
        public static boolean getCanSelectRandomInt() {
            return canSelectRandomInt;
        }

        /**
         * Sets canSelectRandomInt to the condition passed in the parameters
         *
         * @param condition A boolean value that stores either true or false
         */
        public static void setCanSelectRandomInt(boolean condition) {
            canSelectRandomInt = condition;
        }

        /**
         * Initializes the logicArray for a new slide game by setting all values to 0, except a random index
         * that will have a 1
         */
        public static void initializeLogicArray() {
            // Loops through the logicArray and assigns each index an int value of 0
            for (int i = 0; i < getLogicArray().length; i++) {
                for (int j = 0; j < getLogicArray()[i].length; j++) {
                    getLogicArray()[i][j] = 0;
                }
            }
            selectRandomInt();
            displayGameBoard();
        }

        /**
         * Selects a random index from the logicArray to change to 1
         */
        public static void selectRandomInt() {
            // Initializes and stores a new Random object that is used to locate random indices
            Random randomIndex = new Random();
            // Stores a random integer index for a row in the logicArray
            int randomRow;
            // Stores a random integer index for a column in the logicArray
            int randomColumn;
            /* Continually assigns random values to randomRow and randomColumn until both the indices point to an
            index in the logicArray where there is a 0 (empty) */
            do {
                randomRow = randomIndex.nextInt(getLogicArray().length);
                randomColumn = randomIndex.nextInt(getLogicArray()[0].length);
            }
            while (getLogicArray()[randomRow][randomColumn] != 0);
            // Once an empty (0) index is found, assign 1 to the index
            getLogicArray()[randomRow][randomColumn] = 1;
        }

        /**
         * Prints the logicArray to the console in a readable format (rows * columns)
         */
        public static void displayGameBoard() {
            // Loops through the logicArray and prints the values in each row and then starts a new line
            for (int[] column : getLogicArray()) {
                for (int row : column) {
                    System.out.print(row + " ");
                }
                System.out.println();
            }
        }

        /**
         * Checks if the logicArray has room to move towards the left
         *
         * @return true if more room to move to the left, false if not
         */
        public static boolean canMergeLeft() {
            // Loops through the logicArray to check if there are still like-values to merge in each row
            for (int i = 0; i < getLogicArray().length; i++) {
                for (int j = 1; j < getLogicArray()[i].length; j++) {
                    if (getLogicArray()[i][j] != 0 && (getLogicArray()[i][j - 1] == 0 || getLogicArray()[i][j] == getLogicArray()[i][j - 1])) {
                        return true;
                    }
                }
            }
            return false;
        }

        /**
         * Merges like values within each row of the logicArray to the left
         */
        public static void mergeLeft() {
            if (canMergeLeft()) {
                // While there are still adjacent cells with like-values, keep looping
                while (canMergeLeft()) {
                    // Loops through each row in the logicArray
                    for (int[] row : getLogicArray()) {
                        // Tracks and stores where to place the next empty index (0) in the row
                        int nextEmptyIndex = 0;
                        // Loops through current row to merge, similar adjacent cells to the left
                        for (int i = 0; i < row.length - 1; i++) {
                            if (row[i] != 0 && row[i] == row[i + 1]) {
                                row[i] += row[i + 1];
                                row[i + 1] = 0;
                                i++;
                                canSelectRandomInt = true;
                            }
                        }
                        // Loops through current row and shifts all non-zero elements to the left
                        for (int i = 0; i < row.length; i++) {
                            if (row[i] != 0) {
                                row[nextEmptyIndex++] = row[i];
                            }
                        }
                        // Loops between the nextEmptyIndex and the end of the current row, overriding the previous values with 0
                        for (int i = nextEmptyIndex; i < row.length; i++) {
                            row[i] = 0;
                        }
                    }
                }
                displayGameBoard();
            }
        }

        /**
         * Checks if the logicArray has room to move towards the right
         *
         * @return true if more room to move to the right, false if not
         */
        public static boolean canMergeRight() {
            // Loops through the logicArray to check if there are still like-values to merge in each row
            for (int i = 0; i < getLogicArray().length; i++) {
                for (int j = 0; j < getLogicArray()[i].length - 1; j++) {
                    if (getLogicArray()[i][j] != 0 && (getLogicArray()[i][j + 1] == 0 || getLogicArray()[i][j] == getLogicArray()[i][j + 1])) {
                        return true;
                    }
                }
            }
            return false;
        }

        /**
         * Merges like values within each row of the logicArray to the right
         */
        public static void mergeRight() {
            if (canMergeRight()) {
                // While there are still adjacent cells with like-values, keep looping
                while (canMergeRight()) {
                    // Loops through each row in the logicArray
                    for (int[] row : getLogicArray()) {
                        // Tracks and stores where to place the next empty index (0) in the row
                        int nextEmptyIndex = row.length - 1;
                        // Loops through current row to merge, similar adjacent cells to the right
                        for (int i = row.length - 1; i > 0; i--) {
                            if (row[i] != 0 && row[i] == row[i - 1]) {
                                row[i] += row[i - 1];
                                row[i - 1] = 0;
                                i--;
                            }
                        }
                        // Loops through current row and shifts all non-zero elements to the right
                        for (int i = row.length - 1; i >= 0; i--) {
                            if (row[i] != 0) {
                                row[nextEmptyIndex--] = row[i];
                            }
                        }
                        // Loops between the nextEmptyIndex and the beginning of the current row, overriding the previous values with 0
                        for (int i = nextEmptyIndex; i >= 0; i--) {
                            row[i] = 0;
                        }
                    }
                }
                displayGameBoard();
                canSelectRandomInt = true;
            }
        }


        /**
         * Checks if the logicArray has room to move upwards
         *
         * @return true if more room to move upwards, false if not
         */
        public static boolean canMergeUp() {
            // Loops through the logic array to check if there are still like-values to merge in each column
            for (int j = 0; j < getLogicArray()[0].length; j++) {
                for (int i = 1; i < getLogicArray().length; i++) {
                    if (getLogicArray()[i][j] != 0 && (getLogicArray()[i - 1][j] == 0 || getLogicArray()[i][j] == getLogicArray()[i - 1][j])) {
                        return true;
                    }
                }
            }
            return false;
        }

        /**
         * Merges like values within each column of the logicArray upwards
         */
        public static void mergeUp() {
            if (canMergeUp()) {
                // While there are still adjacent cells with like-values, keep looping
                while (canMergeUp()) {
                    // Loops through each column in the logicArray
                    for (int col = 0; col < getLogicArray()[0].length; col++) {
                        int nextEmptyIndex = 0;
                        // Loops through current column to merge, similar adjacent cells upwards
                        for (int row = 0; row < getLogicArray().length - 1; row++) {
                            if (getLogicArray()[row][col] == getLogicArray()[row + 1][col]) {
                                getLogicArray()[row][col] += getLogicArray()[row + 1][col];
                                getLogicArray()[row + 1][col] = 0;
                                row++;
                            }
                        }
                        // Loops through current column and shifts all non-zero elements upwards
                        for (int row = 0; row < getLogicArray().length; row++) {
                            if (getLogicArray()[row][col] != 0) {
                                getLogicArray()[nextEmptyIndex][col] = getLogicArray()[row][col];
                                if (nextEmptyIndex != row) {
                                    getLogicArray()[row][col] = 0;
                                }
                                nextEmptyIndex++;
                            }
                        }
                        // Loops between the nextEmptyIndex and the bottom of the current column, overriding the previous values with 0
                        for (int row = nextEmptyIndex; row < getLogicArray().length; row++) {
                            getLogicArray()[row][col] = 0;
                        }
                    }
                }
                displayGameBoard();
                canSelectRandomInt = true;
            }
        }

        /**
         * Checks if the logicArray has room to move downwards
         *
         * @return true if more room to move downwards, false if not
         */
        public static boolean canMergeDown() {
            // Loops through the logic array to check if there are still like-values to merge in each column
            for (int j = 0; j < getLogicArray()[0].length; j++) {
                for (int i = 0; i < getLogicArray().length - 1; i++) {
                    if (getLogicArray()[i][j] != 0 && (getLogicArray()[i + 1][j] == 0 || getLogicArray()[i][j] == getLogicArray()[i + 1][j])) {
                        return true;
                    }
                }
            }
            return false;
        }

        /**
         * Merges like values within each column of the logicArray downwards
         */
        public static void mergeDown() {
            if (canMergeDown()) {
                // While there are still adjacent cells with like-values, keep looping
                while (canMergeDown()) {
                    // Loops through each column in the logicArray
                    for (int col = 0; col < getLogicArray()[0].length; col++) {
                        int nextEmptyIndex = getLogicArray().length - 1;
                        // Loops through current column to merge, similar adjacent cells downwards
                        for (int row = getLogicArray().length - 1; row > 0; row--) {
                            if (getLogicArray()[row][col] == getLogicArray()[row - 1][col] && getLogicArray()[row][col] != 0) {
                                getLogicArray()[row][col] += getLogicArray()[row - 1][col];
                                getLogicArray()[row - 1][col] = 0;
                                row--;
                            }
                        }
                        // Loops through current column and shifts all non-zero elements downwards
                        for (int row = getLogicArray().length - 1; row >= 0; row--) {
                            if (getLogicArray()[row][col] != 0) {
                                getLogicArray()[nextEmptyIndex][col] = getLogicArray()[row][col];
                                if (nextEmptyIndex != row) {
                                    getLogicArray()[row][col] = 0;
                                }
                                nextEmptyIndex--;
                            }
                        }
                        // Loops between the nextEmptyIndex and the top of the current column, overriding the previous values with 0
                        for (int row = nextEmptyIndex; row >= 0; row--) {
                            getLogicArray()[row][col] = 0;
                        }
                    }
                }
                displayGameBoard();
                canSelectRandomInt = true;
            }
        }

        /**
         * Merges the values of the logicArray up and to the left
         */
        public static void mergeUpLeft() {
            mergeUp();
            mergeLeft();
        }

        /**
         * Merges the values of the logicArray up and to the right
         */
        public static void mergeUpRight() {
            mergeUp();
            mergeRight();
        }

        /**
         * Merges the values of the logicArray down and to the left
         */
        public static void mergeDownLeft() {
            mergeDown();
            mergeLeft();
        }

        /**
         * Merges the values of the logicArray down and to the right
         */
        public static void mergeDownRight() {
            mergeDown();
            mergeRight();
        }

        /**
         * Checks if there are more moves that can be made within the logicArray
         *
         * @return true if more moves can be made, false if not
         */
        public static boolean isGameOver() {
            // Check for empty indices first
            for (int i = 0; i < getLogicArray().length; i++) {
                for (int j = 0; j < getLogicArray()[i].length; j++) {
                    if (getLogicArray()[i][j] == 0) {
                        // At least one index is empty, game is not over.
                        return false;
                    }
                }
            }

            // If no empty cells, then check for possible merges horizontally and vertically
            for (int i = 0; i < getLogicArray().length; i++) {
                for (int j = 0; j < getLogicArray()[i].length; j++) {
                    if ((j < getLogicArray()[i].length - 1 && getLogicArray()[i][j] == getLogicArray()[i][j + 1]) ||
                            (i < getLogicArray().length - 1 && getLogicArray()[i][j] == getLogicArray()[i + 1][j])) {
                        // At least 1 merge is possible horizontally or vertically, game is not over.
                        return false;
                    }
                }
            }
            // No cells are empty and no merges can be made, game is over.
            return true;
        }
    }
}