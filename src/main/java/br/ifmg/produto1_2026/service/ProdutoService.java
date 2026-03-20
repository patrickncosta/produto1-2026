package br.ifmg.produto1_2026.service;

import br.ifmg.produto1_2026.dto.ProdutoDTO;

import br.ifmg.produto1_2026.entities.Produto;
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

    @Transactional(readOnly = true)

    public Page<ProdutoDTO> findAll(Pageable pageRequest){

        //lista com os dados do bd
        Page<Produto> produtos = produtoRepository.findAll(pageRequest);


        return produtos.map(ProdutoDTO::new);
    }

    public ProdutoDTO findById(Long id) {
        //buscamos no bd o produtoo. O resultado é um objeto do tipo Optional
        Optional<Produto> opt = produtoRepository.findById(id);

        //buscamos o produto dentro do objeto Optional
        Produto produto = opt.orElseThrow(()->new RegistroNaoEncontrado("Produto não encontrado"));

        //convertemos a entidade de DTO
        return new ProdutoDTO(produto);
    }

    @Transactional
    public ProdutoDTO insert(ProdutoDTO dto){

        Produto entity = new Produto();
        entity.setNome(dto.getNome());

        Produto novo = produtoRepository.save(entity);
        return new ProdutoDTO(entity);
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
            throw new RegistroNaoEncontrado("Produto não encontrado para ser alterado");
        }

        Produto entity = produtoRepository.getReferenceById(id);

        entity.setNome(dto.getNome());
        entity = produtoRepository.save(entity);
        return new ProdutoDTO(entity);
    }
}
