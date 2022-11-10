package com.question.MongoDBPractice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FOUND,reason = "customer id is not found")
public class CustomerNotFoundException extends Exception{
}
