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
   
   public DCTest()
   {
      frame = new JFrame();
      frame.setSize(1000, 800);
      frame.setLayout(new GridLayout(1, 1));
      dcPanel = new DCPanel(48, 160);
      dcPanel.setPalette("WSFont_8x16.png", "WSFont_16x16.png", 16, 16);
      dcPanel.setBackground(Color.BLACK);
      frame.add(dcPanel);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      for(int x = 0; x < 160; x++)
      for(int y = 0; y < 48; y++)
         dcPanel.setRectTile(x, y, '@', Color.CYAN.getRGB(), Color.BLACK.getRGB());
      frame.setVisible(true);
      timer = new javax.swing.Timer(1000, this);
      timer.start();
   }
   
   public void actionPerformed(ActionEvent ae)
   {
      dcPanel.repaint();
   }
   
   public static void main(String[] args)
   {
      DCTest test = new DCTest();
   }
}