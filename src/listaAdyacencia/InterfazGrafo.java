package listaAdyacencia;

public interface InterfazGrafo<K, V> {

	void anadirVertice(V v);

	void removerVertice(K key);

	void anadirArista(K key, K key2, double peso);

	void removerArista(K key, K key2);

}
