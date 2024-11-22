import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;

public class CasaDeRepousoMain {
    static Scanner scan = new Scanner(System.in);
    static StringBuffer memoriaPessoas = new StringBuffer();
    static StringBuffer memoriaAcomodacoes = new StringBuffer();
    static final String ARQUIVO_PESSOAS = "Pessoas.txt";
    static final String ARQUIVO_ACOMODACAO = "Acomodacao.txt";
    public static void main(String[] args) {
        System.out.println("PROGRAMA DESENVOLVIDO POR BERNARDO AUGUSTO LODI E JOÃO VICTOR OLIVEIRA.");
        char menu = 0;
        do {
            System.out.println("\n Digite a opçao que deseja: "+
            "\n1 - Adicionar nova acomodação. "+
            "\n2 - Adicionar nova pessoa. "+
            "\n3 - Alterar dados de alguma pessoa. "+
            "\n4 - Excluir dados de alguma pessoa. "+
            "\n5 - Consultar todos os dados. "+
            "\n6 - Consultar um dado expecífico. "+
            "\n7 - Sair. "+
            "\n>>> Opção:");
            menu = scan.next().charAt(0);
            switch (menu) {
                case '1':
                    adicionarAcomodacao();
                    break;
                case '2':
                    adicionarPessoa();
                    break;
                case '3':
                    menuAlterarDadosPessoa();
                    break;
                case '4':
                    excluirDadosPessoa();
                    break;
                case '7':
                    System.out.println();
                    break;
                default:
                    break;
            }
        } while (menu != '7');
    }

    static void adicionarAcomodacao() {
        String tipo;
        boolean estaDisponivel = true;
        double preco;
        int numAcomodacao;
        try {
            while (true) {
                System.out.println("\nInforme o tipo do quarto: (individual, duplo ou triplo)");
                tipo = scan.next().toLowerCase();
                if (tipo.equals("individual")||tipo.equals("duplo")||tipo.equals("triplo")) {
                    break;
                } else {
                    System.out.println("\nTipo de quarto inválido, tente novamente.");
                }
            }
            System.out.println("\nInforme o número do quarto: ");
            numAcomodacao = scan.nextInt();
            System.out.println("\nInforme o preço mensal do quarto: ");
            preco = scan.nextDouble();

            Acomodacao acomodacao = new Acomodacao(estaDisponivel, preco, tipo, numAcomodacao);
            memoriaAcomodacoes.append(acomodacao.toString());
            gravarDados(ARQUIVO_ACOMODACAO, memoriaAcomodacoes);
        } catch (Exception e) {
            System.out.println("\nErro de gravação");
        }
    }
    
    static void adicionarPessoa() {
        String nome, dataNascimento, dataEntrada, numAcomodacao;
        try {
            if (existemAcomodacoesDisponiveis()) {
                while (true) {
                    System.out.println("\nQuartos disponíveis: ");
                    mostrarAcomodacoesDisponiveis();
                    System.out.println("\nInforme o número do quarto dessa pessoa: ");
                    numAcomodacao = scan.next();
                    if (validarAcomodacao(numAcomodacao)) {
                        break;
                    } else {
                        System.out.println("\nNúmero inválido, tente novamente.");
                    }
                }
                System.out.println("\nInforme o nome da nova pessoa: ");
                nome = scan.next();
                System.out.println("\nInforme a data de nascimento: ");
                dataNascimento = scan.next();
                System.out.println("\nInforme a data de entrada da pessoa: ");
                dataEntrada = scan.next();
                
                Pessoa pessoa = new Pessoa(nome, dataNascimento, dataEntrada, Integer.parseInt(numAcomodacao));
                memoriaPessoas.append(pessoa.toString());
                adicionarUmaPessoaNaAcomodacao(numAcomodacao);
                gravarDados(ARQUIVO_PESSOAS, memoriaPessoas);
            } else {
                System.out.println("\nNão há acomodacoes disponíveis. ");
            }
        } catch (Exception e) {
            System.out.println("\nErro de gravação");
        }
    }

