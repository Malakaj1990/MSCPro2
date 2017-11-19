package com.mycompany.app;
import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.jdom2.JDOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;


import java.io.*;
import java.util.*;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.w3c.dom.Attr;
import org.xml.sax.SAXException;
/**
 * Hello world!
 *
 */

class Configurations
{
	
	public int m_height;
	public int m_width;
	public String m_title;
	public int m_unitSize;
	
}


public class App 
{
    public static void main( String[] args ) throws IOException, JDOMException, ParserConfigurationException, SAXException
    {
        
 
        App pcblayout = new App();
    	LayoutDiplayUnit layout = new LayoutDiplayUnit();
    	Configurations config = pcblayout.readConfigutations();
    	config.m_unitSize = 20;
    	Grid grid = new Grid(config);
    	layout.add(grid);
    	layout.createLayout(config);
        
    }
    
    private Configurations readConfigutations() throws ParserConfigurationException, SAXException, IOException
    {

		int height = 5;
		int width = 5;
    	
    	Configurations config = new Configurations();
    	File inputFile = new File("Configuration.xml"); 
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document  doc = dBuilder.parse(inputFile);
        doc.getDocumentElement().normalize();
      
        //Read Title
        Element rootElement = doc.getDocumentElement();
        String title = rootElement.getAttribute("name");
        System.out.println("Title = "+ rootElement.getAttribute("name"));
        
        //Read Properties
        NodeList nList = doc.getElementsByTagName("properties");
        Node propertiesNode = nList.item(0);
        System.out.println(nList.getLength());
        if(propertiesNode.getNodeType() == Node.ELEMENT_NODE)
        {
        	Element eElement = (Element) propertiesNode;
        	String heightStr = eElement.getElementsByTagName("height").item(0)
        						.getTextContent();
        	String widthStr =  eElement.getElementsByTagName("width").item(0)
                    			.getTextContent();
        	height = Integer.parseInt(heightStr);
        	width = Integer.parseInt(widthStr);
        	System.out.println("Width = " + height + "Height = " +width );
        	
        }
        else
        {
        	System.out.println("Invalid Node Type");
        }
        
        config.m_title = title;
        config.m_height = height;
        config.m_width = width;
        
    	return config;
    	
    }
    
    
}
