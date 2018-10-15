/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tagalogcompiler;

import java.io.FileNotFoundException;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import Lexical.Lexer;
import Lexical.Token;
import Lexical.TokenType;
import Syntax.Parser;

/**
 *
 * @author WIN
 */
public class TagalogCompiler {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
   /* public static void main(String[] args) throws IOException {       
        FileReader file;
        //file = new FileReader(new File(""));
        Lexer lexer = new Lexer(file);
        Token token;
        do
        {
            token = lexer.getToken();
            if(token.getType() == TokenType.BUUMBILANG_LITERAL)
                System.out.println(token.getValue().getIntVal());
            if(token.getType() == TokenType.DESIMAL_LITERAL)
                System.out.println(token.getValue().getFloatVal());
            if(token.getType() == TokenType.SINGLE_COMMENT || token.getType() == TokenType.MULTI_COMMENT)
            {}
            else {
                System.out.println(token.getValue().getStringVal());
            }
        }while(token.getType() != TokenType.EOF);
    }         */
    
}