    public static void mostrarAcomodacoesDisponiveis() {
        int inicio, fim, ultimo, primeiro;
        String numQuarto, valor, disponivel, tipo, pessoasNoQuarto;
        iniciarArquivo(ARQUIVO_ACOMODACAO, memoriaAcomodacoes);
        if (memoriaAcomodacoes.length() > 0) {
            inicio = 0;
            while ((inicio != memoriaAcomodacoes.length())) {
				ultimo = memoriaAcomodacoes.indexOf ("\t", inicio);
				numQuarto = memoriaAcomodacoes.substring(inicio, ultimo);

				primeiro = ultimo + 1;
				ultimo = memoriaAcomodacoes.indexOf ("\t", primeiro); 
				disponivel = memoriaAcomodacoes.substring(primeiro, ultimo);	

				primeiro = ultimo + 1;
                ultimo = memoriaAcomodacoes.indexOf ("\t", primeiro); 
				valor = memoriaAcomodacoes.substring(primeiro, ultimo);	
                
                primeiro = ultimo + 1;
                ultimo = memoriaAcomodacoes.indexOf ("\t", primeiro);
				tipo = memoriaAcomodacoes.substring(primeiro, ultimo);

                primeiro = ultimo + 1;
                fim = memoriaAcomodacoes.indexOf ("\n", primeiro);
				pessoasNoQuarto = memoriaAcomodacoes.substring(primeiro, fim);

				inicio = fim + 1;

                if(disponivel.equals("true")){
                    System.out.println("\n *** Quarto "+ numQuarto + " ***" +
                    "\nValor: " + valor +
                    "\nTipo: "+ tipo +
                    "\nPessoas no quarto: "+ pessoasNoQuarto);
                }
			}
        } else {
            System.out.println("Nenhuma acomodaçao cadastrada!");
        }
    }

    public static void mostrarPessoasCadastradas() {
        int inicio, fim, ultimo, primeiro;
        String nome, dataNasc, dataEntrada, quartoVinculado;
        iniciarArquivo(ARQUIVO_PESSOAS, memoriaPessoas);
        if (memoriaPessoas.length() > 0) {
            inicio = 0;
            while ((inicio != memoriaPessoas.length())) {
				ultimo = memoriaPessoas.indexOf ("\t", inicio);
				nome = memoriaPessoas.substring(inicio, ultimo);

				primeiro = ultimo + 1;
				ultimo = memoriaPessoas.indexOf ("\t", primeiro); 
				dataNasc = memoriaPessoas.substring(primeiro, ultimo);	

				primeiro = ultimo + 1;
                ultimo = memoriaPessoas.indexOf ("\t", primeiro); 
				dataEntrada = memoriaPessoas.substring(primeiro, ultimo);	

                primeiro = ultimo + 1;
                fim = memoriaPessoas.indexOf ("\n", primeiro);
				quartoVinculado = memoriaPessoas.substring(primeiro, fim);

				inicio = fim + 1;

                System.out.println("\n *** Cliente "+ nome + " ***" +
                "\nData de Nascimento: " + dataNasc +
                "\nData de Entrada: "+ dataEntrada +
                "\nQuarto do cliente: "+ quartoVinculado);
			}
        } else {
            System.out.println("Nenhuma pessoa cadastrada!");
        }
    }

    public static boolean existemAcomodacoesDisponiveis() {
        int inicio, fim, ultimo, primeiro;
        iniciarArquivo(ARQUIVO_ACOMODACAO, memoriaAcomodacoes);
        String disponivel;
        boolean estaDisponivel = false;
        if (memoriaAcomodacoes.length() > 0) {
            inicio = 0;
            while ((inicio != memoriaAcomodacoes.length())) {
				ultimo = memoriaAcomodacoes.indexOf ("\t", inicio);

				primeiro = ultimo + 1;
				ultimo = memoriaAcomodacoes.indexOf ("\t", primeiro);
				disponivel = memoriaAcomodacoes.substring(primeiro, ultimo);

                primeiro = ultimo + 1;
                fim = memoriaAcomodacoes.indexOf ("\n", primeiro);
				inicio = fim + 1;
                if (disponivel.equals("true")) {
                    estaDisponivel = true;
                    break;
                }
			}
        } else {
            System.out.println("Nenhuma acomodaçao cadastrada!");
        }
        return estaDisponivel;
    }

