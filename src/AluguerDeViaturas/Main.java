package AluguerDeViaturas;

import utils.InputValidation;
import java.util.Scanner;

/*
Instituto Politécnico de Portalegre
Programação Orientada a Objetos
Projeto 1 - Aluguer de viaturas
Engenharia Informática - 1.º Ano

*/

public class Main {
    public static void main(String[] args) {
        if(!GestorDeFicheiros.loadViaturasFile() || !GestorDeFicheiros.loadClientesFile() || !GestorDeFicheiros.loadAlugueresFile()) {
            return;
        }

        Scanner sc = new Scanner(System.in);
        int option;
        int subOption;

        do{
            menu(0);
            option = InputValidation.validateIntBetween(sc, "Opção: ", 0, 3);
            switch(option){
                case 0:
                    System.out.println("Adeus!!");
                    break;
                case 1:
                    menu(1);
                    subOption = InputValidation.validateIntBetween(sc, "Opção: ", 0, 4);
                    switch (subOption){
                        case 0:
                            break;
                        case 1:
                            System.out.println("Adicionar viatura");
                            GestorDeFicheiros.addViaturaToFile(sc);
                            break;
                        case 2:
                            System.out.println("Remover viatura");
                            GestorDeFicheiros.removerViatura(sc);
                            break;
                        case 3:
                            System.out.println("Editar viatura");
                            GestorDeFicheiros.editarViatura(sc);
                            break;
                        case 4:
                            System.out.println("Listar viaturas");
                            GestorDeFicheiros.listarViaturas();
                            break;
                    }
                    break;
                case 2:
                    menu(2);
                    subOption = InputValidation.validateIntBetween(sc, "Opção: ", 0, 4);
                    switch (subOption){
                        case 0:
                            break;
                        case 1:
                            System.out.println("Adicionar cliente");
                            GestorDeFicheiros.addClienteToFile(sc);
                            break;
                        case 2:
                            System.out.println("Remover cliente");
                            GestorDeFicheiros.removerClientes(sc);
                            break;
                        case 3:
                            System.out.println("Editar cliente");
                            GestorDeFicheiros.editarCliente(sc);
                            break;
                        case 4:
                            System.out.println("Listar clientes");
                            GestorDeFicheiros.listarClientes();
                            break;
                    }
                    break;
                case 3:
                    menu(3);
                    subOption = InputValidation.validateIntBetween(sc, "Opção: ", 1, 4);
                    switch (subOption){
                        case 0:
                            break;
                        case 1:
                            System.out.println("Adicionar aluguer");
                            GestorDeFicheiros.adicionarAluguer(sc);
                            break;
                        case 2:
                            System.out.println("Remover aluguer");
                            GestorDeFicheiros.removerAluguer(sc);
                            break;
                        case 3:
                            System.out.println("Editar aluguer");
                            GestorDeFicheiros.editarAluguer(sc);
                            break;
                        case 4:
                            System.out.println("Listar alugueres");
                            GestorDeFicheiros.listarAlugueres();
                            break;
                    }
                    break;
            }
        }while(option != 0);

        sc.close();
    }

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
