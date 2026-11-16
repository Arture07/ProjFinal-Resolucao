public class Ordenacao {
    public static long comparacoes = 0;
    public static long trocas = 0;
    public static long tempo = 0;

    // Cria uma copia independente para que cada algoritmo ordene os mesmos dados
    public static Tripulante[] copiarVetor(Tripulante[] original) {
        Tripulante[] copia = new Tripulante[original.length];
        for (int i = 0; i < original.length; i++) {
            copia[i] = original[i];
        }
        return copia;
    }

    // Bubble Sort: compara vizinhos e troca os assentos fora de ordem
    public static void bubbleSort(Tripulante[] v, boolean passoAPasso) {
        comparacoes = 0;
        trocas = 0;
        long inicio = System.nanoTime();

        for (int i = 0; i < v.length - 1; i++) {
            int compPassagem = 0;
            int trocasPassagem = 0;

            if (passoAPasso) {
                System.out.println("\n* Bubble Sort - passagem " + (i + 1));
            }

            for (int j = 0; j < v.length - 1 - i; j++) {
                comparacoes++;
                compPassagem++;

                if (v[j].codigo > v[j + 1].codigo) {
                    if (passoAPasso) {
                        System.out.println("  " + v[j].nome + " (" + v[j].codigo + ") > "
                                + v[j + 1].nome + " (" + v[j + 1].codigo + ")? SIM -> trocam de assento!");
                    }
                    Tripulante temp = v[j];
                    v[j] = v[j + 1];
                    v[j + 1] = temp;
                    trocas++;
                    trocasPassagem++;
                } else {
                    if (passoAPasso) {
                        System.out.println("  " + v[j].nome + " (" + v[j].codigo + ") > "
                                + v[j + 1].nome + " (" + v[j + 1].codigo + ")? NAO -> " + v[j].nome
                                + " fica onde esta.");
                    }
                }
            }

            if (passoAPasso) {
                System.out.println("  fim da passagem " + (i + 1) + " -> comparacoes: "
                        + compPassagem + " | trocas: " + trocasPassagem);
            }
        }

        tempo = System.nanoTime() - inicio;
    }

    // Insertion Sort: desloca os maiores para a direita e encaixa o eleito na
    // posicao certa
    public static void insertionSort(Tripulante[] v) {
        insertionSort(v, false);
    }

    public static void insertionSort(Tripulante[] v, boolean passoAPasso) {
        comparacoes = 0;
        trocas = 0;
        long inicio = System.nanoTime();

        for (int i = 1; i < v.length; i++) {
            Tripulante eleito = v[i];
            int j = i - 1;

            if (passoAPasso) {
                System.out.println("\n* Insertion Sort - inserindo " + eleito.nome + " (" + eleito.codigo + ")");
            }

            while (j >= 0) {
                comparacoes++;
                if (v[j].codigo > eleito.codigo) {
                    if (passoAPasso) {
                        System.out.println("  " + v[j].nome + " (" + v[j].codigo + ") > "
                                + eleito.nome + " (" + eleito.codigo + ")? SIM -> " + v[j].nome + " desce uma posicao");
                    }
                    v[j + 1] = v[j];
                    trocas++;
                    j--;
                } else {
                    if (passoAPasso) {
                        System.out.println("  " + v[j].nome + " (" + v[j].codigo + ") > "
                                + eleito.nome + " (" + eleito.codigo + ")? NAO -> " + eleito.nome + " encaixa aqui");
                    }
                    break;
                }
            }
            v[j + 1] = eleito;
        }

        tempo = System.nanoTime() - inicio;
    }

    // Quick Sort: usa o elemento do meio como pivo para evitar pior caso em vetores
    // ordenados
    public static void quickSort(Tripulante[] v) {
        quickSort(v, false);
    }

    public static void quickSort(Tripulante[] v, boolean passoAPasso) {
        comparacoes = 0;
        trocas = 0;
        long inicio = System.nanoTime();
        if (v.length > 0) {
            quickSortRec(v, 0, v.length - 1, passoAPasso);
        }
        tempo = System.nanoTime() - inicio;
    }

    private static void quickSortRec(Tripulante[] v, int esq, int dir, boolean passoAPasso) {
        int i = esq;
        int j = dir;
        int pivo = v[(esq + dir) / 2].codigo;

        if (passoAPasso) {
            System.out.println("\n* Quick Sort - pivo = " + pivo + " (posicoes " + esq + " ate " + dir + ")");
        }

        while (i <= j) {
            comparacoes++;
            while (v[i].codigo < pivo) {
                i++;
                comparacoes++;
            }

            comparacoes++;
            while (v[j].codigo > pivo) {
                j--;
                comparacoes++;
            }

            if (i <= j) {
                if (passoAPasso) {
                    if (i != j) {
                        System.out.println("  " + v[i].nome + " (" + v[i].codigo + ") e "
                                + v[j].nome + " (" + v[j].codigo + ") trocam de assento!");
                    } else {
                        System.out.println("  " + v[i].nome + " (" + v[i].codigo + ") ja esta no lugar (pivo)");
                    }
                }
                Tripulante temp = v[i];
                v[i] = v[j];
                v[j] = temp;
                trocas++;
                i++;
                j--;
            }
        }

        if (esq < j) {
            quickSortRec(v, esq, j, passoAPasso);
        }
        if (i < dir) {
            quickSortRec(v, i, dir, passoAPasso);
        }
    }
}
