package org.acme.movimientos.dtos;

public class Responses {

	public Responses() {
		super();
		// TODO Auto-generated constructor stub
	}
	

	public Responses(CanonicalInvexMessageResp canonicalInvexMessage) {
		super();
		this.canonicalInvexMessage = canonicalInvexMessage;
	}
	
	private CanonicalInvexMessageResp canonicalInvexMessage;

	public CanonicalInvexMessageResp getCanonicalInvexMessage() {
		return canonicalInvexMessage;
	}

	public void setCanonicalInvexMessage(CanonicalInvexMessageResp canonicalInvexMessage) {
		this.canonicalInvexMessage = canonicalInvexMessage;
	}

	
	
}
