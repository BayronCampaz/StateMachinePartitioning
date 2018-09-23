package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import listaAdyacencia.GrafoListaAdyacencia;
import listaAdyacencia.Pareja;
import listaAdyacencia.Vertice;

public class Automaton {
	
	private GrafoListaAdyacencia graph = new GrafoListaAdyacencia<>(true);
	private boolean isMealy;
	private String[] inputs;
	private String[][] stateTable;
	private String initialState;
	

	public Automaton(boolean isMealy, String[] inputs, String[][] stateTable, String initialState) {
		super();
		this.isMealy = isMealy;
		this.inputs = inputs;
		this.stateTable = stateTable;
		this.initialState = initialState;
	}

	public GrafoListaAdyacencia<String,String> getGraph() {
		return graph;
	}
	

	
	private String[][] combineStatesMoore(ArrayList<ArrayList<String>> partitioning) {
		
		String[][] statesTable = new String[partitioning.size()+1][inputs.length+2];
		String basedName = "|Q";
		
		for(int i = 1; i < statesTable[0].length-1; i++ ) {
			statesTable[0][i] = inputs[i-1];
		}
		
		for(int i = 1; i < statesTable.length; i++ ) {
			statesTable[i][0] = basedName + i + "|";
		}
		
		int k = 2;
		for(ArrayList partition : partitioning) {
			
			if(partition.contains(initialState)) {			
				statesTable[1][statesTable[0].length-1] = (String) graph.getVertice(initialState).getValor();			
				partition.add("|Q1|");
			}
			else {
				String rename ="|Q"+ k + "|"; 
				partition.add(rename);
				k++;		
			}		
		}
		
		int l = 1;
		for(ArrayList<String> partition : partitioning) {
			
			ArrayList<Pareja> adjacents =  graph.getVertice(partition.get(0)).getAdy();
			for(int i = 0; i < adjacents.size(); i++) {
				for(int j = 1; j< statesTable[0].length-1; j++)
				 if(statesTable[0][j].equals(adjacents.get(i).getID())){
					 
					 boolean ended = false;
					 for(int p = 0; p<partitioning.size() && !ended; p++) {
						 if(partitioning.get(p).contains(adjacents.get(i).getVertice().getKey())) {
							 ended = true;
							 statesTable[l][j] = (String) partitioning.get(p).get(partitioning.get(p).size()-1);
							 statesTable[l][statesTable[0].length-1] = (String) graph.getVertice(partition.get(0)).getValor();
						 }
					 }					 
				 }
			}
			l++;
		}
		
		return statesTable;
	}
	
	private String[][] combineStatesMealy(ArrayList<ArrayList<String>> partitioning) {
		String[][] statesTable = new String[partitioning.size()][inputs.length];
		return statesTable;
	}

	
	public String[][] getMinimumConnectedAutomaton(){
		
		String[][] response = null;
		
		fillGraph(stateTable, isMealy);	
		connectedStateMachine();
		
		
		//partitioning();
		//Para cuestiones de ejemplificacion
		
		/*ArrayList<ArrayList<String>> partitioning = new ArrayList<ArrayList<String>>();
		
		ArrayList<String> p1 = new ArrayList<String>(); p1.add("A"); p1.add("C");
		ArrayList<String> p2 = new ArrayList<String>(); p2.add("B"); p2.add("D");
		ArrayList<String> p3 = new ArrayList<String>(); p3.add("E"); p3.add("F"); p3.add("G");

		
		partitioning.add(p1);
		partitioning.add(p2);
		partitioning.add(p3);

		*/
		

		//AQUI SE LLAMA AL PARTICIONAMIENTO Y SE LE ENVIA A COMBINE STATES ya a sea MOORE o MEALY
	//	response = combineStatesMoore(partitioning);
		
	/*	for (int x=0; x < response.length; x++) {
			  System.out.print("|");
			  for (int y=0; y < response[x].length; y++) {
			    System.out.print (response[x][y]);
			    if (y!=response[x].length-1) System.out.print("\t");
			  }
			  System.out.println("|");
			}*/
		
		return response;
		
	}
	
