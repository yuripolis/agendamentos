package calendario.agendamentos.category;

import java.util.Collection;
import java.util.HashSet;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import calendario.agendamentos.Usuario.UsuarioModel;

@Entity
public class UserType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int duration; 
    
    @ManyToMany(mappedBy = "userType")
    private Collection<UsuarioModel> users = new HashSet<>();
    
    public UserType() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
    
    public Collection<UsuarioModel> getUsers() {
        return users;
    }

    public void setUsers(Collection<UsuarioModel> users) {
        this.users = users;
    }
}
