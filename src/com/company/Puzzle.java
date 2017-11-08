package com.company;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by scottjones on 11/4/17. For AI Class
 */
public class Puzzle {

    private static final int MAX_STEPS = 1000;

    public int getMaxSteps() {
        return MAX_STEPS;
    }

    enum Direction{
        Left, Up, Right, Down
    }

    private String configuration;
    private String goalState = "12345678b";
    private List<String> history;
    int steps = 0;

    public Puzzle(String inputConfiguration){
        configuration = inputConfiguration;
        history = new ArrayList<String>();
        history.add(configuration);
    }

    public int getSteps(){
        return steps;
    }

    private boolean CheckGoalState(){
        return configuration.equalsIgnoreCase(goalState);
    }

    public boolean bestFirstSearch() {
        //i.e. Greedy Best First Search f(n) = h(n)
        int limit = MAX_STEPS;

        while (!CheckGoalState() && steps < limit) {
            BestFirstSearchMove();
            history.add(configuration);
            ++steps;
        }
        if (steps >= limit)
            return false;
        return true;
    }

    public boolean bestFirstSearchManhattanDistance() {
        //i.e. Greedy Best First Search f(n) = h(n)
        int limit = MAX_STEPS;

        while (!CheckGoalState() && steps < limit) {
            BestFirstSearchMoveManhattanDistance();
            history.add(configuration);
            ++steps;
        }
        if (steps >= limit)
            return false;
        return true;
    }

    public boolean bestFirstSearchColumnsRows(){
        int limit = MAX_STEPS;
        while (!CheckGoalState() && steps < limit) {
            BestFirstSearchColumnsRows();
            history.add(configuration);
            ++steps;
        }
        if (steps >= limit)
            return false;
        return true;
    }


    private void BestFirstSearchMove() {
        int up = 100;
        int down = 100;
        int left = 100;
        int right = 100;

        int i = 0;
        while (configuration.charAt(i) != 'b')
            ++i;

        if (i > 2) {
            down = NumMisplacedTiles(i - 3);
        }
        if (i < 6) {
            //look UP
            up = NumMisplacedTiles(i + 3);
        }
        if (i != 0 && i != 3 && i != 6) {
            //look LEFT
            left = NumMisplacedTiles(i - 1);
        }
        if (i != 2 && i != 5 && i != 8) {
            //look RIGHT
            right = NumMisplacedTiles(i + 1);
        }
        //Lowest score changes config, go to unexplored node first
        if (left <= right && left <= up && left <= down && checkHistory(Direction.Left, i)) {
            ChangeConfiguration(Direction.Left, i);
        }
        else if (down <= up && down <= left && down <= right && checkHistory(Direction.Down, i)) {
            ChangeConfiguration(Direction.Down, i);
        }
        else if (right <= left && right <= up && right <= down && checkHistory(Direction.Right, i)) {
            ChangeConfiguration(Direction.Right, i);
        }
        else if (up <= down && up <= left && up <= down && checkHistory(Direction.Up, i)) {
            ChangeConfiguration(Direction.Up, i);
        }
        else if (left <= right && left <= up && left <= down) {
            ChangeConfiguration(Direction.Left, i);
        }
        else if (down <= up && down <= left && down <= right) {
            ChangeConfiguration(Direction.Down, i);
        }
        else if (right <= left && right <= up && right <= down) {
            ChangeConfiguration(Direction.Right, i);
        }
        else{
            ChangeConfiguration(Direction.Up, i);
        }

    }

    private void BestFirstSearchMoveManhattanDistance() {
        int up = 100;
        int down = 100;
        int left = 100;
        int right = 100;

        int i = 0;
        while (configuration.charAt(i) != 'b')
            ++i;

        if (i > 2) {
            down = ManhattanDistance(i - 3);
        }
        if (i < 6) {
            //look UP
            up = ManhattanDistance(i + 3);
        }
        if (i != 0 && i != 3 && i != 6) {
            //look LEFT
            left = ManhattanDistance(i - 1);
        }
        if (i != 2 && i != 5 && i != 8) {
            //look RIGHT
            right = ManhattanDistance(i + 1);
        }
        //Lowest score changes config
        if (left <= right && left <= up && left <= down) {
            ChangeConfiguration(Direction.Left, i);
        } else if (down <= up && down <= left && down <= right) {
            ChangeConfiguration(Direction.Down, i);
        } else if (right <= left && right <= up && right <= down) {
            ChangeConfiguration(Direction.Right, i);
        } else if (up <= down && up <= left && up <= down) {
            ChangeConfiguration(Direction.Up, i);
        }
    }

