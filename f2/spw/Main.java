//package f2.spw;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import java.awt.*;

import javax.swing.*;

import java.awt.event.*;

public class Main {
	public static void main(String[] args){
		JFrame frame = new JFrame("POOHLAND");
		JOptionPane.showMessageDialog(frame, "Welcome To POOHLAND");
		

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400, 650);
		frame.getContentPane().setLayout(new BorderLayout());


        JMenuBar mb = new JMenuBar();

       	JMenu start = new JMenu("Start");

		JMenu pause = new JMenu("Pause");
                
        JMenu exit = new JMenu("Exit");

		JMenuItem restart = new JMenuItem("Restart");

		JMenuItem pause2 = new JMenuItem("Pause");

        JMenuItem play = new JMenuItem("Play");

        JMenuItem exit2 = new JMenuItem("Close");
                 
		start.add(restart);
		exit.add(exit2);
		pause.add(pause2); 
		pause.add(play);
				
		mb.add(start);
		mb.add(pause);
		mb.add(exit);
		frame.setJMenuBar(mb);
		
		SpaceShip v = new SpaceShip(180, 550, 20, 20);
		GamePanel gp = new GamePanel();
		GameEngine engine = new GameEngine(gp, v);

		MenuListener menuListener = new MenuListener(exit2,restart,play,pause2,engine);
        exit2.addActionListener(menuListener);
		restart.addActionListener(menuListener);
		play.addActionListener(menuListener);
		pause2.addActionListener(menuListener);
		
		frame.addKeyListener(engine);
		frame.getContentPane().add(gp, BorderLayout.CENTER);
		frame.setVisible(true);
		
		engine.start();
		
	}
}
