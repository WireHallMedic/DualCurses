package DualCurses;

import javax.swing.*;
import java.awt.*;
import java.awt.image.*;

public class DCPanel extends JPanel
{
	private DCPalette palette;
	private BufferedImage bigImage;
	private BufferedImage smallImage;
   private BufferedImage emptyTile;
	private boolean changeWasMade;
	private int columns;
	private int rows;
	private int columnSize;
	private int rowSize;


	public DCPalette getPalette(){return palette;}
	public BufferedImage getBigImage(){return bigImage;}
	public BufferedImage getSmallImage(){return smallImage;}
   public BufferedImage getEmptyTile(){return emptyTile;}
	public boolean changeWasMade(){return changeWasMade;}
	public int getColumns(){return columns;}
	public int getRows(){return rows;}
	public int getColumnSize(){return columnSize;}
	public int getRowSize(){return rowSize;}


	public void setPalette(DCPalette p){palette = p;}
	public void setBigImage(BufferedImage b){bigImage = b;}
	public void setSmallImage(BufferedImage s){smallImage = s;}
   public void setEmptyTile(BufferedImage e){emptyTile = e;}
	public void setChangeWasMade(boolean c){changeWasMade = c;}
	public void setColumns(int c){columns = c;}
	public void setRows(int r){rows = r;}
	public void setColumnSize(int c){columnSize = c;}
	public void setRowSize(int r){rowSize = r;}


   public DCPanel(int rowsTall, int columnsWide)
   {
      super();
      palette = null;
      rows = rowsTall;
      columns = columnsWide;
      bigImage = null;
      smallImage = null;
      emptyTile = null;
      changeWasMade = true;
   }
   
   public void setPalette(String rectImageFileName, String squareImageFileName, int rows_tall, int columns_wide)
   {
      palette = new DCPalette(rectImageFileName, squareImageFileName, rows_tall, columns_wide);
      emptyTile = palette.getRectTile(0, Color.BLACK.getRGB(), Color.BLACK.getRGB());
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
      Image scaledImage = smallImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
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
   
   @Override
   public void paint(Graphics g)
   {
      if(smallImage != null)
      {
         if(changeWasMade)
         {
            createBigImage(this.getWidth(), this.getHeight());
            changeWasMade = false;
         }
         Graphics2D g2d = (Graphics2D)g;
         g2d.drawImage(bigImage, 0, 0, null);
      }
      super.paint(g);
   }
}