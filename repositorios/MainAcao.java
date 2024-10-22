package br.com.cesarschool.poo.titulos.repositorios;

import br.com.cesarschool.poo.titulos.entidades.Acao;
import br.com.cesarschool.poo.titulos.repositorios.RepositorioAcao;

import java.time.LocalDate;

public class MainAcao {

    public static void main(String[] args) {
        // Criar uma instância do repositório
        RepositorioAcao repositorio = new RepositorioAcao();

        Acao[] array = repositorio.listar();

        for (int i = 0; i < array.length; i++) {
            // Printar cada ação
            System.out.println("----------------------------------------------");
            System.out.println(array[i].getIdentificador());
            System.out.println(array[i].getNome());
            System.out.println(array[i].getValorUnitario());
            System.out.println(array[i].getDataValidade());
            System.out.println("----------------------------------------------");
        }

        // Incluir novas ações
        Acao acao1 = new Acao(1, "PETROBRAS", LocalDate.of(2024, 12, 12), 30.33);
        Acao acao2 = new Acao(2, "BANCO DO BRASIL", LocalDate.of(2026, 1, 1), 21.21);
        Acao acao3 = new Acao(3, "CORREIOS", LocalDate.of(2027, 11, 11), 6.12);

        System.out.println("Incluir ação 1: " + repositorio.incluir(acao1));
        System.out.println("Incluir ação 2: " + repositorio.incluir(acao2));
        System.out.println("Incluir ação 3: " + repositorio.incluir(acao3));

        // Buscar uma ação existente
        Acao acaoBuscada = repositorio.buscar(1);
        if (acaoBuscada != null) {
            System.out.println("Ação encontrada: " + acaoBuscada.getNome() + " - Valor: " + acaoBuscada.getValorUnitario());
        } else {
            System.out.println("Ação não encontrada!");
        }

        // Alterar uma ação existente
        Acao acaoAlterada = new Acao(1, "PETROBRAS ALTERADA", LocalDate.of(2024, 12, 12), 35.50);
        System.out.println("Alterar ação 1: " + repositorio.alterar(acaoAlterada));

        // Buscar a ação alterada
        Acao acaoBuscadaAlterada = repositorio.buscar(1);
        if (acaoBuscadaAlterada != null) {
            System.out.println("Ação alterada encontrada: " + acaoBuscadaAlterada.getNome() + " - Valor: " + acaoBuscadaAlterada.getValorUnitario());
        } else {
            System.out.println("Ação não encontrada!");
        }

        // Excluir uma ação
        System.out.println("Excluir ação 3: " + repositorio.excluir(3));

        // Tentar buscar uma ação excluída
        Acao acaoExcluida = repositorio.buscar(3);
        if (acaoExcluida != null) {
            System.out.println("Ação excluída encontrada: " + acaoExcluida.getNome());
        } else {
            System.out.println("Ação 3 foi excluída com sucesso!");
        }
    }
}
