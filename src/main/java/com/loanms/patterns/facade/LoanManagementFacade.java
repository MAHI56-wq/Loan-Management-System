package com.loanms.patterns.facade;
import com.loanms.model.Loan; import com.loanms.service.*; import java.util.*;
public class LoanManagementFacade {
 private final LoanService loans=new LoanService(); private final ReportService reports=new ReportService();
 public Loan apply(int customer,String type,double amount,double rate,int months){return loans.apply(customer,type,amount,rate,months);}
 public void approve(int id){loans.approve(id);} public void reject(int id){loans.reject(id);} public void assignOfficer(int loan,int officer){loans.assignOfficer(loan,officer);} public void recordPayment(int id,double amount){loans.recordPayment(id,amount);}
 public List<Loan> search(String term){return term==null||term.isBlank()?loans.list():loans.search(term);} public double monthlyPayment(String t,double p,double r,int m){return loans.monthlyPayment(t,p,r,m);} public List<String> paymentHistory(int id){return reports.paymentHistory(id);}
}
