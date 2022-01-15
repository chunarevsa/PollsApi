package com.example.pollsapi.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class FinalDateException extends RuntimeException {

	private final String title;
   private final Object fieldName; // TODO: формат даты
   private final Object fieldValue;

	public FinalDateException(String title, String fieldName, Object fieldValue) {
		super(String.format("%s %s не может быть раньше :'%s'", title, fieldName, fieldValue));
		this.title = title;
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
	}

	public String getTitle() {
		return this.title;
	}


	public Object getFieldName() {
		return this.fieldName;
	}


	public Object getFieldValue() {
		return this.fieldValue;
	}
}
