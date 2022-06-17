
// Java program for the above approach
import java.io.FileReader;
import java.util.*;
import java.util.regex.Pattern;

public class TSPGreedy {

    // Function to find the minimum
    // cost path for all the paths
    static void encontrarMenorRota(int[][] matriz) {
        int somaDaDistancia = 0;
        int contador = 0;
        int j = 0, i = 0;
        int menorVertice = Integer.MAX_VALUE;
        List<Integer> verticesVisiados = new ArrayList<>();

        verticesVisiados.add(0);
        int[] route = new int[matriz.length];

        while (i < matriz.length && j < matriz[i].length) {

            if (contador >= matriz[i].length - 1) {
                break;
            }

            if (j != i && !(verticesVisiados.contains(j))) {
                if (matriz[i][j] < menorVertice) {
                    menorVertice = matriz[i][j];
                    route[contador] = j + 1;
                }
            }

            j++;

            if (j == matriz[i].length) {
                somaDaDistancia += menorVertice;
                menorVertice = Integer.MAX_VALUE;
                verticesVisiados.add(route[contador] - 1);
                j = 0;
                i = route[contador] - 1;
                contador++;
            }
        }
        i = route[contador - 1] - 1;

        for (j = 0; j < matriz.length; j++) {

            if ((i != j) && matriz[i][j] < menorVertice) {
                menorVertice = matriz[i][j];
                route[contador] = j + 1;
            }
        }
        somaDaDistancia += menorVertice;

        System.out.print("O custo minimo e: ");
        System.out.println(somaDaDistancia);
        System.out.print("Rota: ");
        System.out.print(route[route.length - 1] + " ");
        for (int a = 0; a < route.length; a++) {
            System.out.print(route[a] + " ");
        }

    }

    static int[][] leMatrizAdjascencia() throws Exception {
        Scanner in = new Scanner(new FileReader("matriz.txt"));
        int tamMatriz = Integer.parseInt(in.nextLine());
        int[][] matriz = new int[tamMatriz][tamMatriz];
        int countLinhas = 0;

        while (countLinhas < tamMatriz) {
            String linha = in.nextLine();
            String linhaMatriz[] = linha.split(Pattern.quote(","));
            for (int i = 0; i < tamMatriz; i++) {
                matriz[countLinhas][i] = Integer.parseInt(linhaMatriz[i]);
            }
            countLinhas++;
        }
        return matriz;
    }

    public static void main(String[] args) throws Exception {

        int[][] matriz = leMatrizAdjascencia();

        // imprimi matriz pra testar
        /*
         * for (int i = 0; i < matriz.length; i++) {
         * for (int j = 0; j < matriz.length; j++) {
         * System.out.print(matriz[i][j]);
         * System.out.print(",");
         * }
         * System.out.println("");
         * }
         */

        encontrarMenorRota(matriz);
    }
}