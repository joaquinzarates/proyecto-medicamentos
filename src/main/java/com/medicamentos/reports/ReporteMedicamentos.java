package com.medicamentos.reports;

import com.medicamentos.entities.Medicamento;
import com.medicamentos.services.MedicamentoService;
import com.medicamentos.validators.Validadores;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class ReporteMedicamentos {
    private MedicamentoService service;

    public ReporteMedicamentos(MedicamentoService service) {
        this.service = service;
    }

    public void reporteStockBajoConDescuento(String categoria) {
        System.out.println("\n========== STOCK BAJO CON DESCUENTO ==========");
        System.out.println("Categoria con descuento: " + categoria);


        List<Medicamento> medicamentosDescuentados = service.obtenerStockBajoConDescuento(categoria);

        if (medicamentosDescuentados.isEmpty()) {
            System.out.println("No hay medicamentos con stock bajo.");
        } else {
            medicamentosDescuentados.forEach(med ->
                    System.out.println(med.getNombre() + " - Precio: $" +
                            String.format("%.2f", med.getPrecio()) + " - Stock: " + med.getStock())
            );
        }
    }


    public void reporteValidador(String titulo) {
        System.out.println("\n========== REPORTE: " + titulo.toUpperCase() + " ==========");
        System.out.println("------");

        List<Medicamento> medicamentos = service.filtrarPorValidador(Validadores.STOCK_BAJO);

        if (medicamentos.isEmpty()) {
            System.out.println("No hay medicamentos que cumplan el criterio.");
        } else {
            medicamentos.forEach(System.out::println);
        }
    }


    public void reportePorCategoria() {
        System.out.println("\n========== MEDICAMENTOS POR CATEGORIA ==========");


        Map<String, List<Medicamento>> agrupados = service.agruparPorCategoria();

        agrupados.forEach((categoria, medicamentos) -> {
            System.out.println("\n" + categoria + ":");
            medicamentos.forEach(med ->
                    System.out.println("  - " + med.getNombre() + " ($" +
                            String.format("%.2f", med.getPrecio()) + ")")
            );
        });
    }


    public void reporteEstadisticas() {
        System.out.println("\n========== ESTADISTICAS POR CATEGORIA ==========");


        Map<String, Long> conteos = service.contarPorCategoria();

        conteos.forEach((categoria, cantidad) -> {
            double promedio = service.precioPromedioPorCategoria(categoria);
            System.out.println("\nCategoria: " + categoria);
            System.out.println("  Cantidad: " + cantidad);
            System.out.println("  Precio Promedio: $" + String.format("%.2f", promedio));
        });
    }


    public void reporteResumen() {
        System.out.println("\n========== REPORTE MEDICAMENTOS ==========");


        String resumen = service.filtrarPorValidador(med -> med.getStock() > 0).stream()
                .map(Medicamento::getNombre)
                .collect(Collectors.joining(", "));

        System.out.println("Medicamentos disponibles: " + resumen);
    }
}