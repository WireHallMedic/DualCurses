/*********************************************************************************

A small class to store tiles for generation.

*********************************************************************************/

package DualCurses;

import java.awt.image.*;
import java.awt.*;

public class DCTile
{
   private boolean[][] stencilArr;
	private int width;
	private int height;
   private int backgroundRGB;
   
   public static final int DEFAULT_BACKGROUND_RGB = Color.BLACK.getRGB();


	public boolean[][] getStencilArr(){return stencilArr;}
	public int getWidth(){return width;}
	public int getHeight(){return height;}
   
   public DCTile(BufferedImage img, int bgRGB)
   {
      backgroundRGB = bgRGB;
      width = img.getWidth();
      height = img.getHeight();
      stencilArr = new boolean[width][height];
      for(int x = 0; x < width; x++)
      for(int y = 0; y < height; y++)
      {
         if(img.getRGB(x, y) == bgRGB)
            stencilArr[x][y] = false;
         else
            stencilArr[x][y] = true;
      }
   }
   
   public DCTile(BufferedImage img){this(img, DEFAULT_BACKGROUND_RGB);}

}