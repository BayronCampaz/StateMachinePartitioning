package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Menu;
import java.awt.MenuBar;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import model.Automaton;

public class AutomatonFrame extends JFrame {
	
	private ImagePanel imagePanel;
	private CreationOptionPanel creationOptionPanel;
	private CreationPanel creationPanel;
	private Automaton automaton;
	private boolean currentMachine; //True=Mealy - False = Moore
	
	
	public AutomatonFrame() {
		
		setTitle( "Automata Minimo Equivalente" );
        setSize( new Dimension(1100, 750 ));
        automaton = new Automaton();
        
        //Color JFrame
     //   this.getContentPane().setBackground(Color.WHITE);

      
        setResizable( false);
        setDefaultCloseOperation( EXIT_ON_CLOSE );
        setLayout( new BorderLayout( ) );
        
        imagePanel = new ImagePanel();
        creationOptionPanel = new CreationOptionPanel(this);
        creationPanel = new CreationPanel();
        
        //Color JPanel
    //    creationPanel.setBackground(Color.WHITE);
    //    creationOptionPanel.setBackground(Color.WHITE);
        
        add(imagePanel, BorderLayout.NORTH);
        
        JPanel auxPanel = new JPanel();
        auxPanel.setLayout(new BorderLayout());
        auxPanel.add(creationOptionPanel, BorderLayout.NORTH);
        auxPanel.add(creationPanel, BorderLayout.CENTER);
        
        JPanel auxPanel2 = new JPanel();
        auxPanel2.setLayout(new GridLayout(1,2));
        auxPanel2.add(auxPanel);
        auxPanel2.add(new JLabel(""));
        
        add(auxPanel2, BorderLayout.CENTER);

	}
	
	public void createMealyMachine() {
		
		try {
		int rows = Integer.parseInt(JOptionPane.showInputDialog
		(null, "Digite el numero de estados", "Numero de estados", JOptionPane.INFORMATION_MESSAGE));
		
		int columns = Integer.parseInt(JOptionPane.showInputDialog
		(null, "Digite el numero de simbolos del alfabeto de entrada", "Numero de estados", JOptionPane.INFORMATION_MESSAGE));
		
		if (rows > 15 || columns > 15 ) {
			JOptionPane.showMessageDialog(null, "El maximo de estados y simbolos del alfabeto es 15 ", "Error", JOptionPane.ERROR_MESSAGE);
		}else {
		creationPanel.createJTextFieldMatrix(rows+1, columns+1, false);
		validate();
		currentMachine = true;
		}
		
		} catch(NumberFormatException e){
			JOptionPane.showMessageDialog(null, "Debe pasar un numero entero ", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void createMooreMachine() {
		
		try {
		int rows = Integer.parseInt(JOptionPane.showInputDialog
		(null, "Digite el numero de estados", "Numero de estados", JOptionPane.INFORMATION_MESSAGE));
		
		int columns = Integer.parseInt(JOptionPane.showInputDialog
		(null, "Digite el numero de simbolos del alfabeto de salida", "Numero de estados", JOptionPane.INFORMATION_MESSAGE));
		
		if (rows > 15 || columns > 15 ) {
			JOptionPane.showMessageDialog(null, "El maximo de estados y simbolos del alfabeto es 15 ", "Error", JOptionPane.ERROR_MESSAGE);
		}else {
		creationPanel.createJTextFieldMatrix(rows + 1, columns + 2, true);
		validate(); 
		currentMachine = false;
		}
		
		} catch(NumberFormatException e){
		     JOptionPane.showMessageDialog(null, "Debe pasar un numero entero ", "Error", JOptionPane.ERROR_MESSAGE);
	}
		
	}
	
	public void reducedMachine ()   {
		
		try {
		
			String initialState = creationPanel.getInitialState();
			String[][] matrix = creationPanel.getMatrix();
			
			//Conexion con clase principal debe devolver una matriz 
			//Hay que ver a cual maquina hay que enviar
			automaton.fillGraph(matrix, currentMachine);
			// Luego pasar esa matriz al panel encargado de mostrarlo
			
			
		} catch (EmptyFieldException e) {
			
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			
		} catch (NotFoundInitialStateException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		JFrame mainAutomatonFrame = new AutomatonFrame();
		mainAutomatonFrame.setVisible(true);
	}

}
