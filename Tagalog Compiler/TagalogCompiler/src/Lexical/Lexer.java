/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Lexical;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
/**
 *
 * @author WIN
 */
public class Lexer {    
    private final BufferedReader reader;
    private int nextChar;
    private int lineNumber = 1;
    private int columnNumber = 1;
    ArrayList<Token> token = new ArrayList<Token>();
    
    private final static Map<String, TokenType> reserveWords;
    static {
        reserveWords = new HashMap<String, TokenType>();
        reserveWords.put("buumbilang", TokenType.BUUMBILANG);
        reserveWords.put("desimal", TokenType.DESIMAL);
        reserveWords.put("titik", TokenType.TITIK);
        reserveWords.put("salita", TokenType.SALITA);
        reserveWords.put("ipasok", TokenType.IPASOK);
        reserveWords.put("ipakita", TokenType.IPAKITA);
        reserveWords.put("paano", TokenType.PAANO);
        reserveWords.put("paanokung", TokenType.PAANOKUNG);
        reserveWords.put("kung", TokenType.KUNG);
        reserveWords.put("ulit", TokenType.ULIT);
        reserveWords.put("gawin", TokenType.GAWIN);
        reserveWords.put("habang", TokenType.HABANG);
        reserveWords.put("bulyan", TokenType.BULYAN);
        reserveWords.put("true", TokenType.BULYAN_LITERAL);
        reserveWords.put("false", TokenType.BULYAN_LITERAL);
        reserveWords.put("pangunahing", TokenType.PANGUNAHING);
    }
    public Lexer(FileReader file) {
        this.reader = new BufferedReader(file);     
        nextChar = getChar();
    }
    
    public ArrayList<Token> getAllTokens()
    {
        return this.token;
    }
           
    private int getChar() {
        try {
                return reader.read();
        } catch (IOException e) {
                System.err.print(e.getMessage());
                System.err.println("IOException occured in Lexer::getChar()");
                return -1;
        }
    }
    