	public ArrayList<ArrayList<String>> partitioning () {
		
		ArrayList<ArrayList<String>> response = new ArrayList<ArrayList<String>>();
		
		HashMap vertices = graph.getVerticesMap();
		Set keys1 = vertices.keySet();
		ArrayList keys = new ArrayList<>();
		keys.addAll(keys1);
		
		ArrayList<String> diferentsResponses = new ArrayList<String>();
		
		for(int i = 0; i < vertices.size(); i++) {					
			Vertice v = graph.getVertice(keys.get(i));
			if(!diferentsResponses.contains(v.getValor())) {
				ArrayList<String> newPartition = new ArrayList<String>();
				newPartition.add((String)v.getKey());
				response.add(newPartition);
				diferentsResponses.add((String) v.getValor());
			}else {
				
				for(int j = 0; j< response.size(); j++) {										
						if(graph.getVertice(response.get(j).get(0)).getValor().equals(v.getValor())){
							response.get(j).add((String)v.getKey());
						}		
				}
			}	
		}	
		
		boolean change = true;
		
		while (change) {
			
			change = false;	
			
			ArrayList<ArrayList<String>> partitions = response;
			
		    //Recorrido para cada particion
			for(int i = 0; i< response.size(); i++) {
				
				ArrayList<String> partition = response.get(i);
				ArrayList<String> recentlyRemoved = new ArrayList<String>();
				
		       	
				Vertice vertex1 = graph.getVertice(partition.get(0));
				// Recorrrido para cada vertice	
				for(int j = 1; j < partition.size(); j++) {
					
					
					// Recorrido para hacer comparacion para cada simbolo de entrada
					for(int k = 0; k< vertex1.getAdy().size(); k++) {
						
						Vertice vertex2 = graph.getVertice(partition.get(j));
						
						Pareja pair1 = (Pareja)  vertex1.getAdy().get(k);
						Pareja pair2 = (Pareja)  vertex2.getAdy().get(k);

						boolean end = false;
						// Recorrido para encontrar en que grupo esta su adyacente enlazado a determinado simbolo de entrada
						for(int l = 0; l<response.size() && !end; l++) {
							if(response.get(l).contains(pair1.getVertice().getKey())) {
								end = true;
								if(!response.get(l).contains(pair2.getVertice().getKey())) {
									change = true;
									recentlyRemoved.add((String)vertex2.getKey());
									partitions.get(i).remove((String)vertex2.getKey());
								}
							}
						}
					}
								
				}
				
				//Hacer codigo aqui para el manejo de los que removi y estan en los eliminados recientemente
				ArrayList<ArrayList<String>> toAdd = new ArrayList<ArrayList<String>>();
				
				while(!recentlyRemoved.isEmpty()) {
					
				ArrayList<String> newPartition = new ArrayList<String>();
				Vertice vertex3 = graph.getVertice(recentlyRemoved.get(0));
				newPartition.add(recentlyRemoved.get(0));
				recentlyRemoved.remove(0);
				
				for(int m = 1; m < recentlyRemoved.size(); m++) {
					
					
					boolean areDifferentsPartitions = false;
					// Recorrido para hacer comparacion para cada simbolo de entrada entre los que quite
					for(int k = 0; k < vertex3.getAdy().size(); k++) {
						
						Vertice vertex4 = graph.getVertice(recentlyRemoved.get(m));
						
						Pareja pair1 = (Pareja)  vertex3.getAdy().get(k);
						Pareja pair2 = (Pareja)  vertex4.getAdy().get(k);

						boolean end = false;
						// Recorrido para encontrar en que grupo esta su adyacente enlazado a determinado simbolo de entrada
						for(int l = 0; l<response.size() && !end; l++) {
							if(response.get(l).contains(pair1.getVertice().getKey())) {
								end = true;
								if(!response.get(l).contains(pair2.getVertice().getKey())) {
									change = true;
									areDifferentsPartitions = true;									
									
								}
							}
						}
						
					}
					
					if(!areDifferentsPartitions) {
						newPartition.add(recentlyRemoved.get(m));
						recentlyRemoved.remove(recentlyRemoved.get(m));
					}else {
						toAdd.add(newPartition);
					}
					
				}
				
				
				
				}
				partitions.addAll(toAdd);
			}
			
			response = partitions;
		}		
		return response;
	}
	

	
	public void connectedStateMachine() {
		
		ArrayList<String> connectedVertices =  (ArrayList<String>) graph.BFS2(initialState);

		HashMap vertices = graph.getVerticesMap();
		Set keys1 = vertices.keySet();
		ArrayList keys = new ArrayList<>();
		keys.addAll(keys1);
					
		for(int i = 0; i < vertices.size(); i++) {		
			
			Vertice v = graph.getVertice(keys.get(i));
			if(!connectedVertices.contains(v.getKey())) {
				
				graph.removerVertice(v.getKey());
			}
		}
		
	}
	

