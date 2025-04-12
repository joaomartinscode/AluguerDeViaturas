package AluguerDeViaturas;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class GestorDeDados {
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
        viaturas.clear(); // Limpa a lista de viaturas antes de carregá-las do arquivo
        try (BufferedReader br = new BufferedReader(new FileReader(viaturasFilePath, StandardCharsets.ISO_8859_1))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] vArray = line.split(";");
                // Cria um objeto Viatura a partir dos dados lidos e adiciona à lista de viaturas
                Viatura v = new Viatura(vArray[0], vArray[1], vArray[2],
                        Double.parseDouble(vArray[3]), Integer.parseInt(vArray[4]), Integer.parseInt(vArray[5]));
                viaturas.add(v);
            }
        } catch (Exception e) {
            System.out.println("Erro ao carregar o ficheiro de viaturas do caminho " + viaturasFilePath);
            return false; // Retorna false caso ocorra algum erro
        }
        return true; // Retorna true se o carregamento for bem-sucedido
    }


    // Método para atualizar as viaturas para o ficheiro CSV
    public static void updateViaturaToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(viaturasFilePath, StandardCharsets.ISO_8859_1))) {
            // Para cada viatura na lista, escreve os dados no arquivo CSV
            for (Viatura v : viaturas) {
                bw.write(v.getMarca() + ";" + v.getModelo() + ";" + v.getMatricula() + ";" +
                        v.getKm() + ";" + v.getAno() + ";" + v.getNLugares() + "\n");
            }
        } catch (Exception e) {
            System.out.println("Erro ao atualizar o ficheiro de viaturas.");
        }
    }

    // Método para verificar se uma viatura com uma matrícula já existe
    public static boolean viaturaExiste(String matricula) {
        for (Viatura viatura : viaturas) {
            if (viatura.getMatricula().equalsIgnoreCase(matricula)) {
                return true; // Retorna true se a viatura com a matrícula for encontrada
            }
        }
        return false; // Retorna false se a viatura não for encontrada
    }

    // Método para adicionar uma nova viatura à lista e atualizar o arquivo
    public static void gravarNovaViatura(Viatura viatura) {
        viaturas.add(viatura); // Adiciona a nova viatura à lista
        updateViaturaToFile(); // Atualiza o arquivo com a nova viatura
    }

    // Método para obter uma viatura a partir da matrícula
    public static Viatura obterViaturaPorMatricula(String matricula) {
        for (Viatura v : viaturas) {
            if (v.getMatricula().equalsIgnoreCase(matricula)) return v; // Retorna a viatura encontrada
        }
        return null; // Retorna null se a viatura não for encontrada
    }

    // Método para remover uma viatura
    public static boolean removerViatura(String matricula) {
        for (int i = 0; i < viaturas.size(); i++) {
            if (viaturas.get(i).getMatricula().equals(matricula)) {
                viaturas.remove(i);     // Remove a viatura da lista
                updateViaturaToFile();  // Atualiza o arquivo
                return true;            // Retorna true se a viatura foi removida com sucesso
            }
        }
        return false; // Retorna false se a viatura não for encontrada
    }

    // Método para listar todas as viaturas
    public static void listarViaturas() {
        for (Viatura v : viaturas) {
            System.out.println(v); // Imprime as informações de cada viatura
        }
    }

    // === CLIENTES ===

    // Método para carregar os clientes a partir de um ficheiro CSV
    public static boolean loadClientesFile() {
        clientes.clear(); // Limpa a lista de clientes antes de carregá-los do arquivo
        try (BufferedReader br = new BufferedReader(new FileReader(clientesFilePath, StandardCharsets.ISO_8859_1))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] c = line.split(";");
                LocalDate dataNascimento = LocalDate.parse(c[5], formatter);    // Converte a data de nascimento para LocalDate
                // Cria um objeto Cliente e adiciona à lista de clientes
                Cliente cl = new Cliente(Integer.parseInt(c[0]), c[1], c[2], Integer.parseInt(c[3]), c[4], dataNascimento, Integer.parseInt(c[6]));
                clientes.add(cl);
            }
        } catch (Exception e) {
            System.out.println("Erro ao carregar o ficheiro de clientes.");
            return false; // Retorna false se ocorrer um erro ao carregar os clientes
        }
        return true; // Retorna true se o carregamento for bem-sucedido
    }

    // Método para atualizar os clientes para o ficheiro CSV
    public static void updateClienteToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(clientesFilePath, StandardCharsets.ISO_8859_1))) {
            // Para cada cliente na lista, escreve os dados no arquivo CSV
            for (Cliente c : clientes) {
                bw.write(c.getNIF() + ";" + c.getNome() + ";" + c.getMorada() + ";" +
                        c.getTelefone() + ";" + c.getEmail() + ";" +
                        c.getDataNascimento().format(formatter) + ";" + c.getNCartaConducao() + "\n");
            }
        } catch (Exception e) {
            System.out.println("Erro ao atualizar o ficheiro de clientes.");
        }
    }

    // Método para verificar se um cliente com determinado NIF já existe
    public static boolean clienteExiste(int nif) {
        for (Cliente cliente : clientes) {
            if (cliente.getNIF() == nif) {
                return true; // Retorna true se o cliente for encontrado
            }
        }
        return false; // Retorna false se o cliente não for encontrado
    }

    // Método para obter um cliente a partir de seu NIF
    public static Cliente obterClientePorNIF(int nif) {
        for (Cliente c : clientes) {
            if (c.getNIF() == nif) return c; // Retorna o cliente com o NIF fornecido
        }
        return null; // Retorna null se o cliente não for encontrado
    }

    // Método para adicionar um novo cliente à lista e atualizar o arquivo
    public static void gravarNovoCliente(Cliente cliente) {
        clientes.add(cliente); // Adiciona o novo cliente à lista
        updateClienteToFile(); // Atualiza o arquivo de clientes
    }

    // Método para remover um cliente
    public static boolean removerCliente(int nif) {
        for (int i = 0; i < clientes.size(); i++) {
            if (clientes.get(i).getNIF() == nif) {
                clientes.remove(i);     // Remove o cliente da lista
                updateClienteToFile();  // Atualiza o arquivo
                return true; // Retorna true se o cliente foi removido
            }
        }
        return false; // Retorna false se o cliente não for encontrado
    }

    // Método para listar todos os clientes
    public static void listarClientes() {
        for (Cliente c : clientes) {
            System.out.println(c); // Imprime as informações de cada cliente
        }
    }


    // === ALUGUERES ===

    // Método para carregar os alugueres a partir do arquivo CSV
    public static boolean loadAlugueresFile() {
        alugueres.clear(); // Limpa a lista de alugueres antes de carregá-los do arquivo
        try (BufferedReader br = new BufferedReader(new FileReader(alugueresFilePath, StandardCharsets.ISO_8859_1))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] a = line.split(";");
                // Converte a data de inicio e fim para LocalDate
                LocalDate dataInicio = LocalDate.parse(a[2], formatter);
                LocalDate dataFim = LocalDate.parse(a[3], formatter);
                // Cria um objeto Aluguer e adiciona à lista de alugueres
                Aluguer al = new Aluguer(Integer.parseInt(a[0]), a[1], dataInicio, dataFim);
                alugueres.add(al);
            }
        } catch (Exception e) {
            System.out.println("Erro ao carregar o ficheiro de alugueres.");
            return false; // Retorna false se ocorrer um erro ao carregar os alugueres
        }
        return true; // Retorna true se o carregamento for bem-sucedido
    }

    // Método para adicionar um novo aluguer, validando o cliente, a viatura, as datas e os conflitos de alugueres
    public static boolean adicionarAluguer(int nif, String matricula, LocalDate dataInicio, LocalDate dataFim) {
        // Validar cliente
        boolean clienteExiste = false;
        for (Cliente c : clientes) {
            if (c.getNIF() == nif) {
                clienteExiste = true;
                break;
            }
        }
        if (!clienteExiste) {
            System.out.println("Erro: Cliente não encontrado.");
            return false; // Retorna false se o cliente não for encontrado
        }

        // Validar viatura
        boolean viaturaExiste = false;
        for (Viatura v : viaturas) {
            if (v.getMatricula().equalsIgnoreCase(matricula)) {
                viaturaExiste = true;
                break;
            }
        }
        if (!viaturaExiste) {
            System.out.println("Erro: Viatura não encontrada.");
            return false; // Retorna false se a viatura não for encontrada
        }

        // Validar datas
        if (!dataFim.isAfter(dataInicio)) {
            System.out.println("Erro: A data de fim deve ser posterior à data de início.");
            return false; // Retorna false se a data de fim for inválida
        }

        // Verificar conflito de datas com alugueres existentes
        for (Aluguer a : alugueres) {
            if (a.getMatriculaViatura().equalsIgnoreCase(matricula)) {
                if (!(dataFim.isBefore(a.getDataInicio()) || dataInicio.isAfter(a.getDataFim()))) {
                    System.out.println("Erro: Já existe um aluguer para esta viatura neste período.");
                    return false; // Retorna false se houver alugueres em conflito
                }
            }
        }

        // Criar um aluguer com os dados fornecidos
        Aluguer novoAluguer = new Aluguer(nif, matricula, dataInicio, dataFim);
        alugueres.add(novoAluguer); // Adiciona o aluguer à lista de alugueres
        updateAlugueresFile();      // Atualiza o arquivo de alugueres
        return true; // Retorna true se o aluguer foi adicionado com sucesso
    }

    // Método para atualizar o arquivo de alugueres com os dados da lista
    private static void updateAlugueresFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(alugueresFilePath, StandardCharsets.ISO_8859_1))) {
            // Para cada aluguer na lista, escreve os dados no arquivo CSV
            for (Aluguer a : alugueres) {
                bw.write(a.getNifCliente() + ";" +
                        a.getMatriculaViatura() + ";" +
                        a.getDataInicio().format(formatter) + ";" +
                        a.getDataFim().format(formatter) + "\n");
            }
        } catch (Exception e) {
            System.out.println("Erro ao atualizar o ficheiro de alugueres.");  // Exibe mensagem de erro caso falhe a atualização
        }
    }

    // Método para editar um aluguer existente, verificando conflitos com outros alugueres
    public static boolean editarAluguer(int nif, String matricula, LocalDate dataOriginal, LocalDate novaDataInicio, LocalDate novaDataFim) {
        Aluguer aluguerAntigo = null;

        // Procura pelo aluguer antigo com base no NIF, matrícula e data de início original
        for (Aluguer a : alugueres) {
            if (a.getNifCliente() == nif &&
                    a.getMatriculaViatura().equalsIgnoreCase(matricula) &&
                    a.getDataInicio().equals(dataOriginal)) {
                aluguerAntigo = a;
                break;
            }
        }

        if (aluguerAntigo == null) {
            return false; // Aluguer não encontrado
        }

        // Verifica conflitos com outros alugueres da mesma viatura
        for (Aluguer a : alugueres) {
            if (a != aluguerAntigo && a.getMatriculaViatura().equalsIgnoreCase(matricula)) {
                if (!(novaDataFim.isBefore(a.getDataInicio()) || novaDataInicio.isAfter(a.getDataFim()))) {
                    return false; // Conflito com outro aluguer
                }
            }
        }

        // Remove o aluguer antigo e adiciona o novo com as datas atualizadas
        alugueres.remove(aluguerAntigo);
        Aluguer novoAluguer = new Aluguer(nif, matricula, novaDataInicio, novaDataFim);
        alugueres.add(novoAluguer); // Adiciona o novo aluguer à lista
        updateAlugueresFile();      // Atualiza o arquivo de alugueres
        return true; // Retorna true se o aluguer foi editado com sucesso
    }

    // Método para remover um aluguer da lista e atualizar o arquivo
    public static boolean removerAluguer(int nif, String matricula, LocalDate dataInicio) {
        // Procura pelo aluguer com o NIF, matrícula e data de início fornecidos
        for (Aluguer a : alugueres) {
            if (a.getNifCliente() == nif &&
                    a.getMatriculaViatura().equalsIgnoreCase(matricula) &&
                    a.getDataInicio().equals(dataInicio)) {
                alugueres.remove(a);    // Remove o aluguer da lista
                updateAlugueresFile();  // Atualiza o arquivo de alugueres
                return true; // Retorna true se o aluguer foi removido com sucesso
            }
        }
        return false; // Retorna false se o aluguer não for encontrado
    }

    // Método para listar todos os alugueres
    public static void listarAlugueres() {
        if (alugueres.isEmpty()) {
            System.out.println("Não há alugueres registados.");  // Exibe mensagem caso não haja alugueres
            return;
        }

        // Exibe a lista de todos os alugueres
        System.out.println("Lista de Alugueres:");
        for (Aluguer a : alugueres) {
            System.out.println("NIF Cliente: " + a.getNifCliente() +
                    ", Matrícula: " + a.getMatriculaViatura() +
                    ", Data Início: " + a.getDataInicio().format(formatter) +
                    ", Data Fim: " + a.getDataFim().format(formatter));
        }
    }
}