package com.mycompany.app;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
//import java.awt.RenderingHints;
import java.awt.geom.Line2D;

import javax.swing.JPanel;




public class Transversal extends JPanel {
	public Transversal(Configurations config)
	{
		m_phi = Math.toRadians(40);
	    m_barb = 20;
		m_config = config;
	}
	
	 @Override
	  public Dimension getPreferredSize() {
	    	
	    int unitsize = m_config.m_unitSize;
	     return new Dimension(m_config.m_width*unitsize, m_config.m_height*unitsize);
	   }
	
	 protected void paintComponent(Graphics g)
	 {
		 setOpaque(false);
		 System.out.println( "Transversal::Paint Component" );
	        super.paintComponent(g);
	        Graphics2D g2 = (Graphics2D)g;
	       // g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
	                          //  RenderingHints.VALUE_ANTIALIAS_ON);
	        int w = getWidth();
	        int h = getHeight();
	        NodeInst sw = new NodeInst();
	        sw.m_xCoordinate = w/8;
	        sw.m_yCoordinate = h*7/8;
	        NodeInst ne = new NodeInst();
	        ne.m_xCoordinate = w*7/8;
	        ne.m_yCoordinate = h/8;
	        
	        g2.draw(new Line2D.Double(sw.m_xCoordinate,sw.m_yCoordinate, ne.m_xCoordinate,ne.m_yCoordinate));
	        drawArrowHead(g2, sw, ne, Color.red);
	        drawArrowHead(g2, ne, sw, Color.blue);
	}
	  
	 private void drawArrowHead(Graphics2D g2, NodeInst tip, NodeInst tail, Color color)
	 {
	        g2.setPaint(color);
	        double dy = tip.m_yCoordinate - tail.m_yCoordinate;
	        double dx = tip.m_xCoordinate - tail.m_xCoordinate;
	        double theta = Math.atan2(dy, dx);
	        //System.out.println("m_theta = " + Math.toDegrees(m_theta));
	        double x, y, rho = theta + m_phi;
	        for(int j = 0; j < 2; j++)
	        {
	            x = tip.m_xCoordinate- m_barb * Math.cos(rho);
	            y = tip.m_yCoordinate - m_barb * Math.sin(rho);
	            g2.draw(new Line2D.Double(tip.m_xCoordinate, tip.m_yCoordinate, x, y));
	            rho = theta - m_phi;
	        }
	   }
	 
	 
	double m_phi;
	int m_barb;
	private Configurations m_config;
	
}
