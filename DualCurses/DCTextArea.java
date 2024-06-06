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
   private int defaultBGColor;


	public int[] getOrigin(){return origin;}
	public int getWidth(){return width;}
	public int getHeight(){return height;}
	public int[] getCursorLoc(){return cursorLoc;}
	public char[][] getCharMap(){return charMap;}
	public int[][] getFgMap(){return fgMap;}
	public int[][] getBgMap(){return bgMap;}
   public int getDefaultBGColor(){return defaultBGColor;}


	public void setOrigin(int[] o){origin = o;}
	public void setWidth(int w){width = w;}
	public void setHeight(int h){height = h;}
	public void setCursorLoc(int[] c){cursorLoc = c;}
	public void setCharMap(char[][] c){charMap = c;}
	public void setFgMap(int[][] f){fgMap = f;}
	public void setBgMap(int[][] b){bgMap = b;}
   public void setDefaultBGColor(int dbgc){defaultBGColor = dbgc;}


   public DCTextArea(int xOrigin, int yOrigin, int _width, int _height)
   {
      width = _width;
      height = _height;
      charMap = new char[width][height];
      fgMap = new int[width][height];
      bgMap = new int[width][height];
      defaultBGColor = Color.BLACK.getRGB();
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
   
   public void put(DCString str)
   {
      int bg = str.getBGColor();
      int fg = str.getFGColor();
      String text = str.getText();
      for(int i = 0; i < str.length(); i++)
      {
         charMap[cursorLoc[0]][cursorLoc[1]] = text.charAt(i);
         fgMap[cursorLoc[0]][cursorLoc[1]] = fg;
         bgMap[cursorLoc[0]][cursorLoc[1]] = bg;
         cursorLoc[0]++;
      }
   }

}