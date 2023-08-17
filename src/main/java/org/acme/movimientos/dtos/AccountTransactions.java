package org.acme.movimientos.dtos;

public class AccountTransactions {

	
	public AccountTransactions() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public AccountTransactions(Long id, String paymentSubjet, String currency, String transaccionCode,
			String creditDebit, String operationDate, Double operationAmount, String module, String concept,
			Double balance) {
		super();
		this.id = id;
		this.paymentSubjet = paymentSubjet;
		this.currency = currency;
		this.transaccionCode = transaccionCode;
		this.creditDebit = creditDebit;
		this.operationDate = operationDate;
		this.operationAmount = operationAmount;
		this.module = module;
		this.concept = concept;
		this.balance = balance;
	}


	private Long id;
    private String paymentSubjet;
    private String currency;
    private String transaccionCode;
    private String creditDebit;
    private String operationDate;  
    private Double operationAmount;
    private String module;
    private String concept;
    private Double balance;
    
    
	


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getPaymentSubjet() {
		return paymentSubjet;
	}


	public void setPaymentSubjet(String paymentSubjet) {
		this.paymentSubjet = paymentSubjet;
	}


	public String getCurrency() {
		return currency;
	}


	public void setCurrency(String currency) {
		this.currency = currency;
	}


	public String getTransaccionCode() {
		return transaccionCode;
	}


	public void setTransaccionCode(String transaccionCode) {
		this.transaccionCode = transaccionCode;
	}


	public String getCreditDebit() {
		return creditDebit;
	}


	public void setCreditDebit(String creditDebit) {
		this.creditDebit = creditDebit;
	}


	public String getOperationDate() {
		return operationDate;
	}


	public void setOperationDate(String operationDate) {
		this.operationDate = operationDate;
	}


	public Double getOperationAmount() {
		return operationAmount;
	}


	public void setOperationAmount(Double operationAmount) {
		this.operationAmount = operationAmount;
	}


	public String getModule() {
		return module;
	}


	public void setModule(String module) {
		this.module = module;
	}


	public String getConcept() {
		return concept;
	}


	public void setConcept(String concept) {
		this.concept = concept;
	}


	public Double getBalance() {
		return balance;
	}


	public void setBalance(Double balance) {
		this.balance = balance;
	}


	@Override
	public String toString() {
		return "AccountTransactions [id=" + id + ", paymentSubjet=" + paymentSubjet + ", currency=" + currency
				+ ", transaccionCode=" + transaccionCode + ", creditDebit=" + creditDebit + ", operationDate="
				+ operationDate + ", operationAmount=" + operationAmount + ", module=" + module + ", concept=" + concept
				+ ", balance=" + balance + "]";
	}
	
}
