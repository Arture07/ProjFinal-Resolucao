import java.text.Normalizer;

// Representa um tripulante da estacao espacial
public class Tripulante {
    public int codigo;
    public String nome;
    public String planeta;
    public String funcao;

    public Tripulante(int codigo, String nome, String planeta, String funcao) {
        this.codigo = codigo;
        this.nome = nome;
        this.planeta = formatarChave(planeta);
        this.funcao = funcao;
    }

    // Remove acentos e converte para maiusculas para padronizar a chave da tabela
    public static String formatarChave(String texto) {
        String semAcento = Normalizer.normalize(texto.trim(), Normalizer.Form.NFD);
        return semAcento.replaceAll("\\p{M}", "").toUpperCase();
    }

    @Override
    public String toString() {
        return codigo + " - " + nome + " (" + planeta + " | " + funcao + ")";
    }
}
