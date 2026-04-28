package com.medicamentos.validators;

import com.medicamentos.entities.Medicamento;

@FunctionalInterface
public interface ReglaDeValidacion {

    boolean validar(Medicamento medicamento);
}