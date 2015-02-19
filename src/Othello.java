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

public class Othello extends JComponent {
	private static int[][] board = new int[8][8];
	private static int GameState = -1;
	private static int PlayerTurn = -1;
	private static int tileWidth = 0;
	private static int tileDiff = 5;
	final static int EMPTY = 0;
	final static int BLACK = 1;
	final static int WHITE = -1;
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
				if ((j ==3 && i ==3) ||(j ==4 && i ==4)){
					board[i][j] =BLACK; 
				}
				else if((j ==3 && i ==4) ||(j ==4 && i ==3)){
					board[i][j] =WHITE; 
				}
				else
					board[i][j] =EMPTY; 
				//for debugging
				System.out.print(board[i][j]);
			}
			//for debugging
			System.out.println();
		}
		//sets up the button configuration for othello
	}
	
	//checks to see if a move is possible, if so makes the move and returns 1
	public static int  attemptMove(String ij){
		int i,j;
		boolean validMove = false;
		
		//parses the string
		int Row = Integer.parseInt(ij.substring(0, 1)) ;
		int Column = Integer.parseInt(ij.substring(1, 2)) ;
		//Checks for correct parsing
		
		System.out.println("Player Turn:" + PlayerTurn);
		System.out.println("Row: " +Row + " Column: " +  Column);

		//check if a piece exists in the clicked location
		if(board[Row][Column] == BLACK ||board[Row][Column] ==  WHITE){
			return GameState; // if so returns 
		}
				
		validMove = checkMove(Row,Column,true);
	
	if(validMove == true){ //checks to see if a valid move was made
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
		
		//checks if they have moves
		for(i=0;i<8;i++){ //Row
			for(j=0;j<8;j++){ //Column
				if(board[i][j] == EMPTY){
					if(checkMove(i,j,false) == true){
						System.out.println(" player has possible moves");
						return GameState;
					}
				}
			}
		}
		GameState = 0;
		
		
		
	 
		return GameState;
	}
	
	private static boolean checkMove(int Row, int Column, boolean makeMove){
		int i,j;
		boolean validMove = false;
		//checks if the move is valid and makes it if it is
		south:
		if((Column + SOUTH <= 7) && (board[Row][Column + SOUTH] == -1*PlayerTurn)){
			for(j=Column + SOUTH;j <= 7;j += SOUTH){	//Column
				if(board[Row][j] == PlayerTurn){ 		// looks for a piece to flip between
					if(makeMove ==true){
						for(j=j; j >Column; j -= SOUTH){ 	//flip pieces between player color and placed piece
							board[Row][j] = PlayerTurn;
						}
					}
					validMove = true;
					break south;
				}
				else if(board[Row][j] == EMPTY){ 		//if an empty space is found none are flipped
					break south;
				}
			}
		}
		
		north:
		if((Column + NORTH >= 0) && (board[Row][Column + NORTH] == -1*PlayerTurn)){
			for(j=Column + NORTH;j >= 0;j += NORTH){ 	//Column
				if(board[Row][j] == PlayerTurn){ 		// looks for a piece to flip between
					if(makeMove ==true){
						for(j=j; j <Column; j -= NORTH){	//flip pieces between player color and placed piece
							board[Row][j] = PlayerTurn;
							}
						}
					validMove = true;
					break north;
				}
				else if(board[Row][j] == EMPTY){		//if an empty space is found none are flipped
					break north;
				}
			}
		}
		
		northwest:
		if((Column + NORTH >= 0) && (Row + WEST >= 0) && (board[Row+WEST][Column + NORTH] == -1*PlayerTurn)){
			i = Row+WEST;															//first spot to start checking the row
			for(j=Column + NORTH;j >= 0;j += NORTH){ 	//Column
				if(i<0){																	// if the row is outside the array: stop
					break northwest;
				}
				if(board[i][j] == PlayerTurn){ 		// looks for a piece to flip between
					if(makeMove ==true){
						for(j=j; j <Column; j -= NORTH){	//flip pieces between player color and placed piece
							board[i][j] = PlayerTurn;
							i-=WEST;
						}
					}
					validMove = true;
					break northwest;
				}
				else if(board[i][j] == EMPTY){		//if an empty space is found none are flipped
					break northwest;
				}
				i += WEST;																//Increments the row
			}
		}
		
		northeast:
		if((Column + NORTH >= 0) && (Row + EAST <= 7) && (board[Row+EAST][Column + NORTH] == -1*PlayerTurn)){
			i = Row+EAST;															//first spot to start checking the row
			for(j=Column + NORTH;j >= 0;j += NORTH){ 	//Column
				if(i>7){																	// if the row is outside the array: stop
					break northeast;
				}
				if(board[i][j] == PlayerTurn){ 		// looks for a piece to flip between
					if(makeMove ==true){
						for(j=j; j <Column; j -= NORTH){	//flip pieces between player color and placed piece
							board[i][j] = PlayerTurn;
							i-=EAST;
						}
					}
					validMove = true;
					break northeast;
				}
				else if(board[i][j] == EMPTY){		//if an empty space is found none are flipped
					break northeast;
				}
				i += EAST;																//Increments the row
			}
		}
			
		southwest:
		if((Column + SOUTH <= 7) && (Row + WEST >= 0) && (board[Row+WEST][Column + SOUTH] == -1*PlayerTurn)){
			i = Row+WEST;															//first spot to start checking the row
			for(j=Column + SOUTH;j <= 7;j += SOUTH){ 	//Column
				if(i<0){																	// if the row is outside the array: stop
					break southwest;
				}
				if(board[i][j] == PlayerTurn){ 		// looks for a piece to flip between
					if(makeMove ==true){
						for(j=j; j >Column; j -= SOUTH){	//flip pieces between player color and placed piece
							board[i][j] = PlayerTurn;
							i-=WEST;
						}
					}
					validMove = true;
					break southwest;
				}
				else if(board[i][j] == EMPTY){		//if an empty space is found none are flipped
					break southwest;
				}
				i += WEST;																//Increments the row
			}
		}
			
		southeast:
		if((Column + SOUTH <= 7) && (Row + EAST <= 7) && (board[Row+EAST][Column + SOUTH] == -1*PlayerTurn)){
			i = Row+EAST;															//first spot to start checking the row
			for(j=Column + SOUTH;j <= 7;j += SOUTH){ 	//Column
				if(i>7){																	// if the row is outside the array: stop
					break southeast;
				}
				if(board[i][j] == PlayerTurn){ 		// looks for a piece to flip between
					if(makeMove ==true){
						for(j=j; j >Column; j -= SOUTH){	//flip pieces between player color and placed piece
							board[i][j] = PlayerTurn;
							i-=EAST;
						}
					}
					validMove = true;
					break southeast;
				}
				else if(board[i][j] == EMPTY){		//if an empty space is found none are flipped
					break southeast;
				}
				i += EAST;																//Increments the row
			}
		}
		
		west:
		if((Row + WEST >= 0) && (board[Row + WEST][Column] == -1*PlayerTurn)){
			for(i=Row + WEST;i >= 0;i += WEST){ 	//Row
				if(board[i][Column] == PlayerTurn){ 		// looks for a piece to flip between
					if(makeMove ==true){
						for(i=i; i <Row; i -= WEST){	//flip pieces between player color and placed piece
							board[i][Column] = PlayerTurn;
						}
					}
					validMove = true;
					break west;
				}
				else if(board[i][Column] == EMPTY){		//if an empty space is found none are flipped
					break west;
				}
			}
		}
		
		east:
		if((Row + EAST <= 7) && (board[Row + EAST][Column] == -1*PlayerTurn)){
			for(i=Row + EAST;i <= 7;i += EAST){ 	//Row
				if(board[i][Column] == PlayerTurn){ 		// looks for a piece to flip between
					if(makeMove ==true){
						for(i=i; i >Row; i -= EAST){	//flip pieces between player color and placed piece
							board[i][Column] = PlayerTurn;
						}
					}
					validMove = true;
					break east;
				}
				else if(board[i][Column] == EMPTY){		//if an empty space is found none are flipped
					break east;
				}
			}
		}
		
		return validMove;
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.setColor(Color.black);
		int blackPieces = 0;
		int whitePieces = 0;
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
					g.setColor(Color.WHITE);;
					g.fillOval((getWidth()-tileWidth*8)/2+tileDiff+  i*tileWidth,tileWidth*2+  tileDiff + j*tileWidth, tileWidth-2*tileDiff, tileWidth-2*tileDiff);
					whitePieces++;
				}
					//needs to draw each piece as a filled color circle   board[i][j] 
			} 
		}
		g.setColor(Color.black);
		g.setFont(new Font("Arial", Font.BOLD,tileWidth/2));
		
		if(blackPieces ==0){
			g.drawString("White Wins!",(getWidth()-tileWidth*8)/2+tileDiff,tileWidth);
		}
		else if(whitePieces == 0){
			g.drawString("Black Wins!",(getWidth()-tileWidth*8)/2+tileDiff,tileWidth);
		}
		else if(blackPieces + whitePieces == 64 || GameState == 0){
			if(blackPieces > whitePieces){
				g.drawString("Black Wins!",(getWidth()-tileWidth*8)/2+tileDiff,tileWidth);
			}
			else{
				g.drawString("White Wins!",(getWidth()-tileWidth*8)/2+tileDiff,tileWidth);
			}
		}
		else{
			if(PlayerTurn == -1){
				g.drawString("White Turn",(getWidth()-tileWidth*8)/2+tileDiff,tileWidth);
			}
			else if(PlayerTurn == 1){
				g.drawString("Black Turn",(getWidth()-tileWidth*8)/2+tileDiff,tileWidth);
			}
		}

	}
}
	