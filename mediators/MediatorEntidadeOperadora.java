package br.com.cesarschool.poo.titulos.mediators;

import br.com.cesarschool.poo.titulos.entidades.EntidadeOperadora;
import br.com.cesarschool.poo.titulos.repositorios.RepositorioEntidadeOperadora;

public class MediatorEntidadeOperadora {
    private final RepositorioEntidadeOperadora repositorioEntidadeOperadora = new RepositorioEntidadeOperadora();

    private static MediatorEntidadeOperadora mediatorEntidadeOperadora;

    private MediatorEntidadeOperadora() {}

    public static MediatorEntidadeOperadora getMediatorEntidadeOperadora() {
        if (mediatorEntidadeOperadora == null) {
            mediatorEntidadeOperadora = new MediatorEntidadeOperadora();
        }

        return mediatorEntidadeOperadora;
    }

    private String validar(EntidadeOperadora entidadeOperadora) {
        long identificador = entidadeOperadora.getIdentificador();
        if (identificador < 0 || identificador > 100000) {
            return "Identificador deve estar entre 100 e 1000000.";
        }

        String nome = entidadeOperadora.getNome();
        if(nome == null || nome.isEmpty()) {
            return "Nome deve ser preenchido.";
        }

        if(nome.length() < 5 || nome.length() > 60) {
            return "Nome deve estar entre 10 e 100 caracteres.";
        }

        return null;
    }

    public String incluir(EntidadeOperadora entidade) {
        String validator = validar(entidade);

        if(validator == null) {
            boolean incluido = repositorioEntidadeOperadora.incluir(entidade);

            if(incluido) {
                return null;
            } else {
                return "Entidade já existente.";
            }
        } else {
            return validator;
        }
    }

    public String alterar(EntidadeOperadora entidade) {
        String validator = validar(entidade);

        if(validator == null) {
            boolean alterado = repositorioEntidadeOperadora.alterar(entidade);

            if(alterado) {
                return null;
            } else {
                return "Entidade inexistente.";
            }
        } else {
            return validator;
        }
    }

    public String excluir(int identificador) {
        if (identificador < 0 || identificador >= 100000) {
            return "Entidade inexistente";
        }

        boolean excluido = repositorioEntidadeOperadora.excluir(identificador);

        if (excluido) {
            return null;
        } else {
            return "Entidade inexistente";
        }
    }

    public EntidadeOperadora buscar(int identificador) {
        if (identificador < 0 || identificador >= 100000) {
            return null;
        }

        return repositorioEntidadeOperadora.buscar(identificador);
    }
}

