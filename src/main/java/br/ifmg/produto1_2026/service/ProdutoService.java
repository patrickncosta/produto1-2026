package br.ifmg.produto1_2026.service;

import br.ifmg.produto1_2026.dto.CategoriaDTO;
import br.ifmg.produto1_2026.dto.ProdutoDTO;
import br.ifmg.produto1_2026.entities.Categoria;
import br.ifmg.produto1_2026.entities.Produto;
import br.ifmg.produto1_2026.repositories.CategoriaRepository;
import br.ifmg.produto1_2026.repositories.ProdutoRepository;
import br.ifmg.produto1_2026.service.exception.ErroNoBancoDeDados;
import br.ifmg.produto1_2026.service.exception.RegistroNaoEncontrado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;
    @Autowired
    private CategoriaRepository categoriaRepository;

    @Transactional(readOnly = true)

    public Page<ProdutoDTO> findAll(Pageable pageRequest){

        //lista com os dados do bd
        Page<Produto> produtos = produtoRepository.findAll(pageRequest);



        return produtos.map(ProdutoDTO::new);
    }

    public ProdutoDTO findById(Long id) {
        //buscamos no bd o produto. O resultado é um objeto do tipo Optional
        Optional<Produto> opt = produtoRepository.findById(id);

        //buscamos o produto dentro do objeto Optional
        Produto produto = opt.orElseThrow(()->new RegistroNaoEncontrado("Produto não encontrada"));

        //convertemos a entidade de DTO
        return new ProdutoDTO(produto);
    }

    @Transactional
    public ProdutoDTO insert(ProdutoDTO dto){

        Produto entity = new Produto();
        copyDtoToEntity(dto, entity);

        Produto novo = produtoRepository.save(entity);
        return new ProdutoDTO(novo);
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

        entity = produtoRepository.save(entity);
        return new ProdutoDTO(entity);
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
