package AluguerDeViaturas;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Aluguer {
    private int nifCliente;
    private String matriculaViatura;
    private LocalDate dataInicio;
    private LocalDate dataFim;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public Aluguer(int nifCliente, String matriculaViatura, LocalDate dataInicio, LocalDate dataFim) {
        this.nifCliente = nifCliente;
        this.matriculaViatura = matriculaViatura;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
    }

    public int getNifCliente() {
        return nifCliente;
    }

    public String getMatriculaViatura() {
        return matriculaViatura;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
    }

    @Override
    public String toString() {
        return "NIF: " + nifCliente + ", Viatura: " + matriculaViatura +
                ", De: " + dataInicio.format(formatter) +
                " Até: " + dataFim.format(formatter);
    }
}
