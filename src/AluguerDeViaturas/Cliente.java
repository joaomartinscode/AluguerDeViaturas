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

    public void setNIF(int NIF) {
        this.NIF = NIF;
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

    public int getnCartaConducao() {
        return nCartaConducao;
    }

    public void setnCartaConducao(int nCartaConducao) {
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
        System.out.print("Digite o nome do cliente: ");
        nome = sc.nextLine();
        System.out.print("Digite o morada do cliente: ");
        morada = sc.nextLine();
        telefone = InputValidation.validateNineDigitInteger(sc, "Digite o telefone: ");
        while (true) {
            System.out.print("Digite o email do cliente: ");
            email = sc.nextLine();
            if (email.contains("@") && email.contains(".") && email.indexOf("@") < email.lastIndexOf(".")) {
                break;
            } else {
                System.out.println("Email inválido. Por favor, insira um email com '@' e domínio (ex: exemplo@dominio.com).");
            }
        }

        while (true) {
            try {
                System.out.print("Digite a data de nascimento do cliente (formato: dd/MM/yyyy): ");
                String dataStr = sc.nextLine();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                dataNascimento = LocalDate.parse(dataStr, formatter);
                break;
            } catch (DateTimeParseException e) {
                System.out.println("Data inválida. Por favor, use o formato correto (dd/MM/yyyy).");
            }
        }
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
