package AluguerDeViaturas;

import utils.InputValidation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class GestorDeFicheiros {
    // Declaração do caminho para os ficheiros CSV onde estão guardadas as informações
    private static final String viaturasFilePath = Paths.get(Paths.get("").toAbsolutePath().toString(),
            "src\\AluguerDeViaturas", "Viaturas.csv").toString();
    private static final String clientesFilePath = Paths.get(Paths.get("").toAbsolutePath().toString(),
            "src\\AluguerDeViaturas", "Clientes.csv").toString();
    private static final String alugueresFilePath = Paths.get(Paths.get("").toAbsolutePath().toString(),
            "src\\AluguerDeViaturas", "Alugueres.csv").toString();

    // Formatação para datas no formato dd/MM/yyyy
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    // Listas que guardam os objetos de viaturas, clientes e alugueres
    private static ArrayList<Viatura> viaturas = new ArrayList<>();
    private static ArrayList<Cliente> clientes = new ArrayList<>();
    private static ArrayList<Aluguer> alugueres = new ArrayList<>();

    // === VIATURAS ===

    // Método para carregar as viaturas a partir do ficheiro CSV
    public static boolean loadViaturasFile() {
        viaturas.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(viaturasFilePath, StandardCharsets.ISO_8859_1))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] vArray = line.split(";");
                Viatura v = new Viatura(vArray[0], vArray[1], vArray[2],
                        Double.parseDouble(vArray[3]), Integer.parseInt(vArray[4]), Integer.parseInt(vArray[5]));
                viaturas.add(v);
            }
        } catch (Exception e) {
            System.out.println("Erro ao carregar o ficheiro de viaturas do caminho " + viaturasFilePath);
            return false;
        }
        return true;
    }

    // Método para adicionar uma nova viatura ao ficheiro CSV
    public static void addViaturaToFile(Scanner sc) {
        String matricula = "";
        try {
            System.out.print("Digite a matrícula da nova viatura: ");
            matricula = sc.nextLine().trim();

            for (Viatura v : viaturas) {
                if (v != null && v.getMatricula().equalsIgnoreCase(matricula)) {
                    System.out.println("Erro: Já existe uma viatura com essa matrícula.");
                    return;
                }
            }
        } catch (Exception e) {
            System.out.println("Erro ao ler a matrícula. Tente novamente.");
        }

        Viatura novaViatura = new Viatura(sc, matricula);
        viaturas.add(novaViatura);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(viaturasFilePath, StandardCharsets.ISO_8859_1, true))) {
            bw.write(novaViatura.getMarca() + ";" + novaViatura.getModelo() + ";" +
                    novaViatura.getMatricula() + ";" + novaViatura.getKm() + ";" +
                    novaViatura.getAno() + ";" + novaViatura.getnLugares() + "\n");
            System.out.println("Viatura adicionada com sucesso.");
        } catch (Exception e) {
            System.out.println("Erro ao atualizar o ficheiro de viaturas.");
        }
    }

    // Método para atualizar as viaturas para o ficheiro CSV
    public static void updateViaturaToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(viaturasFilePath, StandardCharsets.ISO_8859_1))) {
            for (Viatura v : viaturas) {
                bw.write(v.getMarca() + ";" + v.getModelo() + ";" + v.getMatricula() + ";" +
                        v.getKm() + ";" + v.getAno() + ";" + v.getnLugares() + "\n");
            }
        } catch (Exception e) {
            System.out.println("Erro ao atualizar o ficheiro de viaturas.");
        }
    }

    // Método para editar os dados de uma viatura
    public static void editarViatura(Scanner sc) {
        System.out.print("Digite a matrícula da viatura que deseja editar: ");
        String matricula = sc.nextLine();

        for (Viatura v : viaturas) {
            if (v.getMatricula().equalsIgnoreCase(matricula)) {
                System.out.print("Nova marca (deixe vazio para manter a atual): ");
                String newMarca = sc.nextLine();
                if (!newMarca.isEmpty()) v.setMarca(newMarca);

                System.out.print("Novo modelo (deixe vazio para manter o atual): ");
                String newModelo = sc.nextLine();
                if (!newModelo.isEmpty()) v.setModelo(newModelo);

                int newAno = InputValidation.validateInt(sc, "Novo ano (-1 para manter o atual): ");
                if (newAno >= 0) v.setAno(newAno);

                double newKm = InputValidation.validateDouble(sc, "Nova quilometragem (-1 para manter a atual): ");
                if (newKm >= 0) v.setKm(newKm);

                int newLugares = InputValidation.validateInt(sc, "Novo número de lugares (-1 para manter o atual): ");
                if (newLugares >= 0) v.setnLugares(newLugares);

                System.out.println("Viatura atualizada com sucesso!");
                updateViaturaToFile();
                return;
            }
        }
        System.out.println("Viatura não encontrada");
    }

    // Método para remover uma viatura
    public static void removerViatura(Scanner sc) {
        System.out.print("Digite a matrícula da viatura que deseja remover: ");
        String matricula = sc.nextLine();

        for (int i = 0; i < viaturas.size(); i++) {
            if (viaturas.get(i).getMatricula().equalsIgnoreCase(matricula)) {
                viaturas.remove(i);
                System.out.println("Viatura removida com sucesso!");
                updateViaturaToFile();
                return;
            }
        }
        System.out.println("Viatura não encontrada");
    }

    // Método para listar todas as viaturas
    public static void listarViaturas() {
        for (Viatura v : viaturas) {
            System.out.println(v);
        }
    }

    // === CLIENTES ===

    // Método para carregar os clientes a partir de um ficheiro CSV
    public static boolean loadClientesFile() {
        clientes.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(clientesFilePath, StandardCharsets.ISO_8859_1))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] c = line.split(";");
                LocalDate dataNascimento = LocalDate.parse(c[5], formatter);
                Cliente cl = new Cliente(Integer.parseInt(c[0]), c[1], c[2], Integer.parseInt(c[3]), c[4], dataNascimento, Integer.parseInt(c[6]));
                clientes.add(cl);
            }
        } catch (Exception e) {
            System.out.println("Erro ao carregar o ficheiro de clientes.");
            return false;
        }
        return true;
    }

    // Método para adicionar um novo cliente ao ficheiro CSV
    public static void addClienteToFile(Scanner sc) {
        int NIF = 0;
        try {
            NIF = InputValidation.validateInt(sc, "Digite o NIF do novo cliente: ");

            for (Cliente c : clientes) {
                if (c != null && c.getNIF() == NIF) {
                    System.out.println("Erro: Já existe um cliente com esse NIF.");
                    return;
                }
            }
        } catch (Exception e) {
            System.out.println("Erro ao ler o NIF. Tente novamente.");
        }

        Cliente novoCliente = new Cliente(sc, NIF);
        clientes.add(novoCliente);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(clientesFilePath, StandardCharsets.ISO_8859_1, true))) {
            bw.write(novoCliente.getNIF() + ";" + novoCliente.getNome() + ";" + novoCliente.getMorada() + ";" +
                    novoCliente.getTelefone() + ";" + novoCliente.getEmail() + ";" +
                    novoCliente.getDataNascimento().format(formatter) + ";" + novoCliente.getnCartaConducao() + "\n");
        } catch (Exception e) {
            System.out.println("Erro ao atualizar o ficheiro de clientes.");
        }
    }

    // Método para atualizar os clientes para o ficheiro CSV
    public static void updateClienteToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(clientesFilePath, StandardCharsets.ISO_8859_1))) {
            for (Cliente c : clientes) {
                bw.write(c.getNIF() + ";" + c.getNome() + ";" + c.getMorada() + ";" +
                        c.getTelefone() + ";" + c.getEmail() + ";" +
                        c.getDataNascimento().format(formatter) + ";" + c.getnCartaConducao() + "\n");
            }
        } catch (Exception e) {
            System.out.println("Erro ao atualizar o ficheiro de clientes.");
        }
    }

    // Método para editar os dados de um cliente
    public static void editarCliente(Scanner sc) {
        int NIF = InputValidation.validateIntGT0(sc, "Digite o NIF do cliente que deseja editar: ");
        for (Cliente c : clientes) {
            if (c.getNIF() == NIF) {
                System.out.print("Novo nome (deixe vazio para manter): ");
                String newNome = sc.nextLine();
                if (!newNome.isEmpty()) c.setNome(newNome);

                System.out.print("Nova morada (deixe vazio para manter): ");
                String newMorada = sc.nextLine();
                if (!newMorada.isEmpty()) c.setMorada(newMorada);

                int newTelefone = InputValidation.validateNineDigitIntegerOrEnter(sc, "Novo telefone (9 dígitos) (-1 para manter): ");
                if (newTelefone >= 900000000 && newTelefone <= 999999999) c.setTelefone(newTelefone);

                while (true) {
                    System.out.print("Novo email (deixe vazio para manter): ");
                    String newEmail = sc.nextLine();
                    if (newEmail.isEmpty()) break;
                    if (newEmail.contains("@") && newEmail.contains(".") && newEmail.indexOf("@") < newEmail.lastIndexOf(".")) {
                        c.setEmail(newEmail);
                        break;
                    } else {
                        System.out.println("Email inválido. Por favor, insira um email com '@' e domínio (ex: exemplo@dominio.com).");
                    }
                }

                while (true) {
                    System.out.print("Nova data de nascimento (deixe vazio para manter): ");
                    String newData = sc.nextLine();
                    if (newData.isEmpty()) break;
                    try {
                        LocalDate newDataNascimento = LocalDate.parse(newData, formatter);
                        c.setDataNascimento(newDataNascimento);
                        break;
                    } catch (Exception e) {
                        System.out.println("Data inválida. Por favor, use o formato dd/MM/yyyy.");
                    }
                }

                int newNCarta = InputValidation.validateInt(sc, "Novo nº carta condução (-1 para manter): ");
                if (newNCarta >= 0) c.setnCartaConducao(newNCarta);

                System.out.println("Cliente atualizado com sucesso!");
                updateClienteToFile();
                return;
            }
        }
        System.out.println("Cliente não encontrado");
    }

    // Método para remover uma viatura
    public static void removerClientes(Scanner sc) {
        int NIF = InputValidation.validateInt(sc, "Digite o NIF do cliente que deseja remover: ");
        for (int i = 0; i < clientes.size(); i++) {
            if (clientes.get(i).getNIF() == NIF) {
                clientes.remove(i);
                System.out.println("Cliente removido com sucesso!");
                updateClienteToFile();
                return;
            }
        }
        System.out.println("Cliente não encontrado");
    }

    // Método para listar todas as viaturas
    public static void listarClientes() {
        for (Cliente c : clientes) {
            System.out.println(c);
        }
    }

    // === ALUGUERES ===

    public static boolean loadAlugueresFile() {
        alugueres.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(alugueresFilePath, StandardCharsets.ISO_8859_1))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] a = line.split(";");
                Aluguer al = new Aluguer(Integer.parseInt(a[0]), a[1], a[2], a[3]);
                alugueres.add(al);
            }
        } catch (Exception e) {
            System.out.println("Erro ao carregar o ficheiro de alugueres.");
            return false;
        }
        return true;
    }

    public static void listarAlugueres() {
        for (Aluguer a : alugueres) {
            System.out.println(a);
        }
    }

    public static void editarAluguer(Scanner sc) {
        System.out.println("Ainda não implementado.");
    }

    public static void removerAluguer(Scanner sc) {
        System.out.println("Ainda não implementado.");
    }
}