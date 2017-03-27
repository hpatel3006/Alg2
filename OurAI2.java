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
	public void getNextMove(final GameStateModule state){
		
		final GameStateModule game = state.copy();
		
		int k = 0;
		chosenMove = 0; //Initialize the move to be chosen to 
		ourPlayer = state.getActivePlayer();
		if(ourPlayer == 1)
			otherPlayer = 2;
		else
			otherPlayer = 1;
		while(!terminate)
		{
			if(terminate) break;
			//System.out.println("Depth in getNextMove " + depth);
			chosenMove = MinimaxDecision(game, ourPlayer, depth-depthsub);
			break;
		}
		System.out.println("chosenMove is  " + chosenMove);
		depth = depth - 2;
		depthsub = depthsub-2;
		
	}

	public int MinimaxDecision(final GameStateModule state, final int ourPlayer, final int depth4){
		//System.out.println("Depth in MinimaxDecision " + depth4);
		if(terminate){
			System.out.println("Terminate in MinimaxDecision " + count);
			return 1;
		}
		int makeplay = 0;
		int move = 0;
		int score = Integer.MIN_VALUE;
		for(int i = 0; i < state.getWidth(); ++i){
			if(state.canMakeMove(i)){
				//System.out.println(" MinimaxDecision " + i);
				state.makeMove(i);
				makeplay = MinValue(state, otherPlayer, depth4-1);
				//System.out.println("----- Fuckmeshitass Move is " + move + " score " + score + " makeplay = "+makeplay + " i = " + i);
				if(makeplay >= score) {
					move = i;
					score = makeplay;
				//	System.out.println("Fuckmeshitass Move is " + move + " score " + score + " makeplay = "+makeplay + " i = " + i);
				}
				state.unMakeMove();
			}
		}
		//count++;
		return move;
	}

	public int MaxValue(final GameStateModule state, final int ourPlayer, final int depth2){
		//System.out.println("Depth in Maxvalue " + depth2);

		if(terminate) {
			System.out.println("Terminate in Maxvalue " + count);
			return 0;
		}
		if ( state.isGameOver() || depth2 == 0) {
			//System.out.println("Depth = 0  in Max value");
			return GetEvaluationforourplayer(state);	
		}

		//System.out.println(" ");
		int v = Integer.MIN_VALUE;
			for(int i = 0; i < state.getWidth(); ++i){
					if(state.canMakeMove(i)){
		//				System.out.println(" ---------------------------------------------Maxvalue " + i);
						state.makeMove(i);
						int makeplay = MinValue(state, otherPlayer, depth2 - 1);
						if(makeplay >= v) {
							v = makeplay;
		//					System.out.println(" Make play in Maxvalue = " + makeplay + " V = " + v);
						}
						state.unMakeMove();
					}
			}

		//count ++;
		return v;
	}

	public int MinValue(final GameStateModule state, final int ourPlayer, final int depth3){
		//System.out.println("Depth in         Minvalue " + depth3);

		if(terminate){ 
			System.out.println("Terminate in MinValue " + count);
			return 0;
		}
		if ( state.isGameOver() || depth3 == 0 ) {
		//	System.out.println("Depth = 0  in min value");
			return GetEvaluationforotherplayer(state);
		}	
		//System.out.println(" ");
		int v = Integer.MAX_VALUE;
		for(int i = 0; i < state.getWidth(); ++i){
				if(state.canMakeMove(i)){
					//System.out.print(" MinValue " + i);
		//			System.out.println(" ---------------------------------------------Minvalue " + i);
					state.makeMove(i);
					int makeplay = MaxValue(state, ourPlayer, depth3 - 1);
					if(makeplay <= v) {
						v = makeplay;
		//				System.out.println(" Make play in         Minvalue = " + makeplay + " V = " + v);
					}
					state.unMakeMove();
				}
		}
		//count++;
		return v;
	}

	public int GetEvaluationforourplayer(final GameStateModule state) {
		/*int sum = 0;
		int [] HeightArray = new int[state.getHeight()];
		for(int i = 0; i < state.getWidth(); ++i){
			for(int j = 0; j < state.getHeight(); ++j){
				if(state.getAt(i, j) == 0)
					;
				else if (state.getAt(i, j) == ourPlayer)
					HeightArray[j]++;
				else
					HeightArray[j]--;
			}
		}
		for(int j = 0; j < state.getHeight(); ++j){
			sum = sum + HeightArray[j] * j;
		}
		return sum;*/
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
	            		//System.out.println("State get at " + state.getAt(i, j) + " " + i + " "  + j + " Evaluation Table = " + evaluationTable[i][j]);
	            		if (state.getAt(j, i) == ourPlayer)
	            			sum = sum + evaluationTable[i][j];
	            		else if(state.getAt(j, i) == otherPlayer)
	            			sum = sum - evaluationTable[i][j];
	            		else
	            			;
	            	}
	            }		
		//		System.out.println("evaluation function  " + sum + " utility = " + utility);
	            return utility + sum;

    }



    public int GetEvaluationforotherplayer(final GameStateModule state) {
   /* 	int sum = 0;
		int [] HeightArray = new int[state.getHeight()];
		for(int i = 0; i < state.getWidth(); ++i){
			for(int j = 0; j < state.getHeight(); ++j){
				if(state.getAt(i, j) == 0)
					;
				else if (state.getAt(i, j) == otherPlayer)
					HeightArray[j]++;
				else
					HeightArray[j]--;
			}
		}
		for(int j = 0; j < state.getHeight(); ++j){
			sum = sum + HeightArray[j] * j;
		}
		return sum;*/

		int[][] evaluationTable = {{3, 4, 5, 7, 5, 4, 3}, 
                                   {4, 6, 8, 10, 8, 6, 4},
                                   {5, 8, 11, 13, 11, 8, 5}, 
                                   {5, 8, 11, 13, 11, 8, 5},
                                   {4, 6, 8, 10, 8, 6, 4},
                                   {3, 4, 5, 7, 5, 4, 3}};

		        int utility = 128;
		        int sum = 0;
		        //System.out.println("Our player is------------------------------------------ " + ourPlayer);



		        for(int i=0; i<6; i++) {
	            	for(int j=0; j<7; j++) {
	            		if (state.getAt(j, i) == otherPlayer)
	            			sum = sum + evaluationTable[i][j];
	            		else if(state.getAt(j, i) == ourPlayer)
	            			sum = sum - evaluationTable[i][j];
	            		else
	            			;
	            	}
	            }		
	            return utility + sum;

    }
}	