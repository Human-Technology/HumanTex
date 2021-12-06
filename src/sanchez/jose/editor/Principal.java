package sanchez.jose.editor;

import javax.swing.*;

public class Principal {
	public static void main(String [] args) {
		Marco marco = new Marco();
		marco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		marco.setVisible(true);
	}
}


class Marco extends JFrame{
	public Marco() {
		setBounds(300,300,300,300);
		setTitle("HumanTex");
		add(new Panel());
	}
}


class Panel extends JPanel{
	public Panel() {
	
		//----------- Area de Texto --------------------------
		tPane = new JTabbedPane();
		
		//----------------------------------------------------
		
		creaPanel();
		
		add(tPane);
	}
	
	public void creaPanel() {
		ventana = new JPanel();
		areaTexto = new JTextPane();
		
		ventana.add(areaTexto);
		
		tPane.addTab("title",ventana);
	}
	
	
	private JTabbedPane tPane;
	private JPanel ventana;
	private JTextPane areaTexto;
	
}


