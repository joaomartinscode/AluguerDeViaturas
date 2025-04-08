package AluguerDeViaturas;

import utils.InputValidation;

import java.nio.file.Paths;
import java.util.Scanner;

public class Aluguer {
    private static final String alugueresFilePath = Paths.get(Paths.get("").toAbsolutePath().toString(),
            "src\\AluguerDeViaturas", "Alugueres.csv").toString();

    private static Aluguer[] aluguer = new Aluguer[3];
    private static int nextAluguerIndex = 0;

    int nifCliente;
    String matriculaCarro;
    String dataInicio;
    String dataFim;

    public int getNifCliente() {
        return nifCliente;
    }

    public void setNifCliente(int nifCliente) {
        this.nifCliente = nifCliente;
    }

    public String getMatriculaCarro() {
        return matriculaCarro;
    }

    public void setMatriculaCarro(String matriculaCarro) {
        this.matriculaCarro = matriculaCarro;
    }

    public String getDataFim() {
        return dataFim;
    }

    public void setDataFim(String dataFim) {
        this.dataFim = dataFim;
    }

    public String getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(String dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Aluguer(int nifCliente, String matriculaCarro, String dataInicio, String dataFim) {
        this.nifCliente = nifCliente;
        this.matriculaCarro = matriculaCarro;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
    }

    public Aluguer(Scanner sc) {
        nifCliente = InputValidation.validateInt(sc, "NIF do cliente: ");
        System.out.println("Insira a matricula do carro: ");
        matriculaCarro = sc.nextLine();
        System.out.println("Insira o data de inicio do aluguer: ");
        dataInicio = sc.nextLine();
        System.out.println("Insira o data de fim do aluguer: ");
        dataFim = sc.nextLine();
    }

    @Override
    public String toString() {
        return "Nif do Cliente: " + nifCliente +
                ", Matricula do Carro: " + matriculaCarro +
                ", Data Inicio: " + dataInicio +
                ", Data Fim: " + dataFim;
    }
}
