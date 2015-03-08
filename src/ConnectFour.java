import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;

/*
 * Noah Baker CPE 102-03
 */

public class ConnectFour extends JComponent {
	private static int[][] board = new int[8][8];
	private static int GameState = 0;
	private static int PlayerTurn = -1;
	private static int tileWidth = 0;
	private static int tileDiff = 5;
	final static int EMPTY = 0;
	final static int BLACK = 1;
	final static int RED = -1;
	final static int NORTH = -1;
	final static int SOUTH = 1;
	final static int WEST = -1;
	final static int EAST = 1;
	
	public static void setup(int size) {
		tileWidth = size;
		int i,j;
		PlayerTurn = -1;
		//places initial pieces on board
		for(i=0;i<8;i++){ //Row
			for(j=0;j<8;j++){ //Column
					board[i][j] =EMPTY; 
				//for debugging
				System.out.print(board[i][j]);
			}
			//for debugging
			System.out.println();
		}
		//sets up the button configuration for ConnectFour
	}
	
	//checks to see if a move is possible, if so makes the move and returns 1
	public static int  attemptMove(String ij){
		int i,j;
		int movesLeft =0;
		boolean ValidMove = false;
		
		//parses the string
		int Row = Integer.parseInt(ij.substring(0, 1)) ;
		int Column = Integer.parseInt(ij.substring(1, 2)) ;
		//Checks for correct parsing
		
		System.out.println("Player Turn:" + PlayerTurn);
		System.out.println("Row: " +Row + " Column: " +  Column);

		//check if a piece exists in the clicked location
		if(board[Row][Column] == BLACK ||board[Row][Column] ==  RED){
			return GameState; // if so returns 
		}
				
		ValidMove = checkMove(Row,Column,true);
		
		if(checkWin(Row,Column) ==true){
			GameState = PlayerTurn;
		}
		
		if(ValidMove == true){ //checks to see if a valid move was made
			board[Row][Column] = PlayerTurn;
			PlayerTurn *= -1; //passes the turn to the next player
		}
		
		//for debugging
		/*
		for(i=0;i<8;i++){ //Row
			for(j=0;j<8;j++){ //Column
				System.out.print(board[j][i]);
			}
			//for debugging
			System.out.println();
		}
		*/
		/*
		//checks to see if next player has any possible moves, if not, gives the turn to the player that played
		for(i=0;i<8;i++){ //Row
			for(j=0;j<8;j++){ //Column
				if(board[i][j] == EMPTY){
					if(checkMove(i,j,false) == true){
						System.out.println("next player has possible moves");
						return GameState;
					}
				}
			}
		}
		
		System.out.println("no moves for player" + PlayerTurn);
		PlayerTurn *= -1; //passes the turn to the next player
		*/
		//checks if they have moves
		
		for(i=0;i<8;i++){ //Row
			for(j=0;j<8;j++){ //Column
				if(board[i][j] == EMPTY){
					movesLeft++;
				}
			}
		}
		if(movesLeft ==0){
			GameState = 2;
		}
		
		return GameState;
	}
	
	private static boolean checkMove(int Row, int Column, boolean makeMove){
		if((Column + SOUTH == 8) || (board[Row][Column + SOUTH] != 0)){
			   return true;
		}
		return false;
	}
	
