
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

        // Starting from the 0th indexed
        // city i.e., the first city
        verticesVisiados.add(0);
        int[] route = new int[matriz.length];

        // Traverse the adjacency
        // matrix matriz[][]
        while (i < matriz.length && j < matriz[i].length) {
            // System.out.println(i + " " + j + " " + matriz[i].length + " " +
            // matriz.length);

            // Corner of the Matrix
            if (contador >= matriz[i].length - 1) {
                break;
            }

            // If this path is unvisited then
            // and if the cost is less then
            // update the cost
            if (j != i && !(verticesVisiados.contains(j))) {
                if (matriz[i][j] < menorVertice) {
                    menorVertice = matriz[i][j];
                    route[contador] = j + 1;
                    // System.out.println("if um");
                }
            }
            j++;

            // Check all paths from the
            // ith indexed city
            if (j == matriz[i].length) {
                somaDaDistancia += menorVertice;
                menorVertice = Integer.MAX_VALUE;
                verticesVisiados.add(route[contador] - 1);
                j = 0;
                i = route[contador] - 1;
                contador++;
                // System.out.println("if dois");
            }
        }

        // Update the ending city in array
        // from city which was last visited
        i = route[contador - 1] - 1;

        for (j = 0; j < matriz.length; j++) {

            if ((i != j) && matriz[i][j] < menorVertice) {
                menorVertice = matriz[i][j];
                route[contador] = j + 1;
            }
        }
        somaDaDistancia += menorVertice;

        /*
         * System.out.print("O custo minimo e: ");
         * System.out.println(somaDaDistancia);
         * System.out.print("Rota: ");
         * for (int a = 0; a < route.length; a++) {
         * System.out.print(route[a] + " ");
         * }
         */
    }

    // Driver Code
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
}