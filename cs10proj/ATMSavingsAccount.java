public class ATMSavingsAccount extends SavingsAccount
{
	private int withdrawCounter = 0;
  public ATMSavingsAccount(double rate)
	{
		super();
		super(rate);
	}
	public ATMSavingsAccount(double initialAmount)
	{
		super(initialAmount);
		super(rate);
	}
	public void withdraw(double amount) 
	{
     balance -= amount;
     withdrawCounter++;
  }
	public void transfer(BankAccount other, double amount) 
	{
    withdraw(amount);
    other.deposit(amount);
  }
	public void addPeriodicInterest() 
	{
    double interest = getBalance() * super.interestRate / 100.0;
    deposit(interest);
    if(withdrawCounter - 2 > 0)
    {
    	super.balance -= (withdrawCharges - 2) * 1.50;
    }
    withdrawCounter = 0;
  }
}