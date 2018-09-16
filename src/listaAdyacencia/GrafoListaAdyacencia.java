package listaAdyacencia;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

import javax.naming.ldap.HasControls;

public class GrafoListaAdyacencia<K, V> implements InterfazGrafo<K, V> {
	public static final double INFINITO = 10000000.0;
	// Sirve para el DFS
	int tiempo;
	
	private HashMap<K, V> verticesMap;
	private boolean dirigido;
	private static ArrayList a;
	/**
	 * Crea un nuevo grafa
	 * @param dirigido-saber si es o no dirigido
	 */

	public GrafoListaAdyacencia(boolean dirigido) {
		verticesMap = new HashMap<K, V>();
		this.dirigido = dirigido;
		tiempo = 0;
	}

	@Override
	/**
	 * Anade un vertice al grafo
	 */
	public void anadirVertice(V v) {
		
		verticesMap.put((K) ((Vertice) v).getKey(), v);
	}

	
	@Override
	/**
	 * Anade una nueva arista al grafo
	 *  Le entra por parametro los hashCode de del valor (que es tipo T)
	 *  key1 sera el vertice de origen y key2 el vertice de llegada
	 *   
	 */
	public void anadirArista(K key1, K key2, double peso) {
		if (dirigido) {
			((Vertice) verticesMap.get(key1)).addPareja((Vertice) verticesMap.get(key2), peso);
		} else {
			((Vertice) verticesMap.get(key1)).addPareja((Vertice) verticesMap.get(key2), peso);
			((Vertice) verticesMap.get(key2)).addPareja((Vertice) verticesMap.get(key1), peso);
		}
	}
	/**
	 * Inserta una nueva arista en el grafo
	 * @param key1-clave de vertice de origen
	 * @param key2-clave de vertice de llegada
	 * @param peso-peso de la arista
	 * @param ruta-Nombre de la arista
	 */
	public void anadirArista1(K key1, K key2, double peso, String ruta) {
		if (dirigido) {
			((Vertice) verticesMap.get(key1)).addPareja1((Vertice) verticesMap.get(key2), peso, ruta);
		} else {
			((Vertice) verticesMap.get(key1)).addPareja1((Vertice) verticesMap.get(key2), peso, ruta);
			((Vertice) verticesMap.get(key2)).addPareja1((Vertice) verticesMap.get(key1), peso, ruta);
		}
	}
	// Por parametro entra el hasCode del valor(que es tipo T)
	@Override
	public void removerVertice(K key) {
		if (dirigido) {
			verticesMap.remove(key);
		} else {
			Vertice vAEliminar = (Vertice) verticesMap.get(key);
			ArrayList<Pareja> parejasVAEliminar = vAEliminar.getAdy();
			for (int i = 0; i < parejasVAEliminar.size(); i++) {
				Vertice vecino = parejasVAEliminar.get(i).getVertice();
				ArrayList<Pareja> parejasVecino = vecino.getAdy();
				for (int j = 0; j < parejasVecino.size(); j++) {
					if (parejasVecino.get(j).getVertice().equals(vAEliminar)) {
						parejasVecino.remove(j);
					}
				}
			}
			verticesMap.remove(key);
		}
	}

	/**
	 * Elimina una arista del grafo dada su llave de origen y llegada
	 */
	@Override
	public void removerArista(K key, K key2) {
		ArrayList<Pareja> parejas = ((Vertice) verticesMap.get(key)).getAdy();
		for (int i = 0; i < parejas.size(); i++) {
			if (parejas.get(i).getVertice().equals(verticesMap.get(key2))) {
				parejas.remove(i);
			}
		}
		if (!dirigido) {
			ArrayList<Pareja> parejasW = ((Vertice) verticesMap.get(key2)).getAdy();
			for (int i = 0; i < parejasW.size(); i++) {
				if (parejasW.get(i).getVertice().equals(verticesMap.get(key))) {
					parejasW.remove(i);
				}
			}
		}
	}
/**
 * Devuelve un vertice  del grafo
 * @param key-clave del vertice
 * @return Vertice
 */
	public Vertice getVertice(K key) {
		return (Vertice) verticesMap.get(key);
	}
	

	
	/**
	 * Realiza el recorrido por amplitud
	 * @param keyInicial-clave del vertice de inicio
	 */

