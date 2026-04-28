package com.medicamentos.validators;

import com.medicamentos.entities.Medicamento;


public class Validadores{


    public static final ValidadorCalidad STOCK_BAJO =
            medicamento -> medicamento.getStock() < 10;


    public static final ValidadorCalidad PRECIO_ELEVADO =
            medicamento -> medicamento.getPrecio() > 50;


    public static final ValidadorCalidad SIN_STOCK =
            medicamento -> medicamento.getStock() == 0;


    public static ValidadorCalidad enCategoria(String categoria) {
        return medicamento -> medicamento.getCategoria().equalsIgnoreCase(categoria);
    }


    public static ValidadorCalidad y(ValidadorCalidad v1, ValidadorCalidad v2) {
        return medicamento -> v1.validar(medicamento) && v2.validar(medicamento);
    }


    public static ValidadorCalidad o(ValidadorCalidad v1, ValidadorCalidad v2) {
        return medicamento -> v1.validar(medicamento) || v2.validar(medicamento);
    }
}