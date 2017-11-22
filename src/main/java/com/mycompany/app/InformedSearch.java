package com.mycompany.app;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.TreeMap;

class INFNodeInst implements Comparable<INFNodeInst>
{
	public INFNodeInst(int id, double distanceFunction)
	{
		m_id = id;
		m_hasVisited = false;
		m_sourceID = -1;
		m_distanceFunction = distanceFunction;
	}
	
	
	public int compareTo(INFNodeInst object2)
	{
		if(this.m_distanceFunction> object2.m_distanceFunction)
		{
			return -1;
		}
		else if(this.m_distanceFunction < object2.m_distanceFunction)
		{
			return +1;
		}
		else
		{
			return 0;
		}
			
		
	}
	
	public int m_id;
	public int m_sourceID;
	public boolean m_hasVisited;
	public double m_distanceFunction;
}

public class InformedSearch{
	
	public InformedSearch(DIGraph graph  ,TreeMap<Integer,NodeInst> nodeInstMap)
	{
		m_graph = graph;
		m_toProcessStack = new ArrayDeque<Integer>();
		m_processedStack = new ArrayDeque<Integer>();
		m_nodeList = new HashMap< Integer, INFNodeInst> ();
		m_nodeInstMap = nodeInstMap;
	}
	
	public ArrayList<Integer> getRoute(int sourceNodeId, int destinationNodeID) throws Exception
	{
		ArrayList<Integer> route = new ArrayList<Integer>();
		boolean isSourceFound = false;
		m_currentDestionation = destinationNodeID;
		
		INFNodeInst parentNode = new INFNodeInst(sourceNodeId,-1);
		parentNode.m_hasVisited = true;
		m_nodeList.put(sourceNodeId,parentNode);
		m_toProcessStack.push(sourceNodeId);
		while(m_toProcessStack.size() != 0)
		{
			int nodeID = m_toProcessStack.pop();
			if(nodeID == destinationNodeID)
			{
				System.out.println("Destionation Found");
				isSourceFound = true;
				System.out.println("ProcessedNodes = "+m_processedStack.size());
				break;
			}
			
			populateChildList(nodeID);
			m_processedStack.push(nodeID);
		}
		
		if(isSourceFound == true)
		{
			route.add(destinationNodeID);
			INFNodeInst nodeInst = m_nodeList.get(destinationNodeID);
			int nodeID = nodeInst.m_sourceID;
			while(nodeID != -1)
			{
				route.add(nodeID);
				nodeInst =  m_nodeList.get(nodeID);
				nodeID = nodeInst.m_sourceID;
			}
		}
		
		m_nodeList.clear();
		m_toProcessStack.clear();
		m_processedStack.clear();
		return route;
	}
	

	
	private void populateChildList(int nodeID) throws Exception
	{
		
		PriorityQueue<INFNodeInst> priorQueue = getPriortizedQueue(nodeID);
		Iterator<INFNodeInst> iterator = priorQueue.iterator();
		  while(iterator.hasNext())
		  {
			  INFNodeInst queueChildNodeInst = iterator.next();
			  INFNodeInst childNodeInst = m_nodeList.get(queueChildNodeInst.m_id);
			  if(childNodeInst == null)
			  {		
				  queueChildNodeInst.m_sourceID = nodeID;
				  queueChildNodeInst.m_hasVisited = true;
				  m_nodeList.put(queueChildNodeInst.m_id, queueChildNodeInst);
				  m_toProcessStack.push(queueChildNodeInst.m_id);
			  }
			  else
			  {
				  if(childNodeInst.m_hasVisited == false)
				  {
					  childNodeInst.m_hasVisited = true;
					  m_toProcessStack.add(childNodeInst.m_id);
				  }
			  }
		  }
			
	}
	

	private PriorityQueue<INFNodeInst> getPriortizedQueue(int sourceNodeID) throws Exception
	{

		PriorityQueue<INFNodeInst> priorityQueue = new PriorityQueue<INFNodeInst>();
		NodeInst destinationNode = m_nodeInstMap.get(m_currentDestionation);
	
		int expectedx = destinationNode.m_xCoordinate;
		int expectedy = destinationNode.m_yCoordinate;
		
		Set<Integer> childList = m_graph.getChildList(sourceNodeID);
		Iterator<Integer> iterator = childList.iterator();
		 while(iterator.hasNext())
		 {
			 Integer childNodeID = iterator.next();
			 NodeInst childNodeInst = m_nodeInstMap.get(childNodeID);
			 int nextX = childNodeInst.m_xCoordinate;
			 int nextY = childNodeInst.m_yCoordinate;
			 double error1 = expectedx-nextX;
			 double error2  = expectedy-nextY;
			double funcation = Math.pow(error1, 2)+ Math.pow(error2,2);
			 //double funcation = Math.abs(error1 + error2);
			 INFNodeInst childInFnodeInst = new  INFNodeInst(childNodeID,funcation);
			 priorityQueue.add(childInFnodeInst);
		 }		
		return priorityQueue;
	}

	
	
	DIGraph m_graph;
	HashMap< Integer, INFNodeInst> m_nodeList;
	Deque<Integer> m_toProcessStack;
	Deque<Integer> m_processedStack;
	int m_currentDestionation;
	
	 private TreeMap<Integer,NodeInst> m_nodeInstMap;
	
}
