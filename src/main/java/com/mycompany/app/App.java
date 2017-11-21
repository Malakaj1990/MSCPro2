package com.mycompany.app;
import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.jdom2.JDOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
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

import org.w3c.dom.Element;
import org.w3c.dom.Attr;
import org.xml.sax.SAXException;

import sun.font.CreatedFontTracker;
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
	
	public App()
	{
		m_nodeInstMap = new TreeMap<Integer, NodeInst>();
	}
	
	
    public static void main( String[] args ) throws Exception
    {
        
 
        App pcblayout = new App();
        //ReadConfigurations
    	Configurations config = pcblayout.readConfigutations();
    	config.m_unitSize = 20;
    	
    	
    	
    	
    	//pcblayout.drawPanels(config);
    	
    	//createGraph
    	DIGraph graph = pcblayout.createGraph(config);
        
    	//DFS
    	pcblayout.DepthFirstSearch(graph);
    	
    }
    
    void drawPanels(Configurations config)
    {
    	//Draw Panels
    	LayoutDiplayUnit layout = new LayoutDiplayUnit();
    	Grid grid = new Grid(config);
    	Transversal trnasversal = new Transversal(config);
    	//Route router = pcblayout.createRoute(config);
    	layout.addNewPanel(grid);
    	//layout.addNewPanel(router);
    	layout.createLayout(config);
    }
    
  /*  private Route createRoute(Configurations config)
    {
    	Route route = new Route(config);
    	NodeInst node1 = new NodeInst();
    	NodeInst node2 = new NodeInst();
    	NodeInst node3 = new NodeInst();
    	node1.m_xCoordinate = 10;
    	node1.m_yCoordinate = 10;
    	
    	node2.m_xCoordinate = 10;
    	node2.m_yCoordinate = 11;
    	
     	node3.m_xCoordinate = 11;
    	node3.m_yCoordinate = 11;
    	
    	ArrayList<NodeInst> nodeList = new ArrayList<NodeInst>();
    	nodeList.add(node1);
    	nodeList.add(node2);
    	nodeList.add(node3);
    	
    	route.addRoute(nodeList);
    	return route;
    }
    */
    
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
        	System.out.println("Width = " + width + "Height = " + height);
        	
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
    
   private DIGraph createGraph(Configurations config) throws Exception
    {
    	DIGraph graph = new DIGraph();
    	int nodeID = 0;
    	for(int i = 0;i<config.m_width;i++)
    	{
    		for(int j = 0; j < config.m_height;j++)
    		{
    			NodeInst node = new NodeInst();
    			node.m_xCoordinate = i;
    			node.m_yCoordinate = j;
    			node.m_id = nodeID;
    			m_nodeInstMap.put(node.m_id, node);
    			graph.addNode(node);
    			++nodeID;
    		}
    	}
    	
    	for(int i = 0; i < config.m_width; i++)
    	{
    		for(int j = 0; j < config.m_height;j++)
    		{
    			if(i != config.m_width-1)
    			{
    				graph.addConnection((j*config.m_width+i),(j*config.m_width+i+1));
    			}
    			
    			if(j!= config.m_height-1)
    			{
    				graph.addConnection((j*config.m_width+i),((j+1)*config.m_width+i));
    			}
    		}
    	}
    	
    	
    	System.out.println(graph.getChildList(61));
    	return graph;
    	
    	
    }
    
   private void DepthFirstSearch(DIGraph graph) throws Exception
   {
		System.out.println("DFS Started");
    	DepthFirstSearch dfs = new DepthFirstSearch(graph);
    	System.out.println(dfs.getRoute(60,120));
    	System.out.println("DFS Ended");
   }
   
   
   private TreeMap<Integer,NodeInst> m_nodeInstMap;
    
}
