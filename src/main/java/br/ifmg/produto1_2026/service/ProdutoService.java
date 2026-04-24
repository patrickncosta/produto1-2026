package br.ifmg.produto1_2026.service;

import br.ifmg.produto1_2026.dto.CategoriaDTO;
import br.ifmg.produto1_2026.dto.ProdutoDTO;
import br.ifmg.produto1_2026.entities.Categoria;
import br.ifmg.produto1_2026.entities.Produto;
import br.ifmg.produto1_2026.repositories.CategoriaRepository;
import br.ifmg.produto1_2026.repositories.ProdutoRepository;
import br.ifmg.produto1_2026.resources.ProdutoResource;
import br.ifmg.produto1_2026.service.exception.ErroNoBancoDeDados;
import br.ifmg.produto1_2026.service.exception.RegistroNaoEncontrado;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    private static final Logger logger = LoggerFactory.getLogger(ProdutoService.class);

    @Autowired
    private ProdutoRepository produtoRepository;
    @Autowired
    private CategoriaRepository categoriaRepository;

    @Transactional(readOnly = true)

    public Page<ProdutoDTO> findAll(Pageable pageRequest){

        logger.info("Consultando a lista de produtos");
        logger.error("Consultando a lista de produtos");
        logger.warn("Consultando a lista de produtos");
        logger.debug("Consultando {} a lista {} de produtos",123,"teste");

        //lista com os dados do bd
        Page<Produto> produtos = produtoRepository.findAll(pageRequest);


        Pageable pageable= PageRequest.of(0,10, Sort.by("id"));

        return produtos.map(produto -> new ProdutoDTO(produto)
                .add(linkTo(methodOn(ProdutoResource.class).produtos(pageable)).withSelfRel())
                .add(linkTo(methodOn(ProdutoResource.class).produto(produto.getId())).withRel("Obter produto pelo iD"))
        );
    }

    public ProdutoDTO findById(Long id) {
        //buscamos no bd o produto. O resultado é um objeto do tipo Optional
        Optional<Produto> opt = produtoRepository.findById(id);

        //buscamos o produto dentro do objeto Optional
        Produto produto = opt.orElseThrow(()->new RegistroNaoEncontrado("Produto não encontrada"));

        ProdutoDTO dto = new ProdutoDTO(produto);

        Pageable pageable= PageRequest.of(0,10, Sort.by("id"));

        //convertemos a entidade de DTO
        return dto
                .add(linkTo(methodOn(ProdutoResource.class).produto(produto.getId())).withSelfRel())
                .add(linkTo(methodOn(ProdutoResource.class).produtos(pageable)).withRel("todos os produtos"))
                .add(linkTo(methodOn(ProdutoResource.class).update(produto.getId(), dto)).withRel("atualizar o produto"))
                .add(linkTo(methodOn(ProdutoResource.class).delete(produto.getId())).withRel("apagar o produto"))
                ;

    }

    @Transactional
    public ProdutoDTO insert(ProdutoDTO dto){

        Produto entity = new Produto();
        copyDtoToEntity(dto, entity);

        Pageable pageable= PageRequest.of(0,10, Sort.by("id"));

        Produto novo = produtoRepository.save(entity);
        return new ProdutoDTO(novo)
                .add(linkTo(methodOn(ProdutoResource.class).insert(dto)).withSelfRel())
                .add(linkTo(methodOn(ProdutoResource.class).produto(novo.getId())).withRel("Busca pelo ID"))
                .add(linkTo(methodOn(ProdutoResource.class).produtos(pageable)).withRel("todos os produtos"))
                .add(linkTo(methodOn(ProdutoResource.class).update(novo.getId(), dto)).withRel("atualizar o produto"))
                .add(linkTo(methodOn(ProdutoResource.class).delete(novo.getId())).withRel("apagar o produto"));
    }

    @Transactional
    public void  delete(Long id){
        if(!produtoRepository.existsById(id)){
            throw new RegistroNaoEncontrado("Produto não encontrado ao tentar ser excluído");
        }

        try{
            produtoRepository.deleteById(id);
        }catch (DataIntegrityViolationException e){
            throw new ErroNoBancoDeDados(e.getMessage());
        }

    }

    public ProdutoDTO update(Long id, ProdutoDTO dto) {

        if(!produtoRepository.existsById(id)){
            throw new RegistroNaoEncontrado("Produto não encontrado para ser alterada");
        }

        Produto entity = produtoRepository.getReferenceById(id);

        copyDtoToEntity(dto, entity);

        Pageable pageable= PageRequest.of(0,10, Sort.by("id"));

        entity = produtoRepository.save(entity);
        return new ProdutoDTO(entity)
                .add(linkTo(methodOn(ProdutoResource.class).update(id, dto)).withSelfRel())
                .add(linkTo(methodOn(ProdutoResource.class).produto(id)).withRel("Busca pelo ID"))
                .add(linkTo(methodOn(ProdutoResource.class).produtos(pageable)).withRel("todos os produtos"))
                .add(linkTo(methodOn(ProdutoResource.class).delete(id)).withRel("apagar o produto"));
    }

    private void copyDtoToEntity(ProdutoDTO dto, Produto entity) {
        entity.setNome(dto.getNome());
        entity.setDescricao(dto.getDescription());
        entity.setPreco(dto.getPrice());
        entity.setImgUrl(dto.getImgURL());

        entity.getCategorias().clear();
        for(CategoriaDTO catDto: dto.getCategorias()){
            Categoria cat = categoriaRepository.getReferenceById(catDto.getId());
            entity.getCategorias().add(cat);
        }
    }
}
