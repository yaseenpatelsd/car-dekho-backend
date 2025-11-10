package com.car.dekho.car.dekho.Exeptions;

public class UserNotFoundExeption extends RuntimeException{
    public UserNotFoundExeption(String message){
        super(message);
    }
}
