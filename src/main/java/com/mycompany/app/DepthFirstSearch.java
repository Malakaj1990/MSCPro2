package com.mycompany.app;

import java.util.*;

class DFSNodeInst
{
	public DFSNodeInst(int id)
	{
		m_id = id;
		m_hasVisited = false;
		m_sourceID = -1;
	}
	
	public int m_id;
	public int m_sourceID;
	public boolean m_hasVisited;
}

public class DepthFirstSearch {

	public DepthFirstSearch(DIGraph graph)
	{
		m_graph = graph;
		m_toProcessStack = new ArrayDeque<Integer>();
		m_processedStack = new ArrayDeque<Integer>();
		m_nodeList = new HashMap< Integer, DFSNodeInst> ();
	}
	
	public ArrayList<Integer> getRoute(int sourceNodeId, int destinationNodeID) throws Exception
	{
		ArrayList<Integer> route = new ArrayList<Integer>();
		boolean isSourceFound = false;
		
		DFSNodeInst parentNode = new DFSNodeInst(sourceNodeId);
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
			DFSNodeInst nodeInst = m_nodeList.get(destinationNodeID);
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
		Set<Integer> childList = m_graph.getChildList(nodeID);
		  Iterator<Integer> iterator = childList.iterator();
		  while(iterator.hasNext())
		  {
			  Integer childNodeID = iterator.next();
			  DFSNodeInst childNodeInst = m_nodeList.get(childNodeID);
			  if(childNodeInst == null)
			  {
				  childNodeInst = new DFSNodeInst(childNodeID);
				  childNodeInst.m_sourceID = nodeID;
				  childNodeInst.m_hasVisited = true;
				  m_nodeList.put(childNodeID, childNodeInst);
				  m_toProcessStack.push(childNodeID);
			  }
			  else
			  {
				  if(childNodeInst.m_hasVisited == false)
				  {
					  m_toProcessStack.add(childNodeID);
				  }
			  }
		  }
	}
	
	
	DIGraph m_graph;
	HashMap< Integer, DFSNodeInst> m_nodeList;
	Deque<Integer> m_toProcessStack;
	Deque<Integer> m_processedStack;
	
}
