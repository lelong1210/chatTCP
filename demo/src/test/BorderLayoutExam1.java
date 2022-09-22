package test;
import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
 
public class BorderLayoutExam1 {
    JFrame frame;
 
    BorderLayoutExam1() {
        frame = new JFrame();
 
        JButton b1 = new JButton("NORTH");
        JButton b2 = new JButton("SOUTH");
        JButton b3 = new JButton("EAST");
        JButton b4 = new JButton("WEST");
        JButton b5 = new JButton("CENTER");
 
//        frame.add(b1, BorderLayout.NORTH);
//        frame.add(b2, BorderLayout.SOUTH);
//        frame.add(b3, BorderLayout.EAST);
        frame.add(b4, BorderLayout.WEST);
        frame.add(b5, BorderLayout.CENTER);
 
        frame.setSize(800, 300);
        frame.setVisible(true);
        frame.setTitle("Ví dụ BorderLayout trong Java Swing");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
 
    public static void main(String[] args) {
        new BorderLayoutExam1();
    }
}