	public void BFS(K keyInicial) {
		@SuppressWarnings("unchecked")
		K[] listaClaves = (K[]) verticesMap.keySet().toArray();
		for (int i = 0; i < listaClaves.length; i++) {
			((Vertice) verticesMap.get(listaClaves[i])).setColor(Vertice.WHITE);
			((Vertice) verticesMap.get(listaClaves[i])).setDistancia(Vertice.INFINITO);
			((Vertice) verticesMap.get(listaClaves[i])).setPadre(null);
		}
		Vertice vertInicial = (Vertice) verticesMap.get(keyInicial);
		vertInicial.setColor(Vertice.GRAY);
		vertInicial.setDistancia(0);

		Queue<Vertice> cola = new LinkedList<>();
		cola.offer(vertInicial);

		while (!cola.isEmpty()) {
			Vertice verActual = cola.poll();
			ArrayList<Pareja> listaAdya = verActual.getAdy();
			for (int i = 0; i < listaAdya.size(); i++) {
				Vertice verticeAdy = listaAdya.get(i).getVertice();
				if (verticeAdy.getColor() == Vertice.WHITE) {
					verticeAdy.setColor(Vertice.GRAY);
					verticeAdy.setDistancia(verActual.getDistancia() + 1);
					verticeAdy.setPadre(verActual);
					cola.offer(verticeAdy);
				}
			}
			verActual.setColor(Vertice.BLACK);
		}
	}
	
	/**
	 * Realiza el recorrido por amplitud
	 * @param keyInicial-clave del vertice de inicio
	 */

	public ArrayList<K> BFS2(K keyInicial) {
		@SuppressWarnings("unchecked")
		K[] listaClaves = (K[]) verticesMap.keySet().toArray();
		for (int i = 0; i < listaClaves.length; i++) {
			((Vertice) verticesMap.get(listaClaves[i])).setColor(Vertice.WHITE);
			((Vertice) verticesMap.get(listaClaves[i])).setDistancia(Vertice.INFINITO);
			((Vertice) verticesMap.get(listaClaves[i])).setPadre(null);
		}
		Vertice vertInicial = (Vertice) verticesMap.get(keyInicial);
		vertInicial.setColor(Vertice.GRAY);
		vertInicial.setDistancia(0);

		Queue<Vertice> cola = new LinkedList<>();
		cola.offer(vertInicial);

		ArrayList response = new ArrayList();
		response.add(vertInicial.getKey());
		
		while (!cola.isEmpty()) {
			Vertice verActual = cola.poll();
			ArrayList<Pareja> listaAdya = verActual.getAdy();
			for (int i = 0; i < listaAdya.size(); i++) {
				Vertice verticeAdy = listaAdya.get(i).getVertice();
				if (verticeAdy.getColor() == Vertice.WHITE) {
					verticeAdy.setColor(Vertice.GRAY);
					response.add(verticeAdy.getKey());
					verticeAdy.setDistancia(verActual.getDistancia() + 1);
					verticeAdy.setPadre(verActual);
					cola.offer(verticeAdy);
				}
			}
			verActual.setColor(Vertice.BLACK);
		}
		
		return response;
	}
	
	
    /**
     * Devuelve el camino mas corte entre dos vertices
     * @param inicio-vertice de inicio
     * @param llegada-Vertice de llegada
     */
	
	public void imprimirCaminoMasCorto(K inicio, K llegada) {
		a = new ArrayList();
		if (llegada == inicio) {
			
		} else if (((Vertice) verticesMap.get(llegada)).getPadre() == null) {

		} else
			imprimirCaminoMasCorto(inicio, (K) ((Vertice) verticesMap.get(llegada)).getPadre().getKey());
		a.add(llegada);
		
	}
	/**
	 * Realiza el recorrido en profundidad
	 */