    public static boolean validarAcomodacao (String numAcomodacao) {
        int inicio, fim, ultimo, primeiro;
        iniciarArquivo(ARQUIVO_ACOMODACAO, memoriaAcomodacoes);
        String numAcomodacaoTemp, estaDisponivel;
        boolean numeroDeQuartoDisponivel = false;
        if (memoriaAcomodacoes.length() > 0) {
            inicio = 0;
            while ((inicio != memoriaAcomodacoes.length())) {
				ultimo = memoriaAcomodacoes.indexOf ("\t", inicio);
                numAcomodacaoTemp = memoriaAcomodacoes.substring(inicio, ultimo);

				primeiro = ultimo + 1;
				ultimo = memoriaAcomodacoes.indexOf ("\t", primeiro);
				estaDisponivel = memoriaAcomodacoes.substring(primeiro, ultimo);

                primeiro = ultimo + 1;
                fim = memoriaAcomodacoes.indexOf ("\n", primeiro);
				inicio = fim + 1;
                if (numAcomodacaoTemp.equals(numAcomodacao) && estaDisponivel.equals("true")) {
                    numeroDeQuartoDisponivel = true;
                    break;
                }
			}
        } else {
            System.out.println("Nenhuma acomodaçao cadastrada!");
        }
        return numeroDeQuartoDisponivel;
    }

    public static boolean validarPessoa (String nome) {
        int inicio, fim, ultimo, primeiro;
        iniciarArquivo(ARQUIVO_PESSOAS, memoriaPessoas);
        String nomeTemp;
        boolean nomeExiste = false;
        
        inicio = 0;
        while ((inicio != memoriaPessoas.length())) {
			ultimo = memoriaPessoas.indexOf ("\t", inicio);
            nomeTemp = memoriaPessoas.substring(inicio, ultimo);

            primeiro = ultimo + 1;
            fim = memoriaPessoas.indexOf ("\n", primeiro);
			inicio = fim + 1;
            if (nomeTemp.equalsIgnoreCase(nome) ) {
                nomeExiste = true;
                break;
            }
		}
        return nomeExiste;
    }

    static void iniciarArquivo(String filename, StringBuffer memoria){
        try {
            BufferedReader arquivoEntrada = new BufferedReader(new FileReader(filename));
            String linha = "";
            memoria.delete(0,memoria.length());
            do {
                linha = arquivoEntrada.readLine();
                if (linha != null) {
                    memoria.append (linha + "\n");
                }
            } while (linha != null);
            arquivoEntrada.close();
        } catch (FileNotFoundException erro){
            System.out.println("\nArquivo não encontrado");
        } catch (Exception e){
            System.out.println("\nErro de Leitura!");
        }
    }

    public static void gravarDados(String filename, StringBuffer memoria) {
        try {
            BufferedWriter arquivoSaida;
            arquivoSaida = new BufferedWriter(new FileWriter(filename));
            arquivoSaida.write(memoria.toString());
            arquivoSaida.flush();
            arquivoSaida.close();
        } catch (Exception e) {
            System.out.println("\nErro de gravacao!");
        }
    }

