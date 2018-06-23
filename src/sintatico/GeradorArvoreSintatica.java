package sintatico;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.accessibility.AccessibleAttributeSequence;
import javax.lang.model.type.DeclaredType;
import javax.swing.tree.ExpandVetoException;

import lexico.Token;

public class GeradorArvoreSintatica {
	
	private List<Token> tokens = null;
	private List<String> erros = new ArrayList<>();
	private int ptoken;
	private Token token;
	public No raiz;

	public GeradorArvoreSintatica(ArrayList<Token> tokens) {
		this.tokens  = tokens;
	}

	public void analisar(){
		ptoken  = 0;
		nextToken();
		this.raiz = this.commands();
		if(!token.getClasse().equals("$")){
			addErro("Esperado caracter \"$\"", token);
		}
		mostraArvore();
		mostraErros();
	}
	
	private No commands(){
		No no  = new No("commands");
		if(token.getImagem().equals("(")){
			no.addFilho(command());
			no.addFilho(commands());
		}
		
		return no;
	}
	
	// <command> ::= '(' <commandInter> ')'
	private No command(){
		No no = new No("command");
		if(token.getImagem().equals("(")){
			no.addFilho(new No(token));
			nextToken();
			no.addFilho(commandInter());
            if(token.getImagem().equals(")")){
                no.addFilho(new No(token));
                nextToken();
            } else {
            	addErro("Esperado caracter ')' na linha: "+ token.getLinha() +" e na coluna: " + token.getColuna(), token);
            }
		} else {
        	addErro("Esperado caracter '(' na linha: "+ token.getLinha() +" e na coluna: " + token.getColuna(), token);
		}
		return no;
	}
	
	// <commandInter> ::=  <decl>|<>.... 
    private No commandInter(){
        No no = new No("commandInter");
            if(token.getImagem().equals("int") || token.getImagem().equals("real") || 
            		token.getImagem().equals("string") || token.getImagem().equals("bool")){
                no.addFilho(decl());
            } else {
                switch (token.getImagem()) {
                    case "=":
                        no.addFilho(atrib());
                        break;
                    case "...":
                        no.addFilho(loop());
                        break;
                    case "?":
                        no.addFilho(cond());
                        break;
                    case ":<<":
                        no.addFilho(input());
                        break;
                    case ":>>":
                        no.addFilho(output());
                        break;
                    default:
                        addErro("Esperado caracter de (atrib, loop, cond, input, output) na linha: "+ token.getLinha() +" e na coluna: " + token.getColuna(), token);
                        break;
                }
            }
        
        return no;
    }

	private No decl() {
		No no = new No("decl");

		no.addFilho(tipo());
		no.addFilho(ids());
		 
		return no;
	}

	private No tipo() {
		No no  = new No("tipo");
		if(token.getImagem().equals("int") || token.getImagem().equals("real")
				|| token.getImagem().equals("string") || token.getImagem().equals("bool")){
			no.addFilho(new No(token));
			nextToken();
		} else {
			addErro("Esperado caracter 'int', 'real', 'string' ou 'bool' na linha: "+ token.getLinha() +" e na coluna: " + token.getColuna(), token);
		}
		return no;
	}
	
	// <ids> ::= id <ids2>
	private No ids() {
		No no = new No("ids");
		if(token.getClasse().equals("Id")){
			no.addFilho(new No(token));
			nextToken();
			no.addFilho(ids2());
		} else {
			addErro("Esperado caracter de Id válido na linha: "+ token.getLinha() +" e na coluna: " + token.getColuna(), token);
		}
		return no;
	}


	private No ids2() {
		No no = new No("ids2");
		if(token.getClasse().equals("Id")){
			no.addFilho(new No(token));
			nextToken();
			no.addFilho(ids2());
		} 

		return no;
	}
	
	private No output() {
        No no = new No("output");
        if(token.getImagem().equals(":>>")){
            no.addFilho(new No(token));
            nextToken();
            no.addFilho(exp());
        } else {
        	addErro("Esperado caracter ':>>' na linha: "+ token.getLinha() +" e na coluna: " + token.getColuna(), token);
        }
    return no;
	}
	
	private No input() {
		No no = new No("input");
        if(token.getImagem().equals(":<<")){
            no.addFilho(new No(token));
            nextToken();
            if(token.getClasse().equals("Id")){
                no.addFilho(new No(token));
                nextToken();
            } else {
            	addErro("Esperado caracter de Id válido na linha: "+ token.getLinha() +" e na coluna: " + token.getColuna(), token);
            }
        } else {
        	addErro("Esperado caracter ':<<' na linha: "+ token.getLinha() +" e na coluna: " + token.getColuna(), token);
        }
        return no;
	}

