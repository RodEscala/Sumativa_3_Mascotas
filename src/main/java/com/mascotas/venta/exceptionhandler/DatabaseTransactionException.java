package com.mascotas.venta.exceptionhandler;

//Creamos el manejador de las excepciones
// en tiempo de ejecucion y inicia la causa
public class DatabaseTransactionException extends RuntimeException{
    public DatabaseTransactionException(String message, Throwable cause){
        super(message);
        super.initCause(cause);
    }
}
