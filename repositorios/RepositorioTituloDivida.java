package br.com.cesarschool.poo.titulos.repositorios;

import java.io.*;
import java.time.LocalDate;

import br.com.cesarschool.poo.titulos.entidades.TituloDivida;

public class RepositorioTituloDivida {

	private final String arquivoDivida = "TituloDivida.txt";

	// Construtor para garantir a criação do arquivo, se não existir
	public RepositorioTituloDivida() {
		File arquivo = new File(arquivoDivida);
		try {
			if (!arquivo.exists()) {
				arquivo.createNewFile(); // Cria o arquivo caso não exista
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Incluir
	public boolean incluir(TituloDivida tituloDivida) {
		if (buscar(tituloDivida.getIdentificador()) != null) {
			return false;
		}

		try (BufferedWriter bw = new BufferedWriter(new FileWriter(arquivoDivida, true))) {
			bw.write(tituloDivida.getIdentificador() + ";" + tituloDivida.getNome() + ";"
					+ tituloDivida.getDataValidade() + ";" + tituloDivida.getTaxaJuros());
			bw.newLine();
		} catch (IOException erro) {
			erro.printStackTrace();
		}

		return true;
	}

	// Alterar
	public boolean alterar(TituloDivida tituloDivida) {
		String[] linhas = null;

		try (BufferedReader br = new BufferedReader(new FileReader(arquivoDivida))) {
			int qtdLinhas = (int) br.lines().count();
			linhas = new String[qtdLinhas];
			br.close(); // Fechar o BufferedReader após contar as linhas

			// Ler as linhas novamente para preencher o array
			try (BufferedReader br2 = new BufferedReader(new FileReader(arquivoDivida))) {
				String linha;
				int i = 0;
				while ((linha = br2.readLine()) != null) {
					linhas[i++] = linha;
				}
			}
		} catch (IOException erro) {
			erro.printStackTrace();
		}

		boolean troca = false;
		if (linhas != null) {
			try (BufferedWriter bw = new BufferedWriter(new FileWriter(arquivoDivida))) {
				int identificador = tituloDivida.getIdentificador();
				for (String linha : linhas) {
					if (linha != null) {
						String[] elementos = linha.split(";");
						int identificadorAtual = Integer.parseInt(elementos[0]);
						if (identificador == identificadorAtual) {
							bw.write(identificadorAtual + ";" + tituloDivida.getNome() + ";"
									+ tituloDivida.getDataValidade() + ";" + tituloDivida.getTaxaJuros());
							troca = true;
						} else {
							bw.write(linha);
						}
						bw.newLine();
					}
				}
			} catch (IOException erro) {
				erro.printStackTrace();
			}
		}
		return troca;
	}

	// Excluir
	public boolean excluir(int identificador) {
		String[] linhas = null;

		try (BufferedReader br = new BufferedReader(new FileReader(arquivoDivida))) {
			int qtdLinhas = (int) br.lines().count();
			linhas = new String[qtdLinhas];
			br.close();

			// Ler as linhas novamente para preencher o array
			try (BufferedReader br2 = new BufferedReader(new FileReader(arquivoDivida))) {
				String linha;
				int i = 0;
				while ((linha = br2.readLine()) != null) {
					linhas[i++] = linha;
				}
			}
		} catch (IOException erro) {
			erro.printStackTrace();
		}

		boolean excluido = false;
		if (linhas != null) { // Verifica se as linhas foram lidas com sucesso
			try (BufferedWriter bw = new BufferedWriter(new FileWriter(arquivoDivida))) {
				for (String linha : linhas) {
					if (linha != null) {
						String[] elementos = linha.split(";");
						int identificadorAtual = Integer.parseInt(elementos[0]);
						if (identificadorAtual == identificador) {
							excluido = true;
						} else {
							bw.write(linha);
							bw.newLine();
						}
					}
				}
			} catch (IOException erro) {
				erro.printStackTrace();
			}
		}
		return excluido;
	}

	// Buscar
	public TituloDivida buscar(int identificador) {
		try (BufferedReader br = new BufferedReader(new FileReader(arquivoDivida))) {
			String linha;
			while ((linha = br.readLine()) != null) {
				String[] elementos = linha.split(";");
				int identificadorLinha = Integer.parseInt(elementos[0]);
				if (identificadorLinha == identificador) {
					return new TituloDivida(identificador, elementos[1],
							LocalDate.parse(elementos[2]),
							Double.parseDouble(elementos[3]));
				}
			}
		} catch (IOException erro) {
			erro.printStackTrace();
		}
		return null;
	}
}
