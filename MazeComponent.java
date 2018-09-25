// Name: Ruirui Huang
// USC NetID: ruiruihu
// CS 455 PA3
// Spring 2018

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Color;
import javax.swing.JComponent;
import java.util.*;

/**
   MazeComponent class
   
   A component that displays the maze and path through it if one has been found.
*/
public class MazeComponent extends JComponent{
   private static final int START_X = 10; // top left of corner of maze in frame
   private static final int START_Y = 10;
   private static final int BOX_WIDTH = 20;  // width and height of one maze "location"
   private static final int BOX_HEIGHT = 20;
   private static final int INSET = 2;  // how much smaller on each side to make entry/exit inner box
   private Maze maze;
   
   
   /**
      Constructs the component.
      @param maze   the maze to display
   */
   public MazeComponent(Maze maze){
      this.maze = maze;
   }

   
   /**
     Draws the current state of maze including the path through it if one has
     been found.
     @param g the graphics context
   */
   public void paintComponent(Graphics g){
      paintMaze(g);
      if(!maze.getPath().isEmpty()) {
         paintPath(g);
      }
   }
   
   /**
    * Paint the maze
    * @param g the graphics context
    */   
   private void paintMaze(Graphics g) {
      int row = maze.numRows();
      int col = maze.numCols();
      for(int i = 0; i < row; i++) {
         for(int j = 0; j < col; j++) {
         if(maze.hasWallAt(new MazeCoord(i,j))) {
            g.setColor(Color.black);
         } else {
            g.setColor(Color.white);
         }
         g.fillRect(START_X+j*BOX_WIDTH,START_Y+i*BOX_HEIGHT,BOX_WIDTH,BOX_HEIGHT);
         if(maze.getExitLoc().equals(new MazeCoord(i,j))) {
            g.setColor(Color.green);
         }else if(maze.getEntryLoc().equals(new MazeCoord(i,j))) {
            g.setColor(Color.yellow);
         }
         g.fillRect(START_X+j*BOX_WIDTH+INSET,START_Y+i*BOX_HEIGHT+INSET,BOX_WIDTH-2*INSET,BOX_HEIGHT-2*INSET);
         }
      }
      g.setColor(Color.black);
      g.drawRect(START_X,START_Y,col*BOX_WIDTH,row*BOX_HEIGHT);
   }
   
   /**
    * Paint the path of the maze if exist
    * @param g the graphics context
    */
   private void paintPath(Graphics g) {
      Iterator<MazeCoord> iter = maze.getPath().listIterator();
      MazeCoord currentLoc = iter.next();
      MazeCoord nextLoc = currentLoc;
      MazeCoord startLoc = maze.getEntryLoc();
      g.setColor(Color.blue);
      g.drawLine(START_X+startLoc.getCol()*BOX_WIDTH+BOX_WIDTH/2,START_Y+startLoc.getRow()*BOX_HEIGHT+BOX_HEIGHT/2,START_X+startLoc.getCol()*BOX_WIDTH+BOX_WIDTH/2,START_Y+startLoc.getRow()*BOX_HEIGHT+BOX_HEIGHT/2);
      while(iter.hasNext()) {
         currentLoc = nextLoc;
         nextLoc = iter.next();
         g.drawLine(START_X+currentLoc.getCol()*BOX_WIDTH+BOX_WIDTH/2,START_Y+currentLoc.getRow()*BOX_HEIGHT+BOX_HEIGHT/2,START_X+nextLoc.getCol()*BOX_WIDTH+BOX_WIDTH/2,START_Y+nextLoc.getRow()*BOX_HEIGHT+BOX_HEIGHT/2);
      }
   }
}



