/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Semantic;

import Lexical.Token;
import Lexical.TokenType;

/**
 *
 * @author WIN
 */
public class CodeGeneration {
    
    public CodeGeneration()
    {}
    
    public String getEquivalentCode(Token token)
    {
        if(token.getType() == TokenType.AND)
        {
            return "&&";
        }
        if(token.getType() == TokenType.ASSIGN)
        {
            return "=";
        }
        if(token.getType() == TokenType.BULYAN)
        {
            return "boolean";
        }
        if(token.getType() == TokenType.BULYAN_LITERAL)
        {
            return token.value.getStringVal();
        }
        if(token.getType() == TokenType.BUUMBILANG)
        {
            return "int";
        }
        if(token.getType() == TokenType.BUUMBILANG_LITERAL)
        {
            return token.value.getStringVal();
        }
        if(token.getType() == TokenType.DECREMENT)
        {
            return "--";
        }
        if(token.getType() == TokenType.DESIMAL)
        {
            return "float";
        }
        if(token.getType() == TokenType.DESIMAL_LITERAL)
        {
            return token.value.getStringVal();
        }
        if(token.getType() == TokenType.DIVIDE)
        {
            return "/";
        }
        if(token.getType() == TokenType.DIVIDE_EQUAL)
        {
            return "/=";
        }
        if(token.getType() == TokenType.EOF)
        {
            return "";
        }
        if(token.getType() == TokenType.EQ)
        {
            return "==";
        }
        if(token.getType() == TokenType.GAWIN)
        {
            return "do";
        }
        if(token.getType() == TokenType.GT)
        {
            return ">";
        }
        if(token.getType() == TokenType.GTEQ)
        {
            return ">=";
        }
        if(token.getType() == TokenType.HABANG)
        {
            return "while";
        }
        if(token.getType() == TokenType.INCREMENT)
        {
            return "++";
        }
        if(token.getType() == TokenType.IPAKITA)
        {
            
        }
        if(token.getType() == TokenType.IPASOK)
        {
            
        }
        if(token.getType() == TokenType.KUNG)
        {
            return "else";
        }
        if(token.getType() == TokenType.LBRACKET)
        {
            return "{";
        }
        if(token.getType() == TokenType.LPAREN)
        {
            return "(";
        }
        if(token.getType() == TokenType.LT)
        {
            return "<";
        }
        if(token.getType() == TokenType.LTEQ)
        {
            return "<=";
        }
        if(token.getType() == TokenType.MINUS)
        {
            return "-";
        }
        if(token.getType() == TokenType.MINUS_EQUAL)
        {
            return "-=";
        }
        if(token.getType() == TokenType.MULTIPLY)
        {
            return "*";
        }
        if(token.getType() == TokenType.MULTIPLY_EQUAL)
        {
            return "*=";
        }
        if(token.getType() == TokenType.NEQ)
        {
            return "!=";
        }
        if(token.getType() == TokenType.NOT)
        {
            return "!";
        }
        if(token.getType() == TokenType.OR)
        {
            return "||";
        }
        if(token.getType() == TokenType.PAANO)
        {
            return "if";
        }
        if(token.getType() == TokenType.PAANOKUNG)
        {
            return "else if";
        }
        if(token.getType() == TokenType.PANGUNAHING)
        {
            return "public static void main(String args[]) {";
        }
        if(token.getType() == TokenType.PLUS)
        {
            return "+";
        }
        if(token.getType() == TokenType.PLUS_EQUAL)
        {
            return "+=";
        }
        if(token.getType() == TokenType.RBRACKET)
        {
            return "}";
        }
        if(token.getType() == TokenType.RPAREN)
        {
            return ")";
        }
        if(token.getType() == TokenType.SALITA)
        {
            return "String";
        }
        if(token.getType() == TokenType.SALITA_LITERAL)
        {
            return token.value.getStringVal();
        }
        if(token.getType() == TokenType.SINGLE_COMMENT)
        {
            return token.value.getStringVal();
        }
        if(token.getType() == TokenType.MULTI_COMMENT)
        {
            return token.value.getStringVal();
        }
        if(token.getType() == TokenType.TERMINATOR)
        {
            return ";";
        }
        if(token.getType() == TokenType.TITIK)
        {
            return "char";
        }
        if(token.getType() == TokenType.TITIK_LITERAL)
        {
            return token.value.getStringVal();
        }
        if(token.getType() == TokenType.ULIT)
        {
            return "for";
        }
        if(token.getType() == TokenType.UNKNOWN)
        {
            return "";
        }
        if(token.getType() == TokenType.VARIABLE)
        {
            return token.value.getStringVal().replace("?","");
        }
        
        
        return "";
    }
}
