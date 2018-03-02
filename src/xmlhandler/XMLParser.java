/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xmlhandler;

import java.io.File;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.ArrayList;
import java.lang.String;

/**
 *
 * @author Noah
 */
public class XMLParser extends DefaultHandler {
    
    private XMLNode root;
    private XMLNode currentNode;
    private ArrayList<XMLNode> stack;
    private final String xmlFileName;
    
    public XMLParser(String filename){
        
        root = null;
        currentNode = null;
        
        stack = new ArrayList();
        xmlFileName = filename;
    }
    
    
    @Override
    public void startElement(String uri, String elementName, String qualifiedName, Attributes attributes)
           throws SAXException {
       
        XMLNode node = new XMLNode();
        
        node.name = qualifiedName;
        node.attributes = attributes;
        
        stack.add(node);
        
        if(currentNode != null && qualifiedName != null){
            if(currentNode.properties.get(qualifiedName) != null){
                currentNode.properties.get(qualifiedName).add(node);
            } else {
                currentNode.properties.put(qualifiedName, new ArrayList());
                currentNode.properties.get(qualifiedName).add(node);
            }
        }
       
        setCurrent(node);
    }
    
    @Override
    public void characters(char ch[], int start, int length) throws SAXException {
        
        String temp = new String(ch, start, length);
        currentNode.content =  (currentNode.content + temp)
                .replace(" ", "").replace("\n", "");
        
        System.out.println(currentNode.name + ": " + currentNode.content);
        
    }
    
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {

        XMLNode poppedNode = stack.remove(stack.size() - 1);
        
       if(poppedNode != null){
           
           poppedNode.content = poppedNode.content.trim();
           
           if(stack.isEmpty()){
               root = poppedNode;
               currentNode = null;
           } else {
               currentNode = stack.get(stack.size() - 1);
           }
       }
       
    }
    
    public XMLNode parse(){
        
        // check the xml file
        if(xmlFileName == null) return null;
        
        try{
            
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            saxParser.parse(xmlFileName, this);
            
            return root;
            
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public XMLNode getRoot() {
        return this.root;
    }
    
    /* Private Methods */
    
    private void setCurrent(XMLNode node){
        if(this.root == null){
            this.root = node;
        }

        if(node != null){
            this.currentNode = node;
        }
    }
   
}
