import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JFrame;

public class MazeViewer2 {
   private static final char WALL_CHAR = '1';
   private static final char FREE_CHAR = '0';
   
   public static void main(String[] args)  {
	            
	            JFrame frame = readMazeFile();

	            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	            frame.setVisible(true);
	   }
   
   private static MazeFrame readMazeFile(){
	   boolean[][] mazeData = {{false,false,true,true},{true,false,false,true},{true,true,true,true},{false,false,false,false}};
	   return new MazeFrame(mazeData, new MazeCoord(0,0), new MazeCoord(2,3));
   }
}
