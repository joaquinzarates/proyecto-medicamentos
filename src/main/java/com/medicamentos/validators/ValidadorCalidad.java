package com.medicamentos.validators;

import com.medicamentos.entities.Medicamento;

@FunctionalInterface
public interface ValidadorCalidad {

    boolean validar(Medicamento medicamento);
}