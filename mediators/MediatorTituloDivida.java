package br.com.cesarschool.poo.titulos.mediators;

import br.com.cesarschool.poo.titulos.entidades.TituloDivida;
import br.com.cesarschool.poo.titulos.repositorios.RepositorioTituloDivida;

import java.time.LocalDate;

public class MediatorTituloDivida {
    private final RepositorioTituloDivida repositorioTituloDivida = new RepositorioTituloDivida();

    private static MediatorTituloDivida mediatorTituloDivida;

    private MediatorTituloDivida() {
    }

    public static MediatorTituloDivida getMediatorTituloDivida() {
        if (mediatorTituloDivida == null) {
            mediatorTituloDivida = new MediatorTituloDivida();
        }

        return mediatorTituloDivida;
    }


    String validar(TituloDivida titulo) {
        int identificador = titulo.getIdentificador();
        if (identificador < 0 || identificador >= 100000) {
            return "Identificador deve estar entre 1 e 99999.";
        }

        String nome = titulo.getNome();
        if(nome == null || nome.trim().isEmpty()) {
            return "Nome deve ser preenchido.";
        }


        if(nome.length() < 10 || nome.length() > 100) {
            return "Nome deve ter entre 10 e 100 caracteres.";
        }

        LocalDate dataValidade = titulo.getDataValidade();
        LocalDate dataMinima = LocalDate.now().plusDays(180);
        if(dataValidade == null || dataValidade.isBefore(dataMinima) || dataValidade.isEqual(dataMinima)) {
            return "Data de validade deve ter pelo menos 180 dias na frente da data atual.";
        }

        double taxa = titulo.getTaxaJuros();
        if(taxa < 0) {
            return "Taxa deve maior ou igual a zero.";
        }

        return null;
    }

    public String incluir(TituloDivida titulo) {
        String validator = validar(titulo);

        if (validator == null) {
            boolean incluido = repositorioTituloDivida.incluir(titulo);

            if (incluido) {
                return null;
            } else {
                return "Título já existente";
            }
        } else {
            return validator;
        }
    }

    public String alterar(TituloDivida titulo) {
        String validator = validar(titulo);

        if (validator == null) {
            boolean alterado = repositorioTituloDivida.alterar(titulo);

            if (alterado) {
                return null;
            } else {
                return "Título inexistente";
            }
        } else {
            return validator;
        }
    }

    public String excluir(int identificador) {
        if (identificador < 0 || identificador >= 100000) {
            return "Título inexistente";
        }

        boolean excluido = repositorioTituloDivida.excluir(identificador);

        if (excluido) {
            return null;
        } else {
            return "Título inexistente";
        }
    }

    public TituloDivida buscar(int identificador) {
        if (identificador < 0 || identificador >= 100000) {
            return null;
        }

        return repositorioTituloDivida.buscar(identificador);
    }
}
