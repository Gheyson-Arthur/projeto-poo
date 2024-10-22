package br.com.cesarschool.poo.titulos.repositorios;

import java.io.*;

import br.com.cesarschool.poo.titulos.entidades.EntidadeOperadora;

/*
 * Deve gravar em e ler de um arquivo texto chamado Acao.txt os dados dos objetos do tipo
 * Acao. Seguem abaixo exemplos de linhas.
 *
    1;PETROBRAS;2024-12-12;30.33
    2;BANCO DO BRASIL;2026-01-01;21.21
    3;CORREIOS;2027-11-11;6.12 
 * 
 * A inclus�o deve adicionar uma nova linha ao arquivo. N�o � permitido incluir 
 * identificador repetido. Neste caso, o m�todo deve retornar false. Inclus�o com 
 * sucesso, retorno true.
 * 
 * A altera��o deve substituir a linha atual por uma nova linha. A linha deve ser 
 * localizada por identificador que, quando n�o encontrado, enseja retorno false. 
 * Altera��o com sucesso, retorno true.  
 *   
 * A exclus�o deve apagar a linha atual do arquivo. A linha deve ser 
 * localizada por identificador que, quando n�o encontrado, enseja retorno false. 
 * Exclus�o com sucesso, retorno true.
 * 
 * A busca deve localizar uma linha por identificador, materializar e retornar um 
 * objeto. Caso o identificador n�o seja encontrado no arquivo, retornar null.   
 */
public class RepositorioEntidadeOperadora {

	private final String arquivo = "EntidadeOperadora.txt";

	public RepositorioEntidadeOperadora() {
		File arquivoEntidadeOperadora = new File(arquivo);
		try {
			if (!arquivoEntidadeOperadora.exists()) {
				boolean criado = arquivoEntidadeOperadora.createNewFile();
				if (criado) {
					System.out.println("Arquivo criado: " + arquivoEntidadeOperadora.getAbsolutePath());
				} else {
					System.out.println("Falha ao criar o arquivo.");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Incluir
	public boolean incluir(EntidadeOperadora entidadeOperadora) {
		// Verificar se o identificador já existe
		if (buscar(entidadeOperadora.getIdentificador()) != null) {
			System.out.println("Identificador já existe.");
			return false;
		}

		// Tentar escrever no arquivo
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(arquivo, true))) {
			bw.write(entidadeOperadora.getIdentificador() + ";" + entidadeOperadora.getNome() + ";" +
					entidadeOperadora.getAutorizadoAcao() + ";" + entidadeOperadora.getSaldoAcao() + ";" +
					entidadeOperadora.getSaldoTituloDivida());
			bw.newLine(); // Adiciona uma nova linha após a escrita
			System.out.println("Entidade incluída com sucesso.");
			return true; // Inclusão realizada com sucesso
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false; // Retorna false caso ocorra algum erro de I/O
	}

	// Alterar
	public boolean alterar(EntidadeOperadora entidadeOperadora) {

		// Ler todas as linhas e salvar em linhas
		String[] linhas = null;

		try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {

			// Quantas linhas tem no arquivo
			int numeroLinhas = (int)br.lines().count();

            linhas = new String[numeroLinhas];

			br.close();

			try (BufferedReader br2 = new BufferedReader(new FileReader(arquivo))) {

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

		long identificador = entidadeOperadora.getIdentificador();

		boolean flag = false;

		// Escrever o arquivo dnv, usando o array linhas
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(arquivo))) {

            assert linhas != null;
            for (String linha : linhas) {

                String[] elementos = linha.split(";");
                int identificadorAtual = Integer.parseInt(elementos[0]);

                if (identificadorAtual == identificador) {
                    flag = true; 
					bw.write(entidadeOperadora.getIdentificador() + ";" + entidadeOperadora.getNome() + ";" + entidadeOperadora.getAutorizadoAcao() + ";" + entidadeOperadora.getSaldoAcao() + ";" + entidadeOperadora.getSaldoTituloDivida()); 

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
	public boolean excluir(long identificador) {
		String[] linhas;

		// Leitura do arquivo para preencher o array de linhas
		try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {

			// Ler todas as linhas e contá-las
			long numeroLinhas = br.lines().count();
			linhas = new String[(int) numeroLinhas];

			// Reiniciar o leitor para preencher o array (precisamos de um novo BufferedReader)
			br.close();
			try (BufferedReader br2 = new BufferedReader(new FileReader(arquivo))) {
				String linha;
				int i = 0;

				while ((linha = br2.readLine()) != null) {
					linhas[i++] = linha;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

		boolean excluido = false;

		// Reescrita do arquivo, excluindo a linha com o identificador correspondente
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(arquivo))) {
			for (String linha : linhas) {
				if (linha == null || linha.isEmpty()) {
					continue;
				}

				String[] elementos = linha.split(";");
				long identificadorAtual = Long.parseLong(elementos[0]);

				// Se o identificador for encontrado, marcamos como excluído e não reescrevemos a linha
				if (identificadorAtual == identificador) {
					excluido = true;
					continue;
				}

				// Se o identificador não for igual, escrevemos a linha novamente
				bw.write(linha);
				bw.newLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return excluido;
	}

	// Buscar
	public EntidadeOperadora buscar(long identificador) {

		// br será um leitor de buffer deste arquivo.txt
		try(BufferedReader br = new BufferedReader(new FileReader(arquivo))){

			String linha;

			// Ler linha por linha
			while((linha = br.readLine()) != null){

				String[] elementos = linha.split(";");

				long indentificadorLinha = Long.parseLong(elementos[0]);

				if(identificador == indentificadorLinha){
               // long identificador, String nome, boolean autorizadoAcao
					return new EntidadeOperadora(indentificadorLinha, elementos[1], Boolean.parseBoolean(elementos[2]));

				}

			}
		}catch(IOException erro) {
            erro.printStackTrace();
		}
		return null;
	}
}
