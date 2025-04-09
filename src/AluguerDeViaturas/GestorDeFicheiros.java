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
    private final static ArrayList<Viatura> viaturas = new ArrayList<>();
    private final static ArrayList<Cliente> clientes = new ArrayList<>();
    private final static ArrayList<Aluguer> alugueres = new ArrayList<>();

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
                    novaViatura.getAno() + ";" + novaViatura.getNLugares() + "\n");
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
                        v.getKm() + ";" + v.getAno() + ";" + v.getNLugares() + "\n");
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

                String newMarca = InputValidation.validateOptionalString(sc, "Nova marca (ENTER para manter): ");
                if (newMarca != null) v.setMarca(newMarca);

                String newModelo = InputValidation.validateOptionalString(sc, "Novo modelo (ENTER para manter): ");
                if (newModelo != null) v.setModelo(newModelo);

                Integer novoAno = InputValidation.validateOptionalInt(sc, "Novo ano (ENTER para manter): ");
                if (novoAno != null) v.setAno(novoAno);

                Double novaKm = InputValidation.validateOptionalDouble(sc, "Nova quilometragem (ENTER para manter): ");
                if (novaKm != null) v.setKm(novaKm);

                Integer novoNLugares = InputValidation.validateOptionalInt(sc, "Novo número de lugares (ENTER para manter): ");
                if (novoNLugares != null) v.setNLugares(novoNLugares);

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
                    novoCliente.getDataNascimento().format(formatter) + ";" + novoCliente.getNCartaConducao() + "\n");
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
                        c.getDataNascimento().format(formatter) + ";" + c.getNCartaConducao() + "\n");
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
                String newNome = InputValidation.validateOptionalString(sc, "Novo nome (ENTER para manter): ");
                if (newNome != null) c.setNome(newNome);

                String newMorada = InputValidation.validateOptionalString(sc, "Nova morada (ENTER para manter): ");
                if (newMorada != null) c.setMorada(newMorada);

                Integer newTelefone = InputValidation.validateOptionalNineDigitInteger(sc, "Novo telefone (9 dígitos) (ENTER para manter): ");
                if (newTelefone != null) c.setTelefone(newTelefone);

                // email com verificação básica
                while (true) {
                    String newEmail = InputValidation.validateOptionalString(sc, "Novo email (ENTER para manter): ");
                    if (newEmail == null) break;

                    if (newEmail.contains("@") && newEmail.contains(".") && newEmail.indexOf("@") < newEmail.lastIndexOf(".")) {
                        c.setEmail(newEmail);
                        break;
                    } else {
                        System.out.println("Email inválido. Ex: exemplo@dominio.com");
                    }
                }

                // Data de nascimento
                while (true) {
                    String newData = InputValidation.validateOptionalString(sc, "Nova data de nascimento (dd/MM/yyyy) (ENTER para manter): ");
                    if (newData == null) break;

                    try {
                        LocalDate dataNascimento = LocalDate.parse(newData, formatter);
                        c.setDataNascimento(dataNascimento);
                        break;
                    } catch (Exception e) {
                        System.out.println("Data inválida. Use o formato dd/MM/yyyy.");
                    }
                }

                Integer novaCarta = InputValidation.validateOptionalInt(sc, "Novo nº carta condução (ENTER para manter): ");
                if (novaCarta != null) c.setNCartaConducao(novaCarta);

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
                LocalDate dataInicio = LocalDate.parse(a[2], formatter);
                LocalDate dataFim = LocalDate.parse(a[3], formatter);
                Aluguer al = new Aluguer(Integer.parseInt(a[0]), a[1], dataInicio, dataFim);
                alugueres.add(al);
            }
        } catch (Exception e) {
            System.out.println("Erro ao carregar o ficheiro de alugueres.");
            return false;
        }
        return true;
    }

    public static void adicionarAluguer(Scanner sc) {
        // Solicitar o NIF do cliente
        int nif = InputValidation.validateIntGT0(sc, "Digite o NIF do cliente: ");
        boolean clienteExiste = false;

        for (Cliente c : clientes) {
            if (c.getNIF() == nif) {
                clienteExiste = true;
                break;
            }
        }
        if (!clienteExiste) {
            System.out.println("Erro: Cliente não encontrado.");
            return;
        }

        // Solicitar a matrícula da viatura
        System.out.print("Digite a matrícula da viatura: ");
        String matricula = sc.nextLine().trim();
        boolean viaturaExiste = false;
        for (Viatura v : viaturas) {
            if (v.getMatricula().equalsIgnoreCase(matricula)) {
                viaturaExiste = true;
                break;
            }
        }
        if (!viaturaExiste) {
            System.out.println("Erro: Viatura não encontrada.");
            return;
        }

        // Solicitar as datas de início e fim
        LocalDate dataInicio, dataFim;
        while (true) {
            try {
                System.out.print("Digite a data de início do aluguer (dd/MM/yyyy): ");
                String entradaInicio = sc.nextLine();
                dataInicio = LocalDate.parse(entradaInicio, formatter);

                System.out.print("Digite a data de fim do aluguer (dd/MM/yyyy): ");
                String entradaFim = sc.nextLine();
                dataFim = LocalDate.parse(entradaFim, formatter);

                if (!dataFim.isAfter(dataInicio)) {
                    System.out.println("A data de fim deve ser posterior à data de início.");
                    continue;
                }
                break;
            } catch (Exception e) {
                System.out.println("Erro: As datas inseridas são inválidas. Tente novamente.");
            }
        }

        // Verificar se a viatura já está alugada neste período
        for (Aluguer a : alugueres) {
            if (a.getMatriculaViatura().equalsIgnoreCase(matricula)) {
                if ((dataInicio.isBefore(a.getDataFim()) && dataInicio.isAfter(a.getDataInicio())) ||
                        (dataFim.isBefore(a.getDataFim()) && dataFim.isAfter(a.getDataInicio()))) {
                    System.out.println("Erro: Já existe um aluguer para esta viatura neste período.");
                    return;
                }
            }
        }

        // Adicionar o novo aluguer
        Aluguer novoAluguer = new Aluguer(nif, matricula, dataInicio, dataFim);
        alugueres.add(novoAluguer);
        updateAlugueresFile();

        System.out.println("Novo aluguer adicionado com sucesso!");
    }

    private static void updateAlugueresFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(alugueresFilePath, StandardCharsets.ISO_8859_1))) {
            for (Aluguer a : alugueres) {
                bw.write(a.getNifCliente() + ";" +
                        a.getMatriculaViatura() + ";" +
                        a.getDataInicio().format(formatter) + ";" +
                        a.getDataFim().format(formatter) + "\n");
            }
        } catch (Exception e) {
            System.out.println("Erro ao atualizar o ficheiro de alugueres.");
        }
    }


    public static void editarAluguer(Scanner sc) {
        int nif = InputValidation.validateIntGT0(sc, "Digite o NIF do cliente: ");
        System.out.print("Digite a matrícula da viatura: ");
        String matricula = sc.nextLine().trim();

        // Encontrar o aluguer correspondente
        for (Aluguer a : alugueres) {
            if (a.getNifCliente() == nif && a.getMatriculaViatura().equalsIgnoreCase(matricula)) {

                // Editar a data de início
                LocalDate novaDataInicio, novaDataFim;

                // Nova data de início
                while (true) {
                    try {
                        System.out.print("Nova data de início (dd/MM/yyyy) (ENTER para manter): ");
                        String entradaInicio = sc.nextLine();
                        if (!entradaInicio.isEmpty()) {
                            novaDataInicio = LocalDate.parse(entradaInicio, formatter);
                        } else {
                            novaDataInicio = a.getDataInicio(); // Manter a data antiga
                        }

                        // Nova data de fim
                        System.out.print("Nova data de fim (dd/MM/yyyy) (ENTER para manter): ");
                        String entradaFim = sc.nextLine();
                        if (!entradaFim.isEmpty()) {
                            novaDataFim = LocalDate.parse(entradaFim, formatter);
                        } else {
                            novaDataFim = a.getDataFim(); // Manter a data antiga
                        }

                        if (!novaDataFim.isAfter(novaDataInicio)) {
                            System.out.println("A data de fim deve ser posterior à data de início.");
                            continue;
                        }

                        // Atualizar as datas
                        a.setDataInicio(novaDataInicio);
                        a.setDataFim(novaDataFim);
                        System.out.println("Aluguer atualizado com sucesso.");
                        updateAlugueresFile();
                        return;
                    } catch (Exception e) {
                        System.out.println("Erro: As datas inseridas são inválidas. Tente novamente.");
                    }
                }
            }
        }
        System.out.println("Aluguer não encontrado.");
    }

    public static void removerAluguer(Scanner sc) {
        int nif = InputValidation.validateInt(sc, "Digite o NIF do cliente: ");
        System.out.print("Digite a matrícula da viatura: ");
        String matricula = sc.nextLine().trim();

        LocalDate dataInicio;
        while (true) {
            System.out.print("Digite a data de início do aluguer (dd/MM/yyyy): ");
            String entrada = sc.nextLine().trim();
            try {
                dataInicio = LocalDate.parse(entrada, formatter);
                break;
            } catch (Exception e) {
                System.out.println("Data inválida. Tente novamente no formato dd/MM/yyyy.");
            }
        }

        for (int i = 0; i < alugueres.size(); i++) {
            Aluguer a = alugueres.get(i);
            if (a.getNifCliente() == nif &&
                    a.getMatriculaViatura().equalsIgnoreCase(matricula) &&
                    a.getDataInicio().equals(dataInicio)) {

                alugueres.remove(i);
                System.out.println("Aluguer removido com sucesso.");
                updateAlugueresFile();
                return;
            }
        }

        System.out.println("Aluguer não encontrado.");
    }


    public static void listarAlugueres() {
        if (alugueres.isEmpty()) {
            System.out.println("Não há alugueres registados.");
            return;
        }

        System.out.println("Lista de Alugueres:");
        for (Aluguer a : alugueres) {
            System.out.println("NIF Cliente: " + a.getNifCliente() +
                    ", Matrícula: " + a.getMatriculaViatura() +
                    ", Data Início: " + a.getDataInicio().format(formatter) +
                    ", Data Fim: " + a.getDataFim().format(formatter));
        }
    }


}