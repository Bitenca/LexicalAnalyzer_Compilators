package lexico;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;

import sintatico.GeradorArvoreSintatica;

public class GeradorTabelas {
	
	static ArrayList<Token> TabelaTokens = new ArrayList<Token>();
	static ArrayList<TokenId> TabelaIds = new ArrayList<TokenId>(); 
	static String imgToken[];
	private static Scanner ler;

	public static void main(String[] args) {
		int row = 0;
		int column = 1;
		
		try {
			FileReader arq = new FileReader("C:\\Users\\felip\\workspace\\Analisadores\\src\\fatorial.l2s");
			BufferedReader lerArq = new BufferedReader(arq);
			
			String linha = lerArq.readLine();//split em algum lugar p pegar os tokens em vez de char\
			row++;
			
			char linhaArray[] = linha.toCharArray();
			
			while(linhaArray[0] == '#') {//verifica os casos de ignorar linhas
				linha = lerArq.readLine();
				row++;
				while(linha.isEmpty() ){
					linha = lerArq.readLine();
					row++;
				}
				
				linhaArray = linha.toCharArray();		
				
			}
			
			while ( linha != null) {
				
				if(!linha.isEmpty()){
					if(linha.contains("\"")){// strings check
						String clString = linha.substring(linha.indexOf("\""), linha.lastIndexOf("\"") + 1);
						String linhaRest = linha.replace("\"" + clString + "\"" + 1 , "");
						
						classificaToken(clString, row, linha.indexOf("\""));
						
						if(linhaRest.contains("	")){
							for(int i = 0; i < linhaRest.length(); i++){
								column = linhaRest.indexOf("(") ;
							}
							linhaRest = linhaRest.substring(linhaRest.indexOf("("), linhaRest.lastIndexOf(")"));
						}
						
						imgToken = linhaRest.split(" ");
						sendTokensLine(imgToken, row, column);
						linha = lerArq.readLine();
						row++;
						
					}else{
						
						imgToken = linha.split(" "); //cria os lexemas(tokens)
						sendTokensLine(imgToken, row, column);
						linha = lerArq.readLine();
						row++;
					}
				}else{
					linha = lerArq.readLine();
					row++;
				}
				
			}
			
			criaTabelaID(TabelaTokens);
			
			comunicaTabelas();
			
			printaTabela();
			
			arq.close();
			
			System.out.printf("Deseja gerar a arvore sintática? (S - N) ");
			ler = new Scanner(System.in);
			String n = ler.next();
			
			if(n.equals("S")){
				GeradorArvoreSintatica geradorArvoreSintatica = new GeradorArvoreSintatica(TabelaTokens);
				geradorArvoreSintatica.analisar();
			}else{
				return;
			}
			
			
		} catch (Exception e) {
			
	       System.out.printf("Erro na abertura do arquivo: %s.\n", e.toString());
		}

		
		System.out.println();
	}


	public ArrayList<Token> RetornaTabelaToken(String tabela) {
		return TabelaTokens;
		
	}
	
	public ArrayList<TokenId> RetornaTabelaId(String tabela) {
		return TabelaIds;
		
	}

	private static void comunicaTabelas() {
		for (int i = 0; i < TabelaTokens.size(); i++) {
			int index = 1;
			if(TabelaTokens.get(i).getClasse().equals("Id")){

				for (int j = 0; j < TabelaIds.size(); j++) {
					if (TabelaIds.get(j).getImagem().contains(TabelaTokens.get(i).getImagem())){
						index = j + 1;
						TabelaTokens.get(i).setIndice(index);
					}
					
				}
			}
			
		}
		
	}

	private static void criaTabelaID(ArrayList<Token> tabelaTokens) {
		String[] imgTk = new String[tabelaTokens.size()];
		for (int i = 0; i < tabelaTokens.size(); i++) {
			if(tabelaTokens.get(i).getClasse() == "Id"){
				
				imgTk[i] = tabelaTokens.get(i).getImagem();	
			}
		}
		imgTk = new HashSet<String>(Arrays.asList(imgTk)).toArray(new String[0]);
		for (String string : imgTk) {
			if(string != null && string != "null"){
				TokenId tokenId = new TokenId();
				tokenId.setImagem(string);
				TabelaIds.add(tokenId);
			}
		}

	}

	private static void sendTokensLine(String[] imgToken, int row, int column) {
		for(int i = 0; i < imgToken.length; i++){

			if(i == 0){
				column += imgToken[i].length();
			}
			else{
				column += imgToken[i-1].length();
			}	

			if(imgToken[i] != " " && imgToken[i] != "" && imgToken[i] != "	" ){
				classificaToken(imgToken[i], row, column);
			}
				

		}
		
	}

	private static void printaTabela() {
		System.out.println("######################## TABELA DE TOKENS #######################");
		for (Token token : TabelaTokens) {
			System.out.println(token);
		}
		System.out.println("\n");
		System.out.println("######################## TABELA DE iDS #######################");
		for (TokenId toke : TabelaIds) {
			System.out.println(toke);
		}
		
	}

	private static void classificaToken(String img, int linha, int coluna) {
		String classe = setClasse(img);
		
		if(classe != "branco"){
			Token token = new Token();
			token.setClasse(classe);
			token.setImagem(img);
			token.setLinha(linha);
			token.setColuna(coluna);	
			
			
			TabelaTokens.add(token);
		}
		
	}

	private static String setClasse(String img) {
		switch (img) {
		case ")":
			return ("Delimitador");
		case "(":
			return ("Delimitador");
		case "+":
			return ("Operador");
		case "-":
			return ("Operador");
		case "*":
			return ("Operador");
		case "/":
			return ("Operador");
		case ">":
			return ("Operador");
		case "<":
			return ("Operador");
		case "<=":
			return ("Operador");
		case ">=":
			return ("Operador");
		case "=":
			return ("Operador");
		case "&":
			return ("Operador");
		case "int":
			return ("Palavra Reservada");
		case "real":
			return ("Palavra Reservada");
		case "string":
			return ("Palavra Reservada");
		case "bool":
			return ("Palavra Reservada");
		case "...":
			return ("Palavra Reservada");
		case "?":
			return ("Palavra Reservada");
		case ":>>":
			return ("Palavra Reservada");
		case ":<<":
			return ("Palavra Reservada");
		case "":
			return ("branco");
		default:
			if(img.matches("^[a-zA-Z][a-zA-Z0-9{., _}]*$"))
				return ("Id");
			else if (img.matches("^\\d+\\.\\d+$")) {
				return ("Decimal");
			}else if (img.matches("^\\d+$")) {
				return ("Integer");
			}
			return "String";
		}
	}

}
