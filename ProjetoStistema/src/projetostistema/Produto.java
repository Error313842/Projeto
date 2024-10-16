package projetostistema;

import java.util.Objects;

public class Produto {

    private final String nome;
    private final String descricao;
    private final double preco;

    public Produto(String nome, String descricao, double preco) {
        if (nome == null || nome.isEmpty()) {
            throw new IllegalArgumentException("Nome não pode ser vazio");
        }
        if (descricao == null || descricao.isEmpty()) {
            throw new IllegalArgumentException("Descrição não pode ser vazia");
        }
        if (preco < 0) {
            throw new IllegalArgumentException("Preço não pode ser negativo");
        }

        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
    }

    @Override
    public String toString() {
        return "Nome: " + nome + ", Descrição: " + descricao + ", Preço: " + preco;
    }

    // Getters
    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public double getPreco() {
        return preco;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Produto produto = (Produto) obj;
        return Double.compare(produto.preco, preco) == 0 &&
               nome.equals(produto.nome) &&
               descricao.equals(produto.descricao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, descricao, preco);
    }
}
