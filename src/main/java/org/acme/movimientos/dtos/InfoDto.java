package org.acme.movimientos.dtos;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

import com.fasterxml.jackson.databind.ObjectMapper;


public class InfoDto implements Serializable{
    private static final long serialVersionUID = 1L;

       
    private String transactionID = UUID.randomUUID().toString();
    
    //{200,400,500}
    private int httpStatus;
    
    private String timestamp = LocalDateTime.now().toString();
    
    private String message;
    //Objeto de Respuesta
    private Map<?,?> data;

    public void setData(Object a){
        this.data = convertObjectToMap(a);
    }
    public static Map<String, Object> convertObjectToMap(Object object) {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.convertValue(object, Map.class);
    }
	public String getTransactionID() {
		return transactionID;
	}
	public void setTransactionID(String transactionID) {
		this.transactionID = transactionID;
	}
	public int getHttpStatus() {
		return httpStatus;
	}
	public void setHttpStatus(int httpStatus) {
		this.httpStatus = httpStatus;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
    
    
}
