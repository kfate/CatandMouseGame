package com.logic;

public class Cheese {
    /**
     This class is in charge of making a cheese and getting its location when needed./
     */
    private final Location location;
    public Cheese(Location location){
        this.location = location;
    }
    public Location getLocation(){
        return location;
    }
}
