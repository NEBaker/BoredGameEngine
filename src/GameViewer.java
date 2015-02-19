/*
 * Noah Baker CPE 102-03
 */
import javax.swing.JFrame;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class GameViewer {
	
	public  GameViewer(String Game){
		JFrame gameBoard = new JFrame();
		gameBoard.setSize(500, 500);
	    gameBoard.setTitle(Game);
	    gameBoard.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	    gameBoard.setVisible(true);
	}

}
