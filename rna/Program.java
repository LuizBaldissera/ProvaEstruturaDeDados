package rna;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;



public class Program {
    public static void main(String[] args) {
        System.out.println("Exemplo de RNA Perceptron para classificação de equipes");

        List<Ponto> amostras = new ArrayList<>();
        List<Integer> saidas = new ArrayList<>();

        String csvFile = "C:\\Users\\luizh\\OneDrive\\Documentos\\NetBeansProjects\\rna\\src\\rna\\amostras_saidas_problemaTimes.csv";
        String line;
        String Split = ";";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                String[] data = line.split(Split);
                double x = Double.parseDouble(data[0]);
                double y = Double.parseDouble(data[1]);
                int saida = Integer.parseInt(data[2]);
                amostras.add(new Ponto(x, y));
                saidas.add(saida);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        double taxaAprendizado = 0.1;
        int geracoes = 1000;
        int limiar = 1;
        Perceptron p = new Perceptron(amostras, saidas, taxaAprendizado, geracoes, limiar);

        p.treinar();

        String op;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            do {
                System.out.print("\n\nInforme valor para x (-1 a 1): ");
                double x = Double.parseDouble(br.readLine());
                System.out.print("Informe valor para y (-1 a 1): ");
                double y = Double.parseDouble(br.readLine());

                p.testar(new Ponto(x, y));
                System.out.print("1 - Sair: ");
                op = br.readLine();
            } while (!"1".equals(op));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
