package com.adriankich.pessoaapi.domain.exception;

public class CpfAlreadyUsedException extends RuntimeException {

    /**
     * NotFoundException
     *
     * @param message String
     */
    public CpfAlreadyUsedException( String message )
    {
        super( message );
    }

    /**
     * NotFoundException
     *
     * @param cause Throwable
     */
    public CpfAlreadyUsedException( Throwable cause )
    {
        super( cause );
    }

    /**
     * NotFoundException
     *
     * @param message String
     * @param cause Throwable
     */
    public CpfAlreadyUsedException( String message, Throwable cause )
    {
        super( message, cause );
    }
}
