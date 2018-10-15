/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Lexical;

/**
 *
 * @author WIN
 */
public class Token {
    public TokenType type;
    public TokenValue value;
    public int lineNumber;
    public int columnNumber;
    
    public Token(TokenType type, TokenValue value, int lineNumber, int columnNumber){
        this.type = type;
        this.value = value;
        this.lineNumber = lineNumber;
        this.columnNumber = columnNumber;
    }
    
    public TokenType getType(){
        return type;
    }
    
    public TokenValue getValue(){
        return value;
    }
    
    public int getLineNumber(){
        return lineNumber;
    }
    
    public int getColumnNumber(){
        return columnNumber;
    }
}
