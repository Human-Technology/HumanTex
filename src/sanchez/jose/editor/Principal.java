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
		//----------- Menu ------------------------------------
		JPanel panelMenu = new JPanel();
		
		menu = new JMenuBar();
		archivo = new JMenu("Archivo");
		editar = new JMenu("Editar");
		seleccion = new JMenu("Selección");
		ver = new JMenu("Ver");
		apariencia = new JMenu("Apariencia");
		
		menu.add(archivo);
		menu.add(editar);
		menu.add(seleccion);
		menu.add(ver);
		
		
		//---------------- ELEMENTOS DEL MENU ARCHIVO---------------
		creaItem("Nuevo Archivo", "archivo", "nuevo");
		creaItem("Abrir Archivo", "archivo", "abrir");
		creaItem("Guardar", "archivo", "guardar");
		creaItem("Guardar Como", "archivo", "guardarComo");
		//--------------------------------------------------------
		
		//------------------ ELEMENTOS DEL MENU EDITAR ----------------
		creaItem("Deshacer", "editar", "");
		creaItem("Rehacer", "editar", "");
		editar.addSeparator();
		creaItem("Cortar", "editar", "");
		creaItem("Copiar", "editar", "");
		creaItem("Pegar", "editar", "");
		//-------------------------------------------------------------
		
		//----------------- ELEMENTOS DEL MENU SELECCION ---------------
		creaItem("Seleccionar Todo", "seleccion", "");
		//--------------------------------------------------------------
		
		//------------------- ELEMENTOS DEL MENU VER -------------------
		creaItem("Numeracion", "ver", "");
		ver.add(apariencia);
		creaItem("Normal", "apariencia", "");
		creaItem("Dark", "apariencia", "");
		//--------------------------------------------------------------
		
		
		panelMenu.add(menu);
		//-----------------------------------------------------
		
		
		//----------- Area de Texto --------------------------
		tPane = new JTabbedPane();
		
		//----------------------------------------------------
		
		creaPanel();
		
		add(panelMenu);
		add(tPane);
	}
	
	public void creaItem(String rotulo, String menu, String accion) {
		elementoItem = new JMenuItem(rotulo);
		
		if(menu.equals("archivo")) {
			archivo.add(elementoItem);
		}
		else if(menu.equals("editar")) {
			editar.add(elementoItem);
		}
		else if(menu.equals("seleccion")) {
			seleccion.add(elementoItem);
		}
		else if(menu.equals("ver")) {
			ver.add(elementoItem);
		}
		else if(menu.equals("apariencia")) {
			apariencia.add(elementoItem);
		}
		
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
	private JMenuBar menu;
	private JMenu archivo, editar, seleccion, ver, apariencia;
	private JMenuItem elementoItem;
}


