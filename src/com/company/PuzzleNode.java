package com.company;

/**
 * Created by scottjones on 11/7/17.
 */
public class PuzzleNode extends AbstractPuzzle {

    private String config;
    private PuzzleNode [] next = new PuzzleNode [4];
    private PuzzleNode parent;
    private int depth;
    private int currentKeyValue = 0;

    //first node is root, parentReference is itself
    public PuzzleNode(String configValue){
        config =configValue;
        parent = this;
        depth = 0;
    }

    public PuzzleNode(String configValue, PuzzleNode parentReference){
        config =configValue;
        parent = parentReference;
        depth = parentReference.depth + 1;
    }

    public PuzzleNode expand(Direction someDirection){

        int i;
        if(someDirection == Direction.Left)
            i = 0;
        else if(someDirection == Direction.Down)
            i = 1;
        else if(someDirection == Direction.Right)
            i = 2;
        else
            i = 3;
        String newConfigValue = this.moveDirection(someDirection);
        next[i] = new PuzzleNode(newConfigValue, this);

        return next[i];
    }

    // LEFT = 0, DOWN = 1, RIGHT = 2, UP = 3
    // returns current nodes config string with blank moved in direction i
    // caller responsible for checking move is legal
    private String moveDirection(Direction someDirection) {
        String newConfig;
        char [] charArray = this.config.toCharArray();
        int currentBlank = 0;
        while(this.config.charAt(currentBlank) != 'b') {
            ++currentBlank;
        }
        switch (someDirection) {
            case Left:
                charArray[currentBlank] = this.config.charAt(currentBlank - 1);
                charArray[currentBlank - 1] = 'b';
                break;
            case Down:
                charArray[currentBlank] = this.config.charAt(currentBlank + 3);
                charArray[currentBlank + 3] = 'b';
                break;
            case Right:
                charArray[currentBlank] = this.config.charAt(currentBlank + 1);
                charArray[currentBlank + 1] = 'b';
                break;
            case Up:
                charArray[currentBlank] = this.config.charAt(currentBlank - 3);
                charArray[currentBlank - 3] = 'b';
                break;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(charArray);
        newConfig = stringBuilder.toString();
        return newConfig;
    }
    // Looks in direction from blank space in current node's configuration to
    // determine if the direction is a legal move
    public boolean checkDirection(Direction direction) {
        int currentBlank = 0;
        while(this.config.charAt(currentBlank) != 'b') {
            ++currentBlank;
        }

        switch(direction){
            case Left:
                if (currentBlank == 0 || currentBlank == 3 || currentBlank == 6)
                    return false;
                break;
            case Down:
                if(currentBlank > 5)
                    return false;
                break;
            case Right:
                if (currentBlank == 2 || currentBlank == 5 || currentBlank == 8)
                    return false;
                    break;
            case Up:
                if (currentBlank < 3)
                    return false;
                break;
        }
        return true;
    }

    public boolean checkGoalCondition() {
        return config.equalsIgnoreCase("12345678b");
    }

    public String getConfig() {
        return config;
    }

    public int getDepth() {
        return depth;
    }

    public PuzzleNode getParent() {
        return parent;
    }
}
