package br.ifmg.produto1_2026.service;

import br.ifmg.produto1_2026.dto.*;
import br.ifmg.produto1_2026.entities.PasswordRecover;
import br.ifmg.produto1_2026.entities.Role;
import br.ifmg.produto1_2026.entities.Usuario;
import br.ifmg.produto1_2026.repositories.PasswordRecoveryRepository;
import br.ifmg.produto1_2026.repositories.RoleRepository;
import br.ifmg.produto1_2026.repositories.UsuarioRepository;
import br.ifmg.produto1_2026.service.exception.RegistroNaoEncontrado;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class AuthService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository perfilRepository;

    @Value("${spring.mail.username}")
    private String defaultSender;

    @Value("${email.password-recover.uri}")
    private String recoverUri;

    @Value("${email.password-recover.token.minutes}")
    private Long tokenMinutes;

    @Autowired
    private PasswordRecoveryRepository passwordRecoverRepository;

    @Autowired
    private EmailService emailService;


    @Transactional
    public UsuarioDTO signup(UsuarioInsertDTO dto){

        Usuario entity = new Usuario();

        dto.getPerfis().clear();
        Role cliente = perfilRepository.findByNome("ROLE_CLIENTE");
        dto.getPerfis().add(new RoleDTO(cliente));

        copyDtoToEntity(dto, entity);
        entity.setSenha(
                passwordEncoder.encode(dto.getSenha())
        );

        Usuario novo = usuarioRepository.save(entity);
        return new UsuarioDTO(novo);
    }

    @NonNull
    private Usuario copyDtoToEntity(UsuarioDTO dto, Usuario entity){
        entity.setNome(dto.getNome());
        entity.setTelefone(dto.getTelefone());
        entity.setEmail(dto.getEmail());
        entity = usuarioRepository.save(entity);

        entity.getPerfis().clear();
        for(RoleDTO perfDto: dto.getPerfis()){
            Role perf = perfilRepository.getReferenceById(perfDto.getId());
            entity.getPerfis().add(perf);
        }
        return entity;

    }

    public void createRecoverToken(@Valid RequestTokenDTO dto) {
        Usuario user = usuarioRepository.findByEmail(dto.getEmail());
        if (user == null) {
            throw new RegistroNaoEncontrado("Email não encontrado");
        }

        String token = UUID.randomUUID().toString();

        PasswordRecover entity = new PasswordRecover();
        entity.setToken(token);
        entity.setExpiration(Instant.now().plusSeconds(tokenMinutes * 60L));
        entity.setEmail(dto.getEmail());
        passwordRecoverRepository.save(entity);
        String text = "Acesse o link para definir uma nova senha (válido por " + tokenMinutes + " minutos):\n\n"
                + recoverUri + token;
        emailService.sendEmail(
                new EmailDTO(dto.getEmail(), "Recuperação de senha", text)
        );
    }

    public void saveNewPassword(@Valid NewPasswordDTO dto) {
        List<PasswordRecover> list = passwordRecoverRepository.searchValidTokens(dto.getToken(),Instant.now());

        if(list.isEmpty()){
            throw new RegistroNaoEncontrado("Token not found");
        }

        Usuario user = usuarioRepository.findByEmail(list.getFirst().getEmail());

        user.setSenha(passwordEncoder.encode(dto.getPassword()));
        usuarioRepository.save(user);
    }
}