package com.adriankich.pessoaapi.domain.exception;

public class NotFoundException extends RuntimeException {

    /**
     * NotFoundException
     *
     * @param message String
     */
    public NotFoundException( String message )
    {
        super( message );
    }

    /**
     * NotFoundException
     *
     * @param cause Throwable
     */
    public NotFoundException( Throwable cause )
    {
        super( cause );
    }

    /**
     * NotFoundException
     *
     * @param message String
     * @param cause Throwable
     */
    public NotFoundException( String message, Throwable cause )
    {
        super( message, cause );
    }
}