package com.mycompany.app;


import java.util.*;

class BFSNodeInst
{
	public BFSNodeInst(int id)
	{
		m_id = id;
		m_hasVisited = false;
		m_sourceID = -1;
	}
	
	public int m_id;
	public int m_sourceID;
	public boolean m_hasVisited;
}

public class BreadthFirstSearch {
	public BreadthFirstSearch(DIGraph graph)
	{
		m_graph = graph;
		m_toProcessQueue = new ArrayDeque<Integer>();
		m_processedQueue = new ArrayDeque<Integer>();
		m_nodeList = new HashMap< Integer, BFSNodeInst> ();
	}

	
	public ArrayList<Integer> getRoute(int sourceNodeId, int destinationNodeID) throws Exception
	{
		ArrayList<Integer> route = new ArrayList<Integer>();
		boolean isSourceFound = false;
		
		BFSNodeInst parentNode = new BFSNodeInst(sourceNodeId);
		parentNode.m_hasVisited = true;
		m_nodeList.put(sourceNodeId,parentNode);
		m_toProcessQueue.addLast(sourceNodeId);
		while(m_toProcessQueue.size() != 0)
		{
			int nodeID = m_toProcessQueue.removeFirst();
			if(nodeID == destinationNodeID)
			{
				System.out.println("Destionation Found");
				isSourceFound = true;
				System.out.println("ProcessedNodes = "+m_processedQueue.size());
				break;
			}
			
			populateChildList(nodeID);
			m_processedQueue.addLast(nodeID);
		}
		
		if(isSourceFound == true)
		{
			route.add(destinationNodeID);
			BFSNodeInst nodeInst = m_nodeList.get(destinationNodeID);
			int nodeID = nodeInst.m_sourceID;
			while(nodeID != -1)
			{
				route.add(nodeID);
				nodeInst =  m_nodeList.get(nodeID);
				nodeID = nodeInst.m_sourceID;
			}
		}
		
		m_nodeList.clear();
		m_toProcessQueue.clear();
		m_processedQueue.clear();
		return route;
	}
	
	
	private void populateChildList(int nodeID) throws Exception
	{
		Set<Integer> childList = m_graph.getChildList(nodeID);
		  Iterator<Integer> iterator = childList.iterator();
		  while(iterator.hasNext())
		  {
			  Integer childNodeID = iterator.next();
			  BFSNodeInst childNodeInst = m_nodeList.get(childNodeID);
			  if(childNodeInst == null)
			  {
				  childNodeInst = new BFSNodeInst(childNodeID);
				  childNodeInst.m_sourceID = nodeID;
				  childNodeInst.m_hasVisited = true;
				  m_nodeList.put(childNodeID, childNodeInst);
				  m_toProcessQueue.addLast(childNodeID);
			  }
			  else
			  {
				  if(childNodeInst.m_hasVisited == false)
				  {
					  m_toProcessQueue.addLast(childNodeID);
				  }
			  }
		  }
	}
	
	
	DIGraph m_graph;
	HashMap< Integer, BFSNodeInst> m_nodeList;
	ArrayDeque<Integer> m_toProcessQueue;
	ArrayDeque<Integer> m_processedQueue;
}
