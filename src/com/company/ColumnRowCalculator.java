package com.company;

/**
 * Created by scottjones on 11/8/17.
 */
public class ColumnRowCalculator {

    public int ColumnsRowsScore(String configuration) {

        int score = 0;
        char[] configArray = configuration.toCharArray();
        int i = 0;
        while (configArray[i] != 'b') {
            ++i;
        }
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

    private int rowScore(int row, char[] configArray){
        int score;
        String goalState = "12345678b";

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

    private int columnScore(int column, char[] configArray){
        int score;
        String goalState = "12345678b";

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

    private int compareChar(char test, char goal){
        if(test == goal)
            return 0;
        return 1;
    }
}
