package br.ifmg.produto1_2026.resources;

import br.ifmg.produto1_2026.dto.ProdutoDTO;
import br.ifmg.produto1_2026.dto.ProdutoListDTO;
import br.ifmg.produto1_2026.service.ProdutoService;
import ch.qos.logback.classic.LoggerContext;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/produtos")
@Tag(name="Produtos", description = "Essa API é responsável por gerenciar produtos na plataforma.")
public class ProdutoResource {

    @Autowired
    private ProdutoService produtoService;

    //novo
    @GetMapping(produces = "application/json")
    @Operation(
            summary = "Endpoint para retornar todos os produtos",
            description = "A plataforma precisa disponibilizar uma listagem de produtos...",
            responses = {
                    @ApiResponse(description = "Lista retornada com sucesso", responseCode = "200"),
                    @ApiResponse(description = "Erro interno no servidor", responseCode = "500")
            }
    )
    public ResponseEntity<Page<ProdutoListDTO>> produtos(
                        @RequestParam(value="categoriasId", defaultValue = "0") String categoriasID,
                        @RequestParam(value="name", defaultValue = "") String nome,
                        Pageable pageable){

        //PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.valueOf(direction), sort);

        Page<ProdutoListDTO> produtos = produtoService.findAll(categoriasID, nome, pageable);
        return ResponseEntity.ok().body(produtos);
    };


    //velho
    @GetMapping(value = "/v1/", produces = "application/json")
    @Operation(
            summary = "Endpoint para retornar todos os produtos",
            description = "A plataforma precisa disponibilizar uma listagem de produtos...",
            responses = {
                    @ApiResponse(description = "Lista retornada com sucesso", responseCode = "200"),
                    @ApiResponse(description = "Erro interno no servidor", responseCode = "500")
            }
    )
    public ResponseEntity<Page<ProdutoDTO>> produtos(Pageable pageable){

        //PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.valueOf(direction), sort);

        Page<ProdutoDTO> produtos = produtoService.findAll(pageable);
        return ResponseEntity.ok().body(produtos);
    };

    @GetMapping(value = "/{id}", produces = "application/json")
    @Operation(
            summary = "Endpoint para retornar um produto",
            description = "A plataforma precisa disponibilizar uma listagem de um produto especifico...",
            responses = {
                    @ApiResponse(description = "Produto retornado com sucesso", responseCode = "200"),
                    @ApiResponse(description = "Produto não encontrado", responseCode = "404")
            }
    )
    public ResponseEntity<ProdutoDTO> produto(@PathVariable Long id){
        ProdutoDTO dto = produtoService.findById(id);
        return ResponseEntity.ok().body(dto);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR', 'ROLE_VENDEDOR')")
    @PostMapping(produces = "aplication/json")
    @Operation(
            summary = "Endpoint para inserir um produto",
            description = "A plataforma precisa disponibilizar um cadastro de produtos...",
            responses = {
                    @ApiResponse(description = "Registro Criado", responseCode = "201"),
                    @ApiResponse(description = "Bad request", responseCode = "400", content = {}),
                    @ApiResponse(description = "Não autorizado", responseCode = "401"),
                    @ApiResponse(description = "Proibido no seu perfil", responseCode = "403"),
                    @ApiResponse(description = "Erro ao processar", responseCode = "422"),
                    @ApiResponse(description = "Erro interno no servidor", responseCode = "500")
            }
    )
    public ResponseEntity<ProdutoDTO> insert(@RequestBody ProdutoDTO dto){

        //inserindo no BD e pegando o objeto inserido
        ProdutoDTO retorno = produtoService.insert(dto);

        //criando um link para acessar o produto criado
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(retorno.getId()).toUri();

        return ResponseEntity.created(location).body(retorno);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR', 'ROLE_VENDEDOR')")
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Endpoint para apagar um produto",
            description = "A plataforma precisa disponibilizar a deleção de um produto...",
            responses = {
                    @ApiResponse(description = "Sucesso", responseCode = "204"),
                    @ApiResponse(description = "Bad request", responseCode = "400", content = {}),
                    @ApiResponse(description = "Não autorizado", responseCode = "401"),
                    @ApiResponse(description = "Proibido no seu perfil", responseCode = "403"),
                    @ApiResponse(description = "Não encontrado", responseCode = "404"),
                    @ApiResponse(description = "Erro ao processar", responseCode = "422"),
                    @ApiResponse(description = "Erro interno no servidor", responseCode = "500")
            }
    )
    public ResponseEntity<Void> delete(@PathVariable Long id){
        produtoService.delete(id);

        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR', 'ROLE_VENDEDOR')")
    @PutMapping(value="/{id}", produces="application/json")
    @Operation(
            summary = "Endpoint para atualizar um produto",
            description = "A plataforma precisa disponibilizar uma atualização de produtos...",
            responses = {
                    @ApiResponse(description = "OK", responseCode = "200"),
                    @ApiResponse(description = "Bad request", responseCode = "400", content = {}),
                    @ApiResponse(description = "Não autorizado", responseCode = "401"),
                    @ApiResponse(description = "Proibido no seu perfil", responseCode = "403"),
                    @ApiResponse(description = "Não encontrado", responseCode = "404"),
                    @ApiResponse(description = "Erro ao processar", responseCode = "422"),
                    @ApiResponse(description = "Erro interno no servidor", responseCode = "500")
            }
    )
    public ResponseEntity<ProdutoDTO> update(@PathVariable Long id, @RequestBody ProdutoDTO dto){
        ProdutoDTO retorno = produtoService.update(id,dto);

        return ResponseEntity.ok().body(retorno);
    }

}
