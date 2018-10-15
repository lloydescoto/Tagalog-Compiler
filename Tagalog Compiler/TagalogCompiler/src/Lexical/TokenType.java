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
public enum TokenType {
    VARIABLE,
    //LITERALS
    BUUMBILANG_LITERAL,
    DESIMAL_LITERAL,
    TITIK_LITERAL,
    SALITA_LITERAL,
    BULYAN_LITERAL,
    //DATA TYPES
    BUUMBILANG,
    DESIMAL,
    TITIK,
    SALITA,
    BULYAN,
    //RESERVED WORDS
    PANGUNAHING,
    IPASOK,
    IPAKITA,
    PAANO,
    PAANOKUNG,
    KUNG,
    ULIT,
    GAWIN,
    HABANG, 
    //ARITHMETIC OPERATORS
    PLUS,
    MINUS,
    MULTIPLY,
    DIVIDE,
    //RELATIONAL OPERATORS
    LT,
    GT,
    LTEQ,
    GTEQ,
    EQ,
    NEQ,
    //CONDITIONAL OPERATORS
    AND,
    OR,
    //SYMBOLS
    TERMINATOR,
    ASSIGN,
    LPAREN,
    RPAREN,
    LBRACKET,
    RBRACKET,
    NOT,
    //VALUE INCREMENTATION/DECREMENTATION
    INCREMENT,
    DECREMENT,
    PLUS_EQUAL,
    MINUS_EQUAL,
    MULTIPLY_EQUAL,
    DIVIDE_EQUAL,
    //COMMENTS
    SINGLE_COMMENT,
    MULTI_COMMENT,
    
    //INDICATORS
    EOF,
    UNKNOWN
}
