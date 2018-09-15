package model;

import java.util.ArrayList;

import listaAdyacencia.GrafoListaAdyacencia;
import listaAdyacencia.Vertice;

public class Automaton {
	GrafoListaAdyacencia graph = new GrafoListaAdyacencia<>(true);

	public GrafoListaAdyacencia getGraph() {
		return graph;
	}

	public void fillGraph(String[][] matrixInf, boolean isMealy) {
		if (isMealy) {
			fillMealy(matrixInf);
		}else {
			fillMoore(matrixInf);
		}
	}

	public void fillMealy(String[][] matrixInf) {
		ArrayList transitions = new ArrayList<>();
		ArrayList state = new ArrayList<>();
		for (int i = 0; i < matrixInf[0].length; i++) {

			transitions.add(matrixInf[0][i]);
		}

		for (int i = 0; i < matrixInf.length; i++) {

			state.add(matrixInf[i][0]);
		}

		for (int i = 1; i < matrixInf.length; i++) {
			for (int j = 1; j < matrixInf[0].length; j++) {
				String nameState = (String) state.get(i) + transitions.get(j);
				Vertice<String, String> vtoAdd = new Vertice<String, String>(nameState);
				vtoAdd.setValor(transitions.get(j) + "");
				graph.anadirVertice(vtoAdd);

			}
		}

		for (int i = 1; i < matrixInf.length; i++) {
			for (int j = 1; j < matrixInf[0].length; j++) {
				String nameState = (String) state.get(i) + transitions.get(j);
				for (int k = 1; k < matrixInf[0].length; k++) {
					String test = matrixInf[i][k];
					graph.anadirArista1(nameState, matrixInf[i][k],0, matrixInf[0][k]);
				}

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

		for (int i = 1; i < matrixInf.length; i++) {
			
				String nameState = (String) state.get(i);
				Vertice<String, String> vtoAdd = new Vertice<String, String>(nameState);
				vtoAdd.setValor(answer.get(i) + "");
				graph.anadirVertice(vtoAdd);

			
		}

		for (int i = 1; i < matrixInf.length; i++) {
			for (int j = 1; j < matrixInf[0].length - 1; j++) {
				String nameState = (String) state.get(i);

				graph.anadirArista1(nameState, matrixInf[i][j],0, matrixInf[0][j]);
			}

		}
	}
	
	public void connectedStateMachine() {
		
		//Supongo que el grafo ya esta lleno
		
		
	}
	

	
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
