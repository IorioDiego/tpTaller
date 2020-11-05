package InterfaceGrafica;

import java.awt.Color;

import javax.swing.JPanel;

public class ManoAbajo {
	
	private JPanel panelMano;
	
	
	public JPanel panelAbajo(){
		panelMano = new JPanel();
		panelMano.setBackground(new Color(111, 200, 37));
		panelMano.setBounds(100,75,699,556);
		return panelMano;
	}

}