	public void DFS() {
		K[] listaClaves = (K[]) verticesMap.keySet().toArray();
		for (int i = 0; i < listaClaves.length; i++) {
			((Vertice) verticesMap.get(listaClaves[i])).setColor(Vertice.WHITE);
			((Vertice) verticesMap.get(listaClaves[i])).setPadre(null);
		}
		tiempo = 0;
		for (int i = 0; i < listaClaves.length; i++) {
			Vertice vertInicial = ((Vertice) verticesMap.get(listaClaves[i]));
			if (vertInicial.getColor() == Vertice.WHITE) {
				DFSVisit((V) vertInicial);
			}
		}
	}

	/**
	 * Recorrido auxiliar del metodo Dfs
	 * @param vertice-Vertice inicial
	 */
	public void DFSVisit(V vertice) {
		tiempo = tiempo + 1;
		((Vertice) vertice).setDistancia(tiempo);
		((Vertice) vertice).setColor(Vertice.GRAY);

		ArrayList<Pareja> listaAdya = ((Vertice) vertice).getAdy();
		for (int i = 0; i < listaAdya.size(); i++) {
			Vertice verticeAdy = listaAdya.get(i).getVertice();
			if (verticeAdy.getColor() == Vertice.WHITE) {
				verticeAdy.setPadre((Vertice) vertice);
				DFSVisit((V) verticeAdy);
			}
		}
		((Vertice) vertice).setColor(Vertice.BLACK);
		tiempo = tiempo + 1;
		((Vertice) vertice).setDistanciaFinal(tiempo);
	}

	/**
	 * Camino mas corto y longitud minima entre un vertice y los otros 
	 * @param keyInicial-clave del vertice qeu se debe encontrar longitud minima
	 * Ingresa el key del vertice desde donde quiero empezar . Uso un arrayList de
	   ArrayList en donde hay dos cosas 1) el peso mas pequeno de un vertice al
	   vertice inicial, 2) la clave del vertice al que corresponde ese peso.
	 * @return Devuelve Camino mas corto y longitud minima entre un nodo y todos 
	 */
	public ArrayList Dijkstra(K keyInicial) {
		K[] listaClaves = (K[]) verticesMap.keySet().toArray();
		ArrayList<ArrayList> L = new ArrayList<ArrayList>();

		for (int i = 0; i < listaClaves.length; i++) {
			ArrayList<String> interno = new ArrayList<String>();
			K llaveActual = listaClaves[i];
			if (llaveActual.equals(keyInicial)) {
				interno.add(0 + "");
			} else
				interno.add(Integer.MAX_VALUE + "");
			interno.add(llaveActual + "");
			interno.add(llaveActual + "");
			interno.add("ruta");
			interno.add("pesoRuta");
			L.add(interno);
		}
		ArrayList S = new ArrayList<>();
		int contador = 0;

		while (contador != listaClaves.length) {
			int posicL = -1;
			K u = null;
			int menor = Integer.MAX_VALUE;
			for (int i = 0; i < listaClaves.length; i++) {
				boolean estaEnS = false;
				K claveActualEnL = ((K) L.get(i).get(1));
				// Verifico que la llave que voy a comparar no exista en el conjunto S
				for (int j = 0; j < S.size(); j++) {
					K claveActualEnS = ((K) S.get(j));
					if (claveActualEnL.equals(claveActualEnS)) {
						estaEnS = true;
					}
				}
				// Sí no estaba en el conjunto S entonces lo comparo para saber si es menor
				if (!estaEnS) {
					int actual = Integer.parseInt((String) L.get(i).get(0));
					if (actual < menor) {
						u = (K) L.get(i).get(1);
						posicL = i;
						menor = actual;
					}
				}
			}
			S.add(u);
			contador++;
			for (int i = 0; i < listaClaves.length; i++) {
				boolean estaEnS = false;
				K claveActualEnL = ((K) L.get(i).get(1));
				// Verifico que la llave que voy a comparar no exista en el conjunto S
				for (int j = 0; j < S.size(); j++) {
					K claveActualEnS = (K) S.get(j);
					if (claveActualEnL.equals(claveActualEnS)) {
						estaEnS = true;
					}
				}
				if (!estaEnS) {
					int numeroVecinos = 0;
					ArrayList adyacentes = null;
					Vertice verticeU = (Vertice) verticesMap.get(u);
					if (verticeU.getAdy() != null) {
						adyacentes = verticeU.getAdy();
						numeroVecinos = adyacentes.size();

					}

					for (int j = 0; j < numeroVecinos; j++) {
						// Peso de esa ruta
						int menorPesoAV = Integer.MAX_VALUE;
						// Para el problema
						String ruta = null;
						K vecino = null;
						vecino = (K) ((Pareja) adyacentes.get(j)).getVertice().getKey();
						
						for (int j2 = 0; j2 < numeroVecinos ; j2++) {
							K actual = (K) ((Pareja) adyacentes.get(j2)).getVertice().getKey();
							if (actual.equals(vecino)) {
								
								int pesoActual = (int) ((Pareja) adyacentes.get(j2)).getPeso();
								if (pesoActual < menorPesoAV) {
									menorPesoAV = pesoActual;
									ruta = (String) ((Pareja) adyacentes.get(j2)).getID();
									
								}
							}
						}
						int posicionV = -1;
						boolean encontrado1 = false;
						for (int k = 0; k < L.size() && !encontrado1; k++) {
							K claveActual = (K) L.get(k).get(1);
							if (claveActual.equals(vecino)) {
								posicionV = k;
								encontrado1 = true;
							}
						}
						double valorUEnL = ((Integer.parseInt((String) L.get(posicL).get(0))));
						double valorVEnL = Integer.parseInt((String) L.get(posicionV).get(0));

						if (posicL != -1 && (valorUEnL + menorPesoAV) < valorVEnL) {
							int nuevo = (int) (valorUEnL + menorPesoAV);
							ArrayList arregloV = L.get(posicionV);
							String textoAConcatenar = (String) L.get(posicL).get(2) + ","
									+ (String) L.get(posicionV).get(1);

							arregloV.set(0, (((nuevo + ""))));
							arregloV.set(2, textoAConcatenar);
							arregloV.set(3, ruta);
							arregloV.set(4, menorPesoAV + "");
						}
					}
				}
			}
		}
		return L;
	}
   
