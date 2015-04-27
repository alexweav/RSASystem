//Alexander Weaver
//Last update: 4-27-2015 2:33pm
package Euclidean;

class EquationStageOne extends EuclideanEquation {
    
    protected EquationStageOne(int nDividend, int nDivisor) {
        super.dividend = nDividend;
        super.divisor = nDivisor;
        super.quotient = nDividend/nDivisor;
        super.remainder = nDividend%nDivisor;
    }
}
