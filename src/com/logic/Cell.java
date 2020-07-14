package com.logic;

public class Cell {
    /**
     This class is a simple class that will be representing several boolean values
     in the maze and what a single cell in the maze contains.
     In a way, it helps maze understand what the location class does in a very
     simple way.
     */
        private boolean isHidden;
        private boolean isWall;
        private boolean isVisited;
        private Location cellLocation;

        public Cell(boolean isHidden, boolean isWall, int x, int y) {
            this.isHidden = isHidden;
            this.isWall = isWall;
            isVisited = false;
            cellLocation = new Location(x, y);
        }

        public boolean isHidden() {
            return isHidden;
        }

        public boolean isWall() {
            return isWall;
        }

        public boolean isVisited() {
            return isVisited;
        }

        public int getX() {
            return cellLocation.getX();
        }

        public int getY() {
            return cellLocation.getY();
        }

        public Location getCellLocation() { return cellLocation; }

        public void setHidden(boolean hidden) {
            isHidden = hidden;
        }

        public void setWall(boolean wall) {
            isWall = wall;
        }

        public void setVisited(boolean visited) {
        isVisited = visited;
    }
}
