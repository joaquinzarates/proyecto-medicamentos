package com.medicamentos.validators;


public class Validadores{


    public static final ReglaDeValidacion STOCK_BAJO =
            medicamento -> medicamento.getStock() < 10;


    public static final ReglaDeValidacion PRECIO_ELEVADO =
            medicamento -> medicamento.getPrecio() > 50;


    public static final ReglaDeValidacion SIN_STOCK =
            medicamento -> medicamento.getStock() == 0;


    public static ReglaDeValidacion enCategoria(String categoria) {
        return medicamento -> medicamento.getCategoria().equalsIgnoreCase(categoria);
    }


    public static ReglaDeValidacion y(ReglaDeValidacion v1, ReglaDeValidacion v2) {
        return medicamento -> v1.validar(medicamento) && v2.validar(medicamento);
    }


    public static ReglaDeValidacion o(ReglaDeValidacion v1, ReglaDeValidacion v2) {
        return medicamento -> v1.validar(medicamento) || v2.validar(medicamento);
    }
}