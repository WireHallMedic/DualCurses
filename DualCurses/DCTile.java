/*********************************************************************************

A small class to store tiles for generation.

*********************************************************************************/

package DualCurses;

import java.awt.image.*;
import java.awt.*;

public class DCTile implements DCConstants
{
   private boolean[][] stencilArr;
	private int width;
	private int height;
   private int backgroundRGB;


	public boolean[][] getStencilArr(){return stencilArr;}
	public int getWidth(){return width;}
	public int getHeight(){return height;}
   
   // constructor
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
   
   public DCTile(BufferedImage img){this(img, DEFAULT_BG_COLOR);}
   
   // get tile in specified colors
//    public BufferedImage generateImage(Color fg, Color bg)
//    {
//       return generateImage(fg.getRGB(), bg.getRGB());
//    }
   
   // get tile in specified colors as RGB values
   public BufferedImage generateImage(int fg, int bg)
   {
      BufferedImage newImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
      for(int x = 0; x < width; x++)
      for(int y = 0; y < height; y++)
      {
         if(stencilArr[x][y])
            newImg.setRGB(x, y, fg);
         else
            newImg.setRGB(x, y, bg);
      }
      return newImg;
   }
}