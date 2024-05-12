package DualCurses;

import java.awt.*;
import java.awt.image.*;
import java.net.*;
import java.io.*;
import javax.imageio.*;

public class DCPalette
{
   private DCTile[] rectTile;
	private DCTile[] squareTile;
	private int columns;
	private int rows;


	public DCTile[] getRectTile(){return rectTile;}
	public DCTile[] getSquareTile(){return squareTile;}
	public int getColumns(){return columns;}
	public int getRows(){return rows;}


	public void setRectTile(DCTile[] r){rectTile = r;}
	public void setSquareTile(DCTile[] s){squareTile = s;}
	public void setColumns(int c){columns = c;}
	public void setRows(int r){rows = r;}

   public DCPalette(String rectImageFileName, String squareImageFileName, int rows_tall, int columns_wide)
   {
      rows = rows_tall;
      columns = columns_wide;
      rectTile = new DCTile[columns * rows];
      squareTile = new DCTile[columns * rows];
      generateTiles(rectImageFileName, rectTile);
      generateTiles(squareImageFileName, squareTile);
   }
   
   // turn Cartesian index into linear index
   public int flatten(int x, int y)
   {
      return x + (y * columns);
   }
   
   // create a tile array
   public void generateTiles(String imageFileName, DCTile[] tileArr)
   {
      BufferedImage fullImage = loadImageFromFile(imageFileName);
      int tileWidth = fullImage.getWidth() / columns;
      int tileHeight = fullImage.getHeight() / rows;
      for(int x = 0; x < columns; x++)
      for(int y = 0; y < rows; y++)
      {
         tileArr[flatten(x, y)] = new DCTile(fullImage.getSubimage(x * tileWidth, y * tileHeight, tileWidth, tileHeight));
      }
   }
   
   
   // get URL of a resource
   private URL loadResource(String fileName)
   {
      try
      {
         URL url = DCPalette.class.getClassLoader().getResource(fileName);
         if(url == null)
            throw new Exception("Could not find resource: " + fileName);
         return url;
      } 
      catch(Exception e) 
      {
         e.printStackTrace(); 
      }
      throw new Error("Unable to load resource " + fileName);
   }
   
   // load an image file
   private BufferedImage loadImageFromFile(String fileName)
   {
      try
      {
         return ImageIO.read(loadResource(fileName));
      }
      catch(Exception e)
      {
         e.printStackTrace(); 
      }
      throw new Error("Unable to create image " + fileName);
   }
   
   // get colored rect tile
   public BufferedImage getRectTile(int x, int y, int fg, int bg){return getRectTile(flatten(x, y), fg, bg);}
   public BufferedImage getRectTile(int index, int fg, int bg)
   {
      return rectTile[index].generateImage(fg, bg);
   }
   
   // get colored square tile
   public BufferedImage getSquareTile(int x, int y, int fg, int bg){return getSquareTile(flatten(x, y), fg, bg);}
   public BufferedImage getSquareTile(int index, int fg, int bg)
   {
      return squareTile[index].generateImage(fg, bg);
   }
   
   // simple test
   public static void main(String[] args)
   {
      DCPalette palette = new DCPalette("WSFont_8x16.png", "WSFont_16x16.png", 16, 16);
   }
}