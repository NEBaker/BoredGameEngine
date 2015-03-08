/*
 * Noah Baker CPE 102-03
 */
import java.awt.Color;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;

public class GameLauncher extends JFrame implements ActionListener {
private JButton tiles[][] = new JButton[10][10];
private String GameType = "";

	public GameLauncher() {
		
	      //setup loader menu
	      JMenuBar  menuBar = new JMenuBar();
	      JMenu file  = new JMenu("Load");
	      JMenuItem OthelloItem = new JMenuItem("Othello");
	      JMenuItem Connect4Item = new JMenuItem("Connect Four");
	      //add listener for click events
	      OthelloItem.addActionListener(this);
	      Connect4Item.addActionListener(this);
	      
	      file.add(OthelloItem);
	      file.add(Connect4Item);
	      menuBar.add(file);
	      setJMenuBar(menuBar);
	      
	      setTitle("Selection Menu");
	      setSize(600, 600);
	      
	}
	
	public void actionPerformed(ActionEvent X){
		System.out.println("Action Command:" + X.getActionCommand());
		// menu click logic.
		// figure out which item was clicked
		// depending on item, setup different board
		 
		// 
		//file menu actions first
		//each menu item MUST have a else if statement
		if(X.getActionCommand()=="Othello"){
		    setTitle("Othello");
		    getContentPane().removeAll();
		    setResizable(false);
		    setVisible(true);	
			repaint();
		    int tileWidth = getContentPane().getHeight()/12;
			Othello game = new Othello();
			Othello.setup(tileWidth);
			getContentPane().removeAll();
			getContentPane().setBackground(Color.green.darker().darker().darker());
			
			//sets up the button configuration for Othello
			System.out.println(tileWidth);
			for(int i=0;i<8;i++){ //Row
				for(int j=0;j<8;j++){ //Column	
					tiles[i][j] = new JButton(Integer.toString(i)+Integer.toString(j));
					tiles[i][j].setLocation((getContentPane().getWidth()-tileWidth*8)/2+ i*tileWidth,tileWidth*2+ j*tileWidth);
					tiles[i][j].setSize(tileWidth,tileWidth);
					tiles[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK,2));
					tiles[i][j].setActionCommand(Integer.toString(i)+Integer.toString(j));
					tiles[i][j].addActionListener(this);
					tiles[i][j].setText("");
					tiles[i][j].setOpaque(false);
					tiles[i][j].setContentAreaFilled(false);
					add(tiles[i][j]);
				}
			}
			
			add(game);
			setVisible(true);	
			repaint();
			GameType = "Othello";
		}
		else if(X.getActionCommand()=="Connect Four"){
			 setTitle("Connect Four");
			    getContentPane().removeAll();
			    setResizable(false);
			    setVisible(true);	
				repaint();
			    int tileWidth = getContentPane().getHeight()/12;
				ConnectFour game = new ConnectFour();
				ConnectFour.setup(tileWidth);
				getContentPane().removeAll();
				getContentPane().setBackground(Color.blue);
				
				//sets up the button configuration for Othello
				System.out.println(tileWidth);
				for(int i=0;i<8;i++){ //Row
					for(int j=0;j<8;j++){ //Column	
						tiles[i][j] = new JButton(Integer.toString(i)+Integer.toString(j));
						tiles[i][j].setLocation((getContentPane().getWidth()-tileWidth*8)/2+ i*tileWidth,tileWidth*2+ j*tileWidth);
						tiles[i][j].setSize(tileWidth,tileWidth);
						tiles[i][j].setBorder(BorderFactory.createLineBorder(Color.yellow,2));
						tiles[i][j].setActionCommand(Integer.toString(i)+Integer.toString(j));
						tiles[i][j].addActionListener(this);
						tiles[i][j].setText("");
						tiles[i][j].setOpaque(false);
						tiles[i][j].setContentAreaFilled(false);
						add(tiles[i][j]);
					}
				}
				
				add(game);
				setVisible(true);	
				repaint();
				GameType = "Connect Four";
		}
		
		
		//Game tile actions...
		//
		else if(GameType == "Othello"){
			Othello.attemptMove(X.getActionCommand());
			setVisible(true);
			repaint();
		}
		else if(GameType == "Connect Four"){
			ConnectFour.attemptMove(X.getActionCommand());
			setVisible(true);
			repaint();
		}
	}
}
