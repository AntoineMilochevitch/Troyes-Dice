package main.java.exceptions;

public class InvalidColorException extends Exception{
    public InvalidColorException() {
        super("Cannot be an empty color.");
    }
}
