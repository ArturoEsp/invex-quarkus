package org.acme.movimientos.dtos;

public class AddThirdAccountsInvexReq {

	private String idAccount;
	private String currency;
	private String beneficiaryAccount;
	private String bankId;
	private String curpRfc;
	public AddThirdAccountsInvexReq() {
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
	public String getBeneficiaryAccount() {
		return beneficiaryAccount;
	}
	public void setBeneficiaryAccount(String beneficiaryAccount) {
		this.beneficiaryAccount = beneficiaryAccount;
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
