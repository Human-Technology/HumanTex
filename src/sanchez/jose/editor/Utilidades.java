package sanchez.jose.editor;

import java.util.ArrayList;

import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

public class Utilidades {

	//-------------------- Agregar Texto al Final ----------------------------------------------
	public static void append(String linea, JTextPane areaTexto) {
		try {
			Document doc = areaTexto.getDocument();
			doc.insertString(doc.getLength(), linea, null);
		} catch (BadLocationException exc) {
			exc.printStackTrace();
		}
	}
	//-------------------------------------------------------------------------------------------
	
	// ------------------- Numeracion -----------------------------------------------------------
	//Metodo que nos permite mostrar y ocultar numeracion
	
	public static void viewNumeroInicio(int contador, boolean numeracion, JTextPane textArea, JScrollPane scroll ) {
		if(numeracion) {
			scroll.setRowHeaderView(new TextLineNumber(textArea));
		}else {
			scroll.setRowHeaderView(null);
		}
	}
	
	public static void viewNumeracion(int contador, boolean numeracion, ArrayList<JTextPane> textArea, ArrayList<JScrollPane> scroll) {
		if(numeracion) {
			for(int i=0; i<contador; i++) {
				scroll.get(i).setRowHeaderView(new TextLineNumber(textArea.get(i)));
			}
		}else {
			for(int i =0; i<contador; i++) {
				scroll.get(i).setRowHeaderView(null);
			}
		}
	}
	
	//--------------------------------------------------------------------------------------------
	
}
