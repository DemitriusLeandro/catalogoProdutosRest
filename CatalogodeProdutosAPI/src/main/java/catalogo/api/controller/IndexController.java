package catalogo.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import catalogo.api.model.Produtos;
import catalogo.api.repository.ProdutosRepository;

@RestController
@RequestMapping (value = "/products")
public class IndexController {
	
	@Autowired
	private ProdutosRepository produtosRepository;
	
	//Retorna por ID do item informado
	@GetMapping(value = "/{id}", produces = "application/json")
	public ResponseEntity <Produtos> init(@PathVariable(value="id") Long id) {		
			
			Optional<Produtos> produtos = produtosRepository.findById(id);				
					
			if (produtos.isEmpty()) {
				
				throw new ResourceNotFoundException("Produto não encontrado para o ID: " + id);
				
			} else {
			
				return new ResponseEntity<Produtos>(produtos.get(), HttpStatus.OK);
			}

	}

	
	//Retorna todos os itens da tabela
	@GetMapping(value = "", produces = "application/json")
	public ResponseEntity <List<Produtos>> produtos() {		
		
		List<Produtos> list = (List<Produtos>) produtosRepository.findAll();
		
		return new ResponseEntity<List<Produtos>>(list, HttpStatus.OK);			
	}
	
	//Retorna todos os itens de acordo com os parametros 
	@GetMapping(value = "/search", produces = "application/json")
	public ResponseEntity <List<Produtos>> produtosSearch(@RequestParam(value="min_price", required =  false ) String min_price,
	                                                      @RequestParam(value="max_price", required =  false ) String max_price, 
                                                          @RequestParam(value="q", required =  false ) String q) {		
		
		if (min_price!=null && max_price !=null && q !=null ) {			
		
		}
			
		
		min_price = min_price.replaceAll(",", ".");			
		double bdMin_price = Double.parseDouble(min_price);
		
		max_price = max_price.replaceAll(",", ".");
		double bdMax_price = Double.parseDouble(max_price);
		
		
		List<Produtos> list = (List<Produtos>) produtosRepository.findSearchProducts(q, bdMax_price,bdMin_price);
				
		return new ResponseEntity<List<Produtos>>(list, HttpStatus.OK);			
	}
	
	//Cadastrar
	@PostMapping(value = "", produces = "application/json")
	public ResponseEntity <Produtos> cadastrar(@RequestBody Produtos produtos) {		
		
		Produtos produtosSalvo = produtosRepository.save(produtos)	;
		
		return new ResponseEntity<Produtos>(produtosSalvo, HttpStatus.OK);	
	}
	
	//Atualizar os dados
	@PutMapping(value = "/", produces = "application/json")
	public ResponseEntity <Produtos> atualizar(@RequestBody Produtos produtos) {		
		
		Produtos produtosSalvo = produtosRepository.save(produtos)	;
		
		return new ResponseEntity<Produtos>(produtosSalvo, HttpStatus.OK);	
	}
	
	//Deletar
	@DeleteMapping(value = "/{id}", produces = "application/json")
	public ResponseEntity deletar (@PathVariable(value="id") Long id) {		
			
		produtosRepository.deleteById(id);	
		
        List<Produtos> list = (List<Produtos>) produtosRepository.findAll();
		
		return new ResponseEntity<List<Produtos>>(list, HttpStatus.OK);			
	}

}