    static void menuAlterarDadosPessoa() {
        String pessoaASerAlterada, opcao, novaInfo;
        mostrarPessoasCadastradas();
        boolean naoAlterou = true;

        if (memoriaPessoas.length() > 0) {
            while (true) {
                System.out.println("\nInforme o nome da pessoa que deseja alterar alguma informação: ");
                pessoaASerAlterada = scan.next();

                if (validarPessoa(pessoaASerAlterada)) {
                    break;
                } else {
                    System.out.println("\nNome informado nao cadastrado, tente novamente!");
                }
            }
            
            do {
                System.out.println("\nQual informação deseja alterar?" +
                "\n1 - Nome" +
                "\n2 - Data de Nascimento" +
                "\n3 - Data de Entrada" +
                "\n4 - Quarto do cliente");

                opcao = scan.next();

                switch (opcao) {
                    case "1":
                        System.out.println("\nInforme o novo nome: ");
                        novaInfo = scan.next();
                        alterarDadosPessoa(pessoaASerAlterada, novaInfo, "nome");
                        naoAlterou = false;
                        break;
                    case "2":
                        System.out.println("\nInforme a nova data de nascimento: ");
                        novaInfo = scan.next();
                        alterarDadosPessoa(pessoaASerAlterada, novaInfo, "dataNascimento");
                        naoAlterou = false;
                        break;
                    case "3":
                        System.out.println("\nInforme a nova data de entrada: ");
                        novaInfo = scan.next();
                        alterarDadosPessoa(pessoaASerAlterada, novaInfo, "dataEntrada");
                        naoAlterou = false;
                        break;
                    case "4":
                        if (existemAcomodacoesDisponiveis()) {
                            while (true) {
                                mostrarAcomodacoesDisponiveis();
                                System.out.println("\nInforme o novo quarto do cliente: ");
                                novaInfo = scan.next();
                                if (validarAcomodacao(novaInfo)) {
                                    alterarDadosPessoa(pessoaASerAlterada, novaInfo, "quarto");
                                    break;
                                } else {
                                    System.out.println("\nNúmero inválido, tente novamente.");
                                }
                            }
                        } else {
                            System.out.println("\nNão há acomodacoes disponíveis. ");
                        }
                        naoAlterou = false;
                        break;
                    default:
                        System.out.println("\nOpção Inválida!");
                }
            } while (naoAlterou);
        }
    }

    static void alterarDadosPessoa(String nomeProcurado, String novaInfo, String campoASerAlterado){
		int inicio, fim, ultimo, primeiro;
        String nome, dataNasc, dataEntrada, quartoVinculado;

		inicio = 0;
		while (inicio != memoriaPessoas.length()) {
			ultimo = memoriaPessoas.indexOf ("\t", inicio);
			nome = memoriaPessoas.substring(inicio, ultimo);

			primeiro = ultimo + 1;
			ultimo = memoriaPessoas.indexOf ("\t", primeiro); 
			dataNasc = memoriaPessoas.substring(primeiro, ultimo);	

			primeiro = ultimo + 1;
            ultimo = memoriaPessoas.indexOf ("\t", primeiro); 
			dataEntrada = memoriaPessoas.substring(primeiro, ultimo);	

            primeiro = ultimo + 1;
            fim = memoriaPessoas.indexOf ("\n", primeiro);
			quartoVinculado = memoriaPessoas.substring(primeiro, fim);

			Pessoa pessoa = new Pessoa(nome, dataNasc, dataEntrada, Integer.parseInt(quartoVinculado));

			if (nomeProcurado.equalsIgnoreCase(pessoa.getNome())) {
                switch (campoASerAlterado) {
                    case "nome":
                        pessoa.setNome(novaInfo);
                        break;
                    case "dataNascimento":
                        pessoa.setDataNascimento(novaInfo);
                        break;
                    case "dataEntrada":
                        pessoa.setDataEntrada(novaInfo);
                        break;
                    case "quarto":
                        pessoa.setNumAcomodacao(Integer.parseInt(novaInfo));
                        break;
                }
				
				memoriaPessoas.replace(inicio, fim+1, pessoa.toString());
				gravarDados(ARQUIVO_PESSOAS, memoriaPessoas);
                System.out.println("\nDado alterado com sucesso! ");
			}
			inicio = fim + 1; 
		}
    }

