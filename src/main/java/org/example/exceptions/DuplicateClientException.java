package org.example.exceptions;

public class DuplicateClientException extends Exception {
    public DuplicateClientException(String message) {
        super(message);
    }
}
