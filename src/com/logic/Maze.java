package com.logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Maze {
    /**
     This class is in charge of creating the random maze and letting the game class and other classes
     know where they are allowed to go and where is off limits
     */


    Cell[][] maze;
    int height;
    int width;

    private static Random random = new Random();
    public Maze(int height, int width) {
        boolean cornersClear;
        this.height = height;
        this.width = width;
        maze = new Cell[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                maze[i][j] = new Cell(true, true, j, i);
            }
        }
        setBorderWall();


        do {
            resetMaze();
            mazeGenerator();
            cornersClear = cornersAreClear();
        } while (!cornersClear);
        clearSomeInnerWalls();

    }

    private boolean cornersAreClear() {
        return !(maze[1][width - 2].isWall() || maze[height - 2][1].isWall() || maze[height - 2][width - 2].isWall());
    }

    private void mazeGenerator() {
        recursiveBackTracker(maze[1][1]);
    }

    private void resetMaze() {
        for (int i = 1; i < height - 1; i++) {
            for (int j = 1; j < width - 1; j++) {
                maze[i][j].setVisited(false);
                maze[i][j].setWall(true);
            }
        }
    }

    private void recursiveBackTracker(Cell cell) {
        if (cell.isVisited()) {
            return;
        }

        int count = 0;
        List<Cell> neighbors = getNeighbors(cell.getX(), cell.getY());

        for (Cell neighborCell : neighbors) {
            if (!neighborCell.isWall()) {
                count++;
            }
        }

        if (count > 1) {
            return;
        }

        cell.setVisited(true);
        cell.setWall(false);

        Collections.shuffle(neighbors);

        for (Cell neighbor : neighbors) {
            recursiveBackTracker(neighbor);
        }

    }

    private Cell getLeftCell(int x, int y) {
        int x_left = x - 1;
        if (isWithinBounds(x - 1, width)) {
            return maze[y][x_left];
        }
        return null;
    }

    private Cell getRightCell(int x, int y) {
        int x_right = x + 1;
        if (isWithinBounds(x_right, width)) {
            return maze[y][x_right];
        }
        return null;
    }

    private Cell getAboveCell(int x, int y) {
        int y_up = y - 1;
        if (isWithinBounds(y_up, height)) {
            return maze[y_up][x];
        }
        return null;
    }

    private Cell getBelowCell(int x, int y) {
        int y_down = y + 1;
        if (isWithinBounds(y_down, height)) {
            return maze[y_down][x];
        }
        return null;
    }

    private ArrayList<Cell> getNeighbors(int x, int y) {
        ArrayList<Cell> neighbors = new ArrayList<>();
        ArrayList<Cell> temp = new ArrayList<>();

        temp.add(getAboveCell(x, y));
        temp.add(getBelowCell(x, y));
        temp.add(getLeftCell(x, y));
        temp.add(getRightCell(x, y));

        for (Cell cell : temp) {
            if (cell != null) {
                neighbors.add(cell);
            }
        }

        return neighbors;
    }

    private boolean isWithinBounds(int number, int max_bound) {
        return number > 0 && number < max_bound;
    }

    private void setBorderWall() {
        setRowAsWalls(0);
        setRowAsWalls(this.height - 1);
        setColumnAsWalls(0);
        setColumnAsWalls(this.width - 1);
    }

    private void setRowAsWalls(int row) {
        for (int i = 0; i < width; i++) {
            maze[row][i].setWall(true);
            maze[row][i].setHidden(false);
            maze[row][i].setVisited(true);
        }
    }

    private void setColumnAsWalls(int column) {
        for (int i = 0; i < height; i++) {
            maze[i][column].setWall(true);
            maze[i][column].setHidden(false);
            maze[i][column].setVisited(true);
        }
    }

    private Location getLocationAt(int x, int y) {
        if (isWithinBounds(x, width) && isWithinBounds(y, height)) {
            return maze[x][y].getCellLocation();
        }
        return null;
    }

    public ArrayList<Direction> getViableMoves(Location location) {
        Cell up_direction = getAboveCell(location.getX(), location.getY());
        Cell down_direction = getBelowCell(location.getX(), location.getY());
        Cell left_direction = getLeftCell(location.getX(), location.getY());
        Cell right_direction = getRightCell(location.getX(), location.getY());

        ArrayList<Direction> viableDirections = new ArrayList<>();

        if (up_direction != null && !up_direction.isWall()) {
            viableDirections.add(Direction.UP);
        }
        if (down_direction != null && !down_direction.isWall()) {
            viableDirections.add(Direction.DOWN);
        }
        if (left_direction != null && !left_direction.isWall()) {
            viableDirections.add(Direction.LEFT);
        }
        if (right_direction != null && !right_direction.isWall()) {
            viableDirections.add(Direction.RIGHT);
        }

        return viableDirections;

    }

    public void revealArea(Location location) {
        int x = location.getX();
        int y = location.getY();

        for (int i = y - 1; i <= y + 1; i++) {
            if (isWithinBounds(y, height)) {
                for (int j = x - 1; j <= x + 1; j++) {
                    if (isWithinBounds(x, width)) {
                        maze[i][j].setHidden(false);
                    }
                }
            }
        }
    }

    private boolean canBeCleared(Cell cell) {
        int x = cell.getCellLocation().getX();
        int y = cell.getCellLocation().getY();
        Cell top = maze[y - 1][x];
        Cell bot = maze[y + 1][x];
        Cell right = maze[y][x + 1];
        Cell left = maze[y][x - 1];
        Cell upper_right = maze[y - 1][x + 1];
        Cell upper_left = maze[y - 1][x - 1];
        Cell lower_right = maze[y + 1][x + 1];
        Cell lower_left = maze[y + 1][x - 1];

        return !((!top.isWall() && !right.isWall() && !upper_right.isWall()) ||
                (!top.isWall() && !left.isWall() && !upper_left.isWall()) ||
                (!bot.isWall() && !left.isWall() && !lower_left.isWall()) ||
                (!bot.isWall() && !right.isWall() && !lower_right.isWall()));
    }

    private boolean isAccessible(Cell cell) {
        int x = cell.getCellLocation().getX();
        int y = cell.getCellLocation().getY();
        int count = 0;

        ArrayList<Cell> neighbors = getNeighbors(x, y);

        for (Cell neighborCell : neighbors) {
            if (neighborCell.isWall()) {
                count++;
            }
        }

        return !(count == 4);
    }

    private void clearSomeInnerWalls() {
        for (int i = 1; i < height - 1; i++) {
            for (int j = 1; j < width - 1; j++) {
                if (canBeCleared(maze[i][j]) && isAccessible(maze[i][j])) {
                    maze[i][j].setWall(false);
                }
            }
        }
    }

    public void revealMaze() {
        for (int i = 0; i < height - 1; i++) {
            for (int j = 0; j < width - 1; j++) {
                maze[i][j].setHidden(false);
            }
        }
    }

    public String[][] getStringRepOfMaze() {
        String[][] stringMaze = new String[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Cell tempCell = maze[i][j];
                if (tempCell.isHidden()) {
                    stringMaze[i][j] = ".";
                } else if (tempCell.isWall()) {
                    stringMaze[i][j] = "#";
                } else {
                    stringMaze[i][j] = " ";
                }
            }
        }
        return stringMaze;
    }

    public Location getRandomLocationCheese(Mouse mouse) {
        int randomWidth;
        int randomHeight;
        Cell possibleCell;
        Location cheeseLocation;
        do {
            //if the cell chosen is wall or the mouse is in the same cell as cheese
            //redo the random location
            randomWidth = random.nextInt(width - 2) + 1;
            randomHeight = random.nextInt(height - 2) + 1;
            possibleCell = maze[randomHeight][randomWidth];
            cheeseLocation = new Location(randomWidth, randomHeight);
        }
        while (possibleCell.isWall() || mouse.getLocation().isSameLocation(cheeseLocation));
        return cheeseLocation;
    }


    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}