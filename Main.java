import java.io.File;
import java.util.Random;
import java.util.Scanner;

public class Main {

    static String[] nomes = { "Zibo", "Quark", "Lumi", "Gloop", "Cometa", "Pixel", "Vovo", "Plim", "Fafa", "Orbi" };
    static String[] planetas = { "Marte", "Jupiter", "Plutao", "Venus", "Mercurio", "Vega", "Netuno", "Saturno" };

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        TabelaHash tabela = new TabelaHash(13, 1);
        carregarArquivo(tabela);

        int opcao = -1;
        while (opcao != 0) {
            System.out.println("\n+==========================================+");
            System.out.println("|           * MISSAO GALACTICA *           |");
            System.out.println("+==========================================+");
            System.out.println("| 1 - Registrar tripulante                 |");
            System.out.println("| 2 - Buscar tripulantes por planeta       |");
            System.out.println("| 3 - Remover tripulante (codigo)          |");
            System.out.println("| 4 - Mostrar modulos (tabela hash)        |");
            System.out.println("| 5 - Estatisticas da tabela               |");
            System.out.println("| 6 - Organizar embarque na capsula        |");
            System.out.println("| 7 - Modo passo a passo                   |");
            System.out.println("| 8 - A Grande Corrida                     |");
            System.out.println("| 0 - Encerrar a missao                    |");
            System.out.println("+==========================================+");
            System.out.print("Escolha uma opcao: ");
            opcao = lerInteiro(sc);

            if (opcao == 1) {
                registrarTripulante(sc, tabela);

            } else if (opcao == 2) {
                System.out.print("Digite o planeta natal: ");
                String planeta = sc.nextLine();
                tabela.buscarPorPlaneta(planeta);

            } else if (opcao == 3) {
                removerTripulante(sc, tabela);

            } else if (opcao == 4) {
                tabela.exibirTabela();

            } else if (opcao == 5) {
                System.out.println("\n=== TABELA ATUAL EM USO ===");
                tabela.exibirEstatisticas();
                System.out.println("\n=== COMPARATIVO DO RELATORIO (M = 13, 17 e 23) ===");
                testarTamanhosEFuncoes();

            } else if (opcao == 6) {
                organizarEmbarque(tabela);

            } else if (opcao == 7) {
                modoPassoAPasso(tabela);

            } else if (opcao == 8) {
                grandeCorrida();

            } else if (opcao != 0) {
                System.out.println("Opcao invalida! Escolha de 0 a 8.");
            }
        }

