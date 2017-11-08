package com.company;

public class Main {

    public static void main(String[] args) {
	// write your code here
        if(args.length < 1)
            System.out.println("First Argument should be a configuration for the 8-puzzlew");

        Puzzle game1 = new Puzzle(args[0]);
        if(game1.bestFirstSearch()) {
            System.out.println("Best First Search\nSolution found in " + game1.getSteps() + " steps \n");
        }
        else{
            System.out.println("Best First Search\nHeuristic: No. of misplaced tiles\nSolution not found in " + game1.getMaxSteps() + " steps");
        }
        game1.printHistory();

        Puzzle game2 = new Puzzle(args[0]);
        System.out.println("\n\nBest First Search\n Heuristic: Manhattan Distance");
        if(game2.bestFirstSearchManhattanDistance()){
            System.out.println("Solution found in " + game2.getSteps() + " steps \n");
        }
        else{
            System.out.println("Solution not found in " + game2.getMaxSteps() + " steps");
        }
        game2.printHistory();

        //Heuristic #3 - tiles out of order row + column
        Puzzle game3 = new Puzzle(args[0]);
        System.out.println("\n\nBest First Search\n Heuristic: Tiles out of order in row plus column");
        if(game3.bestFirstSearchColumnsRows()){
            System.out.println("Solution found in " + game3.getSteps() + " steps \n");
        }
        else{
            System.out.println("Solution not found in " + game3.getMaxSteps() + " steps");
        }
        game3.printHistory();


        //A* Search
        //heuristic #1 Number of Misplaced Tiles
        AStarSearch game4 = new AStarSearch(args[0], 1);
        System.out.println("\n\nA* Search\n Heuristic: Number of misplaced tiles");
        if(game4.findSolution()){
            System.out.println("Solution found in " + game4.getSteps() + " steps. The path on the front of the queue is: \n");
            game4.printPathWrapper();
        }
        else{
            System.out.println("Solution not found in " + game4.getMaxSteps() + " steps");
            game4.printPathWrapper();
            game4.printHistory();
        }

        //heiristic #2, Manhattan Distance
        AStarSearch game5 = new AStarSearch(args[0], 2);
        System.out.println("\n\nA* Search\n Heuristic: Manhattan Distance");
        if(game5.findSolution()){
            System.out.println("Solution found in " + game5.getSteps() + " steps. The path on the front of the queue is: \n ");
            game5.printPathWrapper();
        }
        else{
            System.out.println("Solution not found in " + game5.getMaxSteps() + " steps");
            game5.printPathWrapper();
            game5.printHistory();
        }

        //heuristic #3, Columns and Rows out of Order
        AStarSearch game6 = new AStarSearch(args[0], 3);
        System.out.println("\n\nA* Search\n Heuristic: Columns and Rows Out of Order");
        if(game6.findSolution()){
            System.out.println("Solution found in " + game6.getSteps() + " steps. The path on the front of the queue is: \n ");
            game6.printPathWrapper();
        }
        else{
            System.out.println("Solution not found in " + game6.getMaxSteps() + " steps");
            game6.printPathWrapper();
            game6.printHistory();
        }

    }
}
