/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Syntax;

import Lexical.TokenValue;
import Lexical.TokenType;
import java.util.ArrayList;

/**
 *
 * @author WIN
 */
public class Expression {
    public TokenValue expressionValue;
    public Expression(TokenValue expressionValue)
    {
        this.expressionValue = expressionValue;
    }
    
    public TokenType getType()
    {
        if(this.expressionValue.getStringVal().contains("&&") || this.expressionValue.getStringVal().contains("||"))
        {
            return TokenType.BULYAN;
        }
        if(this.expressionValue.getStringVal().contains("<") 
                    || this.expressionValue.getStringVal().contains("<=")
                    || this.expressionValue.getStringVal().contains(">")
                    || this.expressionValue.getStringVal().contains(">=")
                    || this.expressionValue.getStringVal().contains("==")
                    || this.expressionValue.getStringVal().contains("!="))
        {
            return TokenType.BULYAN;
        }
        if(expressionValue.getStringVal().contains("+")
            || expressionValue.getStringVal().contains("-")
            || expressionValue.getStringVal().contains("*")
            || expressionValue.getStringVal().contains("/"))
        {
            if(expressionValue.getStringVal().contains("+") && !expressionValue.getStringVal().contains("-") && !expressionValue.getStringVal().contains("*") && !expressionValue.getStringVal().contains("/"))
            {
                if(expressionValue.getStringVal().contains("SALITA_LITERAL") || expressionValue.getStringVal().contains("SALITA"))
                {
                    return TokenType.SALITA;
                }
                if(expressionValue.getStringVal().contains("DESIMAL_LITERAL") || expressionValue.getStringVal().contains("DESIMAL"))
                {
                    return TokenType.DESIMAL;
                }
                return TokenType.BUUMBILANG;
            }
            if(expressionValue.getStringVal().contains("-") || expressionValue.getStringVal().contains("*") || expressionValue.getStringVal().contains("/"))
            {
                return TokenType.BUUMBILANG;
            }
        }
        if(!this.expressionValue.getStringVal().contains("+")
            || !this.expressionValue.getStringVal().contains("-")
            || !this.expressionValue.getStringVal().contains("*")
            || !this.expressionValue.getStringVal().contains("/")
            || !this.expressionValue.getStringVal().contains("<") 
            || !this.expressionValue.getStringVal().contains("<=")
            || !this.expressionValue.getStringVal().contains(">")
            || !this.expressionValue.getStringVal().contains(">=")
            || !this.expressionValue.getStringVal().contains("==")
            || !this.expressionValue.getStringVal().contains("!=")
            || !this.expressionValue.getStringVal().contains("&&") 
            || !this.expressionValue.getStringVal().contains("||"))
        {
            if("BULYAN_LITERAL".equals(this.expressionValue.getStringVal().replaceAll("\\s+","")) || "BULYAN".equals(this.expressionValue.getStringVal().replaceAll("\\s+","")))
            {
                return TokenType.BULYAN;
            }
            if("SALITA_LITERAL".equals(this.expressionValue.getStringVal().replaceAll("\\s+","")) || "SALITA".equals(this.expressionValue.getStringVal().replaceAll("\\s+","")))
            {
                return TokenType.SALITA;
            }
            if("TITIK_LITERAL".equals(this.expressionValue.getStringVal().replaceAll("\\s+","")) || "TITIK".equals(this.expressionValue.getStringVal().replaceAll("\\s+","")))
            {
                return TokenType.TITIK;
            }
            if("DESIMAL_LITERAL".equals(this.expressionValue.getStringVal().replaceAll("\\s+","")) || "DESIMAL".equals(this.expressionValue.getStringVal().replaceAll("\\s+","")))
            {
                return TokenType.DESIMAL;
            }
            if("BUUMBILANG_LITERAL".equals(this.expressionValue.getStringVal().replaceAll("\\s+","")) || "BUUMBILANG".equals(this.expressionValue.getStringVal().replaceAll("\\s+","")))
            {
                return TokenType.BUUMBILANG;
            }
        }
        return TokenType.UNKNOWN;
    }   
}
