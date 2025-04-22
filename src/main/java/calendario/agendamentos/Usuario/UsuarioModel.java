package calendario.agendamentos.Usuario;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import javax.persistence.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import calendario.agendamentos.Agenda.UsuarioAgendaModel;
import calendario.agendamentos.TipoUsuario.TipoUsuarioModel;
import calendario.agendamentos.category.UserType;
import calendario.agendamentos.group.Group;


@Entity
public class UsuarioModel implements Serializable,UserDetails {

    private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String username;
    
    private String senha;
    
    private String email;
    
    private String phone;
    
    @ManyToOne
    @JoinColumn(name = "tu_id")
    private TipoUsuarioModel tipoUsuario;
    
    private String profilePhoto;
    
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UsuarioAgendaModel> agenda = new ArrayList<>();
    
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "user_group",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "group_id")
    )
    private Collection<Group> authorities = new HashSet<>();
    
    @ManyToMany
    @JoinTable(
        name = "user_category",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Collection<UserType> userType = new HashSet<>();

    public UsuarioModel() {}
    
    
    
    public List<UsuarioAgendaModel> getAgenda() {
		return agenda;
	}



	public void setAgenda(List<UsuarioAgendaModel> agenda) {
		this.agenda = agenda;
	}



	public void setUserType(Collection<UserType> userType) {
		this.userType = userType;
	}



	public Collection<UserType> getUserType() {
        return userType;
    }

    public void setCategories(Collection<UserType> userType) {
        this.userType = userType;
    }

    public long getId() {
		return id;
	}



	public void setId(long id) {
		this.id = id;
	}



	public String getSenha() {
		return senha;
	}



	public void setSenha(String senha) {
		this.senha = senha;
	}



	public void setUsername(String username) {
		this.username = username;
	}



	public void setAuthorities(Collection<Group> authorities) {
		this.authorities = authorities;
	}



	@Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getProfilePhoto() {
		return profilePhoto;
	}

	public void setProfilePhoto(String profilePhoto) {
		this.profilePhoto = profilePhoto;
	}

	public TipoUsuarioModel getTipoUsuario() {
		return tipoUsuario;
	}

	public void setTipoUsuario(TipoUsuarioModel tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}
	
	
	
}