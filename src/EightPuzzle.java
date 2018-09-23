import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

// NOTE: When applying h1 and h2 they should both have the same solution depth
// Solution cost should be close to the table
// Catch bad input
public class EightPuzzle implements Comparable<EightPuzzle>{
    // TODO: Change back to private
    private final int[] currentPuzzle;
    protected int stepCost;
    private int estimatedCost;
    private EightPuzzle previousPuzzle;

    public EightPuzzle (int[] currentPuzzle, int stepCost, int estimatedCost, EightPuzzle previousPuzzle) {
        this.currentPuzzle = currentPuzzle;
        this.stepCost = stepCost;
        this.estimatedCost = estimatedCost;
        this.previousPuzzle = previousPuzzle;
    }

    public EightPuzzle(int[] currentPuzzle) {
        this.currentPuzzle = currentPuzzle;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof EightPuzzle)) {
            return false;
        }

        EightPuzzle that = (EightPuzzle) o;
        return Arrays.deepEquals(new int[][]{this.currentPuzzle}, new int[][]{that.currentPuzzle});
    }

    public int[] getPuzzle() {
        return currentPuzzle;
    }

    public EightPuzzle getPreviousPuzzle() {
        return previousPuzzle;
    }


    public void setEstimatedCost(int estimatedCost) {
        this.estimatedCost = estimatedCost;
    }

    public boolean isSolvable() {
        // Finding number of inversions
        int numberOfInversions = 0;
        for (int i = 0; i < currentPuzzle.length; i++) {
            if (i + 1 >= 9) {
                break;
            }
            if (currentPuzzle[i] < currentPuzzle[i + 1]) {
//                System.out.println("1st:" + currentPuzzle[i] + ", 2nd: " + currentPuzzle[i + 1]);
                numberOfInversions += 1;
            }
        }

// Debugging
//        System.out.println("Number of inversions: " + numberOfInversions);
//        System.out.println("Parity: " + parity);

        int parity = numberOfInversions % 2;
        if (parity == 0) {
            return true;
        } else {
            return false;
        }

    }

    public boolean isSolved() {
        for (int i = 0; i < currentPuzzle.length; i++) {
            if (i != currentPuzzle[i]) {
                return false;
            }
        }
        return true;
    }

    private int getTileAt(int location) {
        return currentPuzzle[location];
    }

    private int[] swapTiles(int location1, int location2) {
        int[] tempPuzzle = currentPuzzle.clone();
        int temp = tempPuzzle[location1];
        tempPuzzle[location1] = tempPuzzle[location2];
        tempPuzzle[location2] = temp;
        return tempPuzzle;
    }

    private static void shufflePuzzle(int[] puzzle) {
        int index, temp;
        Random random = new Random();
        for (int i = 0; i < puzzle.length; i++) {
            index = random.nextInt(puzzle.length);
//            System.out.print(index);
            temp = puzzle[index];
            puzzle[index] = puzzle[i];
            puzzle[i] = temp;
        }
    }

    private static int[] generatePuzzle() {
       int[] generatedPuzzle = new int[9];
       for (int i = 0; i < generatedPuzzle.length; i++) {
           generatedPuzzle[i] = i;
       }
       shufflePuzzle(generatedPuzzle);
       return generatedPuzzle;
    }

    // Needs to be changed to have a column for average runtime, and number of cases with a specific depth
    private void printTable(HashMap<Integer, ArrayList<Integer>> data) {
        String leftAlignFormat = "| %-2d | %-6d | %-5d | %n";
        System.out.format("+---+---------+-------+%n");
        System.out.format("| d |   h1    |  h2   |%n");
        System.out.format("+---+---------+-------+%n");

        int i = 2;
        while ((i / 2) < data.size()) {
            ArrayList<Integer> searchCostList;
            if (data.containsKey(i)) {
                searchCostList = data.get(i);
                int h1 = searchCostList.get(0);
                int h2 = searchCostList.get(1);

                System.out.format(leftAlignFormat, i , h1, h2);
            } else {
                System.out.format(leftAlignFormat, -1 , -1, -1);
            }
            i += 2;
        }

        System.out.format("+---+---------+-------+%n");
    }

    public int hammingH1() {
        int hamming = 0;
        for (int i = 0; i < currentPuzzle.length; i++) {
            if (currentPuzzle[i] != i && currentPuzzle[i] != 0) {
                hamming += 1;
            }
        }
        return hamming;
    }

    // TODO: Optimization: Change method to work with 1d array
    public int manhattanH2() {
        int[][] puzzle2d = convertTo2D(currentPuzzle);

        int num = 0;
        int sumOfManhattan = 0;
        for (int y = 0; y < puzzle2d.length; y++) {
            for (int x = 0; x < puzzle2d[y].length; x++) {
                if (puzzle2d[y][x] != num && puzzle2d[y][x] != 0) {
                    int[] correctPosition = getCorrectPosition(puzzle2d[y][x]);
                    int manhattanDist = Math.abs(correctPosition[1] - x) + Math.abs(correctPosition[0] - y);
                    sumOfManhattan += manhattanDist;
                }
                num += 1;
            }
        }
        return sumOfManhattan;
    }

    private static int[][] convertTo2D (int[] puzzle) {
        int num = 0;
        int[][] puzzle2d = new int[3][3];
        for (int i = 0; i < puzzle2d.length; i++) {
            for (int j = 0; j < puzzle2d[i].length; j++) {
                puzzle2d[i][j] = puzzle[num];
                num += 1;
            }
        }
        return puzzle2d;
    }

    private static int[] getCorrectPosition(int num) {
        int iteration = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (num == iteration) {
                    int[] pos = {i, j};
//                    System.out.println(pos[0] + " " + pos[1]);
                    return pos;
                }
                iteration += 1;
            }
        }
        return null;
    }

    public Iterable<EightPuzzle> neighboringBoards() {
        ArrayList<EightPuzzle> neighbors = new ArrayList<EightPuzzle>();
        int locationOfZero = 0;
        for (int i = 0; i < currentPuzzle.length; i++) {
            if (currentPuzzle[i] == 0) {
                locationOfZero = i;
            }
        }

//        System.out.println("Orig Puzzle: ");
//        for (int num: currentPuzzle) {
//            System.out.print(num + " ");
//        }
//        System.out.println();

        EightPuzzle neighborPuzzle;
        int neighborArray[];
        // If tile is in the middle ((1,1) in 2d array)
        if (locationOfZero == 4) {
            for (int i = 1; i <= 7; i = i + 2) {
                neighborArray = swapTiles(locationOfZero, i);
                neighborPuzzle = new EightPuzzle(neighborArray);
                neighbors.add(neighborPuzzle);
            }
//            System.out.println("array1: ");
//            for (int num: array1) {
//                System.out.print(num + " ");
//            }
//            System.out.println();
            return neighbors;
        }
        // top left corner
        if (locationOfZero == 0) {
            neighborArray = swapTiles(locationOfZero, 1);
            neighborPuzzle = new EightPuzzle(neighborArray);
            neighbors.add(neighborPuzzle);

            neighborArray = swapTiles(locationOfZero, 3);
            neighborPuzzle = new EightPuzzle(neighborArray);
            neighbors.add(neighborPuzzle);
            return neighbors;
        }
        // top right corner
        if (locationOfZero == 2) {
            neighborArray = swapTiles(locationOfZero, 1);
            neighborPuzzle = new EightPuzzle(neighborArray);
            neighbors.add(neighborPuzzle);

            neighborArray = swapTiles(locationOfZero, 5);
            neighborPuzzle = new EightPuzzle(neighborArray);
            neighbors.add(neighborPuzzle);
            return neighbors;
        }
        // bottom left corner
        if (locationOfZero == 6) {
            neighborArray = swapTiles(locationOfZero, 3);
            neighborPuzzle = new EightPuzzle(neighborArray);
            neighbors.add(neighborPuzzle);

            neighborArray = swapTiles(locationOfZero, 7);
            neighborPuzzle = new EightPuzzle(neighborArray);
            neighbors.add(neighborPuzzle);
            return neighbors;
        }
        // bottom right corner
        if (locationOfZero == 8) {
            neighborArray = swapTiles(locationOfZero, 5);
            neighborPuzzle = new EightPuzzle(neighborArray);
            neighbors.add(neighborPuzzle);

            neighborArray = swapTiles(locationOfZero, 7);
            neighborPuzzle = new EightPuzzle(neighborArray);
            neighbors.add(neighborPuzzle);
            return neighbors;
        }
        // top middle
        if (locationOfZero == 1) {
            for (int i = 0; i <= 4; i = i + 2) {
                neighborArray = swapTiles(locationOfZero, i);
                neighborPuzzle = new EightPuzzle(neighborArray);
                neighbors.add(neighborPuzzle);
            }
            return neighbors;
        }
        // middle left
        if (locationOfZero == 3) {
            for (int i = 0; i <= 6; i = i + 2) {
                if (i != 2) {
                    neighborArray = swapTiles(locationOfZero, i);
                    neighborPuzzle = new EightPuzzle(neighborArray);
                    neighbors.add(neighborPuzzle);
                }
            }
            return neighbors;
        }
        //middle right
        if (locationOfZero == 5) {
            for (int i = 2; i <= 8; i = i + 2) {
                if (i != 6) {
                    neighborArray = swapTiles(locationOfZero, i);
                    neighborPuzzle = new EightPuzzle(neighborArray);
                    neighbors.add(neighborPuzzle);
                }
            }
            return neighbors;
        }
        // bottom middle
        if (locationOfZero == 7) {
            for (int i = 3; i <= 7; i = i + 2) {
                neighborArray = swapTiles(locationOfZero, i);
                neighborPuzzle = new EightPuzzle(neighborArray);
                neighbors.add(neighborPuzzle);
            }
            return neighbors;
        }

//        System.out.println("Orig Puzzle after swapping: ");
//        for (int num: currentPuzzle) {
//            System.out.print(num + " ");
//        }
//        System.out.println();

        return null;
    }

    private static int [] fileReader(String fileName, int line) {
        BufferedReader br = null;
        FileReader fr = null;
        try {
            fr = new FileReader(fileName);
            br = new BufferedReader(fr);

            String strPuzzle = null;
            for (int i = 1; i <= line; i++) {
                strPuzzle = br.readLine();
                if (strPuzzle.contains("Depth") || strPuzzle.isEmpty()) {
                    strPuzzle = null;
                }
            }

            if (strPuzzle != null) {
                String[] nums = strPuzzle.split(" ");
                int[] readPuzzle = new int[nums.length];
                for(int i = 0; i < nums.length; i++) {
                    readPuzzle[i] = Integer.parseInt(nums[i]);
                }
                return readPuzzle;
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null)
                    br.close();
                if (fr != null)
                    fr.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public int compareTo(EightPuzzle o) {
        int priority1 = stepCost + estimatedCost;
        int priority2 = o.stepCost + o.estimatedCost;

        if (priority1 < priority2) {
            return -1;
        } else if (priority1 > priority2) {
            return 1;
        } else {
            return 0;
        }
    }

    public static void main(String [] args) {
        EightPuzzle test;
        AStar aTest;

        // valid for d = 4
//        System.out.println("Checking file reader: ");
//        int[] puzzo = fileReader("puzzles.txt", 3);
//        for (int num: puzzo) {
//            System.out.print(num + " ");
//        }
//        System.out.println();
//        aTest = new AStar(puzzo, true);

        // debuggo
        int startRange = 205;
        int endRange = 305;
        for (int i = startRange; i <= endRange; i++) {
            int[] testPuzzle = fileReader("puzzles.txt", i);
            if (testPuzzle != null) {
                System.out.println("Puzzle at line: " + i);
                new AStar(testPuzzle, true);
                System.out.print("\n\n");
            }
        }

    }

}