	public ArrayList Dijkstra1(K keyInicial) {
		K[] listaClaves = (K[]) verticesMap.keySet().toArray();
		ArrayList<ArrayList> L = new ArrayList<ArrayList>();
		for (int i = 0; i < listaClaves.length; i++) {
			ArrayList interno = new ArrayList<>();
			if (listaClaves[i].equals(keyInicial)) {
				interno.add(0);
			} else
				interno.add(Integer.MAX_VALUE);
			interno.add(listaClaves[i]);
			L.add(interno);
		}
		ArrayList S = new ArrayList<>();
		int contador = 0;

		while (contador != listaClaves.length) {
			int posicL = -1;
			K u = null;
			int menor = Integer.MAX_VALUE;
			for (int i = 0; i < listaClaves.length; i++) {
				boolean estaEnS = false;
				// Verifico que la llave que voy a comparar no exista en el conjunto S
				for (int j = 0; j < S.size(); j++) {
					if (((K) L.get(i).get(1)).equals((K) S.get(j))) {
						estaEnS = true;
					}
				}
				// Sí no estaba en el conjunto S entonces lo comparo para saber si es menor
				if (!estaEnS) {
					int actual = (int) L.get(i).get(0);
					if (actual < menor) {
						u = (K) L.get(i).get(1);
						posicL = i;
						menor = actual;
					}
				}
			}
			S.add(u);
			contador++;
			for (int i = 0; i < listaClaves.length; i++) {
				boolean estaEnS = false;
				// Verifico que la llave que voy a comparar no exista en el conjunto S
				for (int j = 0; j < S.size(); j++) {
					if (((K) L.get(i).get(1)).equals((K) S.get(j))) {
						estaEnS = true;
					}
				}
				if (!estaEnS) {
					ArrayList adyacentes = ((Vertice) verticesMap.get(u)).getAdy();
					int numeroVecinos = 0;
					if (adyacentes != null)
						numeroVecinos = adyacentes.size();

					for (int j = 0; j < numeroVecinos; j++) {
						int menorCaminoAV = Integer.MAX_VALUE;
						K vecino = null;
						vecino = (K) ((Pareja) adyacentes.get(j)).getVertice().getKey();

						for (int j2 = 0; j2 < numeroVecinos; j2++) {
							K actual = (K) ((Pareja) adyacentes.get(j2)).getVertice().getKey();
							if (actual.equals(vecino)) {
								int pesoActual = (int) ((Pareja) adyacentes.get(j2)).getPeso();
								if (pesoActual < menorCaminoAV) {
									menorCaminoAV = pesoActual;
								}
							}
						}
						int posicionV = -1;
						for (int k = 0; k < L.size(); k++) {
							if (L.get(k).get(1) == vecino) {
								posicionV = k;
							}
						}
						if (posicL != -1
								&& ((((int) L.get(posicL).get(0))) + menorCaminoAV) < (int) L.get(posicionV).get(0)) {
							L.get(posicionV).set(0, ((((int) L.get(posicL).get(0))) + menorCaminoAV));
						}
					}
				}
			}
		}
		return L;
	}

	

