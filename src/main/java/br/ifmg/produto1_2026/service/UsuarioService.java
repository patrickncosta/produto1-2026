package br.ifmg.produto1_2026.service;


import br.ifmg.produto1_2026.dto.UsuarioDTO;
import br.ifmg.produto1_2026.entities.Usuario;
import br.ifmg.produto1_2026.repositories.UsuarioRepository;
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
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional(readOnly = true)

    public Page<UsuarioDTO> findAll(Pageable pageRequest){

        //lista com os dados do bd
        Page<Usuario> usuarios = usuarioRepository.findAll(pageRequest);


        return usuarios.map(UsuarioDTO::new);
    }

    public UsuarioDTO findById(Long id) {
        //buscamos no bd o usuario. O resultado é um objeto do tipo Optional
        Optional<Usuario> opt = usuarioRepository.findById(id);

        //buscamos o usuario dentro do objeto Optional
        Usuario usuario = opt.orElseThrow(()->new RegistroNaoEncontrado("Usuario não encontrado"));

        //convertemos a entidade de DTO
        return new UsuarioDTO(usuario);
    }

    @Transactional
    public UsuarioDTO insert(UsuarioDTO dto){

        Usuario entity = new Usuario();
        entity.setNome(dto.getNome());
        entity.setTelefone(dto.getTelefone());
        entity.setEmail(dto.getEmail());
        entity.setSenha(dto.getSenha());

        Usuario novo = usuarioRepository.save(entity);
        return new UsuarioDTO(novo);
    }

    @Transactional
    public void  delete(Long id){
        if(!usuarioRepository.existsById(id)){
            throw new RegistroNaoEncontrado("Usuario não encontrado ao tentar ser excluído");
        }

        try{
            usuarioRepository.deleteById(id);
        }catch (DataIntegrityViolationException e){
            throw new ErroNoBancoDeDados(e.getMessage());
        }

    }

    public UsuarioDTO update(Long id, UsuarioDTO dto) {

        if(!usuarioRepository.existsById(id)){
            throw new RegistroNaoEncontrado("Usuario não encontrado para ser alterada");
        }

        Usuario entity = usuarioRepository.getReferenceById(id);

        entity.setNome(dto.getNome());
        entity.setTelefone(dto.getTelefone());
        entity.setEmail(dto.getEmail());
        entity.setSenha(dto.getSenha());
        entity = usuarioRepository.save(entity);

        return new UsuarioDTO(entity);
    }
}
