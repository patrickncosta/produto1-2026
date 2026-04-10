package br.ifmg.produto1_2026.resources;

import br.ifmg.produto1_2026.dto.UsuarioDTO;
import br.ifmg.produto1_2026.entities.Usuario;
import br.ifmg.produto1_2026.service.AtivacaoClienteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/venda")
public class VendasResource {
    public VendasResource(AtivacaoClienteService ativacaoCliente) {
        this.ativacaoCliente = ativacaoCliente;
        System.out.println("Camada de resource criada");
    }

    private AtivacaoClienteService ativacaoCliente;

    @PostMapping
    public ResponseEntity<String> insert(){
        Usuario usuario = new Usuario();
        usuario.setNome("Fernando");
        usuario.setTelefone("55555555");
        usuario.setEmail("fernando@email.com");
        ativacaoCliente.ativar(usuario, "ativando....");



        return ResponseEntity.ok().body("Venda criada");
    }
}