    public Token getToken() {
        while(Character.isWhitespace(nextChar)) {
            nextChar = getChar();
            columnNumber++;
            
            if(nextChar == '\n')
            {
                columnNumber = 0;
                lineNumber++;
            }
        }
        if(nextChar == '/')
        {
            columnNumber++;
            String comment = Character.toString((char)nextChar);
            nextChar = getChar();
            if(nextChar == '/')
            {
                columnNumber++;
                comment += (char)nextChar;
                nextChar = getChar();
                while(nextChar != '\n')
                {
                    columnNumber++;
                    comment += (char)nextChar;
                    nextChar = getChar();
                }
                lineNumber++;
                columnNumber = 0;
                comment += (char)nextChar;
                nextChar = getChar();
                token.add(new Token(TokenType.SINGLE_COMMENT,new TokenValue(comment),lineNumber,columnNumber));
                return new Token(TokenType.SINGLE_COMMENT,new TokenValue(comment),lineNumber,columnNumber);
            }
         
            if(nextChar == '*')
            {
                columnNumber++;
                boolean multiStop = true;
                comment += (char)nextChar;
                nextChar = getChar();
                while(multiStop)
                {
                    if(nextChar == -1)
                    {
                        token.add(new Token(TokenType.EOF,new TokenValue(),lineNumber,columnNumber));
                        return new Token(TokenType.EOF,new TokenValue(),lineNumber,columnNumber);
                    }
                    columnNumber++;
                    if(nextChar == '\n')
                    {
                        lineNumber++;
                        columnNumber = 0;
                    }
                    comment += (char)nextChar;
                    nextChar = getChar();
                    if(nextChar == '*')
                    {
                        columnNumber++;
                        comment += (char)nextChar;
                        nextChar = getChar();
                        if(nextChar == '/')
                        {
                            columnNumber++;
                            comment += (char)nextChar;
                            multiStop = false;
                        }
                    }
                }
                comment += (char)nextChar;
                nextChar = getChar();
                token.add(new Token(TokenType.MULTI_COMMENT,new TokenValue(comment),lineNumber,columnNumber));
                return new Token(TokenType.MULTI_COMMENT,new TokenValue(comment),lineNumber,columnNumber);
            }
            if(nextChar == '=')
                {
                    columnNumber++;
                    comment += (char)nextChar;
                    nextChar = getChar();
                    token.add(new Token(TokenType.DIVIDE_EQUAL,new TokenValue(comment),lineNumber,columnNumber));
                    return new Token(TokenType.DIVIDE_EQUAL,new TokenValue(comment),lineNumber,columnNumber);
                }
            token.add(new Token(TokenType.DIVIDE,new TokenValue(comment),lineNumber,columnNumber));
            return new Token(TokenType.DIVIDE,new TokenValue(comment),lineNumber,columnNumber);
        }
        if(nextChar == '\'')
        { 
            columnNumber++;
            String stringLiteral = Character.toString((char)nextChar);
            nextChar = getChar();
            if(Character.isAlphabetic(nextChar))
            {
                columnNumber++;
                stringLiteral += (char)nextChar;
                nextChar = getChar();
                if(nextChar == '\'')
                {
                    columnNumber++;
                    stringLiteral += (char)nextChar;
                    nextChar = getChar();
                    token.add(new Token(TokenType.TITIK_LITERAL,new TokenValue(stringLiteral),lineNumber,columnNumber));
                    return new Token(TokenType.TITIK_LITERAL,new TokenValue(stringLiteral),lineNumber,columnNumber);
                }
            }
            token.add(new Token(TokenType.UNKNOWN,new TokenValue(),lineNumber,columnNumber));
            return new Token(TokenType.UNKNOWN,new TokenValue(),lineNumber,columnNumber);
        }
        if(nextChar == '"')
        {
            columnNumber++;
            String stringLiteral = Character.toString((char)nextChar);
            nextChar = getChar();
            while(nextChar != '"')
            {
                if(nextChar == -1)
                {
                    token.add(new Token(TokenType.EOF,new TokenValue(),lineNumber,columnNumber));
                    return new Token(TokenType.EOF,new TokenValue(),lineNumber,columnNumber);
                }
                columnNumber++;
                stringLiteral += (char)nextChar;
                nextChar = getChar();
            }
             if(nextChar == '"')
            {
                columnNumber++;
                stringLiteral += (char)nextChar;
                nextChar = getChar();
                token.add(new Token(TokenType.SALITA_LITERAL,new TokenValue(stringLiteral),lineNumber,columnNumber));
                return new Token(TokenType.SALITA_LITERAL,new TokenValue(stringLiteral),lineNumber,columnNumber);
            }
            token.add(new Token(TokenType.UNKNOWN,new TokenValue(),lineNumber,columnNumber));
            return new Token(TokenType.UNKNOWN,new TokenValue(),lineNumber,columnNumber);
        }
        if(Character.isDigit(nextChar))
        {
            columnNumber++;
            String numString = Character.toString((char)nextChar);
            nextChar = getChar();
            while(Character.isLetterOrDigit(nextChar))
            {
                columnNumber++;
                numString += (char)nextChar;
                nextChar = getChar();
            }
            if(nextChar == '.')
            {
                columnNumber++;
                numString += (char)nextChar;
                nextChar = getChar();
                while(Character.isLetterOrDigit(nextChar))
                {
                    columnNumber++;
                    numString += (char)nextChar;
                    nextChar = getChar();
                }
                if(hasLetter(numString))
                {
                    token.add(new Token(TokenType.UNKNOWN,new TokenValue(),lineNumber,columnNumber));
                    return new Token(TokenType.UNKNOWN,new TokenValue(),lineNumber,columnNumber);
                }
                token.add(new Token(TokenType.DESIMAL_LITERAL,new TokenValue(numString),lineNumber,columnNumber));
                return new Token(TokenType.DESIMAL_LITERAL,new TokenValue(numString),lineNumber,columnNumber);
            }
            if(hasLetter(numString))
            {
                token.add(new Token(TokenType.UNKNOWN,new TokenValue(),lineNumber,columnNumber));
                return new Token(TokenType.UNKNOWN,new TokenValue(),lineNumber,columnNumber);
            }
            token.add(new Token(TokenType.BUUMBILANG_LITERAL,new TokenValue(numString),lineNumber,columnNumber));
            return new Token(TokenType.BUUMBILANG_LITERAL,new TokenValue(numString),lineNumber,columnNumber);            
        }
        if(nextChar == '?')
        {
            columnNumber++;
            String variable = Character.toString((char)nextChar);
            nextChar = getChar();
            while(Character.isLetterOrDigit((char)nextChar))
            {
                columnNumber++;
                variable += (char)nextChar;
                nextChar = getChar();
            }
            token.add(new Token(TokenType.VARIABLE,new TokenValue(variable),lineNumber,columnNumber));
            return new Token(TokenType.VARIABLE,new TokenValue(variable),lineNumber,columnNumber);
        }
        if(Character.isLetter(nextChar))
        {
            columnNumber++;
            String word = Character.toString((char)nextChar);
            nextChar = getChar();
            while(Character.isLetterOrDigit(nextChar)){
                columnNumber++;
                word += (char)nextChar;
                nextChar = getChar();
            }
            TokenType type = reserveWords.get(word);
            if(type != null)
            {
                token.add(new Token(type,new TokenValue(word),lineNumber,columnNumber));
                return new Token(type,new TokenValue(word),lineNumber,columnNumber);
            }
            token.add(new Token(TokenType.UNKNOWN,new TokenValue(),lineNumber,columnNumber));
            return new Token(TokenType.UNKNOWN,new TokenValue(),lineNumber,columnNumber);
        }
        
        String operators = "";
        switch(nextChar)
        {
            
            case '<':
                columnNumber++;
                operators += (char)nextChar;
                nextChar = getChar();
                if(nextChar == '=')
                {
                    columnNumber++;
                    operators += (char)nextChar;
                    nextChar = getChar();
                    token.add(new Token(TokenType.LTEQ,new TokenValue(operators),lineNumber,columnNumber));
                    return new Token(TokenType.LTEQ,new TokenValue(operators),lineNumber,columnNumber);
                }
                else
                    token.add(new Token(TokenType.LT,new TokenValue(operators),lineNumber,columnNumber));
                    return new Token(TokenType.LT,new TokenValue(operators),lineNumber,columnNumber);
            case '>':
                columnNumber++;
                operators += (char)nextChar;
                nextChar = getChar();
                if(nextChar == '=')
                {
                    columnNumber++;
                    operators += (char)nextChar;
                    nextChar = getChar();
                    token.add(new Token(TokenType.GTEQ,new TokenValue(operators),lineNumber,columnNumber));
                    return new Token(TokenType.GTEQ,new TokenValue(operators),lineNumber,columnNumber);
                }
                else
                    token.add(new Token(TokenType.GT,new TokenValue(operators),lineNumber,columnNumber));
                    return new Token(TokenType.GT,new TokenValue(operators),lineNumber,columnNumber);
            case '=':
                columnNumber++;
                operators += (char)nextChar;
                nextChar = getChar();
                if(nextChar == '=')
                {
                    operators += (char)nextChar;
                    columnNumber++;
                    nextChar = getChar();
                    token.add(new Token(TokenType.EQ,new TokenValue(operators),lineNumber,columnNumber));
                    return new Token(TokenType.EQ,new TokenValue(operators),lineNumber,columnNumber);
                }
                else
                    token.add(new Token(TokenType.ASSIGN,new TokenValue(operators),lineNumber,columnNumber));
                    return new Token(TokenType.ASSIGN,new TokenValue(operators),lineNumber,columnNumber);
            case '!':
                columnNumber++;
                operators += (char)nextChar;
                nextChar = getChar();
                if(nextChar == '=')
                {
                    columnNumber++;
                    operators += (char)nextChar;
                    nextChar = getChar();
                    token.add(new Token(TokenType.NEQ,new TokenValue(operators),lineNumber,columnNumber));
                    return new Token(TokenType.NEQ,new TokenValue(operators),lineNumber,columnNumber);
                }
                else
                    token.add(new Token(TokenType.NOT,new TokenValue(operators),lineNumber,columnNumber));
                    return new Token(TokenType.NOT,new TokenValue(operators),lineNumber,columnNumber);
            case '{':
                columnNumber++;
                operators += (char)nextChar;
                nextChar = getChar();
                token.add(new Token(TokenType.LBRACKET,new TokenValue(operators),lineNumber,columnNumber));
                return new Token(TokenType.LBRACKET,new TokenValue(operators),lineNumber,columnNumber);
            case '}':
                columnNumber++;
                operators += (char)nextChar;
                nextChar = getChar();
                token.add(new Token(TokenType.RBRACKET,new TokenValue(operators),lineNumber,columnNumber));
                return new Token(TokenType.RBRACKET,new TokenValue(operators),lineNumber,columnNumber);
            case '#':
                columnNumber++;
                operators += (char)nextChar;
                nextChar = getChar();
                token.add(new Token(TokenType.TERMINATOR,new TokenValue(operators),lineNumber,columnNumber));
                return new Token(TokenType.TERMINATOR,new TokenValue(operators),lineNumber,columnNumber);
            case '(':
                columnNumber++;
                operators += (char)nextChar;
                nextChar = getChar();
                token.add(new Token(TokenType.LPAREN,new TokenValue(operators),lineNumber,columnNumber));
                return new Token(TokenType.LPAREN,new TokenValue(operators),lineNumber,columnNumber);
            case ')':
                columnNumber++;
                operators += (char)nextChar;
                nextChar = getChar();
                token.add(new Token(TokenType.RPAREN,new TokenValue(operators),lineNumber,columnNumber));
                return new Token(TokenType.RPAREN,new TokenValue(operators),lineNumber,columnNumber);
            case '+':
                columnNumber++;
                operators += (char)nextChar;
                nextChar = getChar();
                if(nextChar == '+')
                {
                    columnNumber++;
                    operators += (char)nextChar;
                    nextChar = getChar();
                    token.add(new Token(TokenType.INCREMENT,new TokenValue(operators),lineNumber,columnNumber));
                    return new Token(TokenType.INCREMENT,new TokenValue(operators),lineNumber,columnNumber);
                }
                if(nextChar == '=')
                {
                    columnNumber++;
                    operators += (char)nextChar;
                    nextChar = getChar();
                    token.add(new Token(TokenType.PLUS_EQUAL,new TokenValue(operators),lineNumber,columnNumber));
                    return new Token(TokenType.PLUS_EQUAL,new TokenValue(operators),lineNumber,columnNumber);
                }
                token.add(new Token(TokenType.PLUS,new TokenValue(operators),lineNumber,columnNumber));
                return new Token(TokenType.PLUS,new TokenValue(operators),lineNumber,columnNumber);
            case '-':
                columnNumber++;
                operators += (char)nextChar;
                nextChar = getChar();
                if(nextChar == '-')
                {
                    columnNumber++;
                    operators += (char)nextChar;
                    nextChar = getChar();
                    token.add(new Token(TokenType.DECREMENT,new TokenValue(operators),lineNumber,columnNumber));
                    return new Token(TokenType.DECREMENT,new TokenValue(operators),lineNumber,columnNumber);
                }
                if(nextChar == '=')
                {
                    columnNumber++;
                    operators += (char)nextChar;
                    nextChar = getChar();
                    token.add(new Token(TokenType.MINUS_EQUAL,new TokenValue(operators),lineNumber,columnNumber));
                    return new Token(TokenType.MINUS_EQUAL,new TokenValue(operators),lineNumber,columnNumber);
                }
                token.add(new Token(TokenType.MINUS,new TokenValue(operators),lineNumber,columnNumber));
                return new Token(TokenType.MINUS,new TokenValue(operators),lineNumber,columnNumber);
            case '*':
                columnNumber++;
                operators += (char)nextChar;
                nextChar = getChar();
                if(nextChar == '=')
                {
                    columnNumber++;
                    operators += (char)nextChar;
                    nextChar = getChar();
                    token.add(new Token(TokenType.MULTIPLY_EQUAL,new TokenValue(operators),lineNumber,columnNumber));
                    return new Token(TokenType.MULTIPLY_EQUAL,new TokenValue(operators),lineNumber,columnNumber);
                }
                token.add(new Token(TokenType.MULTIPLY,new TokenValue(operators),lineNumber,columnNumber));
                return new Token(TokenType.MULTIPLY,new TokenValue(operators),lineNumber,columnNumber);
            case '&':
                columnNumber++;
                operators += (char)nextChar;
                nextChar = getChar();
                if(nextChar == '&')
                {
                    columnNumber++;
                    operators += (char)nextChar;
                    nextChar = getChar();
                    token.add(new Token(TokenType.AND,new TokenValue(operators),lineNumber,columnNumber));
                    return new Token(TokenType.AND,new TokenValue(operators),lineNumber,columnNumber);
                }
                else {
                    token.add(new Token(TokenType.UNKNOWN,new TokenValue(operators),lineNumber,columnNumber));
                    return new Token(TokenType.UNKNOWN,new TokenValue(operators),lineNumber,columnNumber);
                }
            case '|':
                columnNumber++;
                operators += (char)nextChar;
                nextChar = getChar();
                if(nextChar == '|')
                {
                    columnNumber++;
                    operators += (char)nextChar;
                    nextChar = getChar();
                    token.add(new Token(TokenType.OR,new TokenValue(operators),lineNumber,columnNumber));
                    return new Token(TokenType.OR,new TokenValue(operators),lineNumber,columnNumber);
                }
                else {
                    token.add(new Token(TokenType.UNKNOWN,new TokenValue(operators),lineNumber,columnNumber));
                    return new Token(TokenType.UNKNOWN,new TokenValue(operators),lineNumber,columnNumber);
                }
        }
        if(nextChar == -1)
        {
            token.add(new Token(TokenType.EOF,new TokenValue(),lineNumber,columnNumber));
            return new Token(TokenType.EOF,new TokenValue(),lineNumber,columnNumber);
        }
        
        nextChar = getChar();
        token.add(new Token(TokenType.UNKNOWN,new TokenValue(),lineNumber,columnNumber));
        return new Token(TokenType.UNKNOWN,new TokenValue(),lineNumber,columnNumber);
    }
    
    public boolean hasLetter(String s){
        for(int x = 0;x < s.length();x++) {
            if(Character.isLetter(s.charAt(x))){
                return true;
            }
        }
        return false;
    }
}
