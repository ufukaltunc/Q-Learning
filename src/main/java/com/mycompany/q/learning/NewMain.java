
package com.mycompany.q.learning;

import java.awt.Color;
import java.io.IOException;
import javax.swing.JFrame;


public class NewMain {

    public static void main(String[] args) throws IOException {
        Player yeni = new Player();
        JFrame frame = new JFrame();
        frame.setBounds(0, 0, 1300, 900);
        frame.setBackground(Color.white);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(yeni);
    }
    
}