    private void BestFirstSearchColumnsRows(){
        int up = 100;
        int down = 100;
        int left = 100;
        int right = 100;

        int i = 0;
        while (configuration.charAt(i) != 'b')
            ++i;

        if (i > 2) {
            down = ColumnsRowsScore(i - 3);
        }
        if (i < 6) {
            //look UP
            up = ColumnsRowsScore(i + 3);
        }
        if (i != 0 && i != 3 && i != 6) {
            //look LEFT
            left = ColumnsRowsScore(i - 1);
        }
        if (i != 2 && i != 5 && i != 8) {
            //look RIGHT
            right = ColumnsRowsScore(i + 1);
        }
        //Lowest score changes config
        if (left <= right && left <= up && left <= down) {
            ChangeConfiguration(Direction.Left, i);
        } else if (down <= up && down <= left && down <= right) {
            ChangeConfiguration(Direction.Down, i);
        } else if (right <= left && right <= up && right <= down) {
            ChangeConfiguration(Direction.Right, i);
        } else if (up <= down && up <= left && up <= down) {
            ChangeConfiguration(Direction.Up, i);
        }
    }

    //caller must check to make sure move isn't illegal
    // index is position of blank, Direction is direction to move blank
    private void ChangeConfiguration(Direction someDirection, int index) {

        char [] configArray = new char[9];
        for(int i=0; i<9; ++i){
            configArray[i] = configuration.charAt(i);
        }
        if(someDirection == Direction.Left){
            configArray[index] = configArray[index - 1];
            configArray[index - 1] = 'b';
        }
        if(someDirection == Direction.Right){
            configArray[index] = configArray[index + 1];
            configArray[index + 1] = 'b';
        }
        if(someDirection == Direction.Up){
            configArray[index] = configArray[index + 3];
            configArray[index + 3] = 'b';
        }
        if(someDirection == Direction.Down){
            configArray[index] = configArray[index - 3];
            configArray[index - 3] = 'b';
        }
        configuration = "";
        for (int i = 0; i < 9 ; ++i){
            configuration += configArray[i];
        }
    }

    //returns true if the move is not in the history, false if the move shouldn;t be made
    private boolean checkHistory(Direction someDirection, int indexOfB){
        char [] configArray = new char[9];
        for(int i=0; i<9; ++i)
            configArray[i] = configuration.charAt(i);
        
        switch (someDirection) {
            case Left:
                configArray[indexOfB] = configArray[indexOfB - 1];
                configArray[indexOfB - 1] = 'b';
                break;
            case Right:
                configArray[indexOfB] = configArray[indexOfB + 1];
                configArray[indexOfB + 1] = 'b';
                break;
            case Up:
                configArray[indexOfB] = configArray[indexOfB + 3];
                configArray[indexOfB + 3] = 'b';
                break;
            case Down:
                configArray[indexOfB] = configArray[indexOfB - 3];
                configArray[indexOfB - 3] = 'b';
        }
        for (String priorString: history){
            if(priorString.equalsIgnoreCase(String.valueOf(configArray)))
                return false;
        }
        return true;
    }


    private int NumMisplacedTiles(int index) {
        int count = 0;
        char [] configArray = new char[9];
        for (int i = 0; i < 9; ++i)
            configArray[i] = configuration.charAt(i);
        int i = 0;
        while(configArray[i] != 'b') {
            ++i;
        }
        configArray[i] = configArray[index];
        configArray[index] = 'b';

        for (i = 0; i < 9; ++i){
            if(configArray[i] != goalState.charAt(i))
                ++count;
        }
        return count;
    }

    private int ManhattanDistance(int index){
        int count = 0;
        int distance = 0;
        char [] configArray = configuration.toCharArray();
        char [] goalStateArray = goalState.toCharArray();
        String tempConfigString = "";
        
        int i = 0;
        while(configArray[i] != 'b') {
            ++i;
        }
        configArray[i] = configArray[index];
        configArray[index] = 'b';
        for (char item: configArray) {
            tempConfigString += item;
        }

        ManhattanDistanceCalculator calculator = new ManhattanDistanceCalculator(tempConfigString, goalState);
        count = calculator.getDistance();

        return count;
    }

