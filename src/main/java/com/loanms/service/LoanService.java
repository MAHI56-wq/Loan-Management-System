package com.loanms.service;
import com.loanms.db.Database; import com.loanms.model.*; import com.loanms.patterns.factory.LoanTypeFactory; import com.loanms.patterns.observer.*; import com.loanms.patterns.state.*; import com.loanms.patterns.builder.LoanBuilder; import java.sql.*; import java.time.*; import java.util.*;
public class LoanService {
 private final List<LoanObserver> observers=new ArrayList<>();
 public LoanService(){observers.add(new NotificationObserver());}
 private void notifyAll(int id,String msg){observers.forEach(o->o.onLoanChanged(id,msg));}
 public double monthlyPayment(String type,double principal,double rate,int months){return LoanTypeFactory.strategyFor(type).monthlyPayment(principal,rate,months);}
 public Loan apply(int customerId,String type,double principal,double rate,int months){if(principal<=0||months<=0)throw new IllegalArgumentException("Principal and term must be positive");
  try(Connection c=Database.connect();PreparedStatement p=c.prepareStatement("INSERT INTO loan(customer_id,loan_type,principal,annual_interest_rate,term_months,status,created_at) VALUES(?,?,?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS)){
   p.setInt(1,customerId);p.setString(2,type);p.setDouble(3,principal);p.setDouble(4,rate);p.setInt(5,months);p.setString(6,"Pending");p.setString(7,LocalDateTime.now().toString());p.executeUpdate();
   try(ResultSet r=p.getGeneratedKeys()){int id=r.next()?r.getInt(1):0;notifyAll(id,"Loan application submitted");return get(id);}
  }catch(SQLException e){throw new IllegalStateException(e);}
 }
 public void approve(int id){changeState(id,new PendingState().approve());}
 public void reject(int id){changeState(id,new PendingState().reject());}
 private void changeState(int id,LoanState target){try(Connection c=Database.connect();PreparedStatement p=c.prepareStatement("UPDATE loan SET status=? WHERE id=?")){p.setString(1,target.name());p.setInt(2,id);p.executeUpdate();notifyAll(id,"Loan status changed to "+target.name());}catch(SQLException e){throw new IllegalStateException(e);}}
 public void assignOfficer(int loanId,int officerId){try(Connection c=Database.connect()){if(get(loanId)==null)throw new IllegalArgumentException("Loan not found");try(PreparedStatement chk=c.prepareStatement("SELECT id FROM officer WHERE id=?")){chk.setInt(1,officerId);try(ResultSet r=chk.executeQuery()){if(!r.next())throw new IllegalArgumentException("Officer not found");}}try(PreparedStatement p=c.prepareStatement("UPDATE loan SET officer_id=? WHERE id=?")){p.setInt(1,officerId);p.setInt(2,loanId);p.executeUpdate();}notifyAll(loanId,"Officer assigned");}catch(SQLException e){throw new IllegalStateException(e);}}
 public void recordPayment(int loanId,double amount){if(amount<=0)throw new IllegalArgumentException("Payment must be positive");
  try(Connection c=Database.connect()){Loan l=get(loanId);if(l==null)throw new IllegalArgumentException("Loan not found");if(!"Active".equals(l.status()))throw new IllegalStateException("Only active loans can receive payments");
   try(PreparedStatement p=c.prepareStatement("INSERT INTO payment(loan_id,amount,paid_at) VALUES(?,?,?)")){p.setInt(1,loanId);p.setDouble(2,amount);p.setString(3,LocalDateTime.now().toString());p.executeUpdate();}
   double paid=totalPaid(loanId), due=monthlyPayment(l.loanType(),l.principal(),l.annualInterestRate(),l.termMonths())*l.termMonths();
   if(paid+0.005>=due){try(PreparedStatement p=c.prepareStatement("UPDATE loan SET status='Completed' WHERE id=?")){p.setInt(1,loanId);p.executeUpdate();}notifyAll(loanId,"Loan completed automatically");}else notifyAll(loanId,"Payment recorded");
  }catch(SQLException e){throw new IllegalStateException(e);}
 }
 public double totalPaid(int id){try(Connection c=Database.connect();PreparedStatement p=c.prepareStatement("SELECT COALESCE(SUM(amount),0) FROM payment WHERE loan_id=?")){p.setInt(1,id);try(ResultSet r=p.executeQuery()){return r.next()?r.getDouble(1):0;}}catch(SQLException e){throw new IllegalStateException(e);}}
 public Loan get(int id){try(Connection c=Database.connect();PreparedStatement p=c.prepareStatement("SELECT * FROM loan WHERE id=?")){p.setInt(1,id);try(ResultSet r=p.executeQuery()){return r.next()?map(r):null;}}catch(SQLException e){throw new IllegalStateException(e);}}
 public List<Loan> search(String term){List<Loan> out=new ArrayList<>();String q="SELECT l.* FROM loan l LEFT JOIN customer c ON c.id=l.customer_id WHERE CAST(l.id AS TEXT) LIKE ? OR lower(l.loan_type) LIKE ? OR lower(l.status) LIKE ? OR lower(c.name) LIKE ? ORDER BY l.id DESC";try(Connection c=Database.connect();PreparedStatement p=c.prepareStatement(q)){String x="%"+term.toLowerCase()+"%";p.setString(1,x);p.setString(2,x);p.setString(3,x);p.setString(4,x);try(ResultSet r=p.executeQuery()){while(r.next())out.add(map(r));}return out;}catch(SQLException e){throw new IllegalStateException(e);}}
 public List<Loan> list(){List<Loan> out=new ArrayList<>();try(Connection c=Database.connect();ResultSet r=c.createStatement().executeQuery("SELECT * FROM loan ORDER BY id DESC")){while(r.next())out.add(map(r));return out;}catch(SQLException e){throw new IllegalStateException(e);}}
 private Loan map(ResultSet r)throws SQLException{return new LoanBuilder().id(r.getInt("id")).customerId(r.getInt("customer_id")).officerId((Integer)r.getObject("officer_id")).loanType(r.getString("loan_type")).principal(r.getDouble("principal")).annualInterestRate(r.getDouble("annual_interest_rate")).termMonths(r.getInt("term_months")).status(r.getString("status")).createdAt(LocalDateTime.parse(r.getString("created_at"))).build();}
}