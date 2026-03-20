package br.ifmg.produto1_2026.resources;

import br.ifmg.produto1_2026.dto.ProdutoDTO;
import br.ifmg.produto1_2026.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/produtos")

public class ProdutoResource {

    @Autowired
    private ProdutoService produtoService;

    @GetMapping
    public ResponseEntity<Page<ProdutoDTO>> produto(/*@RequestParam(value = "page", defaultValue = "0") Integer page,
                                                        @RequestParam(value = "size", defaultValue = "10") Integer size,
                                                        @RequestParam(value = "direction", defaultValue = "ASC") String direction,
                                                        @RequestParam(value = "sort", defaultValue = "id") String sort*/
            Pageable pageable
    ){

        //PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.valueOf(direction), sort);

        Page<ProdutoDTO> produtos = produtoService.findAll(pageable);
        return ResponseEntity.ok().body(produtos);
    };

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoDTO> produto(@PathVariable Long id){
        ProdutoDTO dto = produtoService.findById(id);
        return ResponseEntity.ok().body(dto);
    }

    @PostMapping
    public ResponseEntity<ProdutoDTO> insert(@RequestBody ProdutoDTO dto){

        //inserindo no BD e pegando o objeto inserido
        ProdutoDTO retorno = produtoService.insert(dto);

        //criando um link para acessar o produto criado
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(retorno.getId()).toUri();

        return ResponseEntity.created(location).body(retorno);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        produtoService.delete(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoDTO> update(@PathVariable Long id, @RequestBody ProdutoDTO dto){
        ProdutoDTO retorno = produtoService.update(id,dto);

        return ResponseEntity.ok().body(retorno);
    }

}
