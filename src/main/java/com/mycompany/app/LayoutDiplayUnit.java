
package com.mycompany.app;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.*;

import java.awt.*;



class Component
{
	public Component(String componentID)
	{
		m_componentID = componentID;
	}
	
	public void setAttributes(int height, int width, int xCoordination, int yCoordination)
	{
		
	}
	
	
	private String m_componentID;
	private int m_height;
	private int m_width;
	private int m_xCoordination;
	private int m_yCoordination;
}

public class LayoutDiplayUnit extends JFrame{

	public LayoutDiplayUnit()
	{
		m_container = new JPanel();
		m_container.setLayout(new OverlayLayout(m_container));
	}
	
	void createLayout(Configurations config)
	{
		int unitSize = config.m_unitSize;
		
        setLayout(new FlowLayout());
		setVisible(true);
	    setSize(config.m_width*unitSize, config.m_height*unitSize);
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setTitle(config.m_title);
	    add(m_container);
	    setResizable(false);
	    setBackground(Color.gray);
	    getRootPane().setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, Color.BLACK));
        
		
	}
	
	void addNewPanel(JPanel panel)
	{
		m_container.add(panel);
	}

	JPanel  m_container;
	
}
