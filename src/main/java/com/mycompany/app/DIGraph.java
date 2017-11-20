package com.mycompany.app;

import java.util.*;

class GraphNodeInst
{
	public GraphNodeInst(int id)
	{
		m_childList = new TreeSet<Integer>();
		m_nodeID = id;
	}
	
	public void setChild(int nodeID)
	{
		m_childList.add(nodeID);
	}
	
	public Set<Integer> getChildList()
	{
		return m_childList;
	}
	
	public int m_nodeID;
	public Set<Integer> m_childList;
}

public class DIGraph {

	public DIGraph()
	{
		m_nodeMap = new HashMap<Integer, GraphNodeInst>();
	}
	
	public void addNode(NodeInst nodeInst)
	{
		GraphNodeInst inst = new GraphNodeInst(nodeInst.m_id);
		m_nodeMap.put(inst.m_nodeID, inst);
	}
	
	public void addConnection(int nodeID1, int nodeId2) throws Exception
	{
		GraphNodeInst inst1 = m_nodeMap.get(nodeID1);
		if(inst1 == null)
		{
			throw new NullPointerException();
		}
		
		GraphNodeInst inst2 = m_nodeMap.get(nodeId2);
		if(inst2 == null)
		{
			System.out.println(nodeId2);
			throw new NullPointerException();
		}
		
		inst1.setChild(nodeId2);
		inst2.setChild(nodeID1);	
	}
	
	public Set<Integer> getChildList(int nodeID) throws Exception
	{
		GraphNodeInst inst1 = m_nodeMap.get(nodeID);
		if(inst1 == null)
		{
			throw new NullPointerException();
		}
		
		return inst1.getChildList();
		
	}
	
	
	
	
	private HashMap<Integer,GraphNodeInst> m_nodeMap;
}
