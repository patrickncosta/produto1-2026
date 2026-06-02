package br.ifmg.produto1_2026.resources;

import br.ifmg.produto1_2026.dto.NewPasswordDTO;
import br.ifmg.produto1_2026.dto.RequestTokenDTO;
import br.ifmg.produto1_2026.dto.UsuarioDTO;
import br.ifmg.produto1_2026.dto.UsuarioInsertDTO;
import br.ifmg.produto1_2026.repositories.UsuarioRepository;
import br.ifmg.produto1_2026.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/auth")
public class AuthResource {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<UsuarioDTO> signup(@RequestBody @Valid UsuarioInsertDTO dto) {
        UsuarioDTO newDto = authService.signup(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(newDto.getId()).toUri();
        return ResponseEntity.created(uri).body(newDto);
    }

    @PostMapping(value = "/recover-token")
    public ResponseEntity<Void> createRecoverToken(@Valid @RequestBody RequestTokenDTO dto) {
        authService.createRecoverToken(dto);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/new-password")
    public ResponseEntity<Void> newPassword(@Valid @RequestBody NewPasswordDTO dto){
        authService.saveNewPassword(dto);
        return ResponseEntity.noContent().build();
    }
}
