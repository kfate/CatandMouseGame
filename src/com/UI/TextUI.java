package com.UI;

import com.logic.Direction;
import com.logic.Game;
import com.logic.GameStatus;

import java.util.ArrayList;
import java.util.Scanner;

/**
 This class will be in charge of handling user input, and printing the necessary instructions.
 All of the printing will be taking place from this class.
 */
public class TextUI {
    static private Scanner myScanner  = new Scanner(System.in);
    public static void main(String[] args) throws CloneNotSupportedException {
        Game game = new Game();
        TextUI myUI = new TextUI(game);
        myUI.start(game);
    }

    public void start(Game game){
        printIntro();
        printHelp();
        System.out.println(game.printFullMaze());
        while(game.getGameStatus() == GameStatus.ONGOING){
            Direction userWantDirection = getValidInput(game);
            game.makeNewCheeseOrCheckWonCondition();
            game.moveMouseToDirection(userWantDirection);
            game.makeCatsMove();
            System.out.println(game.printFullMaze());
        }
        game.getMaze().revealMaze();
        if(game.getGameStatus() == GameStatus.WON){
            System.out.println("CONGRATSSSS");
            System.out.println("YOUVE WON!!!");
        }
        else if (game.getGameStatus() == GameStatus.LOST){
            System.out.println("YOU HAVE DIED :(((");
            System.out.println("BETTER LUCK NEXT TIME!!!");

        }

    }



    private Direction getValidInput(Game game) {
        int index = -1;
        String input;
        Direction dir = null;
        ArrayList<Direction> viableMoves = game.getMaze().getViableMoves(game.getMouse().getLocation());
        ArrayList<String> options = new ArrayList<>();
        options.add("w");
        options.add("a");
        options.add("s");
        options.add("d");
        options.add("m");
        options.add("c");
        options.add("?");
        ArrayList<Direction> directions = new ArrayList<>();
        directions.add(Direction.UP);
        directions.add(Direction.LEFT);
        directions.add(Direction.DOWN);
        directions.add(Direction.RIGHT);

        while (index < 0 || index > 3) {
            System.out.println("Enter your move [WASD?]: ");
            input = myScanner.nextLine();
            index = options.indexOf(input);


            switch(index){
                case -1:
                    System.out.println("Invalid move. Please enter just A (left), S (down), D (right), or W (up).");
                    break;
                case 4:
                    game.getMaze().revealMaze();
                    System.out.println(game.printFullMaze());
                    break;
                case 5:
                    game.setNumOfCheeseNeededTo1();
                    break;
                case 6:
                    printHelp();
                    break;
                default:
                    dir = directions.get(index);
                    break;
            }

            index = viableMoves.indexOf(dir);
            if (index == -1) {
                System.out.println("Invalid move: you cannot run through walls!.");
            }


        }
        return dir;
    }

    private void printHelp(){
        String help = "\n" +
                "DIRECTIONS:\n" +
                "\tFind 5 cheese before a cat eats you!\n" +
                "LEGEND:\n" +
                "\t#: Wall\n" +
                "\t@: You (a mouse)\n" +
                "\t!: Cat\n" +
                "\t$: Cheese\n" +
                "\t.: Unexplored space\n" +
                "MOVES:\n" +
                "\tUse W (up), A (left), S (down) and D (right) to move.\n" +
                "\t(You must press enter after each move).\n";
        System.out.println(help);
    }

    private void printIntro(){
        String intro = "----------------------------------------\n" +
                "Welcome to Cat and Mouse Maze Adventure!\n" +
                "by FATEMEH KIANNEAD, CHANG YUN \n" +
                "----------------------------------------";
        System.out.println(intro);
    }
    public TextUI(Game game){

    }

}
