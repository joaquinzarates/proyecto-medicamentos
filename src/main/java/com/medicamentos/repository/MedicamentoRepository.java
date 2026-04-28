package com.medicamentos.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class MedicamentoRepository<T> {
    private List<T> items;

    public MedicamentoRepository() {
        this.items = new ArrayList<>();
    }


    public void agregar(T item) {
        items.add(item);
    }


    public void agregarTodos(List<T> itemsList) {
        items.addAll(itemsList);
    }


    public List<T> obtenerTodos() {
        return new ArrayList<>(items);
    }


    public Optional<T> buscar(java.util.function.Predicate<T> predicate) {
        return items.stream()
                .filter(predicate)
                .findFirst();
    }


    public int tamaño() {
        return items.size();
    }
}