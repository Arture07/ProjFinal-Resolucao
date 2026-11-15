// Tabela hash propria com tratamento de colisoes por encadeamento
public class TabelaHash {
    public No[] baldes;
    public int M;
    public int tipoHash;
    public int totalTripulantes;
    public int colisoes;

    public TabelaHash(int M, int tipoHash) {
        this.M = M;
        this.tipoHash = tipoHash;
        this.baldes = new No[M];
        this.totalTripulantes = 0;
        this.colisoes = 0;
    }

    // Funcao Hash 1: soma dos caracteres (material de apoio)
    public int funcaoHash1(String chave) {
        int soma = 0;
        for (int i = 0; i < chave.length(); i++) {
            soma += chave.charAt(i);
        }
        return soma % M;
    }

    // Funcao Hash 2: caractere multiplicado pela posicao (criada pelo grupo)
    public int funcaoHash2(String chave) {
        int soma = 0;
        for (int i = 0; i < chave.length(); i++) {
            soma += chave.charAt(i) * (i + 1);
        }
        return soma % M;
    }

    public int calcularHash(String chave) {
        if (tipoHash == 1) {
            return funcaoHash1(chave);
        } else {
            return funcaoHash2(chave);
        }
    }

    // Insere o tripulante no balde correspondente (no fim da lista se houver colisao)
    public void inserir(Tripulante novo) {
        int indice = calcularHash(novo.planeta);
        No novoNo = new No(novo);

        if (baldes[indice] == null) {
            baldes[indice] = novoNo;
        } else {
            colisoes++;
            No atual = baldes[indice];
            while (atual.proximo != null) {
                atual = atual.proximo;
            }
            atual.proximo = novoNo;
        }
        totalTripulantes++;
    }

    // Vai direto ao balde calculado pelo hash e lista quem for daquele planeta
    public void buscarPorPlaneta(String planeta) {
        String chave = Tripulante.formatarChave(planeta);
        int indice = calcularHash(chave);
        No atual = baldes[indice];
        boolean achou = false;

        System.out.println("\nBuscando no Modulo [" + indice + "] pelo planeta " + chave + ":");
        while (atual != null) {
            if (atual.tripulante.planeta.equals(chave)) {
                System.out.println("  -> " + atual.tripulante);
                achou = true;
            }
            atual = atual.proximo;
        }

        if (!achou) {
            System.out.println("  Nenhum tripulante encontrado desse planeta.");
        }
    }

    // Procura o codigo nos baldes e ajusta os ponteiros da lista para remover o no
    public boolean removerPorCodigo(int codigo) {
        for (int i = 0; i < M; i++) {
            No atual = baldes[i];
            No anterior = null;

            while (atual != null) {
                if (atual.tripulante.codigo == codigo) {
                    if (anterior == null) {
                        baldes[i] = atual.proximo;
                    } else {
                        anterior.proximo = atual.proximo;
                    }
                    totalTripulantes--;
                    return true;
                }
                anterior = atual;
                atual = atual.proximo;
            }
        }
        return false;
    }

    public void exibirTabela() {
        System.out.println("\n--- MODULOS DA ESTACAO (M = " + M + " | Funcao Hash " + tipoHash + ") ---");
        for (int i = 0; i < M; i++) {
            System.out.print("[" + i + "]: ");
            if (baldes[i] == null) {
                System.out.println("vazio");
            } else {
                No atual = baldes[i];
                while (atual != null) {
                    System.out.print(atual.tripulante.nome + " (" + atual.tripulante.planeta
                            + " - " + atual.tripulante.codigo + ")");
                    if (atual.proximo != null) {
                        System.out.print(" -> ");
                    }
                    atual = atual.proximo;
                }
                System.out.println();
            }
        }
    }

    public void exibirEstatisticas() {
        int vazios = 0;
        int maiorLista = 0;

        for (int i = 0; i < M; i++) {
            if (baldes[i] == null) {
                vazios++;
            } else {
                int tamanho = 0;
                No atual = baldes[i];
                while (atual != null) {
                    tamanho++;
                    atual = atual.proximo;
                }
                if (tamanho > maiorLista) {
                    maiorLista = tamanho;
                }
            }
        }

        double fatorCarga = (double) totalTripulantes / M;

        System.out.println("\n--- ESTATISTICAS (M = " + M + " | Funcao Hash " + tipoHash + ") ---");
        System.out.println("Total de tripulantes : " + totalTripulantes);
        System.out.printf("Fator de carga       : %.2f%n", fatorCarga);
        System.out.println("Baldes vazios        : " + vazios);
        System.out.println("Tamanho maior lista  : " + maiorLista);
        System.out.println("Numero de colisoes   : " + colisoes);
    }

    // Copia todos os tripulantes da tabela para um vetor antes da ordenacao
    public Tripulante[] exportarParaVetor() {
        Tripulante[] vetor = new Tripulante[totalTripulantes];
        int pos = 0;
        for (int i = 0; i < M; i++) {
            No atual = baldes[i];
            while (atual != null) {
                vetor[pos] = atual.tripulante;
                pos++;
                atual = atual.proximo;
            }
        }
        return vetor;
    }
}
