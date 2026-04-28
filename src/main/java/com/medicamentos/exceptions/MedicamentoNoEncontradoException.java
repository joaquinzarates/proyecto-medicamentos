package com.medicamentos.exceptions;

public class MedicamentoNoEncontradoException extends RuntimeException {
    public MedicamentoNoEncontradoException(String nombre) {
        super("El medicamento: " + nombre + "' no fue encontrado en el sistema, pruebe nuevamente ingresando otro farmaco a buscar");
    }
}