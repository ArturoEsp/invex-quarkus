package org.acme.movimientos.dtos;

public class HeaderResp {

	private String transactionId;
	private String requestSystem;
	private String reqType;
	private String requestDateTime;
	private String responseDateTime;
	private String statusResponse;
	public HeaderResp() {
		super();
		// TODO Auto-generated constructor stub
	}
	public HeaderResp(String transactionId, String requestSystem, String reqType, String requestDateTime,
			String responseDateTime, String statusResponse) {
		super();
		this.transactionId = transactionId;
		this.requestSystem = requestSystem;
		this.reqType = reqType;
		this.requestDateTime = requestDateTime;
		this.responseDateTime = responseDateTime;
		this.statusResponse = statusResponse;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public String getRequestSystem() {
		return requestSystem;
	}
	public void setRequestSystem(String requestSystem) {
		this.requestSystem = requestSystem;
	}
	public String getReqType() {
		return reqType;
	}
	public void setReqType(String reqType) {
		this.reqType = reqType;
	}
	public String getRequestDateTime() {
		return requestDateTime;
	}
	public void setRequestDateTime(String requestDateTime) {
		this.requestDateTime = requestDateTime;
	}
	public String getResponseDateTime() {
		return responseDateTime;
	}
	public void setResponseDateTime(String responseDateTime) {
		this.responseDateTime = responseDateTime;
	}
	public String getStatusResponse() {
		return statusResponse;
	}
	public void setStatusResponse(String statusResponse) {
		this.statusResponse = statusResponse;
	}
	
}
