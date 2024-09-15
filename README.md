# javafx-diagonal-2048
## AKA Slide Game

### Overview

Slide Game is a 2048-like puzzle game where the player must move tiles on a grid to combine them and achieve higher scores. The game board's size can be configured, and the player moves the tiles either by clicking buttons or by using the keyboard. 

The twist is that the tiles can also move diagonally, rather than just up/down and left/right!

This game runs fully in a JavaFX UI, and does not require console input. 

### Files

1. Launcher.java
    - Launcher serves as the entry point for the game but does not contain game logic or UI components. Its primary function is to initiate the application and pass control to the user interface.
  
2. SlideGameUI.java
    - SlideGameUI contains the logic for the graphical user interface (GUI) of the game. 
    - e.g.
      -  Game mechanics such as moving and merging tiles.
      - Methods for handling game inputs like button presses or keyboard events.
      - Functionality to update the game board and check if the game is over.
     
3. FinalSlideGameSHADED.jar
    - An experimental .jar file that contains the JavaFX dependencies and POTENTIALLY can allow you to run the game without setting up a special JavaFX environment.
    - "POTENTIALLY" because it only seems to run on macOS and with Java 22. 


### How to Play
1. Launch the game by running the Launcher class.
2. Select the number of rows and columns for your game board through the dialog boxes.
3. Use the arrow keys or on-screen buttons to slide the tiles in the selected direction.
4. The game will automatically merge tiles when two identical ones collide.
5. The goal is to keep merging tiles to achieve the highest possible score before the board fills up and no more moves can be made.

### Controls
<img width="357" alt="Screenshot 2024-09-13 at 11 29 26 PM" src="https://github.com/user-attachments/assets/ab41eb3f-491c-498e-8d46-7b568e13ca4a">


### Installation Tips
- The game is built using JavaFX. Ensure that you have JavaFX properly set up in your coding environment.
    - https://openjfx.io/openjfx-docs/#install-java
- Compile and run the Launcher.java file to start the game.
- Also ensure that SlideGameUI.java is accessible to Launcher.java.



