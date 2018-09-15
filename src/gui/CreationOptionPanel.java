package gui;


import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class CreationOptionPanel extends JPanel implements ActionListener {
	
	public final static String CREATE_MEALY_MACHINE = "Crear Maquina de Mealy";
	public final static String CREATE_MOORE_MACHINE = "Crear Maquina de Moore";
	public final static String EQUIVALENT_REDUCED_MACHINE = "Dar maquina equivalente reducida";
	
	
	private AutomatonFrame  mainFrame;
	
	private JButton createMealyMachine;
	private JButton createMooreMachine;
	private JButton reducedMachine;
	
	
	public CreationOptionPanel(AutomatonFrame frame) {
		mainFrame = frame;
		setLayout( new GridLayout(3,1,5,5) );
		
		createMealyMachine = new JButton(CREATE_MEALY_MACHINE);
		createMealyMachine.setActionCommand(CREATE_MEALY_MACHINE);
		createMealyMachine.addActionListener(this);
		createMealyMachine.setBackground(Color.WHITE);
		add(createMealyMachine);
			
		createMooreMachine = new JButton(CREATE_MOORE_MACHINE);
		createMooreMachine.setActionCommand(CREATE_MOORE_MACHINE);
		createMooreMachine.addActionListener(this);
		createMooreMachine.setBackground(Color.WHITE);
		add(createMooreMachine);
		
		reducedMachine = new JButton(EQUIVALENT_REDUCED_MACHINE);
		reducedMachine.setActionCommand(EQUIVALENT_REDUCED_MACHINE);
		reducedMachine.addActionListener(this);
		reducedMachine.setBackground(Color.WHITE);
		add(reducedMachine);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String comando = e.getActionCommand();
		
		if(comando.equals(CREATE_MEALY_MACHINE)) {
			
			mainFrame.createMealyMachine();
			
		}else if(comando.equals(CREATE_MOORE_MACHINE)) {
			
			mainFrame.createMooreMachine();
			
		}else if(comando.equals(EQUIVALENT_REDUCED_MACHINE)) {
			
			
			mainFrame.reducedMachine();
			
		}
	}

}