	public double[][] generarMatrizPeso() {
		K[] listaClaves = (K[]) verticesMap.keySet().toArray();
		double[][] pesos = new double[listaClaves.length][listaClaves.length];
		for (int i = 0; i < listaClaves.length; i++) {
			Vertice vertice = (Vertice) verticesMap.get(listaClaves[i]);
			ArrayList<Pareja> ady = vertice.getAdy();
			for (int j = 0; j < listaClaves.length; j++) {
				pesos[i][j] = INFINITO;
				if (i == j)
					pesos[i][j] = 0;
				for (int j2 = 0; j2 < ady.size(); j2++) {
					K verticeAComp = (K) ((Vertice) verticesMap.get(listaClaves[j])).getKey();
					K verticeAdy = (K) ((Pareja) ady.get(j2)).getVertice().getKey();
					if (verticeAdy.equals(verticeAComp)) {
						pesos[i][j] = ((Pareja) ady.get(j2)).getPeso();
					}
				}
			}
		}
		return pesos;
	}

	/**
	 * Camino minimo entre todos los vertices y los otros
	 * @return devuelve el  Camino minimo entre todos los vertices contra todos
	 */
	public ArrayList<Vertice<K, V>>[][] algoritmoWarchall() {
		K[] listaClaves = (K[]) verticesMap.keySet().toArray();
		double[][] matriz = generarMatrizPeso().clone();

		ArrayList<Vertice<K, V>>[][] caminos = new ArrayList[verticesMap.size()][verticesMap.size()];

		for (int i = 0; i < caminos.length; i++) {
			for (int j = 0; j < caminos[0].length; j++) {
				caminos[i][j] = new ArrayList();
				if (matriz[i][j] != INFINITO) {
					caminos[i][j].add((Vertice) verticesMap.get(listaClaves[i]));
					if (i != j && !dirigido) {
						caminos[i][j].add((Vertice) verticesMap.get(listaClaves[j]));
					}
				}
			}
		}
		for (int i = 0; i < verticesMap.size(); i++) {

			for (int j = 0; j < verticesMap.size(); j++) {
				for (int k = 0; k < verticesMap.size(); k++) {

					if (j != k && j != i && k != i) {

						if (matriz[j][i] + matriz[i][k] < matriz[j][k]) {

							matriz[j][k] = matriz[j][i] + matriz[i][k];

							caminos[j][k] = new ArrayList<Vertice<K, V>>();
							caminos[j][k].addAll(caminos[j][i]);
							ArrayList<Vertice<K, V>> aux = (ArrayList<Vertice<K, V>>) caminos[i][k].clone();
							if (aux.size() > 0) {
								aux.remove(0);
							}
							caminos[j][k].addAll(aux);
						}
					}
				}
			}
		}
		return caminos;
	}
	

	public static ArrayList getA() {
		return a;
	}
	public HashMap<K, V> getVerticesMap() {
		return verticesMap;
	}

	public void setVerticesMap(HashMap<K, V> verticesMap) {
		this.verticesMap = verticesMap;
	}

}
