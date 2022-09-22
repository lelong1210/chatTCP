package test;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
 
public class FlowLayoutExam1 {
    JFrame frame;
 
    FlowLayoutExam1() {
        frame = new JFrame();
 
        JButton b1 = new JButton("1");
        JButton b2 = new JButton("2");
        JButton b3 = new JButton("3");
        JButton b4 = new JButton("4");
        JButton b5 = new JButton("5");
 
        frame.add(b1);
        frame.add(b2);
        frame.add(b3);
        frame.add(b4);
        frame.add(b5);
 
        // thiet lap flow layout la can chinh trai
        frame.setLayout(new FlowLayout(FlowLayout.LEADING));
 
        frame.setSize(400, 250);
        frame.setVisible(true);
        frame.setTitle("Ví dụ FlowLayout trong Java Swing");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
 
    public static void main(String[] args) {
        new FlowLayoutExam1();
    }
}