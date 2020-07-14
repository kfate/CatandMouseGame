package com.logic;

public class Location {
    /**
     This class is used by others in order to change and manipulate their own location
     */
    private int x;
    private int y;
    Location(int x, int y){
        this.x = x;
        this.y = y;
    }
//    public static Location setLocation(int x, int y){
//        return new Location(x,y);
//    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    //immutable
    public void changeLocation(Direction direction){
        switch (direction){
            case UP:
                this.y --;
                break;
            case DOWN:
                this.y ++;
                break;
            case LEFT:
                this.x --;
                break;
            case RIGHT:
                this.x ++;
                break;
            default:
                break;
        }
    }
    public boolean isSameLocation(Location location){
        if((location.getX() == this.x) && (location.getY() == this.y)){
            return true;
        }
        return false;
    }

}
