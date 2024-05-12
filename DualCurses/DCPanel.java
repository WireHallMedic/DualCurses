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
   }
   
   @Override
   public void paint(Graphics g)
   {
      if(smallImage != null)
      {
         
      }
      super.paint(g);
   }
}