package com.logic;

import java.util.ArrayList;

public class Game {
    /**
     This class is the main host for the overall logic of the game, it will make sure
     to connect textUI and maze class together well with all the other character based classes
     so they can interact well without overstepping boundaries.
     It will also keep a refrence to maze, and other characters at all times.
     */
    private int numOfCheeseWon;
    private int numOfCheeseNeeded = 5;
    private Maze maze;
    private ArrayList<Cat> cats = new ArrayList<>();
    private Mouse mouse;
    private Cheese cheese;


    private GameStatus gameStatus = GameStatus.ONGOING;
    private boolean cheeseWasJustCollected = false;
    public Game() throws CloneNotSupportedException {
        //make the maze
        maze = new Maze(15,20);
        //place cheese and cats anf mouse here as well
        mouse = new Mouse(1, 1);
        placeCatsInCorners();
        makeCheeseSpawn();
        System.out.println(cheese == null);
    }
    public GameStatus getGameStatus(){
        return gameStatus;
    }
    public String printFullMaze(){
        String[][] semiMaze = maze.getStringRepOfMaze();
        semiMaze[mouse.getLocation().getY()][mouse.getLocation().getX()] = "@";
        for (Cat cat: cats) {
            semiMaze[cat.getLocation().getY()][cat.getLocation().getX()] = "!";
        }
        //System.out.println(cheese.getLocation().getX() + " " +cheese.getLocation().getY());
        semiMaze[cheese.getLocation().getY()][cheese.getLocation().getX()] = "$";
        String fullMaze = "MAZE : \n";
        for (int y = 0 ; y < semiMaze.length; y++){
            for (int x = 0; x < semiMaze[0].length; x++){
                fullMaze+= semiMaze[y][x];
            }
            fullMaze += "\n";
        }
        fullMaze += " cheeses collected : " + numOfCheeseWon + "out of " + numOfCheeseNeeded + " \n";
        return fullMaze;
    }

    public boolean movementIsValid(Location location, Direction direction){
        ArrayList<Direction> viableMoves = maze.getViableMoves(location);
        if( viableMoves.contains(direction)){
            return true;
        }
        return false;

    }
    public void moveMouseToDirection(Direction direction){
            mouse.move(maze, this, direction);
            for (Cat cat: cats) {
                if(cat.getLocation().isSameLocation(mouse.getLocation())){
                    gameStatus = GameStatus.LOST;
                    //terminateGame();
                    return;
                }
            }
            if(mouse.getLocation().isSameLocation(cheese.getLocation())){
                numOfCheeseWon++;
                cheeseWasJustCollected = true;
            }

        }
    public void makeCatsMove(){
        for (Cat cat: cats) {
            cat.makeRandomMove(maze);
            if(cat.getLocation().isSameLocation(mouse.getLocation())){
                //game lost
                terminateGame();
            }
        }
    }
    public void placeCatsInCorners(){
        Cat newCat = new Cat(1, maze.getHeight()-2);
        cats.add(newCat);
        newCat = new Cat(maze.getWidth()-2, maze.getHeight()-2 );
        cats.add(newCat);
        newCat = new Cat(maze.getWidth()-2, 1);
        cats.add(newCat);

    }

    private void terminateGame(){
        gameStatus = GameStatus.LOST;
    }
    public void makeCheeseSpawn(){
        //will update the location of cheese
        this.cheese = new Cheese(maze.getRandomLocationCheese(mouse));
        return;
    }
    public void makeNewCheeseOrCheckWonCondition(){
        //perhaps has to become a boolean
        if(numOfCheeseWon < numOfCheeseNeeded && cheeseWasJustCollected){
            //if u need new cheese
            cheeseWasJustCollected = false;
            makeCheeseSpawn();
        }
        else if( numOfCheeseWon == numOfCheeseNeeded) {
            gameStatus = GameStatus.WON;
        }
        return;


    }
    public void setNumOfCheeseNeededTo1(){
        numOfCheeseNeeded = 1;
    }
    public Maze getMaze(){
        return maze;
    }
    public Mouse getMouse(){
        return mouse;
    }

}
