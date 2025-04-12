package AluguerDeViaturas;

import utils.InputValidation;
import java.time.LocalDate;
import java.util.Scanner;

/*
Instituto Politécnico de Portalegre
Programação Orientada a Objetos
Projeto 1 - Aluguer de viaturas
Engenharia Informática - 1.º Ano
*/

public class Main {
    public static void main(String[] args) {
        //Verificação de leitura correta dos ficheiros
        if(!GestorDeDados.loadViaturasFile() || !GestorDeDados.loadClientesFile() || !GestorDeDados.loadAlugueresFile()) {
            return;
        }

        Scanner sc = new Scanner(System.in);
        int option;
        int subOption;

        do{
            menu(0); // Menu Inicial
            option = InputValidation.validateIntBetween(sc, "Opção: ", 0, 3);
            switch(option){
                case 0:
                    System.out.println("Obrigado por usar o programa!!");
                    break;
                case 1:
                    menu(1); // Menu viaturas
                    subOption = InputValidation.validateIntBetween(sc, "Opção: ", 0, 4);
                    switch (subOption){
                        case 0:
                            break;
                        case 1: // Adicionar nova viatura
                            System.out.println("Adicionar viatura");
                            String matricula = InputValidation.validateString(sc, "Matricula: ");

                            if (GestorDeDados.viaturaExiste(matricula)) {
                                System.out.println("Já existe uma viatura com essa matrícula.");
                                break;
                            }

                            Viatura novaViatura = new Viatura(sc, matricula);
                            GestorDeDados.gravarNovaViatura(novaViatura);

                            System.out.println("Viatura adicionada com sucesso!");
                            break;
                        case 2: // Remover viatura
                            System.out.println("Remover viatura");
                            String matriculaRemover = InputValidation.validateString(sc, "Matricula da viatura a remover: ");

                            if (GestorDeDados.removerViatura(matriculaRemover)) {
                                System.out.println("Viatura removida com sucesso.");
                            } else {
                                System.out.println("Viatura não encontrada.");
                            }
                            break;
                        case 3: // Editar viatura
                            System.out.println("Editar viatura");
                            String matriculaEdit = InputValidation.validateString(sc,"Matricula da viatura a editar: ");

                            Viatura viatura = GestorDeDados.obterViaturaPorMatricula(matriculaEdit);
                            if (viatura == null) {
                                System.out.println("Viatura não encontrada.");
                                break;
                            }
                            // Editar dados da viatura (mantém os atuais se o utilizador carregar ENTER)
                            System.out.println("\n** Deixe em branco para manter os valores atuais **");

                            String newMarca = InputValidation.validateOptionalString(sc, "Nova marca (ENTER para manter): ");
                            if (newMarca != null && !newMarca.isEmpty()) viatura.setMarca(newMarca);

                            String newModelo = InputValidation.validateOptionalString(sc, "Novo modelo (ENTER para manter): ");
                            if (newModelo != null && !newModelo.isEmpty()) viatura.setModelo(newModelo);

                            Integer newAno = InputValidation.validateOptionalInt(sc, "Novo ano (ENTER para manter): ");
                            if (newAno != null) viatura.setAno(newAno);

                            Double newKm = InputValidation.validateOptionalDouble(sc, "Novo total de km (ENTER para manter): ");
                            if (newKm != null) viatura.setKm(newKm);

                            Integer newLugares = InputValidation.validateOptionalInt(sc, "Novo número de lugares (ENTER para manter): ");
                            if (newLugares != null) viatura.setNLugares(newLugares);

                            GestorDeDados.updateViaturaToFile();
                            System.out.println("Viatura atualizada com sucesso!");
                            break;
                        case 4: // Listar viaturas
                            System.out.println("Listar viaturas");
                            GestorDeDados.listarViaturas();
                            break;
                    }
                    break;
                case 2:
                    menu(2); // Menu clientes
                    subOption = InputValidation.validateIntBetween(sc, "Opção: ", 0, 4);
                    switch (subOption){
                        case 0:
                            break;
                        case 1: // Adicionar cliente
                            System.out.println("Adicionar cliente");

                            int nif = InputValidation.validateIntGT0(sc, "NIF: ");
                            if (GestorDeDados.clienteExiste(nif)) {
                                System.out.println("Já existe um cliente com esse NIF.");
                                break;
                            }

                            Cliente novoCliente = new Cliente(sc, nif);
                            GestorDeDados.gravarNovoCliente(novoCliente);

                            System.out.println("Cliente adicionado com sucesso!");
                            break;
                        case 2: // Remover cliente
                            System.out.println("Remover cliente");

                            int nifRemover = InputValidation.validateIntGT0(sc, "NIF do cliente a remover: ");

                            if (GestorDeDados.removerCliente(nifRemover)) {
                                System.out.println("Cliente removido com sucesso.");
                            } else {
                                System.out.println("Cliente não encontrado.");
                            }
                            break;
                        case 3: // Editar cliente
                            System.out.println("Editar cliente");

                            int nifEdit = InputValidation.validateIntGT0(sc, "Digite o NIF do cliente que deseja editar: ");
                            Cliente cliente = GestorDeDados.obterClientePorNIF(nifEdit);

                            if (cliente == null) {
                                System.out.println("Cliente não encontrado.");
                                break;
                            }

                            System.out.println("\n** Deixe em branco para manter os valores atuais **");
                            // Atualização dos dados do cliente
                            String novoNome = InputValidation.validateOptionalString(sc, "Novo nome (ENTER para manter): ");
                            if (novoNome != null && !novoNome.isEmpty()) cliente.setNome(novoNome);

                            String novaMorada = InputValidation.validateOptionalString(sc, "Nova morada (ENTER para manter): ");
                            if (novaMorada != null) cliente.setMorada(novaMorada);

                            String novoEmail = InputValidation.validateOptionalEmail(sc, "Novo email (ENTER para manter): ");
                            if (novoEmail != null && !novoEmail.isEmpty()) cliente.setEmail(novoEmail);

                            Integer novoTelefone = InputValidation.validateOptionalNineDigitInteger(sc, "Novo telefone (ENTER para manter): ");
                            if (novoTelefone != null) cliente.setTelefone(novoTelefone);

                            LocalDate dataNascimento = InputValidation.validateOptionalDate(sc, "Nova data de nascimento (dd/MM/yyyy) (ENTER para manter): ");
                            if (dataNascimento != null) cliente.setDataNascimento(dataNascimento);

                            Integer novaCarta = InputValidation.validateOptionalInt(sc, "Novo nº carta condução (ENTER para manter): ");
                            if (novaCarta != null) cliente.setNCartaConducao(novaCarta);

                            GestorDeDados.updateClienteToFile();
                            System.out.println("Cliente atualizado com sucesso!");
                            break;
                        case 4: // Listar clientes
                            System.out.println("Listar clientes");
                            GestorDeDados.listarClientes();
                            break;
                    }
                    break;
                case 3:
                    menu(3); // Menu alugueres
                    subOption = InputValidation.validateIntBetween(sc, "Opção: ", 1, 4);
                    switch (subOption){
                        case 0:
                            break;
                        case 1: // Adicionar novo aluguer
                            System.out.println("Adicionar aluguer");
                            int nif = InputValidation.validateIntGT0(sc, "Digite o NIF do cliente: ");
                            String matricula = InputValidation.validateString(sc, "Matricula da viatura: ");
                            LocalDate dataInicio = InputValidation.validateDate(sc, "Digite a data de início do aluguer (dd/MM/yyyy): ");
                            LocalDate dataFim = InputValidation.validateDate(sc, "Digite a data de fim do aluguer (dd/MM/yyyy): ");

                            boolean sucesso = GestorDeDados.adicionarAluguer(nif, matricula, dataInicio, dataFim);

                            if (sucesso) {
                                System.out.println("Novo aluguer adicionado com sucesso!");
                            } else {
                                System.out.println("Não foi possível adicionar o aluguer.");
                            }
                            break;
                        case 2: // Remover aluguer existente
                            System.out.println("Remover aluguer");

                            int nifRemove = InputValidation.validateInt(sc, "Digite o NIF do cliente: ");
                            String matriculaRemove = InputValidation.validateString(sc, "Matricula da viatura: ");
                            LocalDate dataInicioRemove = InputValidation.validateDate(sc, "Digite a data de início do aluguer (dd/MM/yyyy): ");

                            boolean sucessoRemove = GestorDeDados.removerAluguer(nifRemove, matriculaRemove, dataInicioRemove);

                            if (sucessoRemove) {
                                System.out.println("Aluguer removido com sucesso.");
                            } else {
                                System.out.println("Aluguer não encontrado.");
                            }
                            break;
                        case 3: // Editar dados de um aluguer
                            System.out.println("Editar aluguer");

                            int nifEdit = InputValidation.validateInt(sc, "Digite o NIF do cliente: ");
                            String matriculaEdit = InputValidation.validateString(sc, "Matricula da viatura: ");
                            LocalDate dataInicioEdit = InputValidation.validateDate(sc, "Digite a data de início do aluguer (dd/MM/yyyy): ");

                            System.out.println("\n** Novas datas para o aluguer **");

                            LocalDate novaDataInicio, novaDataFim;
                            while (true) {
                                novaDataInicio = InputValidation.validateDate(sc, "Nova data de início (dd/MM/yyyy): ");
                                novaDataFim = InputValidation.validateDate(sc, "Nova data de fim (dd/MM/yyyy): ");

                                if (!novaDataFim.isAfter(novaDataInicio)) {
                                    System.out.println("A data de fim deve ser posterior à data de início.");
                                    continue;
                                }
                                break;
                            }

                            boolean sucessoEdit = GestorDeDados.editarAluguer(nifEdit, matriculaEdit, dataInicioEdit, novaDataInicio, novaDataFim);

                            if (sucessoEdit) {
                                System.out.println("Aluguer atualizado com sucesso.");
                            } else {
                                System.out.println("Erro ao atualizar aluguer (não encontrado ou conflito de datas).");
                            }

                        case 4: // Listar todos os alugueres
                            System.out.println("Listar alugueres");
                            GestorDeDados.listarAlugueres();
                            break;
                    }
                    break;
            }
        }while(option != 0);

        sc.close();
    }

