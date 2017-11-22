package com.mycompany.app;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;

import javax.swing.JPanel;



public class Grid  extends JPanel{

	public Grid(Configurations config,ArrayList<PCBComponent> componentList)
	{
		m_config = config;
		m_componentList = componentList;
	}

	@Override
	public void paintComponent(Graphics g)
	{
		setOpaque(false);
		System.out.println( "Grid::Paint Component" );
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		//Line2D line = new Line2D.Double(10,10,10,10);
	//	g2.setColor(Color.BLACK);
	//	g2.setStroke(new BasicStroke(2));
	//	g2.draw(line);
		//g.drawLine(10, 10, 10, 10);
		
		int unitsize = m_config.m_unitSize;
	
		for (int i = 0; i <m_config.m_width; i++)
		{
			for (int j = 0; j < m_config.m_height; j++)
			{
		
				Line2D line = new Line2D.Double(i*unitsize,j*unitsize,i*unitsize,j*unitsize);
				g2.setColor(Color.BLACK);
				g2.setStroke(new BasicStroke(2));
				g2.draw(line);
			}
		}
		
		
		for(int i = 0 ; i < m_componentList.size(); i++)
		{
			PCBComponent comp = m_componentList.get(i);
			g.drawRect(comp.m_xCoordinate*unitsize, comp.m_yCoordinate*unitsize, comp.m_width*unitsize, 
					comp.m_height*unitsize);
			
			System.out.println(comp.m_xCoordinate+ " "+ comp.m_yCoordinate+ " "+
					 comp.m_width+ " " + comp.m_height);
			// g.setColor(Color.RED); 
			
			
		}
		
		
	}
	

    @Override
    public Dimension getPreferredSize() {
    	
    	int unitsize = m_config.m_unitSize;
        return new Dimension(m_config.m_width*unitsize, m_config.m_height*unitsize);
    }
	
	private Configurations m_config;
	ArrayList<PCBComponent> m_componentList;
	
}
