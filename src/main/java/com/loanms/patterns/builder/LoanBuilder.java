package com.loanms.patterns.builder;
import com.loanms.model.Loan;
import java.time.LocalDateTime;
public class LoanBuilder {
 private int id, customerId, termMonths; private Integer officerId; private String loanType="personal", status="Pending"; private double principal, annualInterestRate; private LocalDateTime createdAt=LocalDateTime.now();
 public LoanBuilder id(int v){id=v;return this;} public LoanBuilder customerId(int v){customerId=v;return this;} public LoanBuilder officerId(Integer v){officerId=v;return this;} public LoanBuilder loanType(String v){loanType=v;return this;} public LoanBuilder principal(double v){principal=v;return this;} public LoanBuilder annualInterestRate(double v){annualInterestRate=v;return this;} public LoanBuilder termMonths(int v){termMonths=v;return this;} public LoanBuilder status(String v){status=v;return this;} public LoanBuilder createdAt(LocalDateTime v){createdAt=v;return this;}
 public Loan build(){if(customerId<=0||principal<=0||termMonths<=0)throw new IllegalArgumentException("Invalid loan data");return new Loan(id,customerId,officerId,loanType,principal,annualInterestRate,termMonths,status,createdAt);}
}