    // Método auxiliar para apresentação dos menus
    private static void menu(int page){
        switch (page){
            case 0:
                System.out.println("\n**** Aluguer de viaturas **** ");
                System.out.println("1. Viaturas");
                System.out.println("2. Clientes");
                System.out.println("3. Alugueres");
                System.out.println("0. Sair");
                break;
            case 1:
                System.out.println("\n**** Menu de Viaturas **** ");
                System.out.println("1. Adicionar viatura");
                System.out.println("2. Remover viatura");
                System.out.println("3. Editar viatura");
                System.out.println("4. Listar viaturas");
                System.out.println("0. Voltar ao menu inicial");
                break;
            case 2:
                System.out.println("\n**** Menu de Clientes **** ");
                System.out.println("1. Adicionar cliente");
                System.out.println("2. Remover cliente");
                System.out.println("3. Editar cliente");
                System.out.println("4. Listar clientes");
                System.out.println("0. Voltar ao menu inicial");
                break;
            case 3:
                System.out.println("\n**** Menu de Alugueres **** ");
                System.out.println("1. Adicionar aluguer");
                System.out.println("2. Remover aluguer");
                System.out.println("3. Editar aluguer");
                System.out.println("4. Listar alugueres");
                System.out.println("0. Voltar ao menu inicial");
                break;
        }
    }
}
