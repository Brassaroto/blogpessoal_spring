package security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.generation.blogpessoal.model.Usuario;

public class UserDetailsImpl implements UserDetails {

    private static final long serialVersionUID = 1L;

    private String username;
    private String password;
    private List<GrantedAuthority> authorities;

    // Construtor que configura os dados do usuário
    public UserDetailsImpl(Usuario user) {
        this.username = user.getUsuario();
        this.password = user.getSenha();

        // Exemplo: se user.getTipo() retornar "admin", adiciona ROLE_ADMIN
        this.authorities = List.of(
            new SimpleGrantedAuthority("ROLE_" + user.getTipo().toUpperCase())
        );
    }

    // Construtor padrão (necessário)
    public UserDetailsImpl() {}

    // Retorna as authorities (roles) do usuário
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    // Retorna a senha
    @Override
    public String getPassword() {
        return password;
    }

    // Retorna o nome de usuário (e-mail, login, etc.)
    @Override
    public String getUsername() {
        return username;
    }

    // Conta não expirada
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // Conta não bloqueada
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // Credenciais não expiradas
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // Conta habilitada
    @Override
    public boolean isEnabled() {
        return true;
    }
}
