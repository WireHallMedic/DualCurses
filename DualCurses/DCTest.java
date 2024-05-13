package DualCurses;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class DCTest implements ActionListener
{
   private JFrame frame;
   private DCPanel dcPanel;
   private javax.swing.Timer timer;
   private int cursorX;
   private int cursorY;
   private static final int COLUMNS = 120;
   private static final int ROWS = 36;
   private static final int[] COLOR_ARR = {
      Color.BLACK.getRGB(), Color.BLUE.getRGB(), Color.CYAN.getRGB(), Color.DARK_GRAY.getRGB(), Color.GRAY.getRGB(), Color.GREEN.getRGB(), 
      Color.LIGHT_GRAY.getRGB(), Color.MAGENTA.getRGB(), Color.ORANGE.getRGB(), Color.PINK.getRGB(), Color.RED.getRGB(), Color.WHITE.getRGB(), 
      Color.YELLOW.getRGB()};
   private Random rng;
   
   public DCTest()
   {
      frame = new JFrame();
      frame.setSize(1000, 800);
      frame.setLayout(new GridLayout(1, 1));
      dcPanel = new DCPanel(COLUMNS, ROWS);
      dcPanel.setPalette("/WSFont_8x16.png", "/WSFont_16x16.png", 16, 16);
      dcPanel.setBackground(Color.BLACK);
      frame.add(dcPanel);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      for(int x = 0; x < COLUMNS; x++)
      for(int y = 0; y < ROWS; y++)
         dcPanel.setRectTile(x, y, '@', Color.CYAN.getRGB(), Color.BLACK.getRGB());
      dcPanel.setSquareTile(20, 4, '!', Color.BLACK.getRGB(), Color.WHITE.getRGB());
      frame.setVisible(true);
      rng = new Random();
      timer = new javax.swing.Timer(16, this);
      timer.start();
   }
   
   private int randomColor()
   {
      return COLOR_ARR[rng.nextInt(COLOR_ARR.length)];
   }
   
   public void actionPerformed(ActionEvent ae)
   {
      dcPanel.setRectTile(cursorX, cursorY, rng.nextInt(256), randomColor(), randomColor());
      cursorX++;
      if(cursorX == COLUMNS)
      {
         cursorX = 0;
         cursorY++;
         if(cursorY == ROWS)
            cursorY = 0;
      }
      dcPanel.repaint();
   }
   
   public static void main(String[] args)
   {
      DCTest test = new DCTest();
   }
}