package com.medicamentos;

import com.medicamentos.entities.Medicamento;
import com.medicamentos.repository.MedicamentoRepository;
import com.medicamentos.reports.ReporteMedicamentos;
import com.medicamentos.services.MedicamentoService;
import com.medicamentos.validators.Validadores;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {

        System.out.println("SISTEMA DE GESTION DE MEDICAMENTOS CON JAVA");
        System.out.println("programacion funcional");



        MedicamentoRepository<Medicamento> repository = new MedicamentoRepository<>();


        List<Medicamento> medicamentos = Arrays.asList(
                new Medicamento("Ibuprofeno 400mg", 25.50, 5, "Analgesicos"),
                new Medicamento("Paracetamol 500mg", 15.00, 15, "Analgesicos"),
                new Medicamento("Amoxicilina 500mg", 45.00, 8, "Antibioticos"),
                new Medicamento("Cefalexina 500mg", 52.00, 3, "Antibioticos"),
                new Medicamento("Vitamina C 1000mg", 18.75, 0, "Vitaminas"),
                new Medicamento("Vitamina D 1000IU", 32.00, 20, "Vitaminas"),
                new Medicamento("Omeprazol 20mg", 38.50, 7, "Gastroprotectores"),
                new Medicamento("Ranitidina 150mg", 22.00, 12, "Gastroprotectores")
        );

        repository.agregarTodos(medicamentos);


        MedicamentoService service = new MedicamentoService(repository);



        System.out.println("\nBUSQUEDA CON OPTIONAL");



        Optional<Medicamento> medicamentoEncontrado = service.buscarPorNombreSeguro("Vitamina C 1000mg");
        medicamentoEncontrado.ifPresentOrElse(
                med -> System.out.println(" Medicamento encontrado: " + med),
                () -> System.out.println("✗ Medicamento no encontrado")
        );


        Optional<Medicamento> noExiste = service.buscarPorNombreSeguro("Inexistente");
        System.out.println("Resultado con orElse: " +
                noExiste.map(Medicamento::getNombre).orElse("Medicamento no disponible"));


        try {
            Medicamento medicamento = service.buscarPorNombre("Medicamento Fantasma");
        } catch (Exception e) {
            System.out.println(" Excepcion capturada: " + e.getMessage());
        }



        System.out.println("STREAMS CON FILTER Y MAP");


        List<Medicamento> stockBajoConDescuento = service.obtenerStockBajoConDescuento("Analgesicos");
        System.out.println("Medicamentos con stock bajo (< 10) con 15% descuento en 'Analgesicos':");
        stockBajoConDescuento.forEach(med ->
                System.out.println("  - " + med.getNombre() +
                        " | Precio: $" + String.format("%.2f", med.getPrecio()) +
                        " | Stock: " + med.getStock() +
                        " | Categoria: " + med.getCategoria())
        );


        System.out.println("INTERFACES FUNCIONALES");


    System.out.println("\n Medicamentos con STOCK BAJO (< 10):");
        List<Medicamento> stockBajo = service.filtrarPorValidador(Validadores.STOCK_BAJO);
        stockBajo.forEach(med -> System.out.println("  - " + med.getNombre() + " (Stock: " + med.getStock() + ")"));

        System.out.println("\n Medicamentos con PRECIO ELEVADO (> 50):");
        List<Medicamento> precioElevado = service.filtrarPorValidador(Validadores.PRECIO_ELEVADO);
        precioElevado.forEach(med -> System.out.println("  - " + med.getNombre() + " (Precio: $" +
                String.format("%.2f", med.getPrecio()) + ")"));

        System.out.println("\n Medicamentos COMPUESTOS (Stock Bajo Y en Categoria 'Analgesicos'):");
        var validadorCompuesto = Validadores.y(
                Validadores.STOCK_BAJO,
                Validadores.enCategoria("Analgesicos")
        );
        List<Medicamento> compuestos = service.filtrarPorValidador(validadorCompuesto);
        compuestos.forEach(med -> System.out.println("  - " + med.getNombre()));


        System.out.println("GENERICOS (Extensibilidad sin Casteos)");


        System.out.println("Total de medicamentos en repositorio: " + repository.tamaño());
        System.out.println("Medicamentos disponibles (generico List<T>): " + repository.obtenerTodos().size());


        System.out.println("\n Medicamentos agrupados por categoria:");
        var agrupados = service.agruparPorCategoria();
        agrupados.forEach((categoria, meds) -> {
            System.out.println("\n  " + categoria + ":");
            meds.forEach(med -> System.out.println("    • " + med.getNombre()));
        });

        System.out.println("\n Conteo de medicamentos por categoria:");
        var conteos = service.contarPorCategoria();
        conteos.forEach((categoria, cantidad) ->
                System.out.println("  " + categoria + ": " + cantidad)
        );



        System.out.println("REPORTES INTEGRADOS");


        ReporteMedicamentos reportes = new ReporteMedicamentos(service);
        reportes.reporteStockBajoConDescuento("Analgesicos");
        reportes.reportePorCategoria();
        reportes.reporteEstadisticas();
        reportes.reporteResumen();


    }
}