	private static boolean checkWin(int Row, int Column){
		int i,j;
		boolean winMove = false;
		int inArow = 0;
		
		south:
		if((Column + SOUTH <= 7) && (board[Row][Column + SOUTH] == PlayerTurn)){
			inArow++;
			for(j=Column + SOUTH;j <= 7;j += SOUTH){	//Column
				if(board[Row][j] == PlayerTurn){ 		// looks for a piece to flip between
					inArow++;
				}
				else if(board[Row][j] == EMPTY){ 		//if an empty space is found none are flipped
					break south;
				}
				if(inArow == 4){
					winMove = true;
				}
			}
		}
		
		inArow =0;

		north:
		if((Column + NORTH >= 0) && (board[Row][Column + NORTH] == PlayerTurn)){
			inArow++;
			for(j=Column + NORTH;j >= 0;j += NORTH){ 	//Column
				if(board[Row][j] == PlayerTurn){ 		// looks for a piece to flip between
					inArow++;
				}
				else if(board[Row][j] == EMPTY){		//if an empty space is found none are flipped
					break north;
				}
				if(inArow == 4){
					winMove = true;
				}
			}
		}
		
		inArow =0;

		northwest:
		if((Column + NORTH >= 0) && (Row + WEST >= 0) && (board[Row+WEST][Column + NORTH] == PlayerTurn)){
			inArow++;
			i = Row+WEST;															//first spot to start checking the row
			for(j=Column + NORTH;j >= 0;j += NORTH){ 	//Column
				if(i<0){																	// if the row is outside the array: stop
					break northwest;
				}
				if(board[i][j] == PlayerTurn){ 		// looks for a piece to flip between
					inArow++;
				}
				else if(board[i][j] == EMPTY){		//if an empty space is found none are flipped
					break northwest;
				}
				i += WEST;																//Increments the row
				if(inArow == 4){
					winMove = true;
				}
			}
		}
		
		inArow =0;
		
		northeast:
		if((Column + NORTH >= 0) && (Row + EAST <= 7) && (board[Row+EAST][Column + NORTH] == PlayerTurn)){
			inArow++;
			i = Row+EAST;															//first spot to start checking the row
			for(j=Column + NORTH;j >= 0;j += NORTH){ 	//Column
				if(i>7){																	// if the row is outside the array: stop
					break northeast;
				}
				if(board[i][j] == PlayerTurn){ 		// looks for a piece to flip between
					inArow++;
				}
				else if(board[i][j] == EMPTY){		//if an empty space is found none are flipped
					break northeast;
				}
				i += EAST;																//Increments the row
				if(inArow == 4){
					winMove = true;
				}
			}
		}
			
		inArow =0;
		
		southwest:
		if((Column + SOUTH <= 7) && (Row + WEST >= 0) && (board[Row+WEST][Column + SOUTH] == PlayerTurn)){
			inArow++;
			i = Row+WEST;															//first spot to start checking the row
			for(j=Column + SOUTH;j <= 7;j += SOUTH){ 	//Column
				if(i<0){																	// if the row is outside the array: stop
					break southwest;
				}
				if(board[i][j] == PlayerTurn){ 		// looks for a piece to flip between
					inArow++;
				}
				else if(board[i][j] == EMPTY){		//if an empty space is found none are flipped
					break southwest;
				}
				i += WEST;																//Increments the row
				if(inArow == 4){
					winMove = true;
				}
			}
		}
			
		inArow =0;
		
		southeast:
		if((Column + SOUTH <= 7) && (Row + EAST <= 7) && (board[Row+EAST][Column + SOUTH] == PlayerTurn)){
			inArow++;
			i = Row+EAST;															//first spot to start checking the row
			for(j=Column + SOUTH;j <= 7;j += SOUTH){ 	//Column
				if(i>7){																	// if the row is outside the array: stop
					break southeast;
				}
				if(board[i][j] == PlayerTurn){ 		// looks for a piece to flip between
					inArow++;
				}
				else if(board[i][j] == EMPTY){		//if an empty space is found none are flipped
					break southeast;
				}
				i += EAST;																//Increments the row
				if(inArow == 4){
					winMove = true;
				}
			}
		}
		
		inArow =0;
		
		west:
		if((Row + WEST >= 0) && (board[Row + WEST][Column] ==PlayerTurn)){
			inArow++;
			for(i=Row + WEST;i >= 0;i += WEST){ 	//Row
				if(board[i][Column] == PlayerTurn){ 		// looks for a piece to flip between
					inArow++;
				}
				else if(board[i][Column] == EMPTY){		//if an empty space is found none are flipped
					break west;
				}
				if(inArow == 4){
					winMove = true;
				}
			}
		}
		
		inArow =0;
		
		east:
		if((Row + EAST <= 7) && (board[Row + EAST][Column] == PlayerTurn)){
			inArow++;
			for(i=Row + EAST;i <= 7;i += EAST){ 	//Row
				if(board[i][Column] == PlayerTurn){ 		// looks for a piece to flip between
					inArow++;
				}
				else if(board[i][Column] == EMPTY){		//if an empty space is found none are flipped
					break east;
				}
				if(inArow == 4){
					winMove = true;
				}
			}
		}
		
		return winMove;
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.setColor(Color.black);
		int blackPieces = 0;
		int REDPieces = 0;
		int i,j;
		for(i=0;i<8;i++){ //Row
			for(j=0;j<8;j++){ //Column	
				//g.drawString(Integer.toString(board[i][j]), 100+ i*50,100+  j*50);
				//
				if(board[i][j] == 1){
					g.setColor(Color.BLACK);;
					g.fillOval((getWidth()-tileWidth*8)/2+tileDiff+  i*tileWidth,tileWidth*2+ tileDiff+ j*tileWidth, tileWidth-2*tileDiff, tileWidth-2*tileDiff);
					blackPieces++;
				}
				else if(board[i][j] == -1){
					g.setColor(Color.RED);;
					g.fillOval((getWidth()-tileWidth*8)/2+tileDiff+  i*tileWidth,tileWidth*2+  tileDiff + j*tileWidth, tileWidth-2*tileDiff, tileWidth-2*tileDiff);
					REDPieces++;
				}
					//needs to draw each piece as a filled color circle   board[i][j] 
			} 
		}
		g.setColor(Color.black);
		g.setFont(new Font("Arial", Font.BOLD,tileWidth/2));
		
			if(GameState == RED ){
				g.drawString("Red Wins!",(getWidth()-tileWidth*8)/2+tileDiff,tileWidth);
			}
			else if(GameState == BLACK ){
				g.drawString("Black Wins!",(getWidth()-tileWidth*8)/2+tileDiff,tileWidth);
			}
			else if(GameState == 2 ){
				g.drawString("Tie!",(getWidth()-tileWidth*8)/2+tileDiff,tileWidth);
			}
			else if(PlayerTurn == -1){
				g.drawString("RED Turn",(getWidth()-tileWidth*8)/2+tileDiff,tileWidth);
			}
			else if(PlayerTurn == 1){
				g.drawString("Black Turn",(getWidth()-tileWidth*8)/2+tileDiff,tileWidth);
			}
			if(GameState != 0){
				PlayerTurn =0;
			}

	}
}
	