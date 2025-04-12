package AluguerDeViaturas;

import utils.InputValidation;

import java.util.Scanner;

public class Viatura {

    String marca;
    String modelo;
    String matricula;
    int ano;
    double km;
    int nLugares;


    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getMatricula() {
        return matricula;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public double getKm() {
        return km;
    }

    public void setKm(double km) {
        this.km = km;
    }

    public int getNLugares() {
        return nLugares;
    }

    public void setNLugares(int nLugares) {
        this.nLugares = nLugares;
    }

    public Viatura(String marca, String modelo, String matricula, double km, int ano, int nLugares) {
        this.marca = marca;
        this.modelo = modelo;
        this.matricula = matricula;
        this.km = km;
        this.ano = ano;
        this.nLugares = nLugares;
    }

    public Viatura(Scanner sc, String matricula) {
        this.matricula = matricula;
        marca = InputValidation.validateString(sc, "Digite a marca da viatura: ");
        modelo = InputValidation.validateString(sc, "Digite o modelo da viatura: ");
        ano = InputValidation.validateIntGT0(sc, "Digite o ano da viatura: ");
        km = InputValidation.validateDoubleGE0(sc, "Digite o número de km da viatura: ");
        nLugares = InputValidation.validateIntGT0(sc, "Digite o número de lugares da viatura: ");
    }


    @Override
    public String toString() {
        return "Marca: " + marca +
                ", Modelo: " + modelo +
                ", Matricula: " + matricula +
                ", Ano: " + ano +
                ", Km: " + km +
                ", nLugares: " + nLugares;
    }
}
