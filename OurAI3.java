import java.util.Random;
import java.util.*;
import java.awt.Point;
/*
 * @authors HardikPatel, Harshil SHah
 */
public class OurAI extends AIModule
{	
	public int depth =  7*6;
	public int count = 0;
	public int otherPlayer;
	int ourPlayer;
	public int depthsub = 7*6 - 7;
	boolean flag;


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
		for(int i = 0; i < state.getWidth(); ++i){
			if(state.canMakeMove(i)){
				state.makeMove(i);
				if(flag) makeplay = MaxValue(state, ourPlayer, depth4-1);
				else makeplay = MinValue(state, otherPlayer, depth4-1);
				if(makeplay >= score) {
					move = i;
					score = makeplay;
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
			return GetEvaluationforourplayer(state);	
		}

		//System.out.println(" ");
		int v = Integer.MIN_VALUE;
			for(int i = 0; i < state.getWidth(); ++i){
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
			return GetEvaluationforotherplayer(state);
		}	
		//System.out.println(" ");
		int v = Integer.MAX_VALUE;
		for(int i = 0; i < state.getWidth(); ++i){
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