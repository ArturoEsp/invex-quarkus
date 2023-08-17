package org.acme.movimientos.dtos;

public class AddThirdAccountsDebitCardReq {

	private String idAccount;
	private String currency;
	private String debitCard;
	private String bankId;
	private String curpRfc;
	public AddThirdAccountsDebitCardReq() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getIdAccount() {
		return idAccount;
	}
	public void setIdAccount(String idAccount) {
		this.idAccount = idAccount;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getDebitCard() {
		return debitCard;
	}
	public void setDebitCard(String debitCard) {
		this.debitCard = debitCard;
	}
	public String getBankId() {
		return bankId;
	}
	public void setBankId(String bankId) {
		this.bankId = bankId;
	}
	public String getCurpRfc() {
		return curpRfc;
	}
	public void setCurpRfc(String curpRfc) {
		this.curpRfc = curpRfc;
	}
	
	
}
