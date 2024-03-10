package com.abw12.absolutefitness.offermgmtms.advice;

public class InvalidDataRequestException extends RuntimeException{
    public InvalidDataRequestException(String errorMsg ){
        super(errorMsg);
    }

}
