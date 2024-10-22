package br.com.cesarschool.poo.titulos.mediators;

import br.com.cesarschool.poo.titulos.entidades.Acao;
import br.com.cesarschool.poo.titulos.repositorios.RepositorioAcao;

import java.time.LocalDate;

public class MediatorAcao {
    private final RepositorioAcao repositorioAcao = new RepositorioAcao();

    private static MediatorAcao mediatorAcao;

    private MediatorAcao() {
    }

    public static MediatorAcao getMediatorAcao() {
        if (mediatorAcao == null) {
            mediatorAcao = new MediatorAcao();
        }

        return mediatorAcao;
    }


    String validar(Acao acao) {
        int identificador = acao.getIdentificador();
        if (identificador < 0 || identificador >= 100000) {
            return "Identificador deve estar entre 1 e 99999.";
        }

        String nome = acao.getNome();
        if(nome == null || nome.trim().isEmpty()) {
            return "Nome deve ser preenchido.";
        }


        if(nome.length() < 10 || nome.length() > 100) {
            return "Nome deve ter entre 10 e 100 caracteres.";
        }

        LocalDate dataValidade = acao.getDataValidade();
        LocalDate dataMinima = LocalDate.now().plusDays(30);

        if(dataValidade == null || dataValidade.isBefore(dataMinima) || dataValidade.isEqual(dataMinima)) {
            return "Data de validade deve ter pelo menos 30 dias na frente da data atual.";
        }

        if(acao.getValorUnitario() <= 0) {
            return "Valor unitário deve ser maior que zero.";
        }

        return null;
    }

    public String incluir(Acao acao) {
        String validator = validar(acao);

        if (validator == null) {
            boolean incluido = repositorioAcao.incluir(acao);

            if (incluido) {
                return null;
            } else {
                return "Ação já existente";
            }
        } else {
            return validator;
        }
    }

    public String alterar(Acao acao) {
        String validator = validar(acao);

        if (validator == null) {
            boolean alterado = repositorioAcao.alterar(acao);

            if (alterado) {
                return null;
            } else {
                return "Ação inexistente";
            }
        } else {
            return validator;
        }
    }

    public String excluir(int identificador) {
        if (identificador < 0 || identificador >= 100000) {
            return "Ação inexistente";
        }

        boolean excluido = repositorioAcao.excluir(identificador);

        if (excluido) {
            return null;
        } else {
            return "Ação inexistente";
        }
    }

    public Acao buscar(int identificador) {
        if (identificador < 0 || identificador >= 100000) {
            return null;
        }

        return repositorioAcao.buscar(identificador);
    }
}
