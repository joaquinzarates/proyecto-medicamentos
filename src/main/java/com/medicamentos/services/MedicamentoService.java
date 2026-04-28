package com.medicamentos.services;

import com.medicamentos.entities.Medicamento;
import com.medicamentos.exceptions.MedicamentoNoEncontradoException;
import com.medicamentos.repository.MedicamentoRepository;
import com.medicamentos.validators.ReglaDeValidacion;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class MedicamentoService {
    private MedicamentoRepository<Medicamento> repository;

    public MedicamentoService(MedicamentoRepository<Medicamento> repository) {
        this.repository = repository;
    }

    public Medicamento buscarPorNombre(String nombre) {
        return repository.buscar(med -> med.getNombre().equalsIgnoreCase(nombre))
                .orElseThrow(() -> new MedicamentoNoEncontradoException(nombre));
    }


    public Optional<Medicamento> buscarPorNombreSeguro(String nombre) {
        return repository.buscar(med -> med.getNombre().equalsIgnoreCase(nombre));
    }


    public List<Medicamento> obtenerStockBajoConDescuento(String categoriaConDescuento) {
        return repository.obtenerTodos().stream()
                .filter(med -> med.getStock() < 10)  // Operación Intermedia: filter
                .map(med -> aplicarDescuentoPorCategoria(med, categoriaConDescuento))  // Operación Intermedia: map
                .collect(Collectors.toList());  // Operación Terminal: collect
    }

    private Medicamento aplicarDescuentoPorCategoria(Medicamento med, String categoria) {
        if (med.getCategoria().equalsIgnoreCase(categoria)) {
            double precioConDescuento = med.getPrecio() * 0.85;  // 15% descuento
            med.setPrecio(precioConDescuento);
        }
        return med;
    }


    public List<Medicamento> filtrarPorValidador(ReglaDeValidacion validador) {
        return repository.obtenerTodos().stream()
                .filter(validador::validar)
                .collect(Collectors.toList());
    }


    public List<Medicamento> obtenerPorCategoriaConStock(String categoria) {
        return repository.obtenerTodos().stream()
                .filter(med -> med.getCategoria().equalsIgnoreCase(categoria))
                .filter(med -> med.getStock() > 0)
                .collect(Collectors.toList());
    }


    public double precioPromedioPorCategoria(String categoria) {
        return repository.obtenerTodos().stream()
                .filter(med -> med.getCategoria().equalsIgnoreCase(categoria))
                .mapToDouble(Medicamento::getPrecio)
                .average()
                .orElse(0.0);
    }

    public Optional<Medicamento> medicamentoMasCaroPorCategoria(String categoria) {
        return repository.obtenerTodos().stream()
                .filter(med -> med.getCategoria().equalsIgnoreCase(categoria))
                .max((med1, med2) -> Double.compare(med1.getPrecio(), med2.getPrecio()));
    }


    public java.util.Map<String, List<Medicamento>> agruparPorCategoria() {
        return repository.obtenerTodos().stream()
                .collect(Collectors.groupingBy(Medicamento::getCategoria));
    }


    public java.util.Map<String, Long> contarPorCategoria() {
        return repository.obtenerTodos().stream()
                .collect(Collectors.groupingBy(
                        Medicamento::getCategoria,
                        Collectors.counting()
                ));
    }
}