    private int ColumnsRowsScore(int index) {

        //TODO: NEED TO CHANGE SO IT CHAnGES LOCATION OF B THEN CALCULATES SCORE
        int score = 0;
        char[] configArray = configuration.toCharArray();
        int i = 0;
        while (configArray[i] != 'b') {
            ++i;
        }
        //Swap b and index
        configArray[i] = configArray[index];
        configArray[index] = 'b';

            switch (i) {
                case 0:
                    score = columnScore(0, configArray) + rowScore(0, configArray);
                    break;
                case 1:
                    score = columnScore(1, configArray) + rowScore(0, configArray);
                    break;
                case 2:
                    score = columnScore(2, configArray) + rowScore(0, configArray);
                    break;
                case 3:
                    score = columnScore(0, configArray) + rowScore(1, configArray);
                    break;
                case 4:
                    score = columnScore(1, configArray) + rowScore(1, configArray);
                    break;
                case 5:
                    score = columnScore(2, configArray) + rowScore(1, configArray);
                    break;
                case 6:
                    score = columnScore(0, configArray) + rowScore(3, configArray);
                    break;
                case 7:
                    score = columnScore(1, configArray) + rowScore(3, configArray);
                    break;
                case 8:
                    score = columnScore(2, configArray) + rowScore(3, configArray);
                    break;
            }
        return score;
    }

    private int compareChar(char test, char goal){
        if(test == goal)
            return 0;
        return 1;
    }

    private int columnScore(int column, char[] configArray){
        int score;

        switch(column){
            case 0:
                score = compareChar(configArray[0], goalState.charAt(0))
                        + compareChar(configArray[3], goalState.charAt(3))
                        + compareChar(configArray[6], goalState.charAt(6));
                break;
            case 1:
                score = compareChar(configArray[1], goalState.charAt(1))
                        + compareChar(configArray[4], goalState.charAt(4))
                        + compareChar(configArray[7], goalState.charAt(7));
                break;
            case 2:
                score = compareChar(configArray[2], goalState.charAt(2))
                        + compareChar(configArray[5], goalState.charAt(5))
                        + compareChar(configArray[8], goalState.charAt(8));
                break;
            default:
                score = -1;
        }

        return score;
    }

    private int rowScore(int row, char[] configArray){
        int score;

        switch(row){
            case 0:
                score = compareChar(configArray[0], goalState.charAt(0))
                        + compareChar(configArray[1], goalState.charAt(1))
                        + compareChar(configArray[2], goalState.charAt(2));
                break;
            case 1:
                score = compareChar(configArray[3], goalState.charAt(3))
                        + compareChar(configArray[4], goalState.charAt(4))
                        + compareChar(configArray[5], goalState.charAt(5));
                break;
            case 2:
                score = compareChar(configArray[6], goalState.charAt(6))
                        + compareChar(configArray[7], goalState.charAt(7))
                        + compareChar(configArray[8], goalState.charAt(8));
                break;
            default:
                score = -1;
        }

        return score;
    }



    public void printHistory() {
        Iterator<String> stringIterator = history.iterator();
        System.out.println("Solution Path:");
        while(stringIterator.hasNext()){
            System.out.print("("+ stringIterator.next() +")");
            if(stringIterator.hasNext())
                System.out.print(" -> ");
        }
        System.out.println();
    }

    private String ChangeTempConfigurationStringAndReturnsString(String configuration, Direction someDirection, int indexOfB) {

        char [] configArray = new char[9];
        for(int i=0; i<9; ++i){
            configArray[i] = configuration.charAt(i);
        }
        if(someDirection == Direction.Left){
            configArray[indexOfB] = configArray[indexOfB - 1];
            configArray[indexOfB - 1] = 'b';
        }
        if(someDirection == Direction.Right){
            configArray[indexOfB] = configArray[indexOfB + 1];
            configArray[indexOfB + 1] = 'b';
        }
        if(someDirection == Direction.Up){
            configArray[indexOfB] = configArray[indexOfB + 3];
            configArray[indexOfB + 3] = 'b';
        }
        if(someDirection == Direction.Down){
            configArray[indexOfB] = configArray[indexOfB - 3];
            configArray[indexOfB - 3] = 'b';
        }
        configuration = "";
        for (int i = 0; i < 9 ; ++i){
            configuration += configArray[i];
        }
        return configuration;

    }
}
