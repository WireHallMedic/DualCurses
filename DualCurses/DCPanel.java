package DualCurses;

import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;

public class DCPanel extends JPanel implements ComponentListener
{
	private DCPalette palette;
	private BufferedImage bigImage;
	private BufferedImage smallImage;
   private BufferedImage emptyTile;
   private int defaultBGColor;
	private boolean changeWasMade;
	private int columns;
	private int rows;
	private int columnSize;
	private int rowSize;
   private int bigImageWidth;
   private int bigImageXInset;


	public DCPalette getPalette(){return palette;}
	public BufferedImage getBigImage(){return bigImage;}
	public BufferedImage getSmallImage(){return smallImage;}
   public BufferedImage getEmptyTile(){return emptyTile;}
   public int getDefaultGBColor(){return defaultBGColor;}
	public boolean changeWasMade(){return changeWasMade;}
	public int getColumns(){return columns;}
	public int getRows(){return rows;}
	public int getColumnSize(){return columnSize;}
	public int getRowSize(){return rowSize;}


	public void setPalette(DCPalette p){palette = p;}
	public void setBigImage(BufferedImage b){bigImage = b;}
	public void setSmallImage(BufferedImage s){smallImage = s;}
   public void setEmptyTile(BufferedImage e){emptyTile = e;}
   public void setDefaultBGColor(int rgb){defaultBGColor = rgb;}
	public void setChangeWasMade(boolean c){changeWasMade = c;}
	public void setColumns(int c){columns = c;}
	public void setRows(int r){rows = r;}
	public void setColumnSize(int c){columnSize = c;}
	public void setRowSize(int r){rowSize = r;}


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
      changeWasMade = true;
      addComponentListener(this);
   }
   
   public void setPalette(String rectImageFileName, String squareImageFileName, int columns_wide, int rows_tall)
   {
      palette = new DCPalette(rectImageFileName, squareImageFileName, rows_tall, columns_wide);
      emptyTile = palette.getRectTile(0, defaultBGColor, defaultBGColor);
      columnSize = emptyTile.getWidth();
      rowSize = emptyTile.getHeight();
      smallImage = new BufferedImage(columns * columnSize, rows * rowSize, BufferedImage.TYPE_INT_ARGB);
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
   
   public void setRectTile(int x, int y, int paletteX, int paletteY, int fgColor, int bgColor)
   {
      setRectTile(x, y, palette.flatten(paletteX, paletteY), fgColor, bgColor);
   }
   
   public void setRectTile(int x, int y, int index, int fgColor, int bgColor)
   {
      placeTile(x, y, palette.getRectTile(index, fgColor, bgColor));
   }
   
   public void setSquareTile(int x, int y, int paletteX, int paletteY, int fgColor, int bgColor)
   {
      setSquareTile(x, y, palette.flatten(paletteX, paletteY), fgColor, bgColor);
   }
   
   public void setSquareTile(int x, int y, int index, int fgColor, int bgColor)
   {
      placeTile(x, y, palette.getSquareTile(index, fgColor, bgColor));
   }
   
   // the big image is the full height of the panel, but must maintain ratio
   private void calcBigImageSize()
   {
      bigImageWidth = (smallImage.getWidth() * this.getHeight()) / smallImage.getHeight();
      bigImageXInset = (this.getWidth() - bigImageWidth) / 2;
   }
   
   @Override
   public void paint(Graphics g)
   {
      super.paint(g);
      if(smallImage != null)
      {
         if(changeWasMade)
         {
            calcBigImageSize();
            createBigImage(bigImageWidth, this.getHeight());
            changeWasMade = false;
         }
         Graphics2D g2d = (Graphics2D)g;
         g2d.drawImage(bigImage, bigImageXInset, 0, null);
      }
   }
   
   public void componentHidden(ComponentEvent e){}
   public void componentMoved(ComponentEvent e){}
   public void componentShown(ComponentEvent e){}
   
   public void componentResized(ComponentEvent e)
   {
      changeWasMade = true;
   }
}