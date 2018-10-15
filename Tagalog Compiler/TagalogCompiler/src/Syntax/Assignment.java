/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Syntax;

import Lexical.TokenValue;

/**
 *
 * @author WIN
 */
public class Assignment {
    public Expression assignExpression;
    public VariableDeclaration variable;
    
    public Assignment(Expression assignExpression, VariableDeclaration variable)
    {
        this.assignExpression = assignExpression;
        this.variable = variable;
    }
}
