/**
 * BankAccount.java
 * 
 * Modification of an example in Cay Horstmann's "Computing Concepts with
 * Java 2 Essentials".
 *
 * This class--along with SavingsAccount, CheckingAccount, and
 * TimeDepositAccount--demonstrates inheritance. Keeps track of a bank account.
 * 
 * @author Scot Drysdale on 4/23/00.  Modified 1/8/12
 */
public class BankAccount {
  private double balance; // Hold the amount in the account

  /** 
   * Default constructor. Creates an account with 0 balance.
   */
  public BankAccount() {
    balance = 0.00;
  }

  /** 
   * Creates an account with an initial balance
   * @param initialAmount the initial amount in the account when it is created
   */
  public BankAccount(double initialAmount) {
    balance = initialAmount;
  }

  /**
   * Deposits into the bank account.
   * @param amount the amount to be deposited
   */
  public void deposit(double amount) {
    balance += amount;
  }

  /**
   * Withdraws from the bank account.
   * Limitation: Does not test for an overdrawn account.
   * @param amount the amount to withdraw
   */
  // 
  public void withdraw(double amount) {
    balance -= amount;
  }

  /**
   * @return the current balance in the account.
   */
  public double getBalance() {
    return balance;
  }

  /**
   * Transfers money from this bank account to other bank.
   * @param other the bank account to transfer money to
   * @param amount the amount to transfer
   */
  public void transfer(BankAccount other, double amount) {
    withdraw(amount);
    other.deposit(amount);
  }

  /**
   * Overrides the toString provided by the Object class.
   * @return String representation of the account
   */
  public String toString() {
    return "Account balance: " + balance;
  }
}