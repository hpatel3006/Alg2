import java.util.Random;
import java.util.*;
import java.awt.Point;
import javax.swing.*;
/*
 * @authors HardikPatel, Harshil SHah
 */
public class OurAI4 extends AIModule
{	
	public int depth =  7*6;
	public int count = 0;
	public int otherPlayer;
	int ourPlayer;
	public int depthsub = 7*6 - 6;
	boolean flag;
    final public int [] arr = {3,2,4,1,5,0,6};
    int[][] evaluationTable = {{3, 4, 5, 7, 5, 4, 3}, 
                               {4, 6, 8, 10, 8, 6, 4},
                               {5, 8, 11, 20, 11, 8, 5}, 
                               {5, 8, 11, 13, 11, 8, 5},
                               {4, 6, 8, 10, 8, 6, 4},
                               {3, 4, 5, 7, 5, 4, 3}};


	public void getNextMove(final GameStateModule state){
		final GameStateModule game = state.copy();		
		int k = 0;
		chosenMove = 0; //Initialize the move to be chosen to 
		ourPlayer = state.getActivePlayer();
		if(ourPlayer == 1){
			flag = true;
			otherPlayer = 2;
		}
		else{
			flag = false;
			otherPlayer = 1;
		}
		while(!terminate)
		{
			if(terminate) break;
			chosenMove = MinimaxDecision(game, ourPlayer, depth-depthsub);
			break;
		}
		System.out.println("chosenMove is  " + chosenMove);
		depth = depth - 2;
		depthsub = depthsub-2;
		}

	public int MinimaxDecision(final GameStateModule state, final int ourPlayer, final int depth4){
		if(terminate){
			System.out.println("Terminate in MinimaxDecision " + count);
			return 1;
		}
		int makeplay = 0;
		int move = 0;
		int score = Integer.MIN_VALUE;
		//for(int i = 0; i < state.getWidth(); ++i){
		for(int i = 0; i < state.getWidth(); i++){
			System.out.println(" I in For Loop--- " + i);
			if(state.canMakeMove(i)){
				System.out.println(" I in For Loop--- " + i);
				state.makeMove(i);
				if(flag) makeplay = MaxValue(state, ourPlayer, depth4-1);
				else makeplay = MinValue(state, otherPlayer, depth4-1);
				System.out.println(" MOVE---- " + move + " MAKEPLAY---- " + makeplay + " SCORE---- " + score);
				if(makeplay >= score) {
					move = i;
					score = makeplay;
					System.out.println(" ------------------MOVE---- " + move + " MAKEPLAY---- " + makeplay + " SCORE---- " + score);
				}
				state.unMakeMove();
			}
		}
		return move;
	}

	public int MaxValue(final GameStateModule state, final int ourPlayer, final int depth2){
		if(terminate) {
			System.out.println("Terminate in Maxvalue " + count);
			return 0;
		}
		if ( state.isGameOver() || depth2 == 0) {
			return GetEvaluationforourplayer(state) - GetEvaluationforotherplayer(state);	
		}

		int v = Integer.MIN_VALUE;
			for(int i = 0; i < state.getWidth(); i++){
					if(state.canMakeMove(i)){
						state.makeMove(i);
						int makeplay = MinValue(state, otherPlayer, depth2 - 1);
						if(makeplay >= v) {
							v = makeplay;
						}
						state.unMakeMove();
					}
			}
		return v;
	}

	public int MinValue(final GameStateModule state, final int ourPlayer, final int depth3){
		if(terminate){ 
			System.out.println("Terminate in MinValue " + count);
			return 0;
		}
		if ( state.isGameOver() || depth3 == 0 ) {
			return GetEvaluationforotherplayer(state) - GetEvaluationforourplayer(state);
		}	
		
		int v = Integer.MAX_VALUE;
		for(int i = 0; i < state.getWidth(); i++){
				if(state.canMakeMove(i)){
					state.makeMove(i);
					int makeplay = MaxValue(state, ourPlayer, depth3 - 1);
					if(makeplay <= v) {
						v = makeplay;
					}
					state.unMakeMove();
				}
		}
		return v;
	}

	public int GetEvaluationforourplayer(final GameStateModule state) {
	        int sum = 0;
	        int inrow = 0, india1 = 0, india2 = 0, incol = 0, evaluationvalue = 0;
       		for(int i = 0; i < 6; i++){
       			for(int j=0; j < 7; j++){
       				if(state.getAt(j,i) == ourPlayer){
       				inrow = count(state, ourPlayer, i, j, 1, 0);
       				incol = count(state, ourPlayer, i, j, 0, 1);
       				india1 = count(state, ourPlayer, i, j, 1, -1);
       				india2 = count(state, ourPlayer, i, j, 1, 1);
       				evaluationvalue = evaluationTable[i][j];
       				}
       			sum = sum + evaluationvalue * (inrow + incol + india1 + india2);
       			}
       		} 	 
			return sum;
    }

    public int GetEvaluationforotherplayer(final GameStateModule state) {

    	int sum = 0;
		int inrow = 0, india1 = 0, india2 = 0, incol = 0, evaluationvalue = 0;
       	for(int i = 0; i < 6; i++){
       		for(int j=0; j < 7; j++){
       			if(state.getAt(j,i) == otherPlayer){
       				inrow = count(state, otherPlayer, i, j, 1, 0);
       				incol = count(state, otherPlayer, i, j, 0, 1);
       				india1 = count(state, otherPlayer, i, j, 1, -1);
       				india2 = count(state, otherPlayer, i, j, 1, 1);
       				evaluationvalue = evaluationTable[i][j];
       			}
       			sum = (sum + evaluationvalue * (inrow + incol + india1 + india2));
       		}
       }
       return sum;
    }

    public int count(final GameStateModule game, int player, int row, int col, int dirX, int dirY) {
          int ct = 1;  // Number of pieces in a row belonging to the player.
          int r, c;    // A row and column to be examined
          r = row + dirX;  // Look at square in specified direction.
          c = col + dirY;
          while ( r >= 0 && r < 6 && c >= 0 && c < 7 && game.getAt(c, r) == player ) {
                  // Square is on the board and contains one of the players's pieces.
             ct++;
             r += dirX;  // Go on to next square in this direction.
             c += dirY;
          }                 
          r = row - dirX;  // Look in the opposite direction.
          c = col - dirY;
          while ( r >= 0 && r < 6 && c >= 7 && c < 13 && game.getAt(c, r) == player ) {
                  // Square is on the board and contains one of the players's pieces.
             ct++;
             r -= dirX;   // Go on to next square in this direction.
             c -= dirY;
          }
    	int sum = 0;
    	if(ct == 1) {
    		sum = sum + (int)Math.pow(2, 1);
    	}
    	else if(ct == 2) {
    		sum = sum + (int)Math.pow(2, 5);
    	}
    	else if(ct == 3) 
    		sum = sum + (int)Math.pow(2, 8);
    	else if(ct == 4) 
    		sum = sum + Integer.MAX_VALUE;
    	//System.out.println("Sum  = " + sum + " count = "+ ct);
        return sum;

    
       }

}	
