package br.ifmg.produto1_2026.service;


import br.ifmg.produto1_2026.dto.CategoriaDTO;
import br.ifmg.produto1_2026.dto.RoleDTO;
import br.ifmg.produto1_2026.dto.UsuarioDTO;
import br.ifmg.produto1_2026.dto.UsuarioInsertDTO;
import br.ifmg.produto1_2026.entities.Categoria;
import br.ifmg.produto1_2026.entities.Role;
import br.ifmg.produto1_2026.entities.Usuario;
import br.ifmg.produto1_2026.projections.UserDetailsProjection;
import br.ifmg.produto1_2026.repositories.RoleRepository;
import br.ifmg.produto1_2026.repositories.UsuarioRepository;
import br.ifmg.produto1_2026.service.exception.ErroNoBancoDeDados;
import br.ifmg.produto1_2026.service.exception.RegistroNaoEncontrado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder encoder;

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
    public UsuarioDTO insert(UsuarioInsertDTO dto){

        Usuario entity = new Usuario();
        copyDtoToEntity(dto,entity);
        entity.setSenha(
                encoder.encode(dto.getSenha())
        );


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

        entity = copyDtoToEntity(dto, entity);

        return new UsuarioDTO(entity);
    }

    @NonNull
    private Usuario copyDtoToEntity(UsuarioDTO dto, Usuario entity) {
        entity.setNome(dto.getNome());
        entity.setTelefone(dto.getTelefone());
        entity.setEmail(dto.getEmail());
        entity = usuarioRepository.save(entity);

        entity.getPerfis().clear();
        for(RoleDTO perfDto: dto.getPerfis()){
            Role perf = roleRepository.getReferenceById(perfDto.getId());
            entity.getPerfis().add(perf);
        }
        return entity;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        List<UserDetailsProjection> dados = usuarioRepository.loadUserByUserName(userName);

        usuarioRepository.loadUserByUserName(userName);

        if(dados.isEmpty()){
            throw new UsernameNotFoundException(userName);
        }


        Usuario usuario = new Usuario();
        usuario.setSenha(dados.getFirst().getPassword());
        usuario.setEmail(dados.getFirst().getUsername());

        for(UserDetailsProjection dado : dados) {
            usuario.addRole(
                    new Role(dado.getRoleId(),
                            dado.getAuthority()
                    )
            );
        }

        return usuario;
    }
}
