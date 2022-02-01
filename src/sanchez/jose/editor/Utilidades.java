package sanchez.jose.editor;


import java.util.ArrayList;
import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

public class Utilidades {

	//-------------------------- Agrega texto al final --------------------------
	public static void append(String linea, JTextPane areaTexto) {
		try {
			Document doc = areaTexto.getDocument();
			doc.insertString(doc.getLength(), linea, null);
		} catch (BadLocationException exc) {
			exc.printStackTrace();
		}
	}
	//---------------------------------------------------------------------------
	
	//-------------- Metodo para mostrar numeracion -----------------------------
	
	public static void viewNumeracionInicio(boolean numeracion, JTextPane textArea, JScrollPane scroll) {
		if(numeracion) {
			scroll.setRowHeaderView(new TextLineNumber(textArea));
		}else {
			scroll.setRowHeaderView(null);
		}
	}
	
	public static void viewNumeracion(int contador, boolean numeracion, ArrayList<JTextPane> textArea, ArrayList<JScrollPane> scroll) {
		if(numeracion) {
			for(int i = 0; i<contador; i++) {
				scroll.get(i).setRowHeaderView(new TextLineNumber(textArea.get(i)));
			}
		}else {
			for(int i = 0; i<contador; i++) {
				scroll.get(i).setRowHeaderView(null);
			}
		}
	}
	
	//----------------------------------------------------------------------------
	
	// ------------------------- Apariencia ---------------------------------------
	
	public static void aFondo(int contador, String tipo, ArrayList<JTextPane> list) {
		if(tipo.equals("w")) {
			for(int i = 0; i<contador; i++) {
				
				list.get(i).selectAll();
				
				StyleContext sc = StyleContext.getDefaultStyleContext();
				
				//Para Color del Texto
				AttributeSet aset =  sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, Color.BLACK);
				
				//Para el Tipo de Texto
				aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Arial");
				
				
				list.get(i).setCharacterAttributes(aset, false);
				list.get(i).setBackground(Color.WHITE);
			}
		}
		else if(tipo.equals("d")) {
			for (int i = 0; i < contador; i++) {

				list.get(i).selectAll();
				
				StyleContext sc = StyleContext.getDefaultStyleContext();

				
				// Para Color del Texto
				AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, new Color(161, 145, 123));

				// Para el Tipo de Texto
				aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Arial");

				list.get(i).setCharacterAttributes(aset, false);
				list.get(i).setBackground(new Color(32, 33, 36));
			}
		}
	}
	
	//-----------------------------------------------------------------------------
	
}
