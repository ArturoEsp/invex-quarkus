package org.acme.movimientos.dtos;

public class RemoveThirdAccountsReq {

	private String thirdAccount;
	private String checkbookNumber;
	private String isBeneficiaryCreditCard;
	public RemoveThirdAccountsReq() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public String getThirdAccount() {
		return thirdAccount;
	}

	public void setThirdAccount(String thirdAccount) {
		this.thirdAccount = thirdAccount;
	}

	public String getCheckbookNumber() {
		return checkbookNumber;
	}
	public void setCheckbookNumber(String checkbookNumber) {
		this.checkbookNumber = checkbookNumber;
	}
	public String getIsBeneficiaryCreditCard() {
		return isBeneficiaryCreditCard;
	}
	public void setIsBeneficiaryCreditCard(String isBeneficiaryCreditCard) {
		this.isBeneficiaryCreditCard = isBeneficiaryCreditCard;
	}
	
	
}
