package com.mycompany.app;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.TreeMap;

class AStarNodeInst implements Comparable<AStarNodeInst>
{
	public AStarNodeInst(int id,int sorceID,NodeInst current,NodeInst destination)
	{
		m_id = id;
		m_sourceID = sorceID;
		calculateStaticDistance(current,destination);
		m_distanceFunction = 0;
		m_currentNodeInst = current;
		m_hasVisited = false;
	}
	
	public AStarNodeInst(int id, AStarNodeInst sourceNode,NodeInst current,NodeInst destination)
	{
		m_id = id;
		m_sourceID = sourceNode.m_id;
		m_currentNodeInst = current;
		calculateStaticDistance(current,destination);
		m_distanceFunction = sourceNode.m_distanceFunction;
		m_distanceFunction = calculateDistanceFunction(sourceNode,m_distanceFunction);
		m_hasVisited = false;
	
	}
	
	public boolean updatesourceNode(AStarNodeInst newsourceNode)
	{
		double newDistanceFunction = calculateDistanceFunction(newsourceNode, newsourceNode.m_distanceFunction);
		if(newDistanceFunction < m_distanceFunction)
		{
			m_distanceFunction = newDistanceFunction;
			m_sourceID = newsourceNode.m_sourceID;
			return true;
		}
		else
		{
			return false;
		}
	}
	
	private double calculateDistanceFunction(AStarNodeInst sourceNode,double sourceDistance)
	{
		double error1 = sourceNode.m_currentNodeInst.m_xCoordinate-m_currentNodeInst.m_xCoordinate;
		double error2  = sourceNode.m_currentNodeInst.m_yCoordinate - m_currentNodeInst.m_yCoordinate;
		double funcation = Math.pow(error1, 2)+ Math.pow(error2,2);
		return (sourceDistance+ Math.sqrt(funcation));
	}
	
	
	
	private void calculateStaticDistance(NodeInst current, NodeInst destinationNode)
	{
		double error1 = destinationNode.m_xCoordinate-current.m_xCoordinate;
		 double error2  = current.m_yCoordinate - current.m_yCoordinate;
		double funcation = Math.pow(error1, 2)+ Math.pow(error2,2);
		m_staticDestinationFunction = Math.sqrt(funcation);
		
	}
	
	public double getWeightDistance()
	{
		return m_staticDestinationFunction+m_distanceFunction;
	}
	
	public int compareTo(AStarNodeInst object2) 
	{
		if(this.getWeightDistance()> object2.getWeightDistance())
		{
			return +1;
		}
		else if(this.getWeightDistance() < object2.getWeightDistance())
		{
			return -1;
		}
		else
		{
			return 0;
		}
	}
	
	public int m_id;
	public int m_sourceID;
	public boolean m_hasVisited;
	public double m_staticDestinationFunction;
	public double m_distanceFunction; 
	public NodeInst m_currentNodeInst;
	

}

public class AStartSearch {
	
	public AStartSearch(DIGraph graph,TreeMap<Integer,NodeInst> nodeInstMap, Set<Integer> invalidSet)
	{
		m_graph =  graph;
		m_nodeInstMap = nodeInstMap;
		m_priorityQueue = new PriorityQueue<AStarNodeInst>();
		m_nodeList = new HashMap<Integer, AStarNodeInst>();
		m_processedStack = new ArrayDeque<Integer>();
		m_invalidSet = invalidSet;
	}
	
	public ArrayList<Integer> getRoute(int sourceNodeId, int destinationNodeID) throws Exception
	{
		ArrayList<Integer> route = new ArrayList<Integer>();
		boolean isSourceFound = false;
		m_destinationInst = m_nodeInstMap.get(destinationNodeID);
		NodeInst sourneNodeInst = m_nodeInstMap.get(sourceNodeId);
		AStarNodeInst sourceStartNodeInst = new AStarNodeInst(sourceNodeId, -1, sourneNodeInst, m_destinationInst);
		m_priorityQueue.add(sourceStartNodeInst);
		m_nodeList.put(sourceStartNodeInst.m_id,sourceStartNodeInst);
		
		while(m_priorityQueue.size() != 0)
		{
			AStarNodeInst selectedInst = m_priorityQueue.poll();
			selectedInst.m_hasVisited = true;
			m_processedStack.add(selectedInst.m_id);
			if(selectedInst.m_id == destinationNodeID)
			{
				System.out.println("AStar search source Found");
				isSourceFound = true;
				break;
			}
			if(selectedInst.m_id != sourceNodeId && selectedInst.m_id != destinationNodeID )
			{
				if(m_invalidSet.contains(selectedInst.m_id) == true)
				{
					continue;
				}
			}
			
			Set<Integer> childList = m_graph.getChildList(selectedInst.m_id);
			Iterator<Integer> childListItr = childList.iterator();
			while(childListItr.hasNext())
			{
				int childNodeID = childListItr.next();
				AStarNodeInst childNodeInst = m_nodeList.get(childNodeID);
				if(childNodeInst == null)
				{
					NodeInst currentNodeInst = m_nodeInstMap.get(childNodeID);
					childNodeInst = new AStarNodeInst(childNodeID, selectedInst, currentNodeInst, m_destinationInst);
					m_nodeList.put(childNodeInst.m_id, childNodeInst);
					m_priorityQueue.add(childNodeInst);
				}
				else
				{
					if(childNodeInst.m_hasVisited == false)
					{
						if(childNodeInst.updatesourceNode(selectedInst) == true)
						{
							m_priorityQueue.remove(childNodeInst);
							m_priorityQueue.add(childNodeInst);
						}
					}
				}
			}
			
			
			
		}
		
		if(isSourceFound == true)
		{
			route.add(destinationNodeID);
			AStarNodeInst nodeInst = m_nodeList.get(destinationNodeID);
			int nodeID = nodeInst.m_sourceID;
			while(nodeID != -1)
			{
				route.add(nodeID);
				nodeInst =  m_nodeList.get(nodeID);
				nodeID = nodeInst.m_sourceID;
			}
		}
		
		System.out.println("ProcessedNodes = " + m_priorityQueue.size());
		m_nodeList.clear();
		m_priorityQueue.clear();
		m_processedStack.clear();
		
		return route;
		
	}
	
	
	DIGraph m_graph;
	HashMap< Integer, AStarNodeInst> m_nodeList;
	PriorityQueue<AStarNodeInst> m_priorityQueue;
	Deque<Integer> m_processedStack;
	NodeInst m_destinationInst;
	TreeMap<Integer,NodeInst>  m_nodeInstMap;
	Set<Integer> m_invalidSet;
}
