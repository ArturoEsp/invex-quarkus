package org.acme.movimientos.dtos;

public class CanonicalInvexMessageResp {

	
    
	public CanonicalInvexMessageResp() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public CanonicalInvexMessageResp(HeaderResp header, BodyResp body) {
		super();
		this.header = header;
		this.body = body;
	}

	private HeaderResp header;
    private BodyResp body;
    
	public HeaderResp getHeader() {
		return header;
	}
	public void setHeader(HeaderResp header) {
		this.header = header;
	}
	public BodyResp getBody() {
		return body;
	}
	public void setBody(BodyResp body) {
		this.body = body;
	}
    
    
}
