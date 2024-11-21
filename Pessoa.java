public class Pessoa {
    private String nome;
    private String dataNascimento;
    private String dataEntrada;
    private int numAcomodacao;

    public Pessoa(String nome, String dataNascimento, String dataEntrada, int numAcomodacao ) {
        this.numAcomodacao = numAcomodacao;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.dataEntrada = dataEntrada;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getDataNascimento() {
        return dataNascimento;
    }
    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }
    public String getDataEntrada() {
        return dataEntrada;
    }
    public void setDataEntrada(String dataEntrada) {
        this.dataEntrada = dataEntrada;
    }
    public int getNumAcomodacao() {
        return numAcomodacao;
    }
    public void setNumAcomodacao(int numAcomodacao) {
        this.numAcomodacao = numAcomodacao;
    }

    public String toString() {
        return this.nome+"\t"+dataNascimento+"\t"+dataEntrada+"\t"+numAcomodacao+"\n";
    }
}