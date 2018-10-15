/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Syntax;

import java.io.FileReader;
import java.io.IOException;

import Lexical.Lexer;
import Lexical.Token;
import Lexical.TokenType;
import Lexical.TokenValue;
import Syntax.Identifier;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author WIN
 */
public class Parser {
    private Lexer lexer;
    private Token token;
    private Token previousToken;
    private String error;
    private ArrayList<Identifier> identifiers;
    private ArrayList<Expression> expressions;
    private ArrayList<VariableDeclaration> variableDeclarations;
    private ArrayList<Assignment> assignments;
    private ArrayList<Token> tokens;
    private int blockLevel = -1;
    public Parser(FileReader file) throws IOException 
    {
        this.lexer = new Lexer(file);
        this.token = lexer.getToken();
        this.previousToken = this.token;
        this.identifiers = new ArrayList<Identifier>();
        this.expressions = new ArrayList<Expression>();
        this.variableDeclarations = new ArrayList<VariableDeclaration>();
        this.assignments = new ArrayList<Assignment>();
        this.tokens = new ArrayList<Token>();
    }
    public String expressionValue = "";
    public ArrayList<Token> getAllTokens()
    {
        return this.tokens;
    }
    public ArrayList<Identifier> getIdentifiers()
    {
        return this.identifiers;
    }
    public ArrayList<Expression> getExpressions()
    {
        return this.expressions;
    }
    public ArrayList<VariableDeclaration> getVariableDeclaration()
    {
        return this.variableDeclarations;
    }
    public ArrayList<Assignment> getAssignment()
    {
        return this.assignments;
    }
    
    public Token nextToken()
    {
        return lexer.getToken();
    }
    
    public String getError()
    {
        return this.error;
    }
    
    public boolean Program()
    {        
        if(token.getType() == TokenType.PANGUNAHING)
        {
            previousToken = token;
            token = nextToken();
        } else {
            token = previousToken;
            error = "Syntax Error : line " + token.getLineNumber() + " missing pangunahing";
            return false;
        }      
        if(token.getType() == TokenType.LPAREN)
        {
            previousToken = token;
            token = nextToken();
        } else {
            token = previousToken;
            error = "Syntax Error : line " + token.getLineNumber() + " '(' expected";
            return false;
        }   
        if(token.getType() == TokenType.RPAREN)
        {
            previousToken = token;
            token = nextToken();
        } else {
            token = previousToken;
            error = "Syntax Error : line " + token.getLineNumber() + " ')' expected";
            return false;
        }
        if(Block())
        {
            if(token.getType() != TokenType.EOF)
            {
                error = "Syntex Error : line " + token.getLineNumber();
                return false;
            }
            tokens = lexer.getAllTokens();
            return true;
        }    
        return false;
    }
    
    public boolean Block()
    {
        if(token.getType() == TokenType.LBRACKET)
        {
            blockLevel++;
            previousToken = token;
            token = nextToken();
        } else {
            token = previousToken;
            error = "Syntax Error: line " + token.getLineNumber() + " '{' expected";
            return false;
        }
        boolean wrongStatement = true;
        while(token.getType() != TokenType.RBRACKET)
        {
            boolean noStop = Statement();
            if(noStop)
            {
                wrongStatement = true;
            } else {
                wrongStatement = false;
                break;      
            }
        }
        if(!wrongStatement)
            return false;
        if(token.getType() == TokenType.RBRACKET)
        {
            blockLevel--;
            previousToken = token;
            token = nextToken();
            return true;
        } else {
            token = previousToken;
            error = "Syntax Error : line " + token.getLineNumber() + " '}' expected";
        }
        return false;
    }
    
    public boolean Statement()
    {
        if(If())
        {
            return true;
        }
        if(ElseIf())
        {
            return true;
        }
        if(Else())
        {
            return true;
        }
        if(While())
        {
            return true;
        }
        if(DoWhile())
        {
            return true;
        }
        if(For())
        {
            return true;
        }
        if(Comment())
        {
            return true;
        }
        if(Input())
        {
            return true;
        }
        if(Output())
        {
            return true;
        }
        if(UnaryORArithmeticEqualStatement())
        {
            return true;
        }
        if(VariableDeclaration())
        {
            return true;
        }
        return false;
    }
    
    public boolean UnaryORArithmeticEqualStatement()
    {
        if(UnaryOperators())
        {
            previousToken = token;
            token = nextToken();
            if(Identifier())
            {
                previousToken = token;
                token = nextToken();
                if(token.getType() == TokenType.TERMINATOR)
                {
                    previousToken = token;
                    token = nextToken();
                    return true;
                } else {
                    error = "Syntax Error : line " + token.getLineNumber() + " missing terminator";
                    return false;
                }
            } else {
                error = "Syntax Error : line " + token.getLineNumber() + " identifier expected";
                return false;
            }
        }
        if(Identifier())
        {
            identifiers.add(new Identifier(token.getType(),token.getValue(),token.getLineNumber(), blockLevel));
            previousToken = token;
            token = nextToken();
            if(UnaryOperators())
            {
                previousToken = token;
                token = nextToken();
                if(token.getType() == TokenType.TERMINATOR)
                {
                    previousToken = token;
                    token = nextToken();
                    return true;
                } else {
                    error = "Syntax Error : line " + token.getLineNumber() + " missing terminator";
                    return false;
                }
            }
            if(ArithmeticEqualOperators())
            {
                previousToken = token;
                token = nextToken();
                if(Expression())
                {
                    if(token.getType() == TokenType.TERMINATOR)
                    {
                        previousToken = token;
                        token = nextToken();
                        return true;
                    } else {
                        error = "Syntax Error : line " + token.getLineNumber() + " missing terminator";
                        return false;
                    }
                } 
            }
        } else {
                error = "Syntax Error : line " + token.getLineNumber() + " identifier expected";
                return false;
        }
        error = "Syntax Error : line " + token.getLineNumber();
        return false;
    }
    
    public boolean ForUpdate()
    {
        if(UnaryOperators()) //++ --
        {
            previousToken = token;
            token = nextToken();
            if(Identifier())
            {
                identifiers.add(new Identifier(token.getType(),token.getValue(),token.getLineNumber(), blockLevel));
                previousToken = token;
                token = nextToken();
                return true;
            } else {
                error = "Syntax Error : line " + token.getLineNumber() + " identifier expected";
                return false;
            }
        }
        if(Identifier())
        {
            identifiers.add(new Identifier(token.getType(),token.getValue(),token.getLineNumber(), blockLevel));
            previousToken = token;
            token = nextToken();            
        } else {
                error = "error line: " + token.getLineNumber() + "'Identifier' expected";
                return false;
        }
        if(UnaryOperators())
        {
            previousToken = token;
            token = nextToken();
            return true;
        }
        if(ArithmeticEqualOperators())
        {
            previousToken = token;
            token = nextToken();
        } else {
            error = "Syntax Error : line " + token.getLineNumber() + " operators expected";
            return false;
        }
        if(Expression())
        {
            return true;
        } 
        return false;
    }
    
    public boolean For()
    {
        if(token.getType() == TokenType.ULIT)
        {
            previousToken = token;
            token = nextToken();
        } else {
            error = "Syntax Error : line " + token.getLineNumber();
            return false;
        }
        if(token.getType() == TokenType.LPAREN)
        {
            previousToken = token;
            token = nextToken();
        } else {
            error = "Syntax Error : line " + token.getLineNumber() + " '(' expected";
            return false;
        }
        if(VariableDeclaration())
        {
        } else {
            error = "Syntax Error : line " + token.getLineNumber() + " variable declaration expected";
            return false;
        }
        if(Expression())
        {
        } else {
            return false;
        }
        if(token.getType() == TokenType.TERMINATOR)
        {
            previousToken = token;
            token = nextToken();
        } else {
            error = "Syntax Error : line " + token.getLineNumber() + " missing terminator";
            return false;
        }
        if(ForUpdate())
        {
            
        } else {
            error = "Syntax Error : line " + token.getLineNumber() + " update expected";
            return false;
        }
        if(token.getType() == TokenType.RPAREN)
        {
            previousToken = token;
            token = nextToken();
        } else {
            error = "Syntax Error : line " + token.getLineNumber() + " ')' expected";
            return false;
        }
        if(Block())
        {
            return true;
        }
        return false;
    }
    
    public boolean DoWhile()
    {
        if(token.getType() == TokenType.GAWIN)
        {
            previousToken = token;
            token = nextToken();
        } else {
            error = "Syntax Error : line " + token.getLineNumber();
            return false;
        }
        if(Block())
        {
        } else {
            return false;
        }
        if(token.getType() == TokenType.HABANG)
        {
            previousToken = token;
            token = nextToken();
        } else {
            error = "Syntax Error : line " + token.getLineNumber() + " habang expected";
            return false;
        }
        if(token.getType() == TokenType.LPAREN)
        {
            previousToken = token;
            token = nextToken();
        } else {
            error = "Syntax Error : line " + token.getLineNumber() + " '(' expected";
            return false;
        }
        if(ConditionalExpression())
        {
        } else {
            return false;
        }
        if(token.getType() == TokenType.RPAREN)
        {
            previousToken = token;
            token = nextToken();
        } else {
            token = previousToken;
            error = "Syntax Error : line " + token.getLineNumber() + " ')' expected";
            return false;
        }
        if(token.getType() == TokenType.TERMINATOR)
        {
            previousToken = token;
            token = nextToken();
            return true;
        }
        token = previousToken;
        error = "Syntax Error : line " + token.getLineNumber() + " missing terminator";
        return false;
    }
    
    public boolean While()
    {
        if(token.getType() == TokenType.HABANG)
        {
            previousToken = token;
            token = nextToken();
        } else {
            error = "Syntax Error : line " + token.getLineNumber();
            return false;
        }
        if(token.getType() == TokenType.LPAREN)
        {
            previousToken = token;
            token = nextToken();
        } else {
            token = previousToken;
            error = "Syntax Error : line " + token.getLineNumber() + " '(' expected";
            return false;
        }
        if(ConditionalExpression())
        {
        } else {
            return false;
        }
        if(token.getType() == TokenType.RPAREN)
        {
            previousToken = token;
            token = nextToken();
        } else {
            token = previousToken;
            error = "Syntax Error : line " + token.getLineNumber() + " ')' expected";
            return false;
        }
        if(Block())
        {
            return true;
        }
        return false;
    }
    
    public boolean If()
    {
        if(token.getType() == TokenType.PAANO)
        {
            previousToken = token;
            token = nextToken();
        } else {
            error = "Syntax Error : line " + token.getLineNumber();
            return false;
        }
        if(token.getType() == TokenType.LPAREN)
        {
            previousToken = token;
            token = nextToken();
        } else {
            token = previousToken;
            error = "Syntax Error : line " + token.getLineNumber() + " '(' expected";
             return false;
        }
        if(Expression())
        {
        } else { 
            return false;     
        }
        if(token.getType() == TokenType.RPAREN)
        {
            previousToken = token;
            token = nextToken();
        } else {
            token = previousToken;
            error = "Sytnax Error : line " + token.getLineNumber() + " ')' expected";
            return false;
        }
        if(Block())
        {          
            return true;
        }
        return false;
    }
    public boolean ElseIf()
    {
        if(token.getType() == TokenType.PAANOKUNG)
        {
            previousToken = token;
            token = nextToken();
        } else {
            error = "Syntax Error : line " + token.getLineNumber();
            return false;
        }
        if(token.getType() == TokenType.LPAREN) 
        {
            previousToken = token;
            token = nextToken();
        } else {
            token = previousToken;
            error = "Syntax Error : line " + token.getLineNumber() + " '(' expected";
            return false;
        }
        if(ConditionalExpression())
        {
        } else { 
            error = "Syntax Error : line " + token.getLineNumber();
            return false;
        }
        if(token.getType() == TokenType.RPAREN)
        {
            previousToken = token;
            token = nextToken();
        } else {
            token = previousToken;
            error = "Syntax Error : line " + token.getLineNumber() + " ')' expected";
            return false;
        }
        if(Block())
        {          
            return true;
        }
        return false;
    }
    public boolean Else()
    {
        if(token.getType() == TokenType.KUNG)
        {
            previousToken = token;
            token = nextToken();
        } else {
            error = "Syntax Error : line " + token.getLineNumber();
            return false;
        }
        if(Block())
        {
            return true;
        }
        return false;
    }
    
    public boolean Output()
    {
        if(token.getType() == TokenType.IPAKITA)
        {
            previousToken = token;
            token = nextToken();
        } else {
            error = "Syntax Error : line " + token.getLineNumber();
            return false;
        }
        if(Expression())
        {
            
        } else {
            token = previousToken;
            return false;
        }
        if(token.getType() == TokenType.TERMINATOR)
        {
            previousToken = token;
            token = nextToken();
            return true;
        }
        token = previousToken;
        error = "Syntax Error : line " + token.getLineNumber() + " missing terminator";
        return false;
    }
    
    public boolean Input()
    {
        if(token.getType() == TokenType.IPASOK)
        {
            previousToken = token;
            token = nextToken();                         
        } else {
            error = "Syntax Error : line " + token.getLineNumber();
            return false;
        }
        if(Identifier())
        {
            identifiers.add(new Identifier(token.getType(),token.getValue(),token.getLineNumber(), blockLevel));
            previousToken = token;
            token = nextToken();
        } else {
            token = previousToken;
            error = "Syntax Error : line " + token.getLineNumber() + " identifier expected";
            return false;
        }
        if(token.getType() == TokenType.TERMINATOR)
        {
            previousToken = token;
            token = nextToken();
            return true;
        }
        token = previousToken;
        error = "Syntax Error : line " + token.getLineNumber() + " missing terminator";
        return false;
    }
    TokenType variableType;
    Identifier variableIdentifier;
    public boolean VariableDeclaration()
    {
        if(DataType())
        {
            variableType = token.getType();
            previousToken = token;
            token = nextToken();
        } else {
            error = "Syntax Error : line " + token.getLineNumber();
            return false;
        }
        if(Identifier())
        {
            variableIdentifier = new Identifier(token.getType(),token.getValue(),token.getLineNumber(), blockLevel);
            identifiers.add(new Identifier(token.getType(),token.getValue(),token.getLineNumber(), blockLevel));
            previousToken = token;
            token = nextToken();
        } else {
            token = previousToken;
            error = "Syntax Error : line " + token.getLineNumber() + " identifier expected";
            return false;
        }
        variableDeclarations.add(new VariableDeclaration(variableType,variableIdentifier,variableIdentifier.lineNumber,blockLevel));
        boolean correctVariableDeclare = true;
        if(token.getType() == TokenType.ASSIGN)
        {
            if(Assignment())
            {
                correctVariableDeclare = true;
            } else {
                 return false;
            }
        } 
        if(token.getType() == TokenType.TERMINATOR)
        {
            previousToken = token;
            token = nextToken();
            correctVariableDeclare = true;
        } else {
            token = previousToken;
            error = "Syntax Error : line " + token.getLineNumber() + " missing terminator";
             return false;
        }
        
        return correctVariableDeclare;
    }
    
    public boolean Assignment()
    {
        if(token.getType() == TokenType.ASSIGN)
        {
            previousToken = token;
            token = nextToken();
        } else {
            token = previousToken;
            error = "Syntax Error : line " + token.getLineNumber() + " '=' expected";
            return false;
        }
        if(Expression())
        {
            assignments.add(new Assignment(new Expression(new TokenValue(expressionValue)), new VariableDeclaration(variableType,variableIdentifier,variableIdentifier.lineNumber,blockLevel)));
            return true;
        } else {
        }
        return false;
    }
    
