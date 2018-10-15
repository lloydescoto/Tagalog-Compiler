/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Semantic;

import Lexical.Token;
import Syntax.Assignment;
import Syntax.Expression;
import Syntax.Identifier;
import Syntax.Parser;
import Syntax.VariableDeclaration;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author WIN
 */
public class SemanticAnalyzer {
    public Parser parser;
    public ArrayList<Identifier> identifiers;
    public ArrayList<Expression> expressions;
    public ArrayList<VariableDeclaration> variableDeclaration;
    public ArrayList<Assignment> assignments;
    public ArrayList<ErrorType> errors;
    public ArrayList<Integer> errorsLine;
    public ArrayList<Token> tokens;
    public String error;
    
    public SemanticAnalyzer(FileReader file) throws IOException
    {
        this.parser = new Parser(file);
        this.errors = new ArrayList<ErrorType>();
        this.errorsLine = new ArrayList<Integer>();
    }
    
    public boolean analyzeProgam()
    {
        if(parser.Program())
        {
            this.tokens = parser.getAllTokens();
            this.identifiers = parser.getIdentifiers();
            this.expressions = parser.getExpressions();
            this.variableDeclaration = parser.getVariableDeclaration();
            this.assignments = parser.getAssignment();
            CheckIdentifier();
            CheckVariable();
            CheckAssign();
            return true;
        }
        else
        {
            error = parser.getError();
            return false;
        }
    }
    
    public void CheckAssign()
    {
        for(int x = 0;x < assignments.size();x++)
        {
            if(assignments.get(x).assignExpression.getType() == assignments.get(x).variable.getType())
            {       
            }
            else
            {
                errors.add(ErrorType.TYPE_MISMATCH);
                errorsLine.add(x);
            }           
        }
    }
    
    public void CheckVariable()
    {
        for(int x = 0;x < variableDeclaration.size();x++)
        {
            if(CheckMultipleDeclare(variableDeclaration.get(x)))
            {
            }
            else
            {
                errorsLine.add(x);
                errors.add(ErrorType.MULTIPLE_DECLARATION);
            }
        }
    }
 
    public void CheckIdentifier()
    {
        for(int x = 0;x < identifiers.size();x++)
        {
            if(IsDeclare(identifiers.get(x)))
            {
                
            }
            else
            {
                errorsLine.add(x);
                errors.add(ErrorType.UNDECLARE_VARIABLE);
            }
            if(CheckScope(identifiers.get(x)))
            {
            }
            else
            {
                errorsLine.add(x);
                errors.add(ErrorType.OUT_OF_SCOPE);
            }
        }
    }
    
    private boolean IsDeclare(Identifier identifier)
    {
        boolean isDeclare = false;
        for(int x = 0;x < variableDeclaration.size();x++)
        {
            if(variableDeclaration.get(x).identifierValue.identifierValue.getStringVal().equals(identifier.identifierValue.getStringVal()))
            {
                if(identifier.lineNumber < variableDeclaration.get(x).lineNumber)
                {
                    return false;
                }
                isDeclare = true;
            }
        }
        return isDeclare;
    }
    
    private boolean CheckScope(Identifier identifier)
    {
        boolean scopeCheck = false;
        for(int x = 0;x < variableDeclaration.size();x++)
        {
            if(variableDeclaration.get(x).identifierValue.identifierValue.getStringVal().equals(identifier.identifierValue.getStringVal()))
            {
                if(variableDeclaration.get(x).blockLevel <= identifier.blockLevel)
                {
                    scopeCheck = true;
                }
            }
        }
        return scopeCheck;
    }
    
    private boolean CheckMultipleDeclare(VariableDeclaration variable)
    {
        boolean multipleDeclare = false;
        int declareCount = 0;
        for(int y = 0; y < variableDeclaration.size();y++)
        {
            if(variable.identifierValue.identifierValue.getStringVal().equals(variableDeclaration.get(y).identifierValue.identifierValue.getStringVal()))
            {
                declareCount++;
            }
        }
        if(declareCount == 1)
        {
            multipleDeclare = true;
        }
        else
        {
            multipleDeclare = false;
        }
        return multipleDeclare;
    }
            
}
