package DualCurses;

import java.awt.*;

public class DCTextArea
{
	private int[] origin = {0, 0};
	private int width;
	private int height;
	private int[] cursorLoc = {0, 0};
	private char[][] charMap;
	private int[][] fgMap;
	private int[][] bgMap;


	public int[] getOrigin(){return origin;}
	public int getWidth(){return width;}
	public int getHeight(){return height;}
	public int[] getCursorLoc(){return cursorLoc;}
	public char[][] getCharMap(){return charMap;}
	public int[][] getFgMap(){return fgMap;}
	public int[][] getBgMap(){return bgMap;}


	public void setOrigin(int[] o){origin = o;}
	public void setWidth(int w){width = w;}
	public void setHeight(int h){height = h;}
	public void setCursorLoc(int[] c){cursorLoc = c;}
	public void setCharMap(char[][] c){charMap = c;}
	public void setFgMap(int[][] f){fgMap = f;}
	public void setBgMap(int[][] b){bgMap = b;}


   public DCTextArea(int xOrigin, int yOrigin, int _width, int _height)
   {
      width = _width;
      height = _height;
      charMap = new char[width][height];
      fgMap = new int[width][height];
      bgMap = new int[width][height];
   }
   
   public void carriageReturn()
   {
      cursorLoc[0] = 0;
      cursorLoc[1]++;
   }
   
   public boolean isInBounds(int x, int y)
   {
      return x > 0 && x < width &&
         y > 0 && y < height;
   }
   
   public void write(DCPanel panel)
   {
      for(int x = 0; x < width; x++)
      for(int y = 0; y < height; y++)
      {
         panel.setRectTile(origin[0] + x, origin[1] + y, charMap[x][y], charMap[x][y], charMap[x][y]);
      }
   }

}