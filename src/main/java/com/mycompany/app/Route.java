package com.mycompany.app;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;

import javax.swing.JPanel;

public class Route  extends JPanel{
	
	public Route(Configurations config)
	{
		m_config = config;
		m_routes = new ArrayList<ArrayList<NodeInst>>();
	}
	
	@Override
	public void paintComponent(Graphics g)
	{
		setOpaque(false);
		System.out.println( "Route::Paint Component" );
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		int unitsize = m_config.m_unitSize;
		for(int i = 0; i < m_routes.size(); i++)
		{
			ArrayList<NodeInst> route = m_routes.get(i);
		
			for(int j = 0; j < route.size()-1; j++)
			{
				NodeInst sourceNode = route.get(j);
				NodeInst destinationNode = route.get(j+1);
			//	System.out.println(sourceNode.m_xCoordinate+ " "+sourceNode.m_yCoordinate + " "+ destinationNode.m_xCoordinate + " "+ destinationNode.m_yCoordinate );
				Line2D line = new Line2D.Double(sourceNode.m_xCoordinate*unitsize,sourceNode.m_yCoordinate*unitsize,
						destinationNode.m_xCoordinate*unitsize,destinationNode.m_yCoordinate*unitsize);
				g2.setColor(Color.BLACK);
				g2.setStroke(new BasicStroke(2));
				g2.draw(line);
			}
		}
		
	}
	
	public void addRoute(ArrayList<NodeInst> route)
	{
		m_routes.add(route);

	}
	
    @Override
    public Dimension getPreferredSize() {
    	
    	int unitsize = m_config.m_unitSize;
        return new Dimension(m_config.m_width*unitsize, m_config.m_height*unitsize);
    }
	
    private Configurations m_config;
    private ArrayList<ArrayList<NodeInst>> m_routes;


}
