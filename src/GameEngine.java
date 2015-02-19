import javax.swing.JFrame;

/*
 * Noah Baker CPE 102-03 
 */
 
public class GameEngine  {
	public static void main(String[] args){
		GameLauncher myUI = new GameLauncher();
		myUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myUI.setVisible(true);   
	}
} 
