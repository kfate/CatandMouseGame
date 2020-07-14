package com.logic;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


public class Cat {
    /**
     This class is in charge of making a cat move randomly
     */
    private Location location;
    private Direction previousMove;
    private static Random random = new Random();

    public Cat(int x, int y){
        location = new Location(x, y);
        previousMove = Direction.DOWN;
    }
    public Location getLocation(){
        return location;
    }

    public void makeRandomMove(Maze maze){
        ArrayList<Direction> whereCatCanGo = maze.getViableMoves(location);
        Direction switchDirection = null;
        if(whereCatCanGo.size()==1){
            //have to go back to where we came from
            switchDirection = whereCatCanGo.get(0);
            location.changeLocation(switchDirection);
        }
        else if (whereCatCanGo.size() > 1){
            //choose a random move
            Collections.shuffle(whereCatCanGo);
            if (whereCatCanGo.get(0) != previousMove) {
                switchDirection = whereCatCanGo.get(0);
            }else {
                switchDirection = whereCatCanGo.get(1);
            }
            //whereCatCanGo.remove(previousMove);
            //switchDirection = (Direction) whereCatCanGo.get(random.nextInt(whereCatCanGo.size()-1));
            location.changeLocation(switchDirection);
        }
        previousMove = switchDirection.getOppositeDirection();
        //previousMove = switchDirection.getOppositeDirection();
//        switch (switchDirection){
//            case UP:
//                previousMove = Direction.DOWN;
//                break;
//            case DOWN:
//                previousMove = Direction.UP;
//                break;
//            case RIGHT:
//                previousMove = Direction.LEFT;
//                break;
//            case LEFT:
//                previousMove = Direction.RIGHT;
//                break;
//            default:
//                break;
//        }
        return;
    }



}
