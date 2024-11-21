public class Acomodacao {
    //to_do:criar restricao para quartos de mesmo numero
    private boolean estaDisponivel;
    private double preco;
    private String tipo;
    private int numAcomodacao;
    private int pessoasNaAcomodacao;

    public Acomodacao(boolean estaDisponivel, double preco, String tipo, int numAcomodacao) {
        this.numAcomodacao = numAcomodacao;
        this.estaDisponivel = estaDisponivel;
        this.preco = preco;
        this.tipo = tipo;
        this.pessoasNaAcomodacao = 0;
    }

    public boolean getEstaDisponivel() {
        return estaDisponivel;
    }
    public void setEstaDisponivel(boolean estaDisponivel) {
        this.estaDisponivel = estaDisponivel;
    }
    public double getPreco() {
        return preco;
    }
    public void setPreco(double preco) {
        this.preco = preco;
    }
    public String getTipo() {
        return tipo;
	}
	public void setTipo(String tipo) {
        this.tipo = tipo;
	}
    public int getNumAcomodacao() {
        return numAcomodacao;
	}
	public void setNumAcomodacao(int numAcomodacao) {
        this.numAcomodacao = numAcomodacao;
	}
    public int getPessoasNaAcomodacao() {
        return pessoasNaAcomodacao;
    }
    public void setPessoasNaAcomodacao(int pessoasNaAcomodacao) {
        this.pessoasNaAcomodacao = pessoasNaAcomodacao;
    }

    public void adicionarPessoa() {
        this.pessoasNaAcomodacao += 1;
    }

    public String toString() {
        return this.numAcomodacao+"\t"+estaDisponivel+"\t"+preco+"\t"+tipo+"\t"+pessoasNaAcomodacao+"\n";
    }
}