    public boolean Comment()
    {
        if(token.getType() == TokenType.SINGLE_COMMENT)
        {
            previousToken = token;
            token = nextToken();
            return true;
        }
        if(token.getType() == TokenType.MULTI_COMMENT)
        {
            previousToken = token;
            token = nextToken();
            return true;
        }
        return false;
    }
    
    public boolean Expression()
    {
        expressionValue = "";
        if(ConditionalExpression())
        {
            expressions.add(new Expression(new TokenValue(expressionValue)));
            return true;
        }
        token = previousToken;
        return false;
    }
    
    public boolean ConditionalExpression()
    {
        if(RelationalExpression())
        { 
            
        } else {
            token = previousToken;
            error = "error line: " + token.getLineNumber();
            return false;
        }
        boolean correctCondition = true;
        while(ConditionalOperators())
        {
            if(ContinueConditional())
            {
                correctCondition = true;
            } else {
                token = previousToken;
                correctCondition = false;
                break;
            }
        }
        
        return correctCondition;
    }
    
    public boolean ContinueConditional()
    {
        if(ConditionalOperators())
        {
            expressionValue += token.getValue().getStringVal();
            previousToken = token;
            token = nextToken();
        } else {
            token = previousToken;
            error = "Syntax Error : line " + token.getLineNumber() + " operators expected";
            return false;
        }
        if(RelationalExpression())
        {
            return true;
        } else {
        }
        error = "Syntax Error : line " + token.getLineNumber();
        return false;
    }
    
    public boolean RelationalExpression()
    {       
        if(ArithmeticExpression())
        {
        } else {
            token = previousToken;
            error = "Syntax Error : line " + token.getLineNumber();
            return false;
        }
        boolean correctRelational = true;
        while(RelationalOperators())
        {
            if(ContinueRelational())
            {
                correctRelational = true;
            } else {
                token = previousToken;
                correctRelational = false;
                break;
            }         
        } 
        return correctRelational;
    }
    
    public boolean ContinueRelational()
    {
        if(RelationalOperators())
        {
            expressionValue += token.getValue().getStringVal();
            previousToken = token;
            token = nextToken();
        } else {
            token = previousToken;
            error = "Syntax Error : line " + token.getLineNumber() + " operators expected";
            return false;
        }
        if(ArithmeticExpression())
        {
            return true;
        } else {
            token = previousToken;
        }
        error = "Syntax Error : line " + token.getLineNumber();
        return false;
    }
    public boolean ArithmeticExpression()
    {
        if(Negative() || Not())
        {
            expressionValue += token.getType().toString();
            expressionValue += " ";
            previousToken = token;
            token = nextToken();
        }
        if(UnaryOperators())
        {
            expressionValue += token.getType().toString();
            expressionValue += " ";
            previousToken = token;
            token = nextToken();
        }
        if(Identifier() || Literal())
        {
            expressionValue += token.getType().toString();
            expressionValue += " ";
            if(Identifier())
            {
                identifiers.add(new Identifier(token.getType(),token.getValue(),token.getLineNumber(), blockLevel));
            }
            previousToken = token;
            token = nextToken();
        } else {
            token = previousToken;
            error = "Syntax Error : line " + token.getLineNumber() + " operand expected";
            return false;
        }
        if(UnaryOperators())
        {
            expressionValue += token.getType().toString();
            expressionValue += " ";
            previousToken = token;
            token = nextToken();
        }
        boolean correctArithmetic = true;
        while(ArithmeticOperators())
        {
            if(ContinueArithmetic())
            {
                correctArithmetic = true;
            } else {
                token = previousToken;
                correctArithmetic = false;
                break;
            }
        }
        return correctArithmetic;
    }
   
