package br.ifmg.produto1_2026.service;

import br.ifmg.produto1_2026.dto.CategoriaDTO;
import br.ifmg.produto1_2026.dto.ProdutoDTO;
import br.ifmg.produto1_2026.dto.ProdutoListDTO;
import br.ifmg.produto1_2026.entities.Categoria;
import br.ifmg.produto1_2026.entities.Produto;
import br.ifmg.produto1_2026.projections.ProdutoProjection;
import br.ifmg.produto1_2026.repositories.CategoriaRepository;
import br.ifmg.produto1_2026.repositories.ProdutoRepository;
import br.ifmg.produto1_2026.resources.ProdutoResource;
import br.ifmg.produto1_2026.service.exception.ErroNoBancoDeDados;
import br.ifmg.produto1_2026.service.exception.RegistroNaoEncontrado;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    private static final Logger logger = LoggerFactory.getLogger(ProdutoService.class);

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    // novo:
    @Transactional(readOnly = true)
    public Page<ProdutoListDTO> findAll(String categoriasID, String name, Pageable pageRequest){

        // 1. A lista precisa nascer instanciada para não dar NullPointerException
        List<Long> categoriasIDs = new ArrayList<>();

        // 2. A verificação é feita na String, e a conversão ocorre se ela for válida
        if(categoriasID != null && !categoriasID.equals("0")){
            categoriasIDs = Arrays.stream(categoriasID.split(","))
                    .map(Long::valueOf)
                    .toList();
        }

        // Lista com os dados do bd. Essa lista vem com os dados em Projections
        Page<ProdutoProjection> produtos = produtoRepository.searchProdutos(categoriasIDs, name, pageRequest);

        // Converter os projections em DTOs, pois a camada de cima so trabalha com DTOs
        List<ProdutoListDTO> produtosDTO = produtos.stream().map(p -> new ProdutoListDTO(p)).toList();

        // 3. PageImpl exige o total de elementos do banco
        return new PageImpl<>(produtosDTO, pageRequest, produtos.getTotalElements());
    }

    // velho:
    @Transactional(readOnly = true)
    public Page<ProdutoDTO> findAll(Pageable pageRequest){

        logger.info("Consultando a lista de produtos");
        logger.error("Consultando a lista de produtos");
        logger.warn("Consultando a lista de produtos");
        logger.debug("Consultando {} a lista {} de produtos", 123, "teste");

        // Lista com os dados do bd
        Page<Produto> produtos = produtoRepository.findAll(pageRequest);

        return produtos.map(produto -> new ProdutoDTO(produto)
                // 4. Atualizado para enviar os 3 parâmetros exigidos pelo controller
                .add(linkTo(methodOn(ProdutoResource.class).produtos(null, null, pageRequest)).withSelfRel())
                .add(linkTo(methodOn(ProdutoResource.class).produto(produto.getId())).withRel("Obter produto pelo iD"))
        );
    }

    @Transactional(readOnly = true)
    public ProdutoDTO findById(Long id) {
        // Buscamos no bd o produto. O resultado é um objeto do tipo Optional
        Optional<Produto> opt = produtoRepository.findById(id);

        // Buscamos o produto dentro do objeto Optional
        Produto produto = opt.orElseThrow(() -> new RegistroNaoEncontrado("Produto não encontrado"));

        ProdutoDTO dto = new ProdutoDTO(produto);

        Pageable pageable = PageRequest.of(0, 10, Sort.by("id"));

        // Convertemos a entidade em DTO
        return dto
                .add(linkTo(methodOn(ProdutoResource.class).produto(produto.getId())).withSelfRel())
                // 4. Atualizado para 3 parâmetros
                .add(linkTo(methodOn(ProdutoResource.class).produtos(null, null, pageable)).withRel("todos os produtos"))
                .add(linkTo(methodOn(ProdutoResource.class).update(produto.getId(), dto)).withRel("atualizar o produto"))
                .add(linkTo(methodOn(ProdutoResource.class).delete(produto.getId())).withRel("apagar o produto"));
    }

    @Transactional
    public ProdutoDTO insert(ProdutoDTO dto){

        Produto entity = new Produto();
        // Mantive o seu copyDtoToEntity, pois ele trata as categorias corretamente!
        copyDtoToEntity(dto, entity);

        Pageable pageable = PageRequest.of(0, 10, Sort.by("id"));

        Produto novo = produtoRepository.save(entity);
        return new ProdutoDTO(novo)
                .add(linkTo(methodOn(ProdutoResource.class).produto(novo.getId())).withSelfRel())
                .add(linkTo(methodOn(ProdutoResource.class).produto(novo.getId())).withRel("Busca pelo ID"))
                // 4. Atualizado para 3 parâmetros
                .add(linkTo(methodOn(ProdutoResource.class).produtos(null, null, pageable)).withRel("todos os produtos"))
                .add(linkTo(methodOn(ProdutoResource.class).update(novo.getId(), dto)).withRel("atualizar o produto"))
                .add(linkTo(methodOn(ProdutoResource.class).delete(novo.getId())).withRel("apagar o produto"));
    }

    @Transactional
    public void delete(Long id){
        if(!produtoRepository.existsById(id)){
            throw new RegistroNaoEncontrado("Produto não encontrado ao tentar ser excluído");
        }

        try{
            produtoRepository.deleteById(id);
        } catch (DataIntegrityViolationException e){
            throw new ErroNoBancoDeDados(e.getMessage());
        }
    }

    @Transactional
    public ProdutoDTO update(Long id, ProdutoDTO dto) {

        if(!produtoRepository.existsById(id)){
            throw new RegistroNaoEncontrado("Produto não encontrado para ser alterado");
        }

        Produto entity = produtoRepository.getReferenceById(id);

        copyDtoToEntity(dto, entity);

        Pageable pageable = PageRequest.of(0, 10, Sort.by("id"));

        entity = produtoRepository.save(entity);
        return new ProdutoDTO(entity)
                .add(linkTo(methodOn(ProdutoResource.class).produto(entity.getId())).withSelfRel())
                .add(linkTo(methodOn(ProdutoResource.class).produto(entity.getId())).withRel("Busca pelo ID"))
                // 4. Atualizado para 3 parâmetros
                .add(linkTo(methodOn(ProdutoResource.class).produtos(null, null, pageable)).withRel("todos os produtos"))
                .add(linkTo(methodOn(ProdutoResource.class).delete(id)).withRel("apagar o produto"));
    }

    // Seu método auxiliar excelente para evitar repetição de código
    private void copyDtoToEntity(ProdutoDTO dto, Produto entity) {
        entity.setNome(dto.getNome());
        entity.setDescricao(dto.getDescription());
        entity.setPreco(dto.getPrice());
        entity.setImgUrl(dto.getImgURL());

        entity.getCategorias().clear();
        for(CategoriaDTO catDto : dto.getCategorias()){
            Categoria cat = categoriaRepository.getReferenceById(catDto.getId());
            entity.getCategorias().add(cat);
        }
    }
}