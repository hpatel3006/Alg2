//Alpha Beta pruning
import java.util.Random;
import java.util.*;
import java.awt.Point;
/*
 * @authors HardikPatel, Harshil SHah
 */
public class AlphaBetaOurAI2 extends AIModule
{	
	public int depth =  7*6;
	public int count = 0;
	public int otherPlayer;
	public int ourPlayer;
	public int depthsub = 7*6 - 9;
	public boolean flag;
	final private int[] alphabetaarr = {3,2,4,1,5,0, 6};
	public int makemove;

	public void getNextMove(final GameStateModule state){

		final GameStateModule game = state.copy();
		int k = 0;
		//chosenMove = 0; //Initialize the move to be chosen to 
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
			AlphaBetaPrune(game, depth - depthsub, ourPlayer, Integer.MIN_VALUE, Integer.MAX_VALUE, 1); 
			//if(!terminate) chosenMove = makemove;
			chosenMove = makemove;
			break;
		}
		System.out.println("chosenMove is  " + chosenMove);
		depth = depth - 2;
		depthsub = depthsub-2;

		
	}


	public int AlphaBetaPrune(final GameStateModule game, final int funcdepth, final int player, int alpha, int beta, int flag){

		//System.out.println("Depth = " + funcdepth);
		if(terminate) {
			System.out.println("We terminate in AlphaBetaPrune");
			return 0;
		}
		if(funcdepth == 0 || game.isGameOver()) {	
			if(player == ourPlayer) return GetEvaluationforourplayer(game);
			else return GetEvaluationforotherplayer(game);
		} 
		
		//System.out.println("We get here");



		if(player == ourPlayer){
			int v = Integer.MIN_VALUE;
			for(int i : alphabetaarr){
				if(game.canMakeMove(i)){
					//System.out.println("In Max " + i);
					game.makeMove(i);
					v = Math.max(v, AlphaBetaPrune(game, funcdepth - 1, otherPlayer, alpha, beta, 0));
					if(alpha < v){
						alpha = v;
						//System.out.println("Makemove is  " + i);
						if(flag == 1){
							//System.out.println("--------------------------------------Makemove is  " + i);
							makemove = i;
						}
					}
					//System.out.println("Alpha in Max " + alpha + " v is " + v);
					game.unMakeMove();
					if(beta <= alpha) break;
				}
			}
			return alpha;
		}
		else{
			int v = Integer.MAX_VALUE;
			for (int i : alphabetaarr){
				if(game.canMakeMove(i)){
					//System.out.println("In Min " + i);
					game.makeMove(i);
					v = Math.min(v, AlphaBetaPrune(game, funcdepth - 1, ourPlayer, alpha, beta, 0));
					if(beta > v) beta = v;
					//System.out.println("Beta in Min " + beta + " v is " + v);
					game.unMakeMove();
					if(beta <= alpha) break;
				}
			}
			return beta;
		}	
	}


	public int GetEvaluationforourplayer(final GameStateModule state) {

	        int sum = 0;
	        int inrow = 0, india1 = 0, india2 = 0, incol = 0;
       		for(int i = 0; i < 6; i++){
       			for(int j=0; j < 7; j++){
       				if(state.getAt(j,i) == ourPlayer){

       				inrow = count(state, ourPlayer, i, j, 1, 0);
       				incol = count(state, ourPlayer, i, j, 0, 1);
       				india1 = count(state, ourPlayer, i, j, 1, -1);
       				india2 = count(state, ourPlayer, i, j, 1, 1);
       				}
       			}

       		sum = sum + inrow + incol + india1 + india2;
       		
       		}

       //System.out.println("---sum = " + sum);
			return sum;
    }



    public int GetEvaluationforotherplayer(final GameStateModule state) {

    	int sum = 0;
		int inrow = 0, india1 = 0, india2 = 0, incol = 0;
       	for(int i = 0; i < 6; i++){
       		for(int j=0; j < 7; j++){
       			if(state.getAt(j,i) == otherPlayer){
       				inrow = count(state, otherPlayer, i, j, 1, 0);
       				incol = count(state, otherPlayer, i, j, 0, 1);
       				india1 = count(state, otherPlayer, i, j, 1, -1);
       				india2 = count(state, otherPlayer, i, j, 1, 1);
       			}
       		}

       	sum = (sum + inrow + incol + india1 + india2);
       }
       //System.out.println("sum = " + sum);
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