    public boolean ContinueArithmetic()
    {
        if(ArithmeticOperators())
        {
            expressionValue += token.getValue().getStringVal();
            previousToken = token;
            token = nextToken();
        } else {
            token = previousToken;
            error = "Syntax Error : line " + token.getLineNumber() + " operators expected";
            return false;
         
        }
        if(Not())
        {
            expressionValue += token.getType().toString();
            expressionValue += " ";
            previousToken = token;
            token = nextToken();
        }
        if(UnaryOperators())
        {
            expressionValue += token.getType().toString();
            expressionValue += " ";
            previousToken = token;
            token = nextToken();
        }
        if(Identifier() || Literal())
        {
            expressionValue += token.getType().toString();
            expressionValue += " ";
            if(Identifier())
            {
                identifiers.add(new Identifier(token.getType(),token.getValue(),token.getLineNumber(), blockLevel));
            }
            previousToken = token;
            token = nextToken();
            return true;
        } else {
            error = "Syntax Error : line " + token.getLineNumber() + " operand expected";
            token = previousToken;
        }
        error = "Syntax Error : line " + token.getLineNumber();
        return false;
    }
    
    public boolean ConditionalOperators()
    {
        if(token.getType() == TokenType.AND)
        {
            return true;
        }
        if(token.getType() == TokenType.OR)
        {
            return true;
        }
        return false;
    }
    
    public boolean RelationalOperators()
    {
        if(token.getType() == TokenType.GT)
        {
            return true;
        }
        if(token.getType() == TokenType.GTEQ)
        {
            return true;
        }
        if(token.getType() == TokenType.LT)
        {
            return true;
        }
        if(token.getType() == TokenType.LTEQ)
        {
            return true;
        }
        if(token.getType() == TokenType.EQ)
        {
            return true;
        }
        if(token.getType() == TokenType.NEQ)
        {
            return true;
        }
        return false;
    }
    
    public boolean ArithmeticEqualOperators()
    {
        if(token.getType() == TokenType.PLUS_EQUAL)
        {
            return true;
        }
        if(token.getType() == TokenType.MINUS_EQUAL)
        {
            return true;
        }
        if(token.getType() == TokenType.MULTIPLY_EQUAL)
        {
            return true;
        }
        if(token.getType() == TokenType.DIVIDE_EQUAL)
        {
            return true;
        }
        if(token.getType() == TokenType.ASSIGN)
        {
            return true;
        }
        return false;
    }
    
    public boolean ArithmeticOperators()
    {
        if(token.getType() == TokenType.PLUS)
        {
            return true;
        }
        if(token.getType() == TokenType.MINUS)
        {
            return true;
        }
        if(token.getType() == TokenType.MULTIPLY)
        {
            return true;
        }
        if(token.getType() == TokenType.DIVIDE)
        {
            return true;
        }
        return false;
    }
    
    public boolean UnaryOperators()
    {
        if(token.getType() == TokenType.INCREMENT)
        {
            return true;
        }
        if(token.getType() == TokenType.DECREMENT)
        {
            return true;
        }
        return false;
    }
    
    public boolean Negative()
    {
        if(token.getType() == TokenType.MINUS)
        {
            return true;
        }
        return false;
    }
    
    public boolean Not()
    {
        if(token.getType() == TokenType.NOT)
        {
            return true;
        }
        return false;
    }
    
    public boolean Identifier()
    {
        if(token.getType() == TokenType.VARIABLE)
        {
            return true;
        }
        return false;
    }
    
    
    public boolean Literal()
    {
        if(token.getType() == TokenType.BUUMBILANG_LITERAL)
        {
            return true;
        }
        if(token.getType() == TokenType.DESIMAL_LITERAL)
        {
            return true;
        }
        if(token.getType() == TokenType.TITIK_LITERAL)
        {
            return true;
        }
        if(token.getType() == TokenType.SALITA_LITERAL)
        {
            return true;
        }
        if(token.getType() == TokenType.BULYAN_LITERAL)
        {
            return true;
        }
        return false;
    }
    
    public boolean DataType()
    {
        if(token.getType() == TokenType.BUUMBILANG)
        {
            return true;
        }
        if(token.getType() == TokenType.DESIMAL)
        {
            return true;
        }
        if(token.getType() == TokenType.SALITA)
        {
            return true;
        }
        if(token.getType() == TokenType.TITIK)
        {
            return true;
        }
        if(token.getType() == TokenType.BULYAN)
        {           
            return true;
        }
        return false;
    }
}
