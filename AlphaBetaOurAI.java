//Alpha Beta pruning
import java.util.Random;
import java.util.*;
import java.awt.Point;
/*
 * @authors HardikPatel, Harshil SHah
 */
public class AlphaBetaOurAI extends AIModule
{	
	public int depth =  7*6;
	public int count = 0;
	public int otherPlayer;
	public int ourPlayer;
	public int depthsub = 7*6 - 10;
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
		int[][] evaluationTable = {{3, 4, 5, 7, 5, 4, 3}, 
                                   {4, 6, 8, 10, 8, 6, 4},
                                   {5, 8, 11, 13, 11, 8, 5}, 
                                   {5, 8, 11, 13, 11, 8, 5},
                                   {4, 6, 8, 10, 8, 6, 4},
                                   {3, 4, 5, 7, 5, 4, 3}};
		        int utility = 128;
		        int sum = 0;
		        for(int i=0; i<6; i++) {
	            	for(int j=0; j<7; j++) {
	            	    if (state.getAt(j, i) == ourPlayer)
	            			sum = sum + evaluationTable[i][j];
	            		else if(state.getAt(j, i) == otherPlayer)
	            			sum = sum - evaluationTable[i][j];
	            	}
	            }		
	            return (utility + sum);

    }



    public int GetEvaluationforotherplayer(final GameStateModule state) {
		int[][] evaluationTable = {{3, 4, 5, 7, 5, 4, 3}, 
                                   {4, 6, 8, 10, 8, 6, 4},
                                   {5, 8, 11, 13, 11, 8, 5}, 
                                   {5, 8, 11, 13, 11, 8, 5},
                                   {4, 6, 8, 10, 8, 6, 4},
                                   {3, 4, 5, 7, 5, 4, 3}};
				
		        int utility = 128;
		        int sum = 0;
		        for(int i=0; i<6; i++) {
	            	for(int j=0; j<7; j++) {
	            		if (state.getAt(j, i) == otherPlayer)
	            			sum = sum + evaluationTable[i][j];
	            		else if(state.getAt(j, i) == ourPlayer)
	            			sum = sum - evaluationTable[i][j];
	            	}
	            }		
	            return (utility + sum);

    }
}	

