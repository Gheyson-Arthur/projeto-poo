package br.com.cesarschool.poo.titulos.repositorios;

import br.com.cesarschool.poo.titulos.entidades.EntidadeOperadora;

public class MainEntidadeOperadora {

    public static void main(String[] args) {

        // Criando uma instância do repositório
        RepositorioEntidadeOperadora repositorio = new RepositorioEntidadeOperadora();

        // Criando e incluindo uma nova EntidadeOperadora
        EntidadeOperadora novaOperadora1 = new EntidadeOperadora(1, "Operadora ABC", true);
        boolean inclusao1 = repositorio.incluir(novaOperadora1);
        System.out.println("Inclusão da Operadora ABC: " + (inclusao1 ? "Sucesso" : "Falhou, identificador já existe"));

        // Criando e incluindo outra EntidadeOperadora
        EntidadeOperadora novaOperadora2 = new EntidadeOperadora(2, "Operadora XYZ", false);
        boolean inclusao2 = repositorio.incluir(novaOperadora2);
        System.out.println("Inclusão da Operadora XYZ: " + (inclusao2 ? "Sucesso" : "Falhou, identificador já existe"));

        // Tentando buscar uma operadora existente
        EntidadeOperadora operadoraEncontrada = repositorio.buscar(1);
        if (operadoraEncontrada != null) {
            System.out.println("Operadora encontrada: " + operadoraEncontrada.getNome());
        } else {
            System.out.println("Operadora com identificador 1 não encontrada.");
        }

        // Tentando buscar uma operadora inexistente
        EntidadeOperadora operadoraNaoEncontrada = repositorio.buscar(3);
        if (operadoraNaoEncontrada != null) {
            System.out.println("Operadora encontrada: " + operadoraNaoEncontrada.getNome());
        } else {
            System.out.println("Operadora com identificador 3 não encontrada.");
        }

        // Alterando uma operadora existente
        EntidadeOperadora operadoraAlterada = new EntidadeOperadora(1, "Operadora ABC Atualizada", false);
        boolean alteracao = repositorio.alterar(operadoraAlterada);
        System.out.println("Alteração da Operadora ABC: " + (alteracao ? "Sucesso" : "Falhou, identificador não encontrado"));

        // Excluindo uma operadora
        boolean exclusao = repositorio.excluir(2);
        System.out.println("Exclusão da Operadora XYZ: " + (exclusao ? "Sucesso" : "Falhou, identificador não encontrado"));

        // Tentando buscar novamente a operadora excluída
        EntidadeOperadora operadoraExcluida = repositorio.buscar(2);
        if (operadoraExcluida != null) {
            System.out.println("Operadora excluída encontrada: " + operadoraExcluida.getNome());
        } else {
            System.out.println("Operadora 2 foi excluída com sucesso!");
        }
    }
}
