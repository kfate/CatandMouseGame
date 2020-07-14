package com.logic;

public class Mouse {
    /**
     This class will take care of the player and making sure the player can move
     */
    private Location location;

    public Mouse(int x, int y){
        location = new Location(x, y);
    }
    public Location getLocation(){
        return location;
    }

    public boolean move(Maze maze, Game game, Direction direction){
        if( game.movementIsValid(location, direction)){
            location.changeLocation(direction);
            maze.revealArea(location);
            return true;
        }
        return false;
     //stuff
    }

}