        System.out.println("Missao encerrada!");
        sc.close();
    }

    // Le um numero inteiro sem quebrar o programa se digitar letra ou Enter vazio
    public static int lerInteiro(Scanner sc) {
        try {
            return Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    // Cadastra um novo tripulante validando codigo unico de 8 digitos
    public static void registrarTripulante(Scanner sc, TabelaHash tabela) {
        System.out.print("Codigo (8 digitos): ");
        int cod = lerInteiro(sc);
        if (cod < 10000000 || cod > 99999999) {
            System.out.println("Codigo invalido! Use exatamente 8 digitos.");
            return;
        }
        if (tabela.existeCodigo(cod)) {
            System.out.println("Esse codigo ja existe!");
            return;
        }

        System.out.print("Nome: ");
        String nome = sc.nextLine();
        if (nome.trim().isEmpty()) {
            System.out.println("Nome nao pode ser vazio!");
            return;
        }
        System.out.print("Planeta natal: ");
        String planeta = sc.nextLine();
        if (planeta.trim().isEmpty()) {
            System.out.println("Planeta nao pode ser vazio!");
            return;
        }
        System.out.print("Funcao: ");
        String funcao = sc.nextLine();
        if (funcao.trim().isEmpty()) {
            System.out.println("Funcao nao pode ser vazia!");
            return;
        }

        tabela.inserir(new Tripulante(cod, nome, planeta, funcao));
        System.out.println("Tripulante registrado com sucesso!");
    }

    // Remove um tripulante pelo codigo de 8 digitos
    public static void removerTripulante(Scanner sc, TabelaHash tabela) {
        System.out.print("Digite o codigo do tripulante para remover: ");
        int cod = lerInteiro(sc);
        if (cod < 10000000 || cod > 99999999) {
            System.out.println("Codigo invalido! Use exatamente 8 digitos.");
        } else if (tabela.removerPorCodigo(cod)) {
            System.out.println("Tripulante removido!");
        } else {
            System.out.println("Codigo nao encontrado.");
        }
    }

    // Carrega os tripulantes iniciais do arquivo tripulantes.txt
    public static void carregarArquivo(TabelaHash tabela) {
        try {
            Scanner leitor = new Scanner(new File("tripulantes.txt"));
            while (leitor.hasNextLine()) {
                String linha = leitor.nextLine().trim();
                if (!linha.isEmpty()) {
                    String[] dados = linha.split(";");
                    int codigo = Integer.parseInt(dados[0]);
                    String nome = dados[1];
                    String planeta = dados[2];
                    String funcao = dados[3];
                    tabela.inserir(new Tripulante(codigo, nome, planeta, funcao));
                }
            }
            leitor.close();
        } catch (Exception e) {
            System.out.println("Erro ao ler arquivo: " + e.getMessage());
        }
    }

    // Testa M = 13, M = 17 e M = 23 com as duas funcoes hash
    public static void testarTamanhosEFuncoes() {
        int[] tamanhos = { 13, 17, 23 };

        for (int i = 0; i < tamanhos.length; i++) {
            for (int f = 1; f <= 2; f++) {
                TabelaHash t = new TabelaHash(tamanhos[i], f);
                carregarArquivo(t);
                t.exibirEstatisticas();
            }
        }
    }

    // Roda os 3 algoritmos em copias do mesmo vetor, mostra os dados e devolve
    // ordenado
    public static Tripulante[] rodarAlgoritmos(Tripulante[] base) {
        Tripulante[] c1 = Ordenacao.copiarVetor(base);
        Ordenacao.bubbleSort(c1, false);
        System.out.println("Bubble Sort    | Tempo: " + Ordenacao.tempo + " ns | Comp: "
                + Ordenacao.comparacoes + " | Trocas: " + Ordenacao.trocas);

        Tripulante[] c2 = Ordenacao.copiarVetor(base);
        Ordenacao.insertionSort(c2);
        System.out.println("Insertion Sort | Tempo: " + Ordenacao.tempo + " ns | Comp: "
                + Ordenacao.comparacoes + " | Deslocamentos: " + Ordenacao.trocas);

        Tripulante[] c3 = Ordenacao.copiarVetor(base);
        Ordenacao.quickSort(c3);
        System.out.println("Quick Sort     | Tempo: " + Ordenacao.tempo + " ns | Comp: "
                + Ordenacao.comparacoes + " | Trocas: " + Ordenacao.trocas);

        return c3;
    }

    // Ordena a tripulacao atual e mostra a fila final de embarque
    public static void organizarEmbarque(TabelaHash tabela) {
        System.out.println();
        Tripulante[] ordenado = rodarAlgoritmos(tabela.exportarParaVetor());

        System.out.println("\n--- FILA DE EMBARQUE ORDENADA POR CODIGO ---");
        for (int i = 0; i < ordenado.length; i++) {
            System.out.println((i + 1) + ". " + ordenado[i]);
        }
    }

    // Mostra na tela cada comparacao e troca do Bubble Sort com ate 10 tripulantes
    public static void modoPassoAPasso(TabelaHash tabela) {
        Tripulante[] todos = tabela.exportarParaVetor();
        int qtd = 10;
        if (todos.length < 10) {
            qtd = todos.length;
        }

        Tripulante[] primeiros = new Tripulante[qtd];
        for (int i = 0; i < qtd; i++) {
            primeiros[i] = todos[i];
        }

        Ordenacao.bubbleSort(primeiros, true);

        System.out.println("\nTotal no passo a passo -> Comparacoes: "
                + Ordenacao.comparacoes + " | Trocas: " + Ordenacao.trocas);
        System.out.println("--- ASSENTOS FINAIS APOS AS MANOBRAS ---");
        for (int i = 0; i < primeiros.length; i++) {
            System.out.println((i + 1) + ". " + primeiros[i]);
        }
    }

    // Gera n tripulantes combinando nomes e planetas com codigos unicos de 8
    // digitos
    public static Tripulante[] gerarAleatorios(int n) {
        Tripulante[] v = new Tripulante[n];
        Random rand = new Random(42);
        int codigoBase = 10000000;

        for (int i = 0; i < n; i++) {
            codigoBase += 1 + rand.nextInt(5000);
            String nome = nomes[rand.nextInt(nomes.length)] + " " + i;
            String planeta = planetas[rand.nextInt(planetas.length)];
            v[i] = new Tripulante(codigoBase, nome, planeta, "Tripulante");
        }

        for (int i = 0; i < n; i++) {
            int pos = rand.nextInt(n);
            Tripulante temp = v[i];
            v[i] = v[pos];
            v[pos] = temp;
        }
        return v;
    }

    public static void inverter(Tripulante[] v) {
        for (int i = 0; i < v.length / 2; i++) {
            Tripulante temp = v[i];
            v[i] = v[v.length - 1 - i];
            v[v.length - 1 - i] = temp;
        }
    }

    // Mede tempo, comparacoes e trocas com 50, 1.000 e 10.000 tripulantes nas 3
    // situacoes
    public static void grandeCorrida() {
        int[] tamanhos = { 50, 1000, 10000 };

        for (int t = 0; t < tamanhos.length; t++) {
            int n = tamanhos[t];
            System.out.println("\n================ GRANDE CORRIDA: N = " + n + " ================");

            Tripulante[] aleatorio = gerarAleatorios(n);

            Tripulante[] ordenado = Ordenacao.copiarVetor(aleatorio);
            Ordenacao.quickSort(ordenado);

            Tripulante[] inverso = Ordenacao.copiarVetor(ordenado);
            inverter(inverso);

            System.out.println("\n--- Situacao: Aleatorio ---");
            rodarAlgoritmos(aleatorio);

            System.out.println("\n--- Situacao: Ja ordenado ---");
            rodarAlgoritmos(ordenado);

            System.out.println("\n--- Situacao: Ordem inversa ---");
            rodarAlgoritmos(inverso);
        }
    }
}
