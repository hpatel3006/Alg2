Hardik Patel
Harshil Shah
ECS 170 Project 2

Part 1 (20 points): 

Evaluation Function: 
We wrote our minimax and alpha-beta pruning code with two evaluation functions. 
1. For our first evaluation function, for a board game state, we try to count the number of ways ourPlayer can win and subtract that by the number of ways the otherPlayer can win, and we return that number. However, since that had a problem with weights, we implemented an evaluation table that had those numbers hardcoded, for example, ar state.getAt(0, 0) there are three ways for a player to win, horizontal 4, verical 4 and diagoanl 4. However, with this function, the minimax algorithm didn't necessarily block a 3 in a row by an opponent. Hence, we decided to move to the second evaluation function. 
2. For our second evaluation function, be basically traverse through the graph and everywhere we find a piece belonging to us we keep a count of it and try to creat a 2 and 3 and 4 in a row. We give those states a weight for 2 for one piece by itself, 2^5 for 2 in a row, 2^10 for 3 in a row and Integer.MAX_Value for 4 in a row. This way if we were to create 3 or 4 in a row in our algorithm, we were most likely to take that path. However, due to time constraints we were unable to fix the algorithm entirelya and it do






a) A detailed motivation on why you believe your evaluation function is reasonable (one paragraph)
Ans: We tried to come up with the function by trying different arbitrary boards and calulating the values that this function may return. At first, we created a state, and in that state we counted the number of ways ourPlayer is able to win vs the number of ways otherPlayer is able to win on that board. For this we, look at a coin and try connecting four places to thaat coin provided that it does not jump over the other player or go aroungd an edge. Once we have the two numbers we subtract the numbers and give the highest for max and lowest for min
b) Your evaluation function as a numerical expression with definitions of all variables.
Ans: 
c) One worked example showing a board state and your evaluation function score.
Ans: 