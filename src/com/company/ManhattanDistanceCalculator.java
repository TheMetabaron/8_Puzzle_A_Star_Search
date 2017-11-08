package com.company;

/**
 * Created by scottjones on 11/5/17.
 */
public class ManhattanDistanceCalculator {

    private char[][] currentState = new char [3][3];
    private char [][] goalState = new char [3][3];

    ManhattanDistanceCalculator(String configuration, String goal){
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

    public int getDistance(){
        int xValue = 0;
        int yValue = 0;
        int count = 0;

        for(int i=0; i<3; ++i){
            for(int j=0; j<3; ++j){
                //For each current state- get steps to goal
                for(int k=0; k<3; ++k){
                    for(int m =0; m<3; ++m){
                        if(currentState[i][j] == goalState[k][m]){
                            xValue = i - k;
                            yValue = j - m;
                            if(xValue < 0)
                                xValue *= -1;
                            if(yValue < 0)
                                yValue *= -1;
                        }
                    }
                }
                count += xValue + yValue;
            }
        }


        return count;
    }

}
