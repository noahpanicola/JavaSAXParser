/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xmlhandler;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import org.xml.sax.Attributes;

/**
 *
 * @author Noah
 */
public class XMLNode {
    
    public String name;
    public String content;
    public Attributes attributes;
    public Dictionary<String, ArrayList<XMLNode>> properties;
    
    public XMLNode(){
        name = "";
        content = "";
        properties = new Hashtable();
        attributes = null;
    }
    
}