    static void adicionarUmaPessoaNaAcomodacao(String numAcomodacao){
		int inicio, fim, ultimo, primeiro;
        String numAcomodacaoTemp, estaDisponivel, valor, tipo, pessoasNaAcomodacao;
        int numPessoasNaAcomodacao, maxPessoas = 0;
        boolean disponivel;

		inicio = 0;
		while (inicio != memoriaAcomodacoes.length()) {
			ultimo = memoriaAcomodacoes.indexOf ("\t", inicio);
			numAcomodacaoTemp = memoriaAcomodacoes.substring(inicio, ultimo);

			primeiro = ultimo + 1;
			ultimo = memoriaAcomodacoes.indexOf ("\t", primeiro); 
			estaDisponivel = memoriaAcomodacoes.substring(primeiro, ultimo);	

			primeiro = ultimo + 1;
            ultimo = memoriaAcomodacoes.indexOf ("\t", primeiro); 
			valor = memoriaAcomodacoes.substring(primeiro, ultimo);

            primeiro = ultimo + 1;
            ultimo = memoriaAcomodacoes.indexOf ("\t", primeiro); 
			tipo = memoriaAcomodacoes.substring(primeiro, ultimo);

            primeiro = ultimo + 1;
            fim = memoriaAcomodacoes.indexOf ("\n", primeiro);
			pessoasNaAcomodacao = memoriaAcomodacoes.substring(primeiro, fim);

            if (numAcomodacao.equals(numAcomodacaoTemp)) {
                numPessoasNaAcomodacao = Integer.parseInt(pessoasNaAcomodacao) + 1;
                disponivel = estaDisponivel.equals("true");

                if (tipo.equals("individual")) maxPessoas = 1;
                if (tipo.equals("duplo")) maxPessoas = 2;
                if (tipo.equals("triplo")) maxPessoas = 3;
            
                if (numPessoasNaAcomodacao == maxPessoas) disponivel = false;

                String acomodacao = numAcomodacaoTemp+"\t"+disponivel+"\t"+valor+"\t"+tipo+"\t"+numPessoasNaAcomodacao+"\n";

                memoriaAcomodacoes.replace(inicio, fim + 1, acomodacao);
                gravarDados(ARQUIVO_ACOMODACAO, memoriaAcomodacoes);
                break;
            }
            inicio = fim + 1; 
		}
	}

    static void excluirDadosPessoa() {
        int inicio, fim, ultimo, primeiro;
        String pessoaASerExcluida;
        String nome, dataNasc, dataEntrada, quartoVinculado;
		boolean achou=false;
        char resp;

        mostrarPessoasCadastradas();

        if (memoriaPessoas.length() != 0) { 
            System.out.println("\nInforme o nome da pessoa que deseja excluir: ");
            pessoaASerExcluida = scan.next();
			inicio = 0;
			while ((inicio != memoriaPessoas.length()) && (!achou)) {
				ultimo = memoriaPessoas.indexOf ("\t", inicio);
				nome = memoriaPessoas.substring(inicio, ultimo);

                primeiro = ultimo + 1;
				ultimo = memoriaPessoas.indexOf ("\t", primeiro); 
				dataNasc = memoriaPessoas.substring(primeiro, ultimo);	

				primeiro = ultimo + 1;
                ultimo = memoriaPessoas.indexOf ("\t", primeiro); 
				dataEntrada = memoriaPessoas.substring(primeiro, ultimo);	

                primeiro = ultimo + 1;
                fim = memoriaPessoas.indexOf ("\n", primeiro);
				quartoVinculado = memoriaPessoas.substring(primeiro, fim);

                Pessoa pessoa = new Pessoa(nome, dataNasc, dataEntrada, Integer.parseInt(quartoVinculado));
                if (nome.equalsIgnoreCase(pessoaASerExcluida)){
					System.out.println("\n\nDeseja excluir?\nDigite S ou N:\n\n"+
							"\nNome: "+pessoa.getNome()+
							"\nData Nascimento: " +pessoa.getDataNascimento()+
							"\nData de Entrada: "+pessoa.getDataEntrada() + 
                            "\nQuarto: "+pessoa.getNumAcomodacao());
					resp = Character.toUpperCase(scan.next().charAt(0));
					if (resp == 'S'){
						memoriaPessoas.delete (inicio, fim + 1);	
						System.out.println("Registro excluido.");
						gravarDados(ARQUIVO_PESSOAS, memoriaPessoas); 
					} else{
						System.out.println("Exclusao cancelada.");
					}
					achou = true;
				}
                inicio = fim + 1;
            }
            if (!achou){
				System.out.println("\nPessoa nao encontrada! ");
			}
        }
    }

    public static void consultarDados(String filename) {
        
    }       
}