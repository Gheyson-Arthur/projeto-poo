package br.com.cesarschool.poo.titulos.repositorios;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import br.com.cesarschool.poo.titulos.entidades.Acao;
import br.com.cesarschool.poo.titulos.entidades.EntidadeOperadora;
import br.com.cesarschool.poo.titulos.entidades.TituloDivida;
import br.com.cesarschool.poo.titulos.entidades.Transacao;
import br.com.cesarschool.poo.titulos.mediators.MediatorAcao;
import br.com.cesarschool.poo.titulos.mediators.MediatorTituloDivida;

public class RepositorioTransacao {

	private final String arquivoTransacao = "Transacao.txt";
	private final MediatorAcao mediatorAcao = MediatorAcao.getMediatorAcao();
	private final MediatorTituloDivida mediatorTituloDivida = MediatorTituloDivida.getMediatorTituloDivida();

	// Construtor para garantir a criação do arquivo, se não existir
	public RepositorioTransacao() {
		File arquivo = new File(arquivoTransacao);
		try {
			if (!arquivo.exists()) {
				arquivo.createNewFile(); // Cria o arquivo caso não exista
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void incluir(Transacao transacao) {

		try(BufferedWriter bw = new BufferedWriter(new FileWriter(arquivoTransacao, true))){

			String linha = transacao.getEntidadeCredito().getIdentificador() + ";" +
			transacao.getEntidadeCredito().getNome() + ";" +
			transacao.getEntidadeCredito().getAutorizadoAcao() + ";" +
			transacao.getEntidadeCredito().getSaldoAcao() + ";" +
			transacao.getEntidadeCredito().getSaldoTituloDivida() + ";" +
			transacao.getEntidadeDebito().getIdentificador() + ";" +
			transacao.getEntidadeDebito().getNome() + ";" +
			transacao.getEntidadeDebito().getAutorizadoAcao() + ";" +
			transacao.getEntidadeDebito().getSaldoAcao() + ";" +
			transacao.getEntidadeDebito().getSaldoTituloDivida() + ";" +
			transacao.getAcao().getIdentificador() + ";" +
			transacao.getAcao().getNome() + ";" +
			transacao.getAcao().getDataValidade() + ";" +
			transacao.getAcao().getValorUnitario() + ";" +
			transacao.getTituloDivida().getIdentificador() + ";" +
			transacao.getTituloDivida().getNome() + ";" +
			transacao.getTituloDivida().getDataValidade() + ";" +
			transacao.getTituloDivida().getTaxaJuros() + ";" +
			transacao.getValorOperacao() + ";" +
			transacao.getDataHoraOperacao().toString(); // Converte LocalDateTime para String


			bw.write(linha);
			bw.newLine();

		}catch(IOException erro){
			erro.printStackTrace();
		}
	}

	// Buscar por Entidade Credora
	public Transacao[] buscarPorEntidadeCredora(int identificadorEntidadeCredito) {
		Transacao[] transacoes = new Transacao[100];
		int i = 0;

		try(BufferedReader br = new BufferedReader(new FileReader(arquivoTransacao))){

			String linha = null;
			while((linha = br.readLine()) != null){

				String[] elementos = linha.split(";");

				// Entidade Credito
				long identificadorEntidadeAtual = Long.parseLong(elementos[0]);
				String nomeEntidadeCredito = elementos[1];
				boolean autorizadoAcaoCredito = Boolean.parseBoolean(elementos[2]);

				// Entidade Debito
				long identificadorEntidadeDebito = Long.parseLong(elementos[5]);
				String nomeEntidadeDebito = elementos[6];
				boolean autorizadoAcaoDebito = Boolean.parseBoolean(elementos[7]);

				// BUSCA A AÇÃO USANDO O 11º ELEMENTO DO ARRAY
				Acao acao = mediatorAcao.buscar(Integer.parseInt(elementos[10]));
				TituloDivida tituloDivida = null;

				if (acao != null) {
					int identificadorAcao = Integer.parseInt(elementos[10]);
					String nomeAcao = elementos[11];
					LocalDate dataValidadeAcao = LocalDate.parse(elementos[12]);
					double valorUnitario = Double.parseDouble(elementos[13]);
				} else {
					// SE NÃO ACHAR UMA AÇÃO, PROCURA UM TÍTULO DÍVIDA
					tituloDivida = mediatorTituloDivida.buscar(Integer.parseInt(elementos[10]));
					if (tituloDivida != null) {
						int identificadorTituloDivida = Integer.parseInt(elementos[10]);
						String nomeTituloDivida = elementos[11];
						LocalDate dataValidadeTituloDivida = LocalDate.parse(elementos[12]);
						double taxaJuros = Double.parseDouble(elementos[13]);
					}
				}

				// O CÓDIGO ACIMA FOI NECESSÁRIO POIS NÃO TERIA COMO DIFERENCIAR UM TITULO DIVIDA DE UMA AÇÃO A PARTIR
				// DO EXEMPLO DADO NO REPOSITORIO DE TRANSAÇÃO

				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

				// Valor da Operação e Data/Hora da Operação
				double valorOperacao = Double.parseDouble(elementos[14]);
				LocalDateTime dataHoraOperacao = LocalDateTime.parse(elementos[15], formatter);

				// Verifica se o identificador da entidade credora corresponde
				if (identificadorEntidadeAtual == identificadorEntidadeCredito) {

					EntidadeOperadora entidadeCredito = new EntidadeOperadora(identificadorEntidadeCredito, nomeEntidadeCredito, autorizadoAcaoCredito);
					EntidadeOperadora entidadeDebito = new EntidadeOperadora(identificadorEntidadeDebito, nomeEntidadeDebito, autorizadoAcaoDebito);
					entidadeCredito.creditarSaldoAcao(Double.parseDouble(elementos[3]));
					entidadeCredito.creditarSaldoTituloDivida(Double.parseDouble(elementos[4]));
					entidadeDebito.creditarSaldoAcao(Double.parseDouble(elementos[8]));
	                entidadeDebito.creditarSaldoTituloDivida(Double.parseDouble(elementos[9]));

					// Cria a transação e adiciona ao array
					transacoes[i++] = new Transacao(entidadeCredito, entidadeDebito, acao, tituloDivida, valorOperacao, dataHoraOperacao);
				}
			
			}

		}catch(IOException erro){
			erro.printStackTrace();
		}

		Transacao [] listaRetorno = new Transacao[i];
		System.arraycopy(transacoes, 0, listaRetorno, 0, i);


		return listaRetorno;
	}

	// Buscar por entitade credora - debito
	public Transacao[] buscarPorEntidadeDebito(int identificadorEntidadeDebito) {

		Transacao[] transacoes = new Transacao[100];
		int i = 0;

		try(BufferedReader br = new BufferedReader(new FileReader(arquivoTransacao))){

			String linha = null;
			while((linha = br.readLine()) != null){

				String[] elementos = linha.split(";");

				// Entidade Credito
				long identificadorEntidadeCredito = Long.parseLong(elementos[0]);
				String nomeEntidadeCredito = elementos[1];
				boolean autorizadoAcaoCredito = Boolean.parseBoolean(elementos[2]);

				// Entidade Debito
				long identificadorEntidadeAtual = Long.parseLong(elementos[5]);
				String nomeEntidadeDebito = elementos[6];
				boolean autorizadoAcaoDebito = Boolean.parseBoolean(elementos[7]);
				// Ação
				Acao acao = null;
				if (!elementos[10].equals("null")) {
					int identificadorAcao = Integer.parseInt(elementos[10]);
					String nomeAcao = elementos[11];
					LocalDate dataValidadeAcao = LocalDate.parse(elementos[12]);
					double valorUnitario = Double.parseDouble(elementos[13]);
					acao = new Acao(identificadorAcao, nomeAcao, dataValidadeAcao, valorUnitario);
				}

				// Título Dívida
				TituloDivida tituloDivida = null;
				if (!elementos[14].equals("null")) {
					int identificadorTituloDivida = Integer.parseInt(elementos[10]);
					String nomeTituloDivida = elementos[11];
					LocalDate dataValidadeTituloDivida = LocalDate.parse(elementos[12]);
					double taxaJuros = Double.parseDouble(elementos[13]);
					tituloDivida = new TituloDivida(identificadorTituloDivida, nomeTituloDivida, dataValidadeTituloDivida, taxaJuros);
				}

				// Valor da Operação e Data/Hora da Operação
				double valorOperacao = Double.parseDouble(elementos[14]);
				LocalDateTime dataHoraOperacao = LocalDateTime.parse(elementos[15]);

				// Verifica se o identificador da entidade credora corresponde
				if (identificadorEntidadeAtual == identificadorEntidadeDebito) {

					EntidadeOperadora entidadeCredito = new EntidadeOperadora(identificadorEntidadeCredito, nomeEntidadeCredito, autorizadoAcaoCredito);
					EntidadeOperadora entidadeDebito = new EntidadeOperadora(identificadorEntidadeDebito, nomeEntidadeDebito, autorizadoAcaoDebito);

					// Cria a transação e adiciona ao array
					transacoes[i++] = new Transacao(entidadeCredito, entidadeDebito, acao, tituloDivida, valorOperacao, dataHoraOperacao);
				}
			}
		}catch(IOException erro){
			erro.printStackTrace();
		}

		Transacao [] listaRetorno = new Transacao[i];
		System.arraycopy(transacoes, 0, listaRetorno, 0, i);


		return listaRetorno;
	}
}
