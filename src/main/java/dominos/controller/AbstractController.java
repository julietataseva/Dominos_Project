package dominos.controller;

import dominos.model.dto.ErrorDTO;
import dominos.exceptions.AuthenticationException;
import dominos.exceptions.BadRequestException;
import dominos.exceptions.NotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.sql.SQLException;

@Log4j2
public class AbstractController {

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDTO handleBadRequest(BadRequestException e) {
        log.error(e.getMessage());
        return new ErrorDTO(e.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDTO handleNotFound(NotFoundException e) {
        log.error(e.getMessage());
        return new ErrorDTO(e.getMessage());
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorDTO handleNotAuthorized(AuthenticationException e) {
        log.error(e.getMessage());
        return new ErrorDTO(e.getMessage());
    }

    @ExceptionHandler(SQLException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDTO handleSQLException(SQLException e) {
        log.error(e.getMessage());
        return new ErrorDTO(e.getMessage());
    }
}