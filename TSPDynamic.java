import java.io.FileReader;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.*;

public class TSPDynamic {
    public static void main(String[] args) throws Exception {
        int[][] C = leMatrizAdjascencia();
        int n = C.length;

        List<int[]> S = Combination.generate(n);
        int lastIndex = S.size() - 1;
        int[] S_final = S.get(lastIndex);
        S.remove(lastIndex);

        Map<Integer, Map> P = new HashMap<>();
        Map<Integer, Map> G = new HashMap<>();
        Map<List<Integer>, Integer> aux1;
        Map<List<Integer>, Integer> aux2;
        List<Integer> arrAux1;
        List<Integer> arrAux2;

        for (int k = 1; k <= n; k++) {
            aux1 = new HashMap<>();
            aux2 = new HashMap<>();
            arrAux1 = new ArrayList<>();
            arrAux2 = new ArrayList<>();

            aux1.put(arrAux1, C[k-1][0]);
            aux2.put(arrAux2, C[k-1][0]);

            P.put(k, aux1);
            G.put(k, aux2);
        }

        List<Integer> sWithoutS;
        int minDistS = -1;
        int minDist;
        for (int[] s : S) {
            for (int k = 2; k <= n; k++) {
                if (!contains(s, k)) {
                    minDist = Integer.MAX_VALUE;
                    for (int _s : s) {
                        sWithoutS = toList(s);
                        sWithoutS.remove(Integer.valueOf(_s));
                        Map gMap = G.get(_s);

                        int c = C[k-1][_s-1];
                        int g = (Integer) gMap.get(sWithoutS);
                        if ((g + c) < minDist) {
                            minDist = g + c;
                            minDistS = _s;

                            G = mergeMaps(s, minDist, G, k);
                        }

                        P = mergeMaps(s, minDistS, P, k);
                    }
                }
            }
        }

        minDist = Integer.MAX_VALUE;
        int k = 1;
        for (int _s : S_final) {
            sWithoutS = toList(S_final);
            sWithoutS.remove(Integer.valueOf(_s));
            Map gMap = G.get(_s);

            int c = C[k-1][_s-1];
            int g = (Integer) gMap.get(sWithoutS);
            if ((g + c) < minDist) {
                minDist = g + c;
                minDistS = _s;
            }
        }

        List<Integer> visited = new ArrayList<>();
        visited.add(k);

        P = mergeMaps(S_final, minDistS, P, k);

        List<Integer> S_final_list = toList(S_final);
        while (S_final_list.size() > 0) {
            k = minDistS;
            S_final_list.remove(Integer.valueOf(k));
            visited.add(minDistS);
            Map pMap = P.get(Integer.valueOf(k));
            minDistS = (Integer) pMap.get(S_final_list);
        }

        System.out.println("Visitados: " + visited);
        System.out.println("Custo: " + minDist);
    }

    static Map<Integer,Map> mergeMaps(int[] s, int val, Map<Integer,Map> target, int key) {
        Map<List<Integer>, Integer> aux1;
        Map<List<Integer>, Integer> aux2;

        aux1 = new HashMap<>();
        aux1.put(toList(s), val);
        aux2 = new HashMap<>();
        aux2.putAll(target.get(key));
        aux2.putAll(aux1);
        target.put(key, aux2);

        return target;
    }

    static List<Integer> toList(int[] s) {
        return IntStream.of(s).boxed().collect(Collectors.toList());
    }

    static boolean contains(int[] arr, int v) {
        for (int a : arr) {
            if (a == v) return true;
        }
        return false;
    }

    static int[][] leMatrizAdjascencia() throws Exception {
        Scanner in = new Scanner(new FileReader("./matriz.txt"));
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


class Combination {
    public static List<int[]> combinations = new ArrayList<>();

    static void combinationUtil(int arr[], int data[], int start,
                                int end, int index, int r) {
        if (index == r) {
            int[] tmp = new int[r];
            for (int j=0; j<r; j++) {
                tmp[j] = data[j];
                combinations.add(tmp);
            }

            return;
        }
 
        for (int i=start; i<=end && end-i+1 >= r-index; i++) {
            data[index] = arr[i];
            combinationUtil(arr, data, i+1, end, index+1, r);
        }
    }
 
    static void printCombination(int arr[], int n, int r) {
        int data[] = new int[r];
        combinationUtil(arr, data, 0, n-1, 0, r);
    }
 
    public static List<int[]> generate(int n) {
        int[] arr = new int[n - 1];
        for (int v = 2; v <= n; v++)
            arr[v-2] = v;

        for (int r = 1; r < (n - 1); r++) {
            printCombination(arr, (n - 1), r);
        }

        combinations.add(arr);

        return combinations;
    }
}
