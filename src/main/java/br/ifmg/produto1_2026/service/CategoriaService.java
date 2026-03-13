package br.ifmg.produto1_2026.service;

import br.ifmg.produto1_2026.dto.CategoriaDTO;
import br.ifmg.produto1_2026.entities.Categoria;
import br.ifmg.produto1_2026.repositories.CategoriaRepository;
import br.ifmg.produto1_2026.service.exception.ErroNoBancoDeDados;
import br.ifmg.produto1_2026.service.exception.RegistroNaoEncontrado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Transactional(readOnly = true)

    public List<CategoriaDTO> findAll(){

        //lista com os dados do bd
        List<Categoria>  categorias = categoriaRepository.findAll();

        //lista com os dados convertidos em DTO
        List<CategoriaDTO> categoriasDTO = new ArrayList<CategoriaDTO>();

        for(Categoria categoria: categorias){
            categoriasDTO.add(new CategoriaDTO(categoria));
        }
        return categoriasDTO;
    }

    public CategoriaDTO findById(Long id) {
        //buscamos no bd a categoria. O resultado é um objeto do tipo Optional
        Optional<Categoria> opt = categoriaRepository.findById(id);

        //buscamos a categoria dentro do objeto Optional
        Categoria categoria = opt.orElseThrow(()->new RegistroNaoEncontrado("Categoria não encontrada"));

        //convertemos a entidade de DTO
        return new CategoriaDTO(categoria);
    }

    @Transactional
    public CategoriaDTO insert(CategoriaDTO dto){

        Categoria entity = new Categoria();
        entity.setNome(dto.getNome());

        Categoria nova = categoriaRepository.save(entity);
        return new CategoriaDTO(entity);
    }

    @Transactional
    public void  delete(Long id){
        if(!categoriaRepository.existsById(id)){
            throw new RegistroNaoEncontrado("Categoria não encontrada");
        }

        try{
            categoriaRepository.deleteById(id);
        }catch (DataIntegrityViolationException e){
            throw new ErroNoBancoDeDados(e.getMessage());
        }

    }
}