	public void fillGraph(String[][] matrixInf, boolean isMealy) {
		if (isMealy) {
			fillMealy(matrixInf);
		}else {
			fillMoore(matrixInf);
		}
	}

	public void fillMealy(String[][] matrixInf) {
		
		for (int i = 1; i < matrixInf.length; i++) {
			
			String response = "";
			for(int j = 1; j < matrixInf[0].length && i != 0 ; j++) {
				response += matrixInf[i][j].split(",")[1];
				if(!(j == matrixInf[0].length-1)) {
					response += ",";
				}
			}			
			Vertice<String, String> vtoAdd = new Vertice<String,String>(matrixInf[i][0]);
			vtoAdd.setValor(response);
			graph.anadirVertice(vtoAdd);
		}
		
		for (int i = 1; i < matrixInf.length; i++) {
			for (int j = 1; j < matrixInf[0].length; j++) {
				
				String anotherVertex = matrixInf[i][j].split(",")[0];
				graph.anadirArista1(matrixInf[i][0], anotherVertex, 0, matrixInf[0][j]);
				
			}		
		}		

	}

	public void fillMoore(String[][] matrixInf) {
		ArrayList transitions = new ArrayList<>();
		ArrayList state = new ArrayList<>();
		ArrayList answer = new ArrayList<>();

		for (int i = 0; i < matrixInf[0].length - 1; i++) {

			transitions.add(matrixInf[0][i]);
		}

		for (int i = 0; i < matrixInf.length; i++) {

			state.add(matrixInf[i][0]);
			answer.add(matrixInf[i][matrixInf[0].length - 1]);
		}

		boolean flat = true;
		for (int i = 1; i < matrixInf.length; i++) {

			String nameState = (String) state.get(i);
			Vertice<String, String> vtoAdd = new Vertice<String, String>(nameState);
			vtoAdd.setValor(answer.get(i) + "");
			if (flat) {
				//vtoAdd.setInitial(true);
				flat = false;
			}
			graph.anadirVertice(vtoAdd);

		}

		for (int i = 1; i < matrixInf.length; i++) {
			for (int j = 1; j < matrixInf[0].length - 1; j++) {
				String nameState = (String) state.get(i);

				graph.anadirArista1(nameState, matrixInf[i][j], 0, matrixInf[0][j]);
				
			}

		}
	}

	public boolean isMealy() {
		return isMealy;
	}

	public void setMealy(boolean isMealy) {
		this.isMealy = isMealy;
	}

	public String[] getInputs() {
		return inputs;
	}

	public void setInputs(String[] inputs) {
		this.inputs = inputs;
	}

	public String[][] getStateTable() {
		return stateTable;
	}

	public void setStateTable(String[][] stateTable) {
		this.stateTable = stateTable;
	}

	public String getInitialState() {
		return initialState;
	}

	public void setInitialState(String initialState) {
		this.initialState = initialState;
	}

	public void setGraph(GrafoListaAdyacencia graph) {
		this.graph = graph;
	}
	
	

}
