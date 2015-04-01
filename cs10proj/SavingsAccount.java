/**
 * SavingsAccount.java
 * 
 * Modification of an example in Cay Horstmann's "Computing Concepts with
 * Java 2 Essentials".
 * 
 * Demonstrates inheritance. Keeps track of a savings account.
 * 
 * A savings account is a bank account that pays periodic interest.
 *
 * @author Scot Drysdale on 4/23/00.  Modified 1/8/12.
 */
public class SavingsAccount extends BankAccount {
  // Note that balance is inherited from BankAccount, but is not directly
  // accessible because it is private.
  // Methods deposit, withdraw, getBalance, transfer, and toString are also
  // inherited, and because they are public, they are directly accessible.

  private double interestRate; // Holds the interest rate for the period.

  /**
   *  Constructor that sets interest rate.
   *  @param rate the interest rate paid by the account
   */
  public SavingsAccount(double rate) {
    // Because no call to super is made in the first line, the default
    // constructor for BankAccount is automatically called here.

    interestRate = rate;
  }

  /**
   *  Constructor that sets rate and initial balance.
   * @param rate the interest rate paid by the account
   * @param initialAmount the amount in the account when it is created
   */
  public SavingsAccount(double rate, double initialAmount) {
    super(initialAmount);  // Calls constructor for BankAccount.
                           // MUST be the first line of the constructor.

    interestRate = rate;
  }

  /**
   * Add interest for the current period to the account balance.
   */
  public void addPeriodicInterest() {
    double interest = getBalance() * interestRate / 100.0;
    deposit(interest);
  }
}