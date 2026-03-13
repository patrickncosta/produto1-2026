package br.ifmg.produto1_2026.resources;

import br.ifmg.produto1_2026.dto.CategoriaDTO;
import br.ifmg.produto1_2026.entities.Categoria;
import br.ifmg.produto1_2026.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/categorias")

public class CategoriaResource {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping
    public ResponseEntity<List<CategoriaDTO>> categoria(){
        List<CategoriaDTO> categorias = categoriaService.findAll();
        return ResponseEntity.ok().body(categorias);
    };

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaDTO> categoria(@PathVariable Long id){
        CategoriaDTO dto = categoriaService.findById(id);
        return ResponseEntity.ok().body(dto);
    }

    @PostMapping
    public ResponseEntity<CategoriaDTO> insert(@RequestBody CategoriaDTO dto){

        //inserindo no BD e pegando o objeto inserido
        CategoriaDTO retorno = categoriaService.insert(dto);

        //criando um link para acessar a categoria criada
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(retorno.getId()).toUri();

        return ResponseEntity.created(location).body(retorno);
    }


}
