// Name: Ruirui Huang
// USC NetID: ruiruihu
// CS 455 PA3
// Spring 2018

import java.util.LinkedList;

/**
   Maze class

   Stores information about a maze and can find a path through the maze
   (if there is one).
   
   Assumptions about structure of the maze, as given in mazeData, startLoc, and endLoc
   (parameters to constructor), and the path:
     -- no outer walls given in mazeData -- search assumes there is a virtual 
        border around the maze (i.e., the maze path can't go outside of the maze
        boundaries)
     -- start location for a path is maze coordinate startLoc
     -- exit location is maze coordinate exitLoc
     -- mazeData input is a 2D array of booleans, where true means there is a wall
        at that location, and false means there isn't (see public FREE / WALL 
        constants below) 
     -- in mazeData the first index indicates the row. e.g., mazeData[row][col]
     -- only travel in 4 compass directions (no diagonal paths)
     -- can't travel through walls

 */

public class Maze {
   
   public static final boolean FREE = false;
   public static final boolean WALL = true;
   private boolean[][] visited;
   private boolean[][] mazeData;
   private MazeCoord startLoc;
   private MazeCoord exitLoc;
   private LinkedList<MazeCoord> path;
  

   /**
      Constructs a maze.
      @param mazeData the maze to search.  See general Maze comments above for what
      goes in this array.
      @param startLoc the location in maze to start the search (not necessarily on an edge)
      @param exitLoc the "exit" location of the maze (not necessarily on an edge)
      PRE: 0 <= startLoc.getRow() < mazeData.length and 0 <= startLoc.getCol() < mazeData[0].length
         and 0 <= endLoc.getRow() < mazeData.length and 0 <= endLoc.getCol() < mazeData[0].length

    */
   public Maze(boolean[][] mazeData, MazeCoord startLoc, MazeCoord exitLoc) {
      if(mazeData != null && mazeData.length != 0) {
         this.mazeData = new boolean[mazeData.length][mazeData[0].length];
         for(int i = 0; i < mazeData.length; i++) {
            for(int j = 0; j < mazeData[0].length; j++) {
               this.mazeData[i][j] = mazeData[i][j];
            }
         }
         this.startLoc = new MazeCoord(startLoc.getRow(),startLoc.getCol());
         this.exitLoc = new MazeCoord(exitLoc.getRow(),exitLoc.getCol());
         this.path = new LinkedList<MazeCoord>();
      }
   }


   /**
      Returns the number of rows in the maze
      @return number of rows
   */
   public int numRows() {
      if(mazeData != null) {
         return mazeData.length;
      } else {
         return 0;
      }
   }

   
   /**
      Returns the number of columns in the maze
      @return number of columns
   */   
   public int numCols() {
      if(numRows() != 0) {
    	  return mazeData[0].length;
      } else {
    	  return 0;
      }
   } 
 
   
   /**
      Returns true iff there is a wall at this location
      @param loc the location in maze coordinates
      @return whether there is a wall here
      PRE: 0 <= loc.getRow() < numRows() and 0 <= loc.getCol() < numCols()
   */
   public boolean hasWallAt(MazeCoord loc) {
      return mazeData[loc.getRow()][loc.getCol()];
   }
   

   /**
      Returns the entry location of this maze.
    */
   public MazeCoord getEntryLoc() {
      return new MazeCoord(startLoc.getRow(),startLoc.getCol());
   }
   
   
   /**
     Returns the exit location of this maze.
   */
   public MazeCoord getExitLoc() {
      return new MazeCoord(exitLoc.getRow(),exitLoc.getCol()); 
   }

   
   /**
      Returns the path through the maze. First element is start location, and
      last element is exit location.  If there was not path, or if this is called
      before a call to search, returns empty list.

      @return the maze path
    */
   public LinkedList<MazeCoord> getPath() {
      return path; 
   }


   /**
      Find a path from start location to the exit location (see Maze
      constructor parameters, startLoc and exitLoc) if there is one.
      Client can access the path found via getPath method.

      @return whether a path was found.
    */
   public boolean search() {
      path.clear();
      if(hasWallAt(startLoc) || hasWallAt(exitLoc)) {
         return false;
      }
      this.visited = new boolean[numRows()][numCols()];
      return helper(exitLoc); 
   }
   
   
   /**
    * Helper function for recursion
    * @param entryLoc to find path to startLoc from
    * @return whether a path was found from entryLoc to startLoc
    */
   private boolean helper(MazeCoord entryLoc) {
      int[] directionCol = {0,0,-1,1};
      int[] directionRow = {1,-1,0,0};
      int[] sequence = new int[4];
      if(entryLoc.equals(startLoc)) {
         path.add(entryLoc);
         return true;
      }
      if(hasWallAt(entryLoc) || visited[entryLoc.getRow()][entryLoc.getCol()]) {
         return false;
      }
	  visited[entryLoc.getRow()][entryLoc.getCol()] = true;
      if(entryLoc.getCol() == startLoc.getCol()) {
         RowFirst(entryLoc,sequence);
      } else if(entryLoc.getRow() == startLoc.getRow()){
         ColFirst(entryLoc,sequence);
      } else {
         RowandCol(entryLoc,sequence);
      }
	  for(int i = 0; i < 4; i++) {
         if(entryLoc.getRow()+directionRow[sequence[i]] >= 0 && entryLoc.getRow()+directionRow[sequence[i]] < numRows() && entryLoc.getCol()+directionCol[sequence[i]] >= 0 && entryLoc.getCol()+directionCol[sequence[i]] < numCols()) {
            if(helper(new MazeCoord(entryLoc.getRow()+directionRow[sequence[i]],entryLoc.getCol()+directionCol[sequence[i]]))){
               path.add(entryLoc);
               return true;
            }
         }
	  }
	  return false;
   }
   
   
   /**
    * If entryLoc and startLoc are in the same column, go up and down first.
    * @param entryLoc to find path to startLoc from
    * @param sequence the sequence of going up, down, left and right
    */
   private void RowFirst(MazeCoord entryLoc,int[] sequence) {	
      if(entryLoc.getRow() > startLoc.getRow()) {
         sequence[0] = 0;
         sequence[1] = 1;
      } else {
         sequence[0] = 1;
         sequence[1] = 0;
      }
      sequence[2] = 2;
      sequence[3] = 3;
   }
   
   
   /**
    * If entryLoc and startLoc are in the same row, go left and right first.
    * @param entryLoc to find path to startLoc from
    * @param sequence the sequence of going up, down, left and right
    */
   private void ColFirst(MazeCoord entryLoc,int[] sequence) {	
      if(entryLoc.getCol() > startLoc.getCol()) {
         sequence[0] = 2;
         sequence[1] = 3;
      } else {
         sequence[0] = 3;
         sequence[1] = 2;
      }
      sequence[2] = 0;
      sequence[3] = 1;
   }
   
   
   /**
    * Construct a sequence of going up, down, left and right. Towards the startLoc's direction first.
    * @param entryLoc to find path to startLoc from
    * @param sequence the sequence of going up, down, left and right
    */
   private void RowandCol(MazeCoord entryLoc,int[] sequence) {
      if(entryLoc.getCol() > startLoc.getCol()) {
         sequence[0] = 2;
   	     sequence[2] = 3;
      } else {
   	     sequence[0] = 3;
   	     sequence[2] = 2;
      }
   	  if(entryLoc.getRow() > startLoc.getRow()) {
   	     sequence[1] = 0;
   	     sequence[3] = 1;
   	  } else {
   	     sequence[1] = 1;
   	     sequence[3] = 0;
   	  }
   }
}
