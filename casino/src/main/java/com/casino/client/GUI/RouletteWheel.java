package com.casino.client.GUI;

import java.awt.*;
import javax.swing.*;

import java.awt.Image;
import java.net.URL;

public class RouletteWheel extends JComponent {
	private int score;
	boolean ballDrawn = false;
	static Image wheelImage;
	static Image wheelBoxImage;
	private Color ballColor = Color.gray;

	public RouletteWheel() {
		setToolTipText("Roulette Wheel");
		ImageIcon wheelIcon = null, wheelBoxIcon = null;
		try {
			wheelIcon = new ImageIcon("Images\\Wheel.png");
			wheelBoxIcon = new ImageIcon("Images\\WheelBox.png");
		} catch (Exception e) {
			e.printStackTrace();
		}
		wheelImage = wheelIcon.getImage();
		wheelBoxImage = wheelBoxIcon.getImage();

	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(wheelImage, 0, 0, this);
		g.drawImage(wheelBoxImage, 0, 0, this);
		if (ballDrawn) {
			g.setColor(ballColor);
		}
	}

	/**
	 * This method sets the boolean state of the ball.
	 * 
	 * @param b true if the ball is drawn
	 */
	public void setBallDrawn(boolean b) {
		ballDrawn = b;
		repaint();
	}

	public void setScore(int s) {
		if (s < 0)
			s = 0;
		else if (s > 37)
			s = 37;
		score = s;

	}

	/**
	 * This method "spins" the wheel by selecting the score at ranom.
	 * 
	 * @return the random score
	 */
	public int spin() {
		setScore((int) (38 * Math.random()));
		return score;
	}
}
