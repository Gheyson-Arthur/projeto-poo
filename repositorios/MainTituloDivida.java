package br.com.cesarschool.poo.titulos.repositorios;

import java.time.LocalDate;

import br.com.cesarschool.poo.titulos.entidades.TituloDivida;

public class MainTituloDivida {

    public static void main(String[] args) {
        // Criação do repositório
        RepositorioTituloDivida repositorio = new RepositorioTituloDivida();

        // Criando alguns objetos TituloDivida
        TituloDivida titulo1 = new TituloDivida(1, "Título 1", LocalDate.of(2025, 12, 31), 5.0);
        TituloDivida titulo2 = new TituloDivida(2, "Título 2", LocalDate.of(2024, 6, 30), 4.5);
        TituloDivida titulo3 = new TituloDivida(3, "Título 3", LocalDate.of(2026, 5, 15), 6.0);

        // Incluir títulos
        System.out.println("Incluindo Títulos:");
        System.out.println("Título 1: " + repositorio.incluir(titulo1)); // Espera true
        System.out.println("Título 2: " + repositorio.incluir(titulo2)); // Espera true
        System.out.println("Título 3: " + repositorio.incluir(titulo3)); // Espera true
        System.out.println("Tentativa de incluir Título 1 novamente: " + repositorio.incluir(titulo1)); // Espera false

        // Buscar um título
        System.out.println("\nBuscando Título 2:");
        TituloDivida buscado = repositorio.buscar(2);
        if (buscado != null) {
            System.out.println("Título encontrado: " + buscado);
        } else {
            System.out.println("Título não encontrado.");
        }

        // Alterar um título
        System.out.println("\nAlterando Título 2:");
        TituloDivida tituloAlterado = new TituloDivida(2, "Título 2 Alterado", LocalDate.of(2024, 12, 31), 5.5);
        boolean alterado = repositorio.alterar(tituloAlterado);
        System.out.println("Alteração bem-sucedida: " + alterado);

        // Buscar o título alterado
        System.out.println("\nBuscando Título 2 Alterado:");
        buscado = repositorio.buscar(2);
        if (buscado != null) {
            System.out.println("Título encontrado: " + buscado);
        } else {
            System.out.println("Título não encontrado.");
        }

        // Excluir um título
        System.out.println("\nExcluindo Título 1:");
        boolean excluido = repositorio.excluir(1);
        System.out.println("Exclusão bem-sucedida: " + excluido);

        // Tentar buscar o título excluído
        System.out.println("\nBuscando Título 1 após exclusão:");
        buscado = repositorio.buscar(1);
        if (buscado != null) {
            System.out.println("Título encontrado: " + buscado);
        } else {
            System.out.println("Título não encontrado.");
        }
    }
}
