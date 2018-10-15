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
public class TokenValue {
    public int intVal;
    public char charVal;
    public float floatVal;
    public String stringVal;
    
    public TokenValue(){
    }
    
    public TokenValue(int intVal){
        this.intVal = intVal;
    }
    
    public TokenValue(char charVal){
        this.charVal = charVal;
    }
    
    public TokenValue(float floatVal){
        this.floatVal = floatVal;
    }
    
    public TokenValue(String stringVal){
        this.stringVal = stringVal;
    }
    public String getStringVal(){
        return stringVal;
    }
    
    public int getIntVal() {
        return intVal;
    }
    
    public float getFloatVal(){
        return floatVal;
    }
    
    public char getCharVal() {
        return charVal;
    }
}
