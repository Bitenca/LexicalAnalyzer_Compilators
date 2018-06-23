package sintatico;

import java.util.ArrayList;
import java.util.List;

import lexico.Token;

public class No {

    private String type;

    private Token token;

    private No pai;

    List< No > filhos = new ArrayList<>();

    No( String type ) {
        this.type = type;
    }

    No( Token token ) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public void setType( String type ) {
        this.type = type;
    }

    public Token getToken() {
        return token;
    }

    public void setToken( Token token ) {
        this.token = token;
    }

    public No getPai() {
        return pai;
    }

    public void setPai( No pai ) {
        this.pai = pai;
    }

    List< No > getFilhos() {
        return filhos;
    }

    public void setFilhos( List< No > filhos ) {
        this.filhos = filhos;
    }

    void addFilho( No filho ) {
        filhos.add( filho );
        filho.pai = this;
    }

    public No getFilho( int index ) {
        return filhos.get( index );
    }
    
    public String toString() {
        if(type != null) {
            return type;
        } else {
            return token.getImagem();
        }
    }
}
