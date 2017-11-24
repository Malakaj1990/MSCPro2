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

class PCBComponent
{
	public PCBComponent(String id, String xCoordinate, String yCoordinate, String width, String height )
	{
		m_id = id;
		m_xCoordinate = Integer.parseInt(xCoordinate);
		m_yCoordinate = Integer.parseInt(yCoordinate);
		m_width = Integer.parseInt(width);
		m_height = Integer.parseInt(height);
	}
	
	int m_xCoordinate;
	int m_yCoordinate;
	int m_width;
	int m_height;
	String m_id;
	
}

class Connection
{
	public int m_destinationNodeID;
	public int m_sourceNodeID;
}


public class App 
{
	
	public App()
	{
		m_nodeInstMap = new TreeMap<Integer, NodeInst>();
		m_componentList = new TreeMap<String,PCBComponent>();
		m_connectionList = new ArrayList<Connection>();
		m_invalidNodes = new TreeSet<Integer>();
	}
	
	
    public static void main( String[] args ) throws Exception
    {
        
 
        App pcblayout = new App();
        //ReadConfigurations
    	Configurations config = pcblayout.readConfigutations();
    	config.m_unitSize = 20;
    	
    	
    	
    	
    	pcblayout.drawPanels(config);
    	
    	//createGraph
    	DIGraph graph = pcblayout.createGraph(config);
        
    	//DFS
    	//pcblayout.DepthFirstSearch(graph);
    	//BFS
      // pcblayout.BreadthFirstSearch(graph);
    	//Informend Search 
    	//pcblayout.InformedSearch(graph);
    	
    	//pcblayout.AStarSearch(graph);
    	pcblayout.ImprovedAStarSearch(graph);
    	
    }
    
    void drawPanels(Configurations config)
    {
    	//Draw Panels
    	 m_layoutPCB = new LayoutDiplayUnit();
    	m_route = new Route(config);
    	Grid grid = new Grid(config,m_componentList);
    	Transversal trnasversal = new Transversal(config);
    	//Route router = pcblayout.createRoute(config);
    	m_layoutPCB.addNewPanel(grid);
    	m_layoutPCB.addNewPanel(m_route);
    	m_layoutPCB.createLayout(config);
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
    
    private Configurations readConfigutations() throws Exception
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
        
        NodeList nComponentsList =  doc.getElementsByTagName("component");
       
        for(int i = 0; i < nComponentsList.getLength(); i++)
        {
        	Element componentElement = (Element)nComponentsList.item(i);
        	String id = componentElement.getAttribute("id");
        	String xCoordinate = componentElement.getElementsByTagName("xcordination").item(0).getTextContent();
        	String yCoordinate = componentElement.getElementsByTagName("ycordination").item(0).getTextContent();
        	String compWidth = componentElement.getElementsByTagName("width").item(0).getTextContent();
        	String compHeight = componentElement.getElementsByTagName("height").item(0).getTextContent();
        	
        	//System.out.println(id + " "+ xCoordinate + " "+ yCoordinate+ " "+ compWidth+" " + compHeight);
        	
        	PCBComponent component = new PCBComponent(id,xCoordinate,yCoordinate,compWidth,compHeight);
        	if((component.m_xCoordinate+component.m_width) > config.m_width)
			{
				throw new Exception();
			}
        	
        	if((component.m_yCoordinate+component.m_height) > config.m_height)
			{
        		System.out.println(component.m_yCoordinate+ " "+ component.m_height);
        		throw new Exception();
			}
        	m_componentList.put(component.m_id, component);
        }
        
        
        NodeList nConnctionList =  doc.getElementsByTagName("connection");
        for(int i = 0; i < nConnctionList.getLength(); i++)
        {
        	Element connectionElement = (Element)nConnctionList.item(i);
        	String sourceComponentString = connectionElement.getElementsByTagName("source").item(0).getTextContent();
        	StringTokenizer tokenizer = new StringTokenizer(sourceComponentString);
        	String componentID = tokenizer.nextToken(".");
        	PCBComponent component = m_componentList.get(componentID);
        	String portIDStr = tokenizer.nextToken();
        	int portID = Integer.parseInt(portIDStr);
        	int sourceNodeID = getNodeIDFromComponent(component,portID,config);
        	String destinationString = connectionElement.getElementsByTagName("destination").item(0).getTextContent();
        	tokenizer = new StringTokenizer(destinationString);
        	componentID = tokenizer.nextToken(".");
        	portIDStr = tokenizer.nextToken();
        	portID = Integer.parseInt(portIDStr);
        	component = m_componentList.get(componentID);
        	int destincationNodeID = getNodeIDFromComponent(component,portID,config);
        	
        	Connection connection = new Connection();
        	connection.m_sourceNodeID = sourceNodeID;
        	connection.m_destinationNodeID = destincationNodeID;
        	
        	System.out.println(connection.m_sourceNodeID + " "+connection.m_destinationNodeID);
        	m_connectionList.add(connection);
        	
        }
        
        populateInvalidNodes(config);
    	return config;
    	
    }
    
