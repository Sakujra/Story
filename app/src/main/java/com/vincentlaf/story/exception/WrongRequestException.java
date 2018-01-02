package com.vincentlaf.story.exception;

/**
 * Created by Johnson on 2017/12/29.
 */

public class WrongRequestException extends Exception {
    public WrongRequestException(){
        super();
    }
    public WrongRequestException(String message){
        super(message);
    }
}
