package code.engine;

import java.awt.*;
import java.awt.image.*;
import javax.swing.*;

public class Window {
	private JFrame f;
	private BufferedImage buffImg;
	private Canvas canvas;
	private BufferStrategy buffStrat;
	private Graphics graph;

	//#region setters y getters
	public Canvas getCanvas() {
		return canvas;
	}

	public BufferedImage getImage() {
		return buffImg;
	}
	//#endregion

	public Window(GameEngine ge) {
		buffImg = new BufferedImage(ge.getWidth(), ge.getHeight(), BufferedImage.TYPE_INT_RGB);
		
		canvas = new Canvas();
		Dimension s = new Dimension((int) (ge.getWidth()* ge.getScale()), (int) (ge.getHeight()*ge.getScale()));
		canvas.setPreferredSize(s);
		canvas.setMaximumSize(s);
		canvas.setMinimumSize(s);

		f = new JFrame(ge.getTitle());
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setLayout(new BorderLayout());
		f.add(canvas, BorderLayout.CENTER);
		f.pack();
		f.setLocationRelativeTo(null);
		f.setResizable(false);
		f.setVisible(true);

		canvas.createBufferStrategy(2);
		buffStrat = canvas.getBufferStrategy();
		graph = buffStrat.getDrawGraphics();
	}

	public void update() {
		graph.drawImage(buffImg, 0, 0, canvas.getWidth(), canvas.getHeight(), null);
		buffStrat.show();
	}

}
