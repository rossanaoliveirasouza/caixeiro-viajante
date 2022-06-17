import java.io.FileReader;
import java.util.*;
import java.util.regex.Pattern;

public class TSPNaive {

    static int verticeInicial = 0;
    static int[][] matriz;
    static int melhorRota = Integer.MAX_VALUE;

    /*
     * Lê matriz de adjascencia de um arquivo.txt
     * 
     */
    static int[][] leMatrizAdjascencia() {
        int[][] matriz = new int[0][0];
        try {
            Scanner in = new Scanner(new FileReader("matriz.txt"));
            int tamMatriz = Integer.parseInt(in.nextLine());
            matriz = new int[tamMatriz][tamMatriz];
            int countLinhas = 0;

            while (countLinhas < tamMatriz) {
                String linha = in.nextLine();
                String linhaMatriz[] = linha.split(Pattern.quote(","));
                for (int i = 0; i < tamMatriz; i++) {
                    matriz[countLinhas][i] = Integer.parseInt(linhaMatriz[i]);
                }
                countLinhas++;
            }

        } catch (Exception e) {
            System.out.println(e);
        }

        return matriz;
    }

    public static void naiveTSP(List<Integer> vet) {
        int[] p = new int[vet.size()];
        naiveTSP(p, vet, 0);
    }

    static void naiveTSP(int[] p, List<Integer> vet, int n) {

        if (n == vet.size()) {

            int j = 0;
            int soma = matriz[verticeInicial][p[j]];

            for (int i = 1; i < p.length; i++, j++)
                soma += matriz[p[j]][p[i]]; // soma de cada possibilidade

            soma += matriz[p[j]][verticeInicial];
            if (soma < melhorRota)
                melhorRota = soma;

            System.out.println();
            imprimeVet(p);
            System.out.print(" custo: " + soma);
        } else {
            for (int i = 0; i < vet.size(); i++) {
                boolean achou = false;
                for (int j = 0; j < n; j++) {
                    if (p[j] == vet.get(i))
                        achou = true;
                }
                if (!achou) {
                    p[n] = vet.get(i);
                    naiveTSP(p, vet, n + 1);
                }
            }
        }
    }

    static void imprimeVet(int[] vet) {
        System.out.print(verticeInicial + "-");
        for (int i = 0; i < vet.length; i++)
            System.out.print(vet[i] + "-");
        System.out.print(verticeInicial);
    }

    public static void main(String[] args) {
        matriz = leMatrizAdjascencia();
        String vertice = "";

        List<Integer> v = new ArrayList<>();
        int tam = matriz.length;
        Scanner reader = new Scanner(System.in);

        System.out.println("Caixeiro viajante - método: Força Bruta");
        System.out.println("Selecione o vertice inicial");
        for (int i = 0; i < tam; i++) {
            System.out.println((char) (i + 65));
        }
        vertice = reader.nextLine();
        verticeInicial = vertice.hashCode() - 65;

        for (int i = 0; i < tam; i++) {
            if (i != verticeInicial)
                v.add(i);
        }

        naiveTSP(v);
        System.out.println("\nMelhor rota tem custo: " + melhorRota);
    }

}
