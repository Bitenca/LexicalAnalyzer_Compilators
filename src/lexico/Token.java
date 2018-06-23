package lexico;

public class Token {
	
	private String Imagem;
	private String Classe;
	private int Linha;
	private int Coluna;
	private int Indice;
	
//	public Token(String imagem, String classe) {
//		this.Imagem = imagem;
//		this.Classe = classe;
//	}


	public String getImagem() {
		return Imagem;
	}
	public void setImagem(String imagem) {
		Imagem = imagem;
	}
	public String getClasse() {
		return Classe;
	}
	public void setClasse(String classe) {
		Classe = classe;
	}
	public int getLinha() {
		return Linha;
	}
	public void setLinha(int linha) {
		Linha = linha;
	}
	public int getColuna() {
		return Coluna;
	}
	public void setColuna(int coluna) {
		Coluna = coluna;
	}
	public int getIndice() {
		return Indice;
	}
	public void setIndice(int indice) {
		Indice = indice;
	}
	@Override
	public String toString() {
		return "Token [Imagem= " + Imagem + ", Classe= " + Classe + ", Linha= " + Linha + ", Coluna= " + Coluna
				+ ", Indice= " + Indice + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((Classe == null) ? 0 : Classe.hashCode());
		result = prime * result + ((Imagem == null) ? 0 : Imagem.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Token other = (Token) obj;
		if (Classe == null) {
			if (other.Classe != null)
				return false;
		} else if (!Classe.equals(other.Classe))
			return false;
		if (Imagem == null) {
			if (other.Imagem != null)
				return false;
		} else if (!Imagem.equals(other.Imagem))
			return false;
		return true;
	}
	
	
	
}
