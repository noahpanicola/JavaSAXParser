/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javasaxparser;

import java.io.File;
import xmlhandler.*;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Hashtable;
import java.util.Enumeration;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import java.util.ArrayList;
import javafx.stage.FileChooser;
import java.io.File;
import java.io.IOException;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import java.util.Set;

/**
 *
 * @author Noah
 */
public class FXMLDocumentController implements Initializable, Startable {
    
    @FXML
    private Label fileLabel;
    
    @FXML
    private VBox domContainer;
    
    private Stage stage;
    private String xmlFileName;
    private File activeFile;
    private Label rootLbl;
    private Label attrLbl;
    private Label propLbl;
    
    @Override
    public void start(Stage stage){
        
        this.stage = stage;
        domContainer.setPadding(new Insets(10));
        
        rootLbl = new Label("Root");
        rootLbl.setFont(Font.font("Amble CN", FontWeight.BOLD, 24));
        
        attrLbl = new Label("Attributes");
        
        propLbl = new Label("Properties");
        
        domContainer.getChildren().addAll(rootLbl, attrLbl, propLbl);
    }
    
    @FXML
    private void chooseFile(ActionEvent event) {
        
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        activeFile = fileChooser.showOpenDialog(stage);
        
        if(activeFile != null){
            
            xmlFileName = activeFile.toPath().toString();
            
            fileLabel.setText(adjustLabelLength(xmlFileName));
            
            XMLParser p = new XMLParser(xmlFileName);
            XMLNode root = p.parse();
        
            setupTree(root);
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }   
    
    private void setupTree(XMLNode root){
        
        XMLNode current = root;
        XMLNode parent = null;
        
        if(!root.name.isEmpty()){
            rootLbl.setText(root.name);
        }
        
        if(root.attributes != null){
            String attrStr = "";
            for(int i = 0; i < root.attributes.getLength(); i++){
                attrStr += root.attributes.getQName(i) + ": " + root.attributes.getValue(i) + "\n";
            }
            attrLbl.setText(attrStr);
        }
        
        if(root.properties != null){
            String propStr = "";
            
            Enumeration<String> keys = root.properties.keys();
            propStr = keys.toString();
            
            propLbl.setText(propStr);
        }
    }
    
    private String adjustLabelLength(String path){
        if(path.length() > 80 ){
            return "..." + path.substring(path.length() - 80);
        }
        return path;
    }    
}
