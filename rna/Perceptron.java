/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rna;

/**
 *
 * @author luizh
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Perceptron {
    public List<Ponto> amostras = new ArrayList<>();
    public List<Integer> saidas = new ArrayList<>();
    public double taxaAprendizado;
    public int geracoes;
    public int limiar;
    public int numeroAmostras;
    public int numeroAtributos;
    public double[] pesos;

    public Perceptron(List<Ponto> amostras, List<Integer> saidas, double taxaAprendizado, int geracoes, int limiar) {
        this.amostras = amostras;
        this.saidas = saidas;
        this.taxaAprendizado = taxaAprendizado;
        this.geracoes = geracoes;
        this.limiar = limiar;
        this.numeroAmostras = amostras.size();
        this.pesos = new double[3]; // tamanho deste vetor peso vai ser igual a quantidade de atributos + um valor para limiar
    }

    private int funcaoAtivacaoSignal(double soma) {
        return (soma >= 0) ? 1 : -1;
    }

    public void treinar() {
        // Inserir o valor do limiar na posição limiar de cada ponto de cada amostra da lista "amostras"
        for (int i = 0; i < this.amostras.size(); i++) {
            amostras.get(i).limiar = this.limiar;
        }

        // Gerar valores randômicos entre 0 e 1 (pesos) conforme o número de atributos
        Random gerador = new Random();
        pesos[0] = limiar;
        pesos[1] = gerador.nextDouble(); // para o peso da entrada x
        pesos[2] = gerador.nextDouble(); // para o peso da entrada y

        int conta = 0;
        boolean aprendeu;
        double soma;
        int saidaGerada;

        while (true) {
            aprendeu = true;
            soma = 0;

            for (int i = 0; i < amostras.size(); i++) {
                Ponto ponto = amostras.get(i);

                // Inicializar potencial de ativação
                soma = ponto.limiar * pesos[0] + ponto.x * pesos[1] + ponto.y * pesos[2];

                // Obter a saída da rede considerando a função sinal
                saidaGerada = funcaoAtivacaoSignal(soma);

                // Verificar se a saída da rede é diferente da saída desejada
                if (saidaGerada != this.saidas.get(i)) {
                    aprendeu = false;
                    double erro = this.saidas.get(i) - saidaGerada;

                    // Fazer o ajuste dos pesos para cada elemento da amostra
                    this.pesos[0] += this.taxaAprendizado * erro * ponto.limiar;
                    this.pesos[1] += this.taxaAprendizado * erro * ponto.x;
                    this.pesos[2] += this.taxaAprendizado * erro * ponto.y;
                }
            }

            // Atualizar contador de gerações
            conta++;

            if (aprendeu || conta > this.geracoes) {
                System.out.println("Geracoes de treinamento: " + conta);
                break;
            }
        }
    }

    // Testes para "novas" amostras
    public void testar(Ponto amostra) {
        // Inserir o valor do limiar na posição "0" para cada amostra da lista "amostras"
        amostra.limiar = this.limiar;

        // Inicializar potencial de ativação
        double soma = amostra.limiar * pesos[0] + amostra.x * pesos[1] + amostra.y * pesos[2];

        // Obter a saída da rede considerando a função funcao_ativacao_signal
        int saidaGerada = this.funcaoAtivacaoSignal(soma);

        if (saidaGerada == 1) {
            System.out.println("Classe: " + saidaGerada + " ou Time Azul");
        } else {
            System.out.println("Classe: " + saidaGerada + " ou Time Vermelho");
        }
    }
}

