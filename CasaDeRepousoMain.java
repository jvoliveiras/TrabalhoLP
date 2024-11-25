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
    static final String ARQUIVO_ACOMODACAO = "Acomodacoes.txt";
    public static void main(String[] args) {
        System.out.println("PROGRAMA DESENVOLVIDO POR BERNARDO AUGUSTO LODI E JOÃO VICTOR OLIVEIRA.");
        char menu = 0;
        iniciarArquivo(ARQUIVO_PESSOAS, memoriaPessoas);
        iniciarArquivo(ARQUIVO_ACOMODACAO, memoriaAcomodacoes);
        do {
            System.out.println("\n MENU - Casa de Repouso:"+
            "\n1 - Adicionar nova acomodação."+
            "\n2 - Adicionar nova pessoa."+
            "\n3 - Alterar dados de alguma pessoa."+
            "\n4 - Excluir dados de alguma pessoa."+
            "\n5 - Consultar todos os dados."+
            "\n6 - Consultar pessoas em uma acomodação (consulta específica)."+
            "\n7 - Sair."+
            "\n>>> Opção:");
            menu = scan.next().charAt(0);
    
            switch (menu) {
                case '1':
                    menuAdicionarAcomodacao();
                    break;
                case '2':
                    menuAdicionarPessoa();
                    break;
                case '3':
                    menuAlterarDadosPessoa();
                    break;
                case '4':
                    menuExcluirDadosPessoa();
                    break;
                case '5':
                    menuConsultarDados();
                    break;
                case '6':
                    menuConsultaEspecifica();
                    break;
                case '7':
                    System.out.println("\nPrograma encerrado!");
                    break;
                default:
                    System.out.println("\nDigite uma das opções disponíveis.");
                    break;
            }
        } while (menu != '7');
    }

    static void menuAdicionarAcomodacao() {
        String tipo, numAcomodacao;
        boolean estaDisponivel = true;
        double preco;
        int numAcomod;
        try {
            while (true) {
                System.out.println("\nInforme o tipo da acomodação (individual, duplo ou triplo) ou \"sair\" para voltar ao menu:");
                tipo = scan.next().toLowerCase();
                if (tipo.equals("individual")||tipo.equals("duplo")||tipo.equals("triplo")) {
                    break;
                } else if (tipo.equals("sair")) {
                    Integer.parseInt(tipo);
                }else {
                    System.out.println("\nTipo de acomodação inválido, tente novamente.");
                }
            }
            while (true) {
                System.out.println("\nInforme o número da acomodação: ");
                numAcomodacao = scan.next();
                try {
                    numAcomod = Integer.parseInt(numAcomodacao);
                    if(existeAcomodacao(numAcomodacao)){
                        System.out.println("\nNúmero da acomodação informada já existe!");
                    } else {
                        break;
                    }
                } catch (Exception e) {
                    System.out.println("\nDigite um número!");
                }
            }
            System.out.println("\nInforme o preço mensal da acomodação: ");
            preco = scan.nextDouble();

            Acomodacao acomodacao = new Acomodacao(estaDisponivel, preco, tipo, numAcomod);
            memoriaAcomodacoes.append(acomodacao.toString());
            gravarDados(ARQUIVO_ACOMODACAO, memoriaAcomodacoes);
            System.out.println("\nAcomodação adicionada com sucesso! ");
        } catch (Exception e) {
            System.out.println("\nErro de gravação");
        }
    }
    
    static void menuAdicionarPessoa() {
        String cpf, nome, dataNascimento, dataEntrada, numAcomodacao;
        @SuppressWarnings("unused")
        long cpfInt;
        try {
            if (existemAcomodacoesDisponiveis()) {
                System.out.println("\nAcomodações disponíveis: ");
                mostrarAcomodacoesDisponiveis();
                while (true) {
                    System.out.println("\nInforme o número da acomodação dessa pessoa ou \"sair\" para voltar ao menu: ");
                    numAcomodacao = scan.next();
                    if (numAcomodacao.equals("sair")) {
                        Integer.parseInt(numAcomodacao);
                    } else if (validarAcomodacao(numAcomodacao)) {
                        break;
                    } else {
                        System.out.println("\nNúmero inválido, tente novamente.");
                    }
                }

                while (true) {
                    System.out.println("\nInforme o CPF da pessoa (apenas números sem espaço): ");
                    cpf = scan.next();
                    if (cpf.matches("\\d{11}")) {
                        try {
                            cpfInt = Long.parseLong(cpf);
                            if (existePessoa(cpf)) {
                                System.out.println("\nCPF informado já existe!");
                            } else {
                                break;
                            }
                        } catch (Exception e) {
                            System.out.println("\nTente novamente!");
                            continue;
                        }
                    } else {
                        System.out.println("\nCPF inválido. ");
                    }
                }
                
                System.out.println("\nInforme o nome:");
                nome = scan.next();
                System.out.println("\nInforme a data de nascimento:");
                dataNascimento = scan.next();
                System.out.println("\nInforme a data de entrada:");
                dataEntrada = scan.next();
                
                Pessoa pessoa = new Pessoa(cpf, nome, dataNascimento, dataEntrada, Integer.parseInt(numAcomodacao));
                memoriaPessoas.append(pessoa.toString());
                adicionarUmaPessoaNaAcomodacao(numAcomodacao);
                gravarDados(ARQUIVO_PESSOAS, memoriaPessoas);
                System.out.println("\nPessoa cadastrada com sucesso! ");
            } else {
                System.out.println("\nNão há acomodações disponíveis. ");
            }
        } catch (Exception e) {
            System.out.println("\nErro de gravação");
        }
    }

    static void menuAlterarDadosPessoa() {
        String cpfASerAlterado, opcao, novaInfo;
        boolean naoAlterou = true;
        mostrarPessoasCadastradas();

        if (memoriaPessoas.length() > 0) {
            while (true) {
                System.out.println("\nInforme o CPF da pessoa que deseja alterar alguma informação (apenas números) ou \"sair\" para voltar ao menu: ");
                cpfASerAlterado = scan.next();
                if (cpfASerAlterado.equals("sair")) {
                    naoAlterou = false;
                    break;
                } else if (existePessoa(cpfASerAlterado)) {
                    break;
                } else {
                    System.out.println("\nCPF informado não cadastrado, tente novamente!");
                }
            }
            
            while (naoAlterou) {
                System.out.println("\nQual informação deseja alterar?" +
                "\n1 - Nome" +
                "\n2 - Data de Nascimento" +
                "\n3 - Data de Entrada" +
                "\n4 - Acomodação do cliente");

                opcao = scan.next();

                switch (opcao) {
                    case "1":
                        System.out.println("\nInforme o novo nome: ");
                        novaInfo = scan.next();
                        alterarDadosPessoa(cpfASerAlterado, novaInfo, "nome");
                        naoAlterou = false;
                        break;
                    case "2":
                        System.out.println("\nInforme a nova data de nascimento: ");
                        novaInfo = scan.next();
                        alterarDadosPessoa(cpfASerAlterado, novaInfo, "dataNascimento");
                        naoAlterou = false;
                        break;
                    case "3":
                        System.out.println("\nInforme a nova data de entrada: ");
                        novaInfo = scan.next();
                        alterarDadosPessoa(cpfASerAlterado, novaInfo, "dataEntrada");
                        naoAlterou = false;
                        break;
                    case "4":
                        if (existemAcomodacoesDisponiveis()) {
                            while (true) {
                                mostrarAcomodacoesDisponiveis();
                                System.out.println("\nInforme a nova acomodação do cliente: ");
                                novaInfo = scan.next();
                                if (validarAcomodacao(novaInfo)) {
                                    alterarDadosPessoa(cpfASerAlterado, novaInfo, "acomodacao");
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
            }
        }
    }

    static void menuExcluirDadosPessoa() {
        int inicio, fim, ultimo, primeiro;
        String pessoaASerExcluida;
        String cpf, nome, dataNasc, dataEntrada, acomodacaoVinculada;
		boolean achou = false;
        char resp;

        mostrarPessoasCadastradas();

        if (memoriaPessoas.length() != 0) {
            inicio = 0;
            System.out.println("\nInforme o cpf da pessoa que deseja excluir ou \"sair\" para voltar ao menu: ");
            pessoaASerExcluida = scan.next();
            if (pessoaASerExcluida.equals("sair")) {
                inicio = memoriaPessoas.length();
                achou = true;
            }
			while ((inicio != memoriaPessoas.length()) && (!achou)) {
				ultimo = memoriaPessoas.indexOf ("\t", inicio);
				cpf = memoriaPessoas.substring(inicio, ultimo);

                primeiro = ultimo + 1;
				ultimo = memoriaPessoas.indexOf ("\t", primeiro); 
				nome = memoriaPessoas.substring(primeiro, ultimo);	

                primeiro = ultimo + 1;
				ultimo = memoriaPessoas.indexOf ("\t", primeiro); 
				dataNasc = memoriaPessoas.substring(primeiro, ultimo);	

				primeiro = ultimo + 1;
                ultimo = memoriaPessoas.indexOf ("\t", primeiro); 
				dataEntrada = memoriaPessoas.substring(primeiro, ultimo);	

                primeiro = ultimo + 1;
                fim = memoriaPessoas.indexOf ("\n", primeiro);
				acomodacaoVinculada = memoriaPessoas.substring(primeiro, fim);

                Pessoa pessoa = new Pessoa(cpf, nome, dataNasc, dataEntrada, Integer.parseInt(acomodacaoVinculada));
                if (cpf.equals(pessoaASerExcluida)){
					System.out.println("\n\nDeseja excluir?\nDigite S ou N:\n\n"+
                            "\nCPF: "+pessoa.getCpf()+
							"\nNome: "+pessoa.getNome()+
							"\nData Nascimento: " +pessoa.getDataNascimento()+
							"\nData de Entrada: "+pessoa.getDataEntrada() + 
                            "\nAcomodação: "+pessoa.getNumAcomodacao());
					resp = Character.toUpperCase(scan.next().charAt(0));
					if (resp == 'S'){
						memoriaPessoas.delete (inicio, fim + 1);	
                        excluirUmaPessoaNaAcomodacao(acomodacaoVinculada);
						System.out.println("\nRegistro excluido.");
						gravarDados(ARQUIVO_PESSOAS, memoriaPessoas); 
					} else{
						System.out.println("\nExclusao cancelada.");
					}
					achou = true;
				}
                inicio = fim + 1;
            }
            if (!achou) {
				System.out.println("\nPessoa nao encontrada! ");
			}
        }
    }

    public static void menuConsultarDados() {
        String opcao;
        while (true) {
            System.out.println("\nDigite a opçao que deseja ou \"sair\" para voltar ao menu:" +
            "\n1 - Dados das acomodacoes" +
            "\n2 - Dados das pessoas" +
            "\n3 - Ambos"); 
    
            opcao = scan.next();

            switch (opcao) {
                case "1":
                    System.out.println("\n\n\nACOMODACOES:");
                    mostrarAcomodacoesCadastradas();
                    break;
                case "2":
                    System.out.println("\n\n\nPESSOAS:");
                    mostrarPessoasCadastradas();
                    break;
                case "3":
                    System.out.println("\n\n\nACOMODACOES:");
                    mostrarAcomodacoesCadastradas();
                    System.out.println("\n\n\nPESSOAS:");
                    mostrarPessoasCadastradas();
                    break;
                case "sair":
                    break;
                default:
                    System.out.println("Digite uma opcao valida!");
            }

            if (opcao.equals("1") || opcao.equals("2") || opcao.equals("3") || opcao.equals("sair")) {
                break;
            }
        }
    }

    static void menuConsultaEspecifica() {
        String numAcomodacao;
        while (true) {
            System.out.println("\nInforme o número da acomodação que deseja verificar os ocupantes ou \"sair\" para voltar ao menu: ");
            numAcomodacao = scan.next();

            if (existeAcomodacao(numAcomodacao)) {
                mostrarPessoasNumaAcomodacao(numAcomodacao);
                break;
            } else if (numAcomodacao.equals("sair")) {
                break;
            } else {
                System.out.println("\nAcomodação informada nao cadastrada!");
                break;
            }
        }
    }

    public static void mostrarAcomodacoesCadastradas() {
        int inicio, fim, ultimo, primeiro;
        String numAcomodacao, valor, tipo, pessoasNaAcomodacao;
        if (memoriaAcomodacoes.length() > 0) {
            inicio = 0;
            while ((inicio != memoriaAcomodacoes.length())) {
				ultimo = memoriaAcomodacoes.indexOf ("\t", inicio);
				numAcomodacao = memoriaAcomodacoes.substring(inicio, ultimo);

				primeiro = ultimo + 1;
				ultimo = memoriaAcomodacoes.indexOf ("\t", primeiro); 	

				primeiro = ultimo + 1;
                ultimo = memoriaAcomodacoes.indexOf ("\t", primeiro); 
				valor = memoriaAcomodacoes.substring(primeiro, ultimo);	
                
                primeiro = ultimo + 1;
                ultimo = memoriaAcomodacoes.indexOf ("\t", primeiro);
				tipo = memoriaAcomodacoes.substring(primeiro, ultimo);

                primeiro = ultimo + 1;
                fim = memoriaAcomodacoes.indexOf ("\n", primeiro);
				pessoasNaAcomodacao = memoriaAcomodacoes.substring(primeiro, fim);

				inicio = fim + 1;
                
                System.out.println("\n *** Acomodação "+ numAcomodacao + " ***" +
                "\nValor: " + valor +
                "\nTipo: "+ tipo +
                "\nPessoas na acomodação: "+ pessoasNaAcomodacao);            
			}
        } else {
            System.out.println("Nenhuma acomodação cadastrada!");
        }
    }

    public static void mostrarAcomodacoesDisponiveis() {
        int inicio, fim, ultimo, primeiro;
        String numAcomodacao, valor, disponivel, tipo, pessoasNaAcomodacao;
        if (memoriaAcomodacoes.length() > 0) {
            inicio = 0;
            while ((inicio != memoriaAcomodacoes.length())) {
				ultimo = memoriaAcomodacoes.indexOf ("\t", inicio);
				numAcomodacao = memoriaAcomodacoes.substring(inicio, ultimo);

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
				pessoasNaAcomodacao = memoriaAcomodacoes.substring(primeiro, fim);

				inicio = fim + 1;

                if(disponivel.equals("true")){
                    System.out.println("\n *** Acomodação "+ numAcomodacao + " ***" +
                    "\nValor: " + valor +
                    "\nTipo: "+ tipo +
                    "\nPessoas na acomodação: "+ pessoasNaAcomodacao);
                }
			}
        } else {
            System.out.println("Nenhuma acomodação cadastrada!");
        }
    }

    public static void mostrarPessoasCadastradas() {
        int inicio, fim, ultimo, primeiro;
        String cpf, nome, dataNasc, dataEntrada, acomodacaoVinculada;
        if (memoriaPessoas.length() > 0) {
            inicio = 0;
            while ((inicio != memoriaPessoas.length())) {
				ultimo = memoriaPessoas.indexOf ("\t", inicio);
				cpf = memoriaPessoas.substring(inicio, ultimo);

                primeiro = ultimo + 1;
				ultimo = memoriaPessoas.indexOf ("\t", primeiro); 
				nome = memoriaPessoas.substring(primeiro, ultimo);	

				primeiro = ultimo + 1;
				ultimo = memoriaPessoas.indexOf ("\t", primeiro); 
				dataNasc = memoriaPessoas.substring(primeiro, ultimo);	

				primeiro = ultimo + 1;
                ultimo = memoriaPessoas.indexOf ("\t", primeiro); 
				dataEntrada = memoriaPessoas.substring(primeiro, ultimo);	

                primeiro = ultimo + 1;
                fim = memoriaPessoas.indexOf ("\n", primeiro);
				acomodacaoVinculada = memoriaPessoas.substring(primeiro, fim);

				inicio = fim + 1;

                System.out.println("\n *** Cliente "+ nome + " ***" +
                "\nCPF: " + cpf +
                "\nData de Nascimento: " + dataNasc +
                "\nData de Entrada: "+ dataEntrada +
                "\nAcomodação do cliente: "+ acomodacaoVinculada);
			}
        } else {
            System.out.println("Nenhuma pessoa cadastrada!");
        }
    }

    public static boolean existemAcomodacoesDisponiveis() {
        int inicio, fim, ultimo, primeiro;
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
            System.out.println("Nenhuma acomodação cadastrada!");
        }
        return estaDisponivel;
    }

    public static boolean validarAcomodacao (String numAcomodacao) {
        int inicio, fim, ultimo, primeiro;
        String numAcomodacaoTemp, estaDisponivel;
        boolean numeroDeAcomodacaoDisponivel = false;
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
                    numeroDeAcomodacaoDisponivel = true;
                    break;
                }
			}
        } else {
            System.out.println("Nenhuma acomodação cadastrada!");
        }
        return numeroDeAcomodacaoDisponivel;
    }

    static void iniciarArquivo(String filename, StringBuffer memoria) {
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

    static void alterarDadosPessoa(String cpfProcurado, String novaInfo, String campoASerAlterado){
		int inicio, fim, ultimo, primeiro;
        String cpf, nome, dataNasc, dataEntrada, acomodacaoVinculada;

		inicio = 0;
		while (inicio != memoriaPessoas.length()) {
			ultimo = memoriaPessoas.indexOf ("\t", inicio);
			cpf = memoriaPessoas.substring(inicio, ultimo);

            primeiro = ultimo + 1;
			ultimo = memoriaPessoas.indexOf ("\t", primeiro); 
			nome = memoriaPessoas.substring(primeiro, ultimo);	

			primeiro = ultimo + 1;
			ultimo = memoriaPessoas.indexOf ("\t", primeiro); 
			dataNasc = memoriaPessoas.substring(primeiro, ultimo);	

			primeiro = ultimo + 1;
            ultimo = memoriaPessoas.indexOf ("\t", primeiro); 
			dataEntrada = memoriaPessoas.substring(primeiro, ultimo);	

            primeiro = ultimo + 1;
            fim = memoriaPessoas.indexOf ("\n", primeiro);
			acomodacaoVinculada = memoriaPessoas.substring(primeiro, fim);

			Pessoa pessoa = new Pessoa(cpf, nome, dataNasc, dataEntrada, Integer.parseInt(acomodacaoVinculada));

			if (cpfProcurado.equals(pessoa.getCpf())) {
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
                    case "acomodacao":
                        excluirUmaPessoaNaAcomodacao(acomodacaoVinculada);
                        adicionarUmaPessoaNaAcomodacao(novaInfo);
                        pessoa.setNumAcomodacao(Integer.parseInt(novaInfo));
                        break;
                }
				memoriaPessoas.replace(inicio, fim+1, pessoa.toString());
				gravarDados(ARQUIVO_PESSOAS, memoriaPessoas);
                System.out.println("\nDado alterado com sucesso! ");
                break;
			}
			inicio = fim + 1; 
		}
    }

    static void adicionarUmaPessoaNaAcomodacao(String numAcomodacao) {
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

    static void excluirUmaPessoaNaAcomodacao(String numAcomodacao) {
		int inicio, fim, ultimo, primeiro;
        String numAcomodacaoTemp, valor, tipo, pessoasNaAcomodacao;
        int numPessoasNaAcomodacao;

		inicio = 0;
		while (inicio != memoriaAcomodacoes.length()) {
			ultimo = memoriaAcomodacoes.indexOf ("\t", inicio);
			numAcomodacaoTemp = memoriaAcomodacoes.substring(inicio, ultimo);

			primeiro = ultimo + 1;
			ultimo = memoriaAcomodacoes.indexOf ("\t", primeiro); 

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
                numPessoasNaAcomodacao = Integer.parseInt(pessoasNaAcomodacao) - 1;
                String acomodacao = numAcomodacaoTemp+"\ttrue\t"+valor+"\t"+tipo+"\t"+numPessoasNaAcomodacao+"\n";

                memoriaAcomodacoes.replace(inicio, fim + 1, acomodacao);
                gravarDados(ARQUIVO_ACOMODACAO, memoriaAcomodacoes);
                break;
            }
            inicio = fim + 1; 
		}
	}

    public static boolean existeAcomodacao(String numAcomodacao) {
        int inicio, fim, ultimo, primeiro;
        String acomodacaoTemp;
        boolean existeAcomodacao = false;
        
        inicio = 0;
        while ((inicio != memoriaAcomodacoes.length())) {
			ultimo = memoriaAcomodacoes.indexOf ("\t", inicio);
            acomodacaoTemp = memoriaAcomodacoes.substring(inicio, ultimo);

            primeiro = ultimo + 1;
            fim = memoriaAcomodacoes.indexOf ("\n", primeiro);
			inicio = fim + 1;
            if (acomodacaoTemp.equals(numAcomodacao) ) {
                existeAcomodacao = true;
                break;
            }
		}
        return existeAcomodacao;
    }

    public static boolean existePessoa(String cpf) {
        int inicio, fim, ultimo, primeiro;
        String cpfTemp;
        boolean existePessoa = false;
        
        inicio = 0;
        while ((inicio != memoriaPessoas.length())) {
			ultimo = memoriaPessoas.indexOf ("\t", inicio);
            cpfTemp = memoriaPessoas.substring(inicio, ultimo);

            primeiro = ultimo + 1;
            fim = memoriaPessoas.indexOf ("\n", primeiro);
			inicio = fim + 1;
            if (cpfTemp.equals(cpf) ) {
                existePessoa = true;
                break;
            }
		}
        return existePessoa;
    }

    public static void mostrarPessoasNumaAcomodacao(String numAcomodacao) {
        int inicio, fim, ultimo, primeiro;
        String numAcomodacaoTemp, cpf, nome, dataNasc, dataEntrada;
        boolean achouAlgumaPessoa = false;

        inicio = 0;
        while ((inicio != memoriaPessoas.length())) {
            ultimo = memoriaPessoas.indexOf ("\t", inicio);
            cpf = memoriaPessoas.substring(inicio, ultimo);

            primeiro = ultimo + 1;
            ultimo = memoriaPessoas.indexOf ("\t", primeiro); 
            nome = memoriaPessoas.substring(primeiro, ultimo);	

            primeiro = ultimo + 1;
            ultimo = memoriaPessoas.indexOf ("\t", primeiro); 
            dataNasc = memoriaPessoas.substring(primeiro, ultimo);	

            primeiro = ultimo + 1;
            ultimo = memoriaPessoas.indexOf ("\t", primeiro); 
            dataEntrada = memoriaPessoas.substring(primeiro, ultimo);	

            primeiro = ultimo + 1;
            fim = memoriaPessoas.indexOf ("\n", primeiro);
            numAcomodacaoTemp = memoriaPessoas.substring(primeiro, fim);

            inicio = fim + 1;

            if(numAcomodacaoTemp.equals(numAcomodacao)){
                achouAlgumaPessoa = true;
                System.out.println("\n *** Cliente "+ nome + " ***" +
                "\nCPF: " + cpf +
                "\nData de Nascimento: " + dataNasc +
                "\nData de Entrada: "+ dataEntrada +
                "\nAcomodação do cliente: "+ numAcomodacaoTemp);
            }
		}
        if(!achouAlgumaPessoa){
            System.out.println("Nenhuma pessoa encontrada nessa acomodação!");
        }
    }
}