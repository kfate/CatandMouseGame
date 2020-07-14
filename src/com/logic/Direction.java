package com.logic;

public enum Direction {
    /**
     This enum is in charge of Directions that characters can take
     */
    LEFT, RIGHT, UP, DOWN;
    public Direction getOppositeDirection(){
        Direction returnDirection = null;
        switch (this){
            case UP:
                returnDirection = Direction.DOWN;
                break;
            case DOWN:
                returnDirection = Direction.UP;
                break;
            case RIGHT:
                returnDirection = Direction.LEFT;
                break;
            case LEFT:
                returnDirection = Direction.RIGHT;
                break;
            default:
                break;
        }

        return returnDirection;
    }
}
