// No da lista encadeada usada para tratar colisoes na tabela hash
public class No {
    public Tripulante tripulante;
    public No proximo;

    public No(Tripulante tripulante) {
        this.tripulante = tripulante;
        this.proximo = null;
    }
}
