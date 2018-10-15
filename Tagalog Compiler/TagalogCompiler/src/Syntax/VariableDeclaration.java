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
public class VariableDeclaration {
    public TokenType dataType;
    public Identifier identifierValue;
    public int lineNumber;
    public int blockLevel;
    public VariableDeclaration(TokenType type, Identifier value, int lineNumber, int blockLevel)
    {
        this.dataType = type;
        this.identifierValue = value;
        this.lineNumber = lineNumber;
        this.blockLevel = blockLevel;
    }
    
    public TokenType getType()
    {
        return this.dataType;
    }
}