	private No cond() {
        No no = new No("cond");
        if(token.getImagem().equals("?")){
            no.addFilho(new No(token));
            nextToken();
            no.addFilho(exp());
            if(token.getImagem().equals("(")){
                no.addFilho(new No(token));
                nextToken();
                no.addFilho(commands());
                if(token.getImagem().equals(")")){
                    no.addFilho(new No(token));
                    nextToken();
                    no.addFilho(elseL());
                } else {
                	addErro("Esperado caracter ')' na linha: "+ token.getLinha() +" e na coluna: " + token.getColuna(), token);
                }
            } else {
            	addErro("Esperado caracter ')' na linha: "+ token.getLinha() +" e na coluna: " + token.getColuna(), token);
            }
        } else {
        	addErro("Esperado caracter '?' na linha: "+ token.getLinha() +" e na coluna: " + token.getColuna(), token);
        }
        return no;
	}

	private No elseL() {
        No no = new No("elsel2s");
        if(token.getImagem().equals("(")){
            no.addFilho(new No(token));
            nextToken();
            no.addFilho(commands());
            if(token.getImagem().equals(")")){
                no.addFilho(new No(token));
                nextToken();
            } else {
            	addErro("Esperado caracter ')' na linha: "+ token.getLinha() +" e na coluna: " + token.getColuna(), token);
            }
        }
        return no;
	}

	private No loop() {
		No no = new No("loop");
		if(token.getImagem().equals("...")){
			no.addFilho(new No(token));
			nextToken();
			no.addFilho(exp());
			no.addFilho(commands());
		} else {
			addErro("Esperado caracter '...' na linha: "+ token.getLinha() +" e na coluna: " + token.getColuna(), token);
		}
		return no;
	}

	private No exp() {
        No no = new No("exp");
        if(token.getClasse().equals("String") || token.getImagem().equals("real")
        		|| token.getClasse().equals("Id") || token.getImagem().equals("bool")
        		|| token.getImagem().equals("int") || token.getClasse().equals("Integer")
        		|| token.getClasse().equals("Decimal")){
            no.addFilho(operan());
        } else {
            if(token.getImagem().equals("(")){
                no.addFilho(new No(token));
                nextToken();
                no.addFilho(op());
                no.addFilho(exp());
                no.addFilho(exp());
                
                if(token.getImagem().equals(")")){
                    nextToken();
                } else {
                	addErro("Esperado caracter ')' na linha: "+ token.getLinha() +" e na coluna: " + token.getColuna(), token);
                }
            } else {
                addErro("Esperado um operando (CLI,CLS,CLR,ID,CLL) ou caracter '(' na linha: "+ token.getLinha() +" e na coluna: " + token.getColuna(), token);
            }
        }
        return no;
	}

	private No op() {
		No no = new No("op");
		if(token.getClasse().equals("Operador")){
			no.addFilho(new No(token));
			nextToken();
		} else {
			addErro("Esperado um operador na linha: "+ token.getLinha() +" e na coluna: " + token.getColuna(), token);
		}
		return no;
	}

	private No atrib() {
		No no = new No("atrib");
        if(Objects.equals(token.getImagem(),"=")){
            no.addFilho(new No(token));
            nextToken();
            if(Objects.equals(token.getClasse(),"Id")){
                no.addFilho(new No(token));
                nextToken();
                if(token.getImagem().equals("(")){
                    no.addFilho(exp());
                } else {
                    no.addFilho(operan());
                }
            } else {
            	addErro("Esperado caracter de Id válido na linha: "+ token.getLinha() +" e na coluna: " + token.getColuna(), token);
            }
        } else {
        	addErro("Esperado caracter '=' na linha: "+ token.getLinha() +" e na coluna: " + token.getColuna(), token);
        }
        return no;
	}

	private No operan() {
		// TODO Auto-generated method stub
		return null;
	}

	private void nextToken(){
		token = tokens.get(ptoken++);
	}
	
	public boolean temErros() {
		return !erros.isEmpty();
	}
	
	public void mostraErros() {
		for(String erro: erros) {
			System.out.println(erro);
		}
	}
	
	public void mostraArvore() {
		mostraNo(raiz, "   ");
	}
	
	private void mostraNo(No no, String espaco){
		System.out.println(espaco + no);
		for(No filho: no.filhos) {
			mostraNo(filho, espaco + "   ");
		}
	}
	
	private void addErro(String erro, Token token){
		erros.add(erro + ". Token atual: " + token);
	}

}
