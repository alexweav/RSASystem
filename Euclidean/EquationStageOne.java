//Alexander Weaver
//Last update: 4-27-2015 2:33pm
package Euclidean;

import java.math.BigInteger;

class EquationStageOne extends EuclideanEquation {
    
    protected EquationStageOne(int nDividend, int nDivisor) {
        super.dividend = nDividend;
        super.divisor = nDivisor;
        super.quotient = nDividend/nDivisor;
        super.remainder = nDividend%nDivisor;
    }
    
    protected EquationStageOne(BigInteger nDividend, BigInteger nDivisor) {
        super.bDividend = nDividend;
        super.bDivisor = nDivisor;
        super.bQuotient = nDividend.divide(nDivisor);
        super.bRemainder = nDividend.mod(nDivisor);
    }
}
