package pruebas;

//import matrizAdyacencia.Grafo;
import model.Automaton;

public class Tests {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String[][] mTest = { {" ", "a"} , {"AC" , "BC1"}, { "BC" , "B1" }, {"CC", "A0"}};
		
		Automaton automaton = new Automaton();
		automaton.fillMealy(mTest);
		String[][] mRepresentation = null;
		//automaton.getGraph().darRepresentacionMatriz2();
		
		for (int x=0; x < mRepresentation.length; x++) {
			  System.out.print("|");
			  for (int y=0; y < mRepresentation[x].length; y++) {
			    System.out.print (mRepresentation[x][y]);
			    if (y != mRepresentation[x].length-1) System.out.print("\t");
			  }
			  System.out.println("|");
			}
		
	/*	for(int i = 0; i<mRepresentation.length; i++) {
			for(int j = 0; j<mRepresentation[0].length; j++){
				
				System.out.print
			}
		}*/
	}

}
