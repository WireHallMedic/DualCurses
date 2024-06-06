package DualCurses;

import java.awt.*;
import java.util.*;
import java.util.regex.*;

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
   
   public static final Pattern splitChars = Pattern.compile("^[^ \n-]*[ \n-]");


   public DCTextArea(int xOrigin, int yOrigin, int _width, int _height)
   {
      width = _width;
      height = _height;
      origin[0] = xOrigin;
      origin[1] = yOrigin;
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
   
   public int remainingSpace()
   {
      return width - cursorLoc[0];
   }
   
   public boolean isInBounds(int x, int y)
   {
      return x > 0 && x < width &&
         y > 0 && y < height;
   }
   
   public void write(DCPanel panel)
   {
      while(cursorLoc[1] < height)
         finishLine();
      for(int x = 0; x < width; x++)
      for(int y = 0; y < height; y++)
      {
         panel.setRectTile(origin[0] + x, origin[1] + y, charMap[x][y], fgMap[x][y], bgMap[x][y]);
      }
   }
   
   public void put(DCString str)
   {
      if(cursorLoc[1] >= height)
         return;
      if(str.isNewline())
      {
         finishLine();
         return;
      }
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
      if(cursorLoc[0] == width)
      {
         cursorLoc[0] = 0;
         cursorLoc[1]++;
      }
   }
   
   public void finishLine()
   {
      for(int x = cursorLoc[0]; x < width; x++)
      {
         charMap[cursorLoc[0]][cursorLoc[1]] = ' ';
         fgMap[cursorLoc[0]][cursorLoc[1]] = defaultBGColor;
         bgMap[cursorLoc[0]][cursorLoc[1]] = defaultBGColor;
         cursorLoc[0]++;
      }
      cursorLoc[0] = 0;
      cursorLoc[1]++;
   }
   
   public Vector<DCString> split(DCString input)
   {
      Vector<DCString> strVect = new Vector<DCString>();
      Matcher matcher;
      String str = input.getText();
      while(str.length() > 0)
      {
         matcher = splitChars.matcher(str);
         if(matcher.find())
         {
            String result = matcher.group();
            str = str.substring(result.length());
            // space and dash groups contain terminating character, newlines do not
            if(result.contains("\n"))
            {
               result = result.substring(0, result.length() - 1);
               strVect.add(new DCString(result, input.getFGColor(), input.getBGColor()));
               strVect.add(new DCString("\n"));
            }
            else
            {
               strVect.add(new DCString(result, input.getFGColor(), input.getBGColor()));
            }
         }
         else
         {
            strVect.add(new DCString(str, input.getFGColor(), input.getBGColor()));
            str = "";
         }
      }
      return strVect;
   }
   
   public void append(String input)
   {
      append(new DCString(input));
   }
   
   public void append(DCString input)
   {
      Vector<DCString> strList = split(input);
      for(int i = 0; i < strList.size(); i++)
      {
         DCString curString = strList.elementAt(i);
         // room for string
         if(curString.length() <= remainingSpace())
         {
            put(curString);
         }
         // room for trimmed string
         else if(curString.hasTrailingSpace() && curString.length() == remainingSpace() + 1)
         {
            curString.setText(curString.getText().substring(0, curString.getText().length() - 1));
            put(curString);
         }
         // room on next line
         else if(curString.length() <= width)
         {
            finishLine();
            put(curString);
         }
         // need to hyphenate
         else
         {
            if(cursorLoc[0] > 0)
               finishLine();
            String remainder = curString.getText().substring(width);
            curString.setText(curString.getText().substring(0, remainder.length()) + "-");
            put(curString);
            strList.add(i + 1, new DCString(remainder, curString.getFGColor(), curString.getBGColor()));
         }
      }
   }

}