   private int getNodeIDFromComponent(PCBComponent component, int portID, Configurations config) throws Exception
    {
	  // System.out.println("PortID = " + portID);
    	if(portID > component.m_height*2)
    	{
    		throw new Exception();
    	}
    	
    	int xCoordinate;
    	int depth;
    	if(portID <=  component.m_height)
    	{
    		xCoordinate = component.m_xCoordinate;
    		depth = portID;
    	}
    	else
    	{
    		xCoordinate = component.m_xCoordinate+ component.m_width;
    		depth = portID - component.m_height;
    	}
    	
    	int yCoordinate = component.m_yCoordinate + depth;
    	
    	int nodeID = yCoordinate*config.m_width + xCoordinate;
    	return nodeID;
    	
    }
    
   private void populateInvalidNodes(Configurations config)
   {
	   Collection<PCBComponent> componentList = m_componentList.values();
		 
		Iterator<PCBComponent> itr = componentList.iterator();
		
		while(itr.hasNext())
		{
			PCBComponent component = itr.next();
			for(int j = 0; j <= component.m_height; j++ )
			{
				for(int i = 0; i <= component.m_width;i++)
				{
					int val = (component.m_yCoordinate+j)*config.m_width + component.m_xCoordinate+i;
					m_invalidNodes.add(val);
				}
			}
			
		}
   }
    
   private DIGraph createGraph(Configurations config) throws Exception
    {
    	DIGraph graph = new DIGraph();
    	int nodeID = 0;
    	for(int j = 0; j < config.m_height;j++)
    	{
    		for(int i = 0;i<config.m_width;i++)
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
    	
    	
    	return graph;
    	
    	
    }
    
   private void DepthFirstSearch(DIGraph graph) throws Exception
   {
		System.out.println("DFS Started");
    	DepthFirstSearch dfs = new DepthFirstSearch(graph);
    	System.out.println(dfs.getRoute(61,136));
    	//drawRoute(dfs.getRoute(61,136));
    	System.out.println("DFS Ended");
   }
   
   
   private void BreadthFirstSearch(DIGraph graph) throws Exception
   {
	   System.out.println("BFS Started");
   		BreadthFirstSearch bfs = new BreadthFirstSearch(graph);
   		for(int i = 0 ;  i < m_connectionList.size(); i++)
   		{
   			Connection connection = m_connectionList.get(i);
   			drawRoute(bfs.getRoute(connection.m_sourceNodeID,connection.m_destinationNodeID));
   		}
   		
   		System.out.println("BFS Ended");
   		
   }
   
   private void InformedSearch(DIGraph graph) throws Exception
   {
	   System.out.println("InformedSearch Started");
   		InformedSearch informendSearch = new InformedSearch(graph,m_nodeInstMap);
   		System.out.println(informendSearch.getRoute(61,136));
   		//drawRoute(informendSearch.getRoute(61,136));
   		System.out.println("InformedSearch Ended");
   }
   
   private void AStarSearch(DIGraph graph) throws Exception
   {
	   System.out.println("AStar Started");
   		AStartSearch astar = new AStartSearch(graph,m_nodeInstMap,m_invalidNodes);
   		for(int i = 0 ;  i < m_connectionList.size(); i++)
   		{
   			Connection connection = m_connectionList.get(i);
   			drawRoute(astar.getRoute(connection.m_sourceNodeID,connection.m_destinationNodeID));
   		}
   		System.out.println("AStar Ended");
   		
   }
   
   private void ImprovedAStarSearch(DIGraph graph) throws Exception
   {
	   System.out.println("Improved AStar Started");
   		ImprovedAStar astar = new ImprovedAStar(graph,m_nodeInstMap,m_invalidNodes);
   		for(int i = 0 ;  i < m_connectionList.size(); i++)
   		{
   			Connection connection = m_connectionList.get(i);
   			drawRoute(astar.getRoute(connection.m_sourceNodeID,connection.m_destinationNodeID));
   		}
   		System.out.println("Imrpved AStar Ended");
   		
   }
   
   private void drawRoute(ArrayList<Integer> route)
   {
	   ArrayList<NodeInst> nodeList = new ArrayList<NodeInst>();
	  Iterator<Integer> itr = route.iterator();
	  while(itr.hasNext())
	  {
		  int nodeID = itr.next();
		  nodeList.add(m_nodeInstMap.get(nodeID));
		 
	  }
	   
	  m_route.addRoute(nodeList); 
	  m_route.repaint();
	  
   }
   
   private TreeMap<Integer,NodeInst> m_nodeInstMap;
    
   LayoutDiplayUnit m_layoutPCB;
   Route m_route;
   private TreeMap<String,PCBComponent> m_componentList;
   private ArrayList<Connection> m_connectionList;
   private Set<Integer> m_invalidNodes;
}
