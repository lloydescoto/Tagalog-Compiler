/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Syntax;
import Lexical.TokenType;
import Lexical.TokenValue;
/**
 *
 * @author WIN
 */
public class Identifier {
    public TokenType identifierType;
    public TokenValue identifierValue;
    public int lineNumber;
    public TokenType unaryOperator;
    public int blockLevel;
    
    public Identifier(TokenType identifierType, TokenValue identifierValue, int lineNumber, int blockLevel)
    {
        this.identifierType = identifierType;
        this.identifierValue = identifierValue;
        this.lineNumber = lineNumber;
        this.blockLevel = blockLevel;
    }
    
    public TokenType getType()
    {
        return this.identifierType;
    }
    
    public int getLevel()
    {
        return this.blockLevel;
    }
}
