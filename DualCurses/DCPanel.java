package DualCurses;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.awt.event.*;

public class DCPanel extends JPanel implements ComponentListener, MouseMotionListener, Runnable
{
	private DCPalette palette;
	private BufferedImage bigImage;
	private BufferedImage smallImage;
   private BufferedImage emptyTile;
   private int defaultBGColor;
   private int defaultFGColor;
	private boolean changeWasMade;
	private int columns;
	private int rows;
	private int columnSize;
	private int rowSize;
   private int bigImageWidth;
   private int bigImageHeight;
   private int bigImageXInset;
   private int bigImageYInset;
   private int[] mouseLoc = {-1, -1};
   private int fps;
   private long lastSecond;
   private int framesThisSecond;
   private long millisPerFrame;


	public DCPalette getPalette(){return palette;}
	public BufferedImage getBigImage(){return bigImage;}
	public BufferedImage getSmallImage(){return smallImage;}
   public BufferedImage getEmptyTile(){return emptyTile;}
   public int getDefaultGBColor(){return defaultBGColor;}
   public int getDefaultFBColor(){return defaultFGColor;}
	public boolean changeWasMade(){return changeWasMade;}
	public int getColumns(){return columns;}
	public int getRows(){return rows;}
	public int getColumnSize(){return columnSize;}
	public int getRowSize(){return rowSize;}
   public int getFPS(){return fps;}


	public void setPalette(DCPalette p){palette = p;}
	public void setBigImage(BufferedImage b){bigImage = b;}
	public void setSmallImage(BufferedImage s){smallImage = s;}
   public void setEmptyTile(BufferedImage e){emptyTile = e;}
   public void setDefaultBGColor(int rgb){defaultBGColor = rgb;}
   public void setDefaultFGColor(int rgb){defaultFGColor = rgb;}
	public void setChangeWasMade(boolean c){changeWasMade = c;}
	public void setColumns(int c){columns = c;}
	public void setRows(int r){rows = r;}
	public void setColumnSize(int c){columnSize = c;}
	public void setRowSize(int r){rowSize = r;}


   // constructor
   public DCPanel(int columnsWide, int rowsTall)
   {
      super();
      palette = null;
      rows = rowsTall;
      columns = columnsWide;
      bigImage = null;
      smallImage = null;
      emptyTile = null;
      defaultBGColor = Color.BLACK.getRGB();
      defaultFGColor = Color.WHITE.getRGB();
      framesThisSecond = 0;
      millisPerFrame = 1000 / 60;
      lastSecond = System.currentTimeMillis();
      changeWasMade = true;
      addComponentListener(this);
      addMouseMotionListener(this);
      Thread thread = new Thread(this);
      thread.start();
   }
   
   // sets the framerate that the panel will try and maintain, in frames per second
   public void setTargetFramerate(int fr)
   {
      millisPerFrame = 1000 / (long)fr;
   }
   
   // used to automatically repaint and calculate framerate
   public void run()
   {
      long lastFrame = System.currentTimeMillis();
      long curFrame;
      while(true)
      {
         curFrame = System.currentTimeMillis();
         if(curFrame - lastFrame >= millisPerFrame)
         {
            lastFrame = curFrame;
            this.repaint();
         }
         Thread.yield();
      }
   }
   
   // overridded repaint, to calculate framerate
   @Override
   public void repaint()
   {
      super.repaint();
      framesThisSecond++;
      long curSecond = System.currentTimeMillis();
      if(curSecond - lastSecond >= 1000)
      {
         fps = framesThisSecond;
         framesThisSecond = 0;
         lastSecond = curSecond;
      }
   }
   
   // creates a palette for this panel
   public void setPalette(String rectImageFileName, String squareImageFileName, int columns_wide, int rows_tall)
   {
      palette = new DCPalette(rectImageFileName, squareImageFileName, rows_tall, columns_wide);
      emptyTile = palette.getRectTile(0, defaultBGColor, defaultBGColor);
      columnSize = emptyTile.getWidth();
      rowSize = emptyTile.getHeight();
      smallImage = new BufferedImage(columns * columnSize, rows * rowSize, BufferedImage.TYPE_INT_ARGB);
      calcBigImageSize();
      for(int x = 0; x < columns; x++)
      for(int y = 0; y < rows; y++)
         placeTile(x, y, emptyTile);
   }
   
   // places a tile on the small image
   public void placeTile(int x, int y, BufferedImage newTile)
   {
      smallImage.createGraphics().drawImage(newTile, x * columnSize, y * rowSize, null);
      changeWasMade = true;
   }
   
