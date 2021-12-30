package sanchez.jose.editor;

import javax.swing.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

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
		
		listFile = new ArrayList<File>();
		listAreaTexto = new ArrayList<JTextPane>();
		listScroll = new ArrayList<JScrollPane>();
		
		//----------------------------------------------------
		
		
		add(panelMenu);
		add(tPane);
	}
	
	public void creaItem(String rotulo, String menu, String accion) {
		elementoItem = new JMenuItem(rotulo);
		
		if(menu.equals("archivo")) {
			archivo.add(elementoItem);
			if(accion.equals("nuevo")) {
				elementoItem.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						creaPanel();
					}
				});
			}
			else if(accion.equals("abrir")) {
				elementoItem.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						creaPanel();
						JFileChooser selectorArchivos = new JFileChooser();
						selectorArchivos.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
						int resultado = selectorArchivos.showOpenDialog(listAreaTexto.get(tPane.getSelectedIndex()));
						
						if (resultado == JFileChooser.APPROVE_OPTION) {
							try {
								boolean existePath = false;
								for (int i = 0; i < tPane.getTabCount(); i++) {
									File f = selectorArchivos.getSelectedFile();
									if (listFile.get(i).getPath().equals(f.getPath()))
										existePath = true;
								}

								if (!existePath) {
									File archivo = selectorArchivos.getSelectedFile();
									listFile.set(tPane.getSelectedIndex(), archivo);

									FileReader entrada = new FileReader(
											listFile.get(tPane.getSelectedIndex()).getPath());
									
									BufferedReader miBuffer = new BufferedReader(entrada);
									String linea = "";
									
									String titulo = listFile.get(tPane.getSelectedIndex()).getName();
									//El titulo se le agrega a la pestaña del panel que se crea, donde se encuentra
									//nuestra area de texto, lugar donde ira el texto del archivo que el usuario ha seleccionado
									tPane.setTitleAt(tPane.getSelectedIndex(), titulo);
									
									while(linea != null) {
										linea = miBuffer.readLine(); //Lee linea a linea cada linea del archivo y la almacena en el string
										if(linea !=null) Utilidades.append(linea+"\n", listAreaTexto.get(tPane.getSelectedIndex()));
										
									}
								}else {
									//si la ruta del fichero ya existe y esta abierto
									//vamos a recorrer todos los paneles para ver cual es el que tiene el path del
									// fichero y seleccionar ese fichero y ese panel
									
									for(int i = 0; i<tPane.getTabCount(); i++) {
										File f = selectorArchivos.getSelectedFile();
										if(listFile.get(i).getPath().equals(f.getPath())) {
											//Seleccionamos el panel que ya tiene el archivo abierto
											tPane.setSelectedIndex(i); //le pasamos por parametro la posicion del panel que tiene el path
											
											listAreaTexto.remove(tPane.getTabCount()-1);
											listScroll.remove(tPane.getTabCount()-1);
											listFile.remove(tPane.getTabCount()-1);
											tPane.remove(tPane.getTabCount()-1);
											contadorPanel--;
											break;
										}
									}
								}
								
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} 
						}else {
							//Si se oprime el boton cancelar en la ventana de abrir archivo
							//eliminamos el panel del area de texto que se crea por defecto
							
							int seleccion = tPane.getSelectedIndex();
							if(seleccion != -1) {
								listAreaTexto.remove(tPane.getTabCount()-1);
								listScroll.remove(tPane.getTabCount()-1);
								listFile.remove(tPane.getTabCount()-1);
								tPane.remove(tPane.getTabCount()-1);
								contadorPanel--;
							}
							
						}

					}
							
					
				});
			}
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
		
		listFile.add(new File(""));
		listAreaTexto.add(new JTextPane());
		listScroll.add(new JScrollPane(listAreaTexto.get(contadorPanel)));
		
		ventana.add(listScroll.get(contadorPanel));
		
		tPane.addTab("title",ventana);
		tPane.setSelectedIndex(contadorPanel);
		contadorPanel++;
		existePanel = true;
	}
	
	private int contadorPanel = 0; //Nos va a contar cuantos paneles se han creado
	private boolean existePanel = false; // nos va a decir si inicialmente existe un panel creado
	private JTabbedPane tPane;
	private JPanel ventana;
	//private JTextPane areaTexto;
	private ArrayList<JTextPane> listAreaTexto;
	private ArrayList<JScrollPane> listScroll;
	private ArrayList<File> listFile;
	private JMenuBar menu;
	private JMenu archivo, editar, seleccion, ver, apariencia;
	private JMenuItem elementoItem;
}


