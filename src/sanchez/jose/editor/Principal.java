package sanchez.jose.editor;

import javax.swing.*;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.DefaultEditorKit.CopyAction;
import javax.swing.text.DefaultEditorKit.CutAction;
import javax.swing.undo.UndoManager;

import java.awt.Color;
import java.awt.BorderLayout;

import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
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
		setLayout(new BorderLayout());
		
		//----------- Menu ------------------------------------
		JPanel panelMenu = new JPanel();
		
		panelMenu.setLayout(new BorderLayout());
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
		creaItem("Deshacer", "editar", "deshacer");
		creaItem("Rehacer", "editar", "rehacer");
		editar.addSeparator();
		creaItem("Cortar", "editar", "cortar");
		creaItem("Copiar", "editar", "copiar");
		creaItem("Pegar", "editar", "pegar");
		//-------------------------------------------------------------
		
		//----------------- ELEMENTOS DEL MENU SELECCION ---------------
		creaItem("Seleccionar Todo", "seleccion", "seleccion");
		//--------------------------------------------------------------
		
		//------------------- ELEMENTOS DEL MENU VER -------------------
		creaItem("Numeracion", "ver", "numeracion");
		ver.add(apariencia);
		creaItem("Normal", "apariencia", "normal");
		creaItem("Dark", "apariencia", "dark");
		//--------------------------------------------------------------
		
		
		panelMenu.add(menu, BorderLayout.NORTH);
		//-----------------------------------------------------
		
		
		//----------- Area de Texto --------------------------
		tPane = new JTabbedPane();
		
		listFile = new ArrayList<File>();
		listAreaTexto = new ArrayList<JTextPane>();
		listScroll = new ArrayList<JScrollPane>();
		listManager = new ArrayList<UndoManager>();
		
		//----------------------------------------------------
		
		//--------------- Barra de Herramientas ------------------
		
		herramientas = new JToolBar(JToolBar.VERTICAL);
		url = Principal.class.getResource("/sanchez/jose/img/marca-x.png");
		Utilidades.addButton(url, herramientas, "Cerrar Pestaña Acrual").addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int seleccion = tPane.getSelectedIndex();
				if(seleccion != -1) {
					//Si existen pestañas abiertas eliminamos la pestaña que tengamos seleccionada
					listScroll.get(tPane.getSelectedIndex()).setRowHeader(null);
					tPane.remove(seleccion);
					listAreaTexto.remove(seleccion);
					listScroll.remove(seleccion);
					listManager.remove(seleccion);
					listFile.remove(seleccion);
					
					contadorPanel--;
					
					if(tPane.getSelectedIndex() == -1) {
						existePanel = false; //Si tPane retorna -1 quiere decir que no exiten paneles creados
					}
				}
			}
			
		});
		
		url = Principal.class.getResource("/sanchez/jose/img/mas (1).png");
		Utilidades.addButton(url, herramientas, "Nuevo Archivo").addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				creaPanel();
			}
			
		});
		
		//--------------------------------------------------------
		
		add(panelMenu, BorderLayout.NORTH);
		add(tPane, BorderLayout.CENTER);
		add(herramientas, BorderLayout.WEST);
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
									Utilidades.aFondo(contadorPanel, tipoFondo, listAreaTexto);
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
			else if(accion.equals("guardar")) {
				elementoItem.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						//Guardar Como si el archivo no existe 
						if(listFile.get(tPane.getSelectedIndex()).getPath().equals("")) {
							JFileChooser guardarArchivos = new JFileChooser();
							int opc = guardarArchivos.showSaveDialog(null);
							
							if(opc == JFileChooser.APPROVE_OPTION) {
								File archivo = guardarArchivos.getSelectedFile();
								listFile.set(tPane.getSelectedIndex(), archivo);
								tPane.setTitleAt(tPane.getSelectedIndex(), archivo.getName());
								
								try {
									FileWriter fw = new FileWriter(listFile.get(tPane.getSelectedIndex()).getPath());
									String texto = listAreaTexto.get(tPane.getSelectedIndex()).getText();

									for(int i = 0; i<texto.length(); i++) {
										fw.write(texto.charAt(i));
									}
									
									fw.close();
									
									
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
								
							}
							
						}
						else {
							try {
								FileWriter fw = new FileWriter(listFile.get(tPane.getSelectedIndex()).getPath());
								String texto = listAreaTexto.get(tPane.getSelectedIndex()).getText();

								for(int i = 0; i<texto.length(); i++) {
									fw.write(texto.charAt(i));
								}
								
								fw.close();
								
								
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
					}
					
				});
			}
			else if(accion.equals("guardarComo")) {
				elementoItem.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						JFileChooser guardarArchivos = new JFileChooser();
						int opc = guardarArchivos.showSaveDialog(null);
						
						if(opc == JFileChooser.APPROVE_OPTION) {
							File archivo = guardarArchivos.getSelectedFile();
							listFile.set(tPane.getSelectedIndex(), archivo);
							tPane.setTitleAt(tPane.getSelectedIndex(), archivo.getName());
							
							try {
								FileWriter fw = new FileWriter(listFile.get(tPane.getSelectedIndex()).getPath());
								String texto = listAreaTexto.get(tPane.getSelectedIndex()).getText();

								for(int i = 0; i<texto.length(); i++) {
									fw.write(texto.charAt(i));
								}
								
								fw.close();
								
								
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							
						}
						
						
					}
					
				});
			}
		}
		else if(menu.equals("editar")) {
			editar.add(elementoItem);
			if(accion.equals("deshacer")) {
				elementoItem.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						if(listManager.get(tPane.getSelectedIndex()).canUndo()) listManager.get(tPane.getSelectedIndex()).undo();
					}
				});
			}
			else if(accion.equals("rehacer")) {
				elementoItem.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						if(listManager.get(tPane.getSelectedIndex()).canRedo()) listManager.get(tPane.getSelectedIndex()).redo();
					}
					
				});
			}
			else if(accion.equals("cortar")) {
				elementoItem.addActionListener(new DefaultEditorKit.CutAction());
			}
			else if(accion.equals("copiar")) {
				elementoItem.addActionListener(new DefaultEditorKit.CopyAction());
			}
			else if(accion.equals("pegar")) {
				elementoItem.addActionListener(new DefaultEditorKit.PasteAction());
			}
			
		}
		else if(menu.equals("seleccion")) {
			seleccion.add(elementoItem);

			if(accion.equals("seleccion")) {
				elementoItem.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						listAreaTexto.get(tPane.getSelectedIndex()).selectAll();
					}

				});
			}

		}
		else if(menu.equals("ver")) {
			ver.add(elementoItem);
			if(accion.equals("numeracion")) {
				elementoItem.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						
						numeracion = !numeracion;
						
						Utilidades.viewNumeracion(contadorPanel, numeracion, listAreaTexto, listScroll);
					}
					
				});
			}
		}
		else if(menu.equals("apariencia")) {
			apariencia.add(elementoItem);
			
			if(accion.equals("normal")) {
				elementoItem.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						tipoFondo="w";
						
						if(tPane.getTabCount() > 0) Utilidades.aFondo(contadorPanel, tipoFondo, listAreaTexto);
					}
					
				});
			}
			else if(accion.equals("dark")) {
				elementoItem.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						tipoFondo = "d";
						
						if(tPane.getTabCount() > 0) Utilidades.aFondo(contadorPanel, tipoFondo, listAreaTexto);
					}
					
				});
			}
			
		}	
	}
	
	public void creaPanel() {
		ventana = new JPanel();
		ventana.setLayout(new BorderLayout());
		listFile.add(new File(""));
		listAreaTexto.add(new JTextPane());
		listScroll.add(new JScrollPane(listAreaTexto.get(contadorPanel)));
		listManager.add(new UndoManager()); //Para rastrear los cambios del Area de texto
		
		listAreaTexto.get(contadorPanel).getDocument().addUndoableEditListener(listManager.get(contadorPanel));
		
		ventana.add(listScroll.get(contadorPanel), BorderLayout.CENTER);
		
		tPane.addTab("title",ventana);
		
		
		Utilidades.viewNumeracionInicio(numeracion, listAreaTexto.get(contadorPanel), listScroll.get(contadorPanel));
		tPane.setSelectedIndex(contadorPanel);
		contadorPanel++;
		Utilidades.aFondo(contadorPanel, tipoFondo, listAreaTexto);
		existePanel = true;
	}
	private static String tipoFondo = "w";
	private boolean numeracion = false;
	private int contadorPanel = 0; //Nos va a contar cuantos paneles se han creado
	private boolean existePanel = false; // nos va a decir si inicialmente existe un panel creado
	private JTabbedPane tPane;
	private JPanel ventana;
	//private JTextPane areaTexto;
	private ArrayList<JTextPane> listAreaTexto;
	private ArrayList<JScrollPane> listScroll;
	private ArrayList<UndoManager> listManager;
	private ArrayList<File> listFile;
	private JMenuBar menu;
	private JMenu archivo, editar, seleccion, ver, apariencia;
	private JMenuItem elementoItem;
	private JToolBar herramientas;
	private URL url;
}


