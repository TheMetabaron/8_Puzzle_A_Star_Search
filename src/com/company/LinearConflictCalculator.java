package com.company;

/**
 * Created by scottjones on 11/5/17.
 */
public class LinearConflictCalculator {

    private char[][] currentState = new char [3][3];
    private char [][] goalState = new char [3][3];

    LinearConflictCalculator(String configuration, String goal){
        int k = 0;
        for(int i=0; i<3; ++i){
            for(int j=0; j<3; ++j){
                currentState[i][j] = configuration.charAt(k);
                ++k;
            }
        }
        k = 0;
        for(int i=0; i<3; ++i){
            for(int j=0; j<3; ++j){
                goalState[i][j] = goal.charAt(k);
                ++k;
            }
        }
    }

    public int getValue(){

        return 0;
    }


}
