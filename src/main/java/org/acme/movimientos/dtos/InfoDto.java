package org.acme.movimientos.dtos;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

import com.fasterxml.jackson.core.type.TypeReference;
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

    //Se integro para validar el token que se ingresa desde api manager, se quitara despues
    private String token;
    
    public void setData(Object a){
        this.data = convertObjectToMap(a);
    }
    public static Map<String, Object> convertObjectToMap(Object object) {
        ObjectMapper objectMapper = new ObjectMapper();
		if (object instanceof String) {
            try {
                // Si es una cadena, primero deserializamos el JSON en un Map
                return objectMapper.readValue((String) object, new TypeReference<Map<String, Object>>() {});
            } catch (Exception e) {
				System.out.println("Error al deserializar "+e.getMessage());
                throw new RuntimeException("Error al deserializar la cadena JSON en un Map", e);
            }
        }
        
        // Si no es una cadena, intentamos convertir directamente el objeto en un Map
        return objectMapper.convertValue(object, Map.class);
    }
	public Map<?,?> getData() {
		return data;
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
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
    
    
}
