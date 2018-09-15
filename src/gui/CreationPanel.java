package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

public class CreationPanel extends JPanel  {	

	private JTextField[][] matrix;
	private JLabel lbInitialState;
	private JTextField txInitialState;
	private JPanel panelMatrix;
	
	public CreationPanel( ) {
		
		setLayout( new BorderLayout(10,10) );
        //Establece el tamaño del layout  
		 setPreferredSize( new Dimension( 0, 200) );
		 
		//Adiciona un marco con titulo
	      
	      TitledBorder border = BorderFactory.createTitledBorder( "Crear automata" );
	      setBorder( border );
	        
	      lbInitialState = new JLabel("Ingrese el estado inicial");
	      txInitialState = new JTextField();
	      txInitialState.setMinimumSize(new Dimension(10,50));
	      
	      JPanel auxPanel = new JPanel();
	      auxPanel.setLayout(new GridLayout(1,2,10,10));
	      auxPanel.add(lbInitialState);
	      auxPanel.add(txInitialState);
	      
	      
	      add(auxPanel, BorderLayout.NORTH);
	}
	
	
	//Hacer excepciones aqui
	
	public String[][] getMatrix() throws EmptyFieldException, NotFoundInitialStateException {
		
		String[][] matrixStrings = new String[matrix.length][matrix[0].length];
		
		//Se verifica que el estado inicial este en la matriz
		boolean found = false;
		for(int i = 0; i< matrixStrings.length && !found ; i++) {
			if(matrix[i][0].getText().equals(getInitialState())) {
				found = true;
			}
		}
		if(!found) {
			throw new NotFoundInitialStateException("No se encuentra el estado inicial dentro de la matriz"); 			
		}
		
		//Ingresa los datos de la matriz de JTexField a una matrixz de Strings
		for(int i = 0; i< matrix.length; i++) {
			for(int j = 0; j < matrix[0].length; j++) {
				
				if(matrix[i][j].getText().length() == 0 && !(i == 0 && j == 0) && !(i==0 && j == matrix[0].length-1)) {
					throw new EmptyFieldException("No pueden haber campos vacios en la matriz ");
				}
				matrixStrings[i][j] = matrix[i][j].getText();
			}
		}
		
		return matrixStrings;
	}
	
	public String getInitialState() throws EmptyFieldException {
		
		String initialState = txInitialState.getText();
		
		if(!initialState.isEmpty() && !initialState.contains(" ")) {
			return initialState;
		}else {
			throw new EmptyFieldException("Indique un estado inicial");
		}
		
	}
	
	public void createJTextFieldMatrix(int rows, int columns, boolean moore) {
		
		if(panelMatrix != null) {
			remove(panelMatrix);
		}
		
		panelMatrix = new JPanel();
		panelMatrix.setLayout(new GridLayout(rows,columns,10 , 10));
		
		matrix = new JTextField[rows][columns];
		
		for(int i = 0; i< matrix.length; i++) {
			for(int j = 0; j < matrix[0].length; j++) {	
				matrix[i][j] = new JTextField();
				panelMatrix.add(matrix[i][j]);					
			}
		}
		matrix[0][0].setEnabled(false);
		matrix[0][0].setVisible(false);
		
		if(moore) {
			matrix[0][matrix[0].length-1].setEnabled(false);
			matrix[0][matrix[0].length-1].setVisible(false);;
		}
		
		add(panelMatrix, BorderLayout.CENTER);
	}



}