   // scale the small image up
   public void createBigImage(int newWidth, int newHeight)
   {
      Image scaledImage = smallImage.getScaledInstance(newWidth, newHeight, Image.SCALE_FAST);
      bigImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
      bigImage.createGraphics().drawImage(scaledImage, 0, 0, null);
   }
   
   // set a rectangular tile at location x, y, using grid coordinates of palette
   public void setRectTile(int x, int y, int paletteX, int paletteY, int fgColor, int bgColor)
   {
      setRectTile(x, y, palette.flatten(paletteX, paletteY), fgColor, bgColor);
   }
   
   // set a rectangular tile at location x, y, using index of palette
   public void setRectTile(int x, int y, int index, int fgColor, int bgColor)
   {
      placeTile(x, y, palette.getRectTile(index, fgColor, bgColor));
   }
   
   // set a square tile at location x, y (coordinates are in rect tiles), using grid coordinates of palette
   public void setSquareTile(int x, int y, int paletteX, int paletteY, int fgColor, int bgColor)
   {
      setSquareTile(x, y, palette.flatten(paletteX, paletteY), fgColor, bgColor);
   }
   
   // set a square tile at location x, y (coordinates are in rect tiles), using index of palette
   public void setSquareTile(int x, int y, int index, int fgColor, int bgColor)
   {
      placeTile(x, y, palette.getSquareTile(index, fgColor, bgColor));
   }
   
   // write a string at x, y
   public void write(int xStart, int yStart, String str, int fgColor, int bgColor)
   {
      boolean defaultBGF = bgColor == defaultBGColor;
      int charVal = ' ';
      for(int x = 0; x < str.length(); x++)
      {
         charVal = str.charAt(x);
         if(defaultBGF && charVal == ' ')
            placeTile(xStart + x, yStart, emptyTile);
         else
            setRectTile(xStart + x, yStart, charVal, fgColor, bgColor);
      }
   }
   
   // write a string at x, y using default background color
   public void write(int xStart, int yStart, String str, int fgColor)
   {
      write(xStart, yStart, str, fgColor, defaultBGColor);
   }
   
   // write a string at x, y using default background and foreground colors
   public void write(int xStart, int yStart, String str)
   {
      write(xStart, yStart, str, defaultFGColor, defaultBGColor);
   }
   
   // write a string at x, y using a DCString object
   public void write(int xStart, int yStart, DCString str)
   {
      write(xStart, yStart, str.getText(), str.getFGColor(), str.getBGColor());
   }
   
   // the big image is the full height of the panel, but must maintain ratio
   private void calcBigImageSize()
   {
      bigImageWidth = (smallImage.getWidth() * this.getHeight()) / smallImage.getHeight();
      if(bigImageWidth > this.getWidth())
         bigImageWidth = this.getWidth();
      bigImageHeight = (smallImage.getHeight() * bigImageWidth) / smallImage.getWidth();
      bigImageXInset = (this.getWidth() - bigImageWidth) / 2;
      bigImageYInset = (this.getHeight() - bigImageHeight) / 2;
   }
   
   // return the mouse location in tiles (x, y)
   public int[] getMouseLoc()
   {
      int[] cl = new int[2];
      cl[0] = mouseLoc[0];
      cl[1] = mouseLoc[1];
      return cl;
   }
   
   // do calculations if needed, and paint the bigImage
   @Override
   public void paint(Graphics g)
   {
      super.paint(g);
      if(smallImage != null)
      {
         if(changeWasMade)
         {
            createBigImage(bigImageWidth, bigImageHeight);
            changeWasMade = false;
         }
         Graphics2D g2d = (Graphics2D)g;
         g2d.drawImage(bigImage, bigImageXInset, bigImageYInset, null);
      }
   }
   
   public void componentHidden(ComponentEvent e){}
   public void componentMoved(ComponentEvent e){}
   public void componentShown(ComponentEvent e){}
   
   // resize big image if panel is resized
   public void componentResized(ComponentEvent e)
   {
      calcBigImageSize();
      changeWasMade = true;
   }
   
   // keep track of mouse location
   public void mouseDragged(MouseEvent me){}
   public void mouseMoved(MouseEvent me)
   {
      int xLoc = me.getX() - bigImageXInset;
      int yLoc = me.getY() - bigImageYInset;
      if(xLoc < 0 || xLoc > bigImageWidth ||
         yLoc < 0 || yLoc > bigImageHeight)
      {
         mouseLoc[0] = -1;
         mouseLoc[1] = -1;
      }
      else
      {
         mouseLoc[0] = (int)((double)xLoc / ((double)bigImageWidth / (double)columns));
         mouseLoc[1] = (int)((double)yLoc / ((double)bigImageHeight / (double)rows));
      }
   }
}