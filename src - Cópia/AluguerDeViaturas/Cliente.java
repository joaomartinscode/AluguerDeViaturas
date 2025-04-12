package AluguerDeViaturas;

import utils.InputValidation;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Cliente {

    int NIF;
    String nome;
    String morada;
    int telefone;
    String email;
    LocalDate dataNascimento;
    int nCartaConducao;

    public int getNIF() {
        return NIF;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMorada() {
        return morada;
    }

    public void setMorada(String morada) {
        this.morada = morada;
    }

    public int getTelefone() {
        return telefone;
    }

    public void setTelefone(int telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public int getNCartaConducao() {
        return nCartaConducao;
    }

    public void setNCartaConducao(int nCartaConducao) {
        this.nCartaConducao = nCartaConducao;
    }

    public Cliente(int NIF, String nome, String morada, int telefone, String email, LocalDate dataNascimento, int nCartaConducao) {
        this.NIF = NIF;
        this.nome = nome;
        this.morada = morada;
        this.telefone = telefone;
        this.email = email;
        this.dataNascimento = dataNascimento;
        this.nCartaConducao = nCartaConducao;
    }

    public Cliente(Scanner sc, int NIF) {
        this.NIF = NIF;
        nome = InputValidation.validateString(sc, "Nome do cliente: ");
        morada = InputValidation.validateString(sc, "Morada do cliente: ");
        telefone = InputValidation.validateNineDigitInteger(sc, "Digite o telefone: ");
        email = InputValidation.validateEmail(sc, "Digite o email: ");
        dataNascimento = InputValidation.validateDate(sc, "Digite a data de nascimento: ");
        nCartaConducao = InputValidation.validateIntGT0(sc, "Digite o numero de carta de condução: ");
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dataNascimentoStr = dataNascimento.format(formatter);

        return "NIF: " + NIF +
                ", Nome: " + nome +
                ", Morada: " + morada +
                ", Telefone: " + telefone +
                ", Email: " + email +
                ", Data de Nascimento: " + dataNascimentoStr +
                ", Número Carta de Condução: " + nCartaConducao;
    }
}
