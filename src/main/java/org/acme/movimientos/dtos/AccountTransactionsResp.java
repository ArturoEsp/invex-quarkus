package org.acme.movimientos.dtos;

import java.util.List;

public class AccountTransactionsResp {

	private List<AccountTransactions> accountTransactions;

	public AccountTransactionsResp() {
		super();
		// TODO Auto-generated constructor stub
	}

	public List<AccountTransactions> getAccountTransactions() {
		return accountTransactions;
	}

	public void setAccountTransactions(List<AccountTransactions> accountTransactions) {
		this.accountTransactions = accountTransactions;
	}

}
