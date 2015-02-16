package br.amiguinho.grafo.mb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import javax.inject.Named;


import br.amiguinho.grafo.entity.Aresta;
import br.amiguinho.grafo.entity.Vertice;
import br.amiguinho.grafo.persist.Persistencia;

//@javax.faces.view.ViewScoped  
//testar essa tag do CDI 
@Named
@org.omnifaces.cdi.ViewScoped  
public class GrafoMB implements Serializable{   
	
	@Inject   
	private Persistencia persistencia;
	 
	private Vertice vertice; 
	private List<Vertice> vertices;  
	private List<Vertice> verticesDestino;
	
	private Aresta aresta;
	private List<Aresta> arestas;

	@PostConstruct
	public void init()
	{   
		setVertice(new Vertice()); 
		setVertices(persistencia.listar());
		setAresta(new Aresta());
		setArestas(persistencia.listarAresta());
	} 
	
	public void listarVerticesDestino(AjaxBehaviorEvent event) {
		verticesDestino = new ArrayList<>();
		verticesDestino.addAll(vertices);
		verticesDestino.remove(aresta.getOrigem());
		
		//-- verifica se existe uma aresta previa.
		//-- evitar criar uma aresta ja existente
		for (Aresta arestaAux : getArestas())
		{
			if (arestaAux.getOrigem().equals(aresta.getOrigem()))
			{
				verticesDestino.remove(arestaAux.getDestino());
			}
			if (arestaAux.getDestino().equals(aresta.getOrigem()))
			{
				verticesDestino.remove(arestaAux.getOrigem());
			} 
		}  
	}
	   
	public void incluirVertice(){
		
		if (vertice.getCor().trim().isEmpty()){
			vertice.setCor("ffffff");
		}
		
		//-- incluir novo vertice
		Vertice verticeIncluido = persistencia.incluir(vertice);    
		    
		//--limpa e atualiza                     
		init();
		
		System.out.println("incluido: " + verticeIncluido + " .. qtd[" + getVertices().size() + "]" );
		  
	}
	         
      
	public void incluirAresta()
	{
		if (aresta.getCor().trim().isEmpty())
		{
			aresta.setCor("cccccc");
		}
		
		persistencia.incluirAresta(aresta);
		System.out.println("Aresta: " + aresta + " incluida com sucesso.");
		init();
	}

	public void excluirVertice()
	{
		System.out.println("excluir vertice...");
		//Vertice vertice = comboVerticeMB.getVertice();
		if (vertice != null)
		{
			//Vertice vertice = new Vertice(idVertice);
			persistencia.excluir(vertice);

			System.out.println("vertice excluido: " + vertice);
			
			//-- limpa e atualiza
			init();
			
		}     
	}
	 
	public void excluirAresta()
	{
		System.out.println("excluir aresta: " + aresta);
		if (aresta != null)
		{
			persistencia.excluirAresta(aresta);
			
			init();
		}
	}

		 

	public Vertice getVertice() {
		return vertice;
	}

	public void setVertice(Vertice vertice) {
		this.vertice = vertice;
	}


	public List<Vertice> getVertices() { 
		return vertices;
	}
 

	public void setVertices(List<Vertice> vertices) { 
		this.vertices = vertices; 
	}    
 

	public Aresta getAresta() {
		return aresta;
	}  
 
     
	public void setAresta(Aresta aresta) {  
		this.aresta = aresta;  
	}      
       

	public List<Vertice> getVerticesDestino() {     
		return verticesDestino;
	} 

 
	public void setVerticesDestino(List<Vertice> verticesDestino) {
		this.verticesDestino = verticesDestino;  
	}

	public List<Aresta> getArestas() {  
		return arestas;
	}
 
	public void setArestas(List<Aresta> arestas) {
		this.arestas = arestas; 
	}

}
