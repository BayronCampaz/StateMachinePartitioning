package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import listaAdyacencia.GrafoListaAdyacencia;
import listaAdyacencia.Pareja;
import listaAdyacencia.Vertice;

public class main {

	public static void main(String[] args) {
		Automaton aut = new Automaton();

	String[][] matrix = new String[4][4];
//		matrix[0][0] = "@";
//		matrix[0][1] = "0";
//		matrix[0][2] = "1";
//		matrix[0][3] = "2";
//		matrix[1][0] = "A";
//		matrix[1][1] = "A1";
//		matrix[1][2] = "B1";
//		matrix[1][3] = "C1";
//		matrix[2][0] = "B";
//		matrix[2][1] = "C1";
//		matrix[2][2] = "D0";
//		matrix[2][3] = "A1";
//		matrix[3][0] = "C";
//		matrix[3][1] = "B0";
//		matrix[3][2] = "C0";
//		matrix[3][3] = "B1";
//		matrix[4][0] = "D";
//		matrix[4][1] = "D1";
//		matrix[4][2] = "A1";
//		matrix[4][3] = "D0";
//		aut.fillMealy(matrix);

		 matrix[0][0] = "@";
		 matrix[0][1] = "0";
		 matrix[0][2] = "1";
		 matrix[0][3] = "@";
		
		 matrix[1][0] = "A";
		 matrix[1][1] = "B";
		 matrix[1][2] = "C";
		 matrix[1][3] = "0";
		
		 matrix[2][0] = "B";
		 matrix[2][1] = "C";
		 matrix[2][2] = "B";
		 matrix[2][3] = "1";
		
		 matrix[3][0] = "C";
		 matrix[3][1] = "A";
		 matrix[3][2] = "C";
		 matrix[3][3] = "0";
		
		 aut.fillMoore(matrix);

		GrafoListaAdyacencia graph = new GrafoListaAdyacencia<>(true);
		graph = aut.getGraph();
		HashMap vertices = graph.getVerticesMap();
		Set keys1 = vertices.keySet();
		ArrayList keys = new ArrayList<>();
		keys.addAll(keys1);
		for (int i = 0; i < vertices.size(); i++) {
			Vertice v = graph.getVertice(keys.get(i));
			ArrayList list = v.getAdy();
			System.out.print(v.getKey() +""+ v.getValor() + ":" + " ");
			for (int j = 0; j < list.size(); j++) {
				String keyNeig = (String) (((Pareja) list.get(j)).getVertice()).getKey();
				String transition= ((Pareja) list.get(j)).getID()+"";
				System.out.print(keyNeig + transition+" ");
			}
			System.out.println();
		}
	}

}
