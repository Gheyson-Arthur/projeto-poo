package br.com.cesarschool.poo.titulos.repositorios;

import java.time.LocalDate;
import java.io.*;
import java.util.ArrayList;
import java.util.List;


import br.com.cesarschool.poo.titulos.entidades.Acao;

 //   1;PETROBRAS;2024-12-12;30.33
 //   2;BANCO DO BRASIL;2026-01-01;21.21
 //   3;CORREIOS;2027-11-11;6.12

public class RepositorioAcao {

	private final String arquivoAcao = "Acao.txt";

	public RepositorioAcao() {
		File arquivo = new File(arquivoAcao);
		try {
			if (!arquivo.exists()) {
				arquivo.createNewFile(); // Cria o arquivo caso não exista
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Incluir
	public boolean incluir(Acao acao) {

		if((buscar(acao.getIdentificador())) != null){
			return false;
		}

			try(BufferedWriter br = new BufferedWriter(new FileWriter(arquivoAcao, true))){

			br.write(acao.getIdentificador() + ";" + acao.getNome() + ";" + acao.getDataValidade() + ";" + acao.getValorUnitario());
			br.newLine();

		}catch(IOException erro) {
            erro.printStackTrace();
		}
		return true;

	}

	// Alterar
	public boolean alterar(Acao acao) {

		// Ler todas as linhas e salvar em linhas
		String[] linhas = null;

		try (BufferedReader br = new BufferedReader(new FileReader(arquivoAcao))) {

			// Quantas linhas tem no arquivo
			int numeroLinhas = (int)br.lines().count();

            linhas = new String[numeroLinhas];

			br.close();

			try (BufferedReader br2 = new BufferedReader(new FileReader(arquivoAcao))) {

				String linha;
				int i = 0;
	
				// Preencher a array Linhas com as linhas do arquivo
				while ((linha = br2.readLine()) != null) {
					linhas[i++] = linha;
				}
			}
            

        } catch (IOException erro) {
            erro.printStackTrace();
        }

		int identificador = acao.getIdentificador();

		boolean flag = false;

		// Escrever o arquivo dnv, usando o array linhas
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(arquivoAcao))) {

            for (String linha : linhas) {

                String[] elementos = linha.split(";");
                int identificadorAtual = Integer.parseInt(elementos[0]);

                if (identificadorAtual == identificador) {
                    flag = true; 
					bw.write(acao.getIdentificador() + ";" + acao.getNome() + ";" + acao.getDataValidade() + ";" + acao.getValorUnitario()); 

                } else {
					// Se o identificador não for igual, escreve no arquivo a linha
                    bw.write(linha); 
                    
                }
				bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return flag;
	}

	// Excluir
	public boolean excluir(int identificador) {
		// Ler todas as linhas e salvar em linhas
		List<String> linhas = new ArrayList<>();

		try (BufferedReader br = new BufferedReader(new FileReader(arquivoAcao))) {
			String linha;

			while ((linha = br.readLine()) != null) {
				linhas.add(linha); // Adiciona linha ao ArrayList
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		boolean excluido = false;

		// Escrever o arquivo novamente, sem a linha que deve ser excluída
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(arquivoAcao))) {
			for (String linha : linhas) {
				String[] elementos = linha.split(";");
				int identificadorAtual = Integer.parseInt(elementos[0]);

				// Se o identificador for igual, ignora essa linha
				if (identificadorAtual == identificador) {
					excluido = true; // Ação encontrada
					continue; // Pula
				}
				// Se o identificador não for igual, escreve no arquivo a linha
				bw.write(linha);
				bw.newLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return excluido;
	}

	// Buscar
	public Acao buscar(int identificador) {

		// br será um leitor de buffer deste arquivo.txt
		try(BufferedReader br = new BufferedReader(new FileReader(arquivoAcao))){

			String linha;

			// Ler linha por linha
			while((linha = br.readLine()) != null){

				String[] elementos = linha.split(";");

				int indentificadorLinha = Integer.parseInt(elementos[0]);

				if(identificador == indentificadorLinha){

					return new Acao(indentificadorLinha, elementos[1], LocalDate.parse(elementos[2]), Double.parseDouble(elementos[3]));

				}

			}
		}catch(IOException erro) {
            erro.printStackTrace();
		}
		return null;
	}
}

