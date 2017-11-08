package com.company;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;

/**
 * Created by scottjones on 11/6/17.
 */
public class AStarSearch extends AbstractPuzzle{

    private static final int MAX_STEPS = 5000;
    private int steps;
    private PriorityQueue<PuzzleNode> queue;
    private PuzzleNode root;
    private ArrayList<PuzzleNode> history;



    private static class NumMisplacedTilesComparator implements Comparator<PuzzleNode>{
        @Override
        public int compare(PuzzleNode o1, PuzzleNode o2){
            //implement heuristic here  h(n)
            String goal = "12345678b";
            int score1 = 0;
            int score2 = 0;
            String config1 = o1.getConfig();
            String config2 = o2.getConfig();
            for (int i = 0; i < 9; ++i){
                if(config1.charAt(i) != goal.charAt(i))
                    ++score1;
                if(config2.charAt(i) != goal.charAt(i))
                    ++score2;
            }
            //Call g(n) ths cost to get to that node
            score1 += o1.getDepth();
            score2 += o2.getDepth();
            return score1 - score2;
        }
    }

    private static class ManhattanDistanceComparator implements Comparator<PuzzleNode> {
        public int compare(PuzzleNode o1, PuzzleNode o2) {
            String goal = "12345678b";
            int score1 = 0;
            int score2 = 0;
            ManhattanDistanceCalculator o1Calculator = new ManhattanDistanceCalculator(o1.getConfig(), goal);
            ManhattanDistanceCalculator o2Calculator = new ManhattanDistanceCalculator(o2.getConfig(), goal);
            score1 = o1Calculator.getDistance();
            score2 = o2Calculator.getDistance();
            //Call g(n) ths cost to get to that node
            score1 += o1.getDepth();
            score2 += o2.getDepth();
            return score1 - score2;
        }
    }

    private class ColumnsRowsComparator implements Comparator<PuzzleNode> {
        public int compare(PuzzleNode o1, PuzzleNode o2){
            ColumnRowCalculator calculator = new ColumnRowCalculator();
            int score1 = 0;
            int score2 = 0;
            score1 = calculator.ColumnsRowsScore(o1.getConfig());
            score2 = calculator.ColumnsRowsScore(o2.getConfig());
            score1 += o1.getDepth();
            score2 += o2.getDepth();

        return score1 - score2;
        }
    }


    // Argument int heuristic: 1 = Number of misplaced tiles
    // 2 = Manhattan Distance
    // 3 = Rows and Columns out of order
    public AStarSearch(String inputConfiguration, int heuristic) {
        Comparator<PuzzleNode> heuristicComparator;

        if(heuristic == 3){
            heuristicComparator = new ColumnsRowsComparator();
        }
        else if(heuristic == 2){
            heuristicComparator = new ManhattanDistanceComparator();
        }
        else {
            heuristicComparator = new NumMisplacedTilesComparator();
        }
        queue = new PriorityQueue<>(heuristicComparator);
        steps = 0;
        root = new PuzzleNode(inputConfiguration);
        history = new ArrayList<>();
    }

    public boolean findSolution() {
        PuzzleNode currentNode;
        //push root onto priority queue
        queue.add(root);

        while(!queue.peek().checkGoalCondition())
        {
            currentNode = queue.remove();
            history.add(currentNode);
            //for each leaf
            for (Direction direction: Direction.values()) {
                //Check if leaf is valid in game space
                if(currentNode.checkDirection(direction)) {
                    //expand leaf to node and put new node in queue
                    queue.add(currentNode.expand(direction));
                }
            }
            //limit number of iterations
            ++steps;
            if(steps >= MAX_STEPS){
                return false;
            }
        }
        return true;
    }

    public int getSteps() {
        return steps;
    }


    public void printHistory() {
        Iterator<PuzzleNode> iterator= history.listIterator();
        System.out.println("\nHistory:");
        while(iterator.hasNext()){
            System.out.print(" (" + iterator.next().getConfig() + ") ->");
        }
        System.out.println();
    }
;

    public void printPathWrapper() {
        //if found, goal node will be in front of queue
        PuzzleNode current = queue.peek();
        printPath(current);
        System.out.println();
        return;
    }

    private void printPath(PuzzleNode current){
        if(current == current.getParent()) {
            System.out.print('('+current.getConfig()+")");
            return;
        }
        printPath(current.getParent());
        System.out.print(" -> (" + current.getConfig() + ")");
        return;
    }


    public int getMaxSteps() {
        return MAX_STEPS;
    }

}
