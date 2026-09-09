package com.loanms;
import com.loanms.patterns.factory.LoanTypeFactory; import org.junit.jupiter.api.Test; import static org.junit.jupiter.api.Assertions.*;
class InterestStrategyTest {
 @Test void personalSimpleInterest(){assertEquals(100.0,LoanTypeFactory.strategyFor("personal").calculateInterest(1000,10,12),0.001);}
 @Test void monthlyPaymentIsPositive(){assertTrue(LoanTypeFactory.strategyFor("business").monthlyPayment(10000,12,12)>0);}
}