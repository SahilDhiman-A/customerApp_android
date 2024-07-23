package com.spectra.consumer.service.model.Response;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class InvoiceDTO implements Serializable {
	private String invoiceNo;
	private String invoiceDate;
	private String fromDate;
	private String toDate;
	private Object invoiceAmount;
	private String dueDate;

	public void setInvoiceNo(String invoiceNo){
		this.invoiceNo = invoiceNo;
	}

	public String getInvoiceNo(){
		return invoiceNo;
	}

	public void setInvoiceDate(String invoiceDate){
		this.invoiceDate = invoiceDate;
	}

	public String getInvoiceDate(){
		return parseDate(invoiceDate);
	}

	public void setFromDate(String fromDate){
		this.fromDate = fromDate;
	}

	public String getFromDate(){
		return parseDate(fromDate);
	}

	public void setToDate(String toDate){
		this.toDate = toDate;
	}

	public String getToDate(){
		return parseDate(toDate);
	}

	public void setInvoiceAmount(Object invoiceAmount){
		this.invoiceAmount = invoiceAmount;
	}

	public Object getInvoiceAmount(){
		return invoiceAmount;
	}

	public void setDueDate(String dueDate){
		this.dueDate = dueDate;
	}

	public String getDueDate(){
		return parseDate(dueDate);
	}

	public String parseDate(String time) {
		String inputPattern = "dd/MM/yyyy";
		String outputPattern = "dd-MM-yyyy";
		SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
		SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

		Date date = null;
		String str = null;

		try {
			date = inputFormat.parse(time);
			str = outputFormat.format(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return str;
	}
	@Override
 	public String toString(){
		return 
			"InvoiceDTO{" + 
			"invoiceNo = '" + invoiceNo + '\'' + 
			",invoiceDate = '" + invoiceDate + '\'' + 
			",fromDate = '" + fromDate + '\'' + 
			",toDate = '" + toDate + '\'' + 
			",invoiceAmount = '" + invoiceAmount + '\'' + 
			",dueDate = '" + dueDate + '\'' + 
			"}";
		}
}