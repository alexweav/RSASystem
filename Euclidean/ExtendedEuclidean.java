//Alexander Weaver
//Last update: 4-27-2015 2:33pm
package Euclidean;

import Util.Stack;

public class ExtendedEuclidean {
    
    public int getGCD(int number1, int number2) {
        Stack<EquationStageOne> stageOneStack = getStageOneStack(number1, number2);
        EquationStageOne top = stageOneStack.pop();
        return top.getRemainder();
    }
    
    private Stack getStageOneStack(int number1, int number2) {
        Stack<EquationStageOne> stageOneStack = new Stack<>();
        EquationStageOne currentEquation = new EquationStageOne(number1, number2);
        while(true) {
            if(currentEquation.getRemainder() == 0) {
                return stageOneStack;
            } else {
                stageOneStack.push(currentEquation);
                currentEquation = new EquationStageOne(currentEquation.getDivisor(), currentEquation.getRemainder());
            }
        }
    }
}
