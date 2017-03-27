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

/*
first lets have the weight of the given condition.

weights for both our player and other playwer will be 
		for 	1 in rows 2^1 
	    for 	2 in rows 2^2
	    for 	3 in rows 2^3
	    for  	4 in rows 2^5

eveluation of next state will be
for ( all columns 0 to 6){
	statevalue = ourplayer( #of1inrows * weight of 1 inrows  +   #of2inrows * weight of 2 inrows   +   #of3inrows * weight of 3 inrows   +   #of4inrows * weight of 4 inrows)

	-  otherplayers ( #of1inrows * weight of 1 inrows  +   #of2inrows * weight of 2 inrows   +   #of3inrows * weight of 3 inrows   +   #of4inrows * weight of 4 inrows)				

} 


return SUM;







*/

// Weight of different numbers of coins in a row
/*
				final int wtoneInRows = 2 ^ 1;
				final int wttwoInRows = 2 ^ 3;
				final int wtthreeInRows = 2 ^ 5;
				final int wtfourInRows = 2 ^ 7;
		        int utility = 128;
		        int sum = 0;
		        int[] statevalue = {};

		        for(int i = 0; i <= 6; i++){
		        	statevalue[i] = ( wtoneInRows + wttwoInRows + wtthreeInRows + wtfourInRows	) - (	wtoneInRows + wttwoInRows + wtthreeInRows + wtfourInRows );

		        }*/
				/*long movesonbard;

				byte height[] = new byte[7];
				movesonbard = 1L << height[4]++;
*/






				
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