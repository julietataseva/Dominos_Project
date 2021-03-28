package dominos.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class NoContentException extends RuntimeException{
    public NoContentException(String msg){
        super(msg);
    }
}