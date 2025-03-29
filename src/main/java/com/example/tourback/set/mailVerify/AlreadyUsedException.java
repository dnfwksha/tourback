package com.example.tourback.set.mailVerify;

public class AlreadyUsedException extends RuntimeException {
    public AlreadyUsedException(String s) {
        super(s);
    }
}
