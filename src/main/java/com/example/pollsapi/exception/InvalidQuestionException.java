package com.example.pollsapi.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidQuestionException extends RuntimeException {

	private final String title;
   private final String fieldName;
   private final Object fieldValue;

	public InvalidQuestionException(String title, String fieldName, Object fieldValue) {
		super(String.format("%s не удалось создать. Неверный формат ответа %s :'%s'", title, fieldName, fieldValue));
		this.title = title;
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
	}

	public String getTitle() {
		return this.title;
	}


	public String getFieldName() {
		return this.fieldName;
	}


	public Object getFieldValue() {
		return this.fieldValue;
	}
	
}
