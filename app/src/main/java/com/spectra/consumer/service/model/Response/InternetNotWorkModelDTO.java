package com.spectra.consumer.service.model.Response;

import java.io.Serializable;

public class InternetNotWorkModelDTO implements Serializable {
	private String responseCode;
	private String status;
	private ResponseDTO response;

	public void setResponseCode(String responseCode){
		this.responseCode = responseCode;
	}

	public String getResponseCode(){
		return responseCode;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	public void setResponse(ResponseDTO response){
		this.response = response;
	}

	public ResponseDTO getResponse(){
		return response;
	}

	@Override
 	public String toString(){
		return 
			"InternetNotWorkModelDTO{" + 
			"responseCode = '" + responseCode + '\'' + 
			",status = '" + status + '\'' + 
			",response = '" + response + '\'' + 
			"}";
		}
}