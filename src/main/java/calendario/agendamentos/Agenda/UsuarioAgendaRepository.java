package calendario.agendamentos.Agenda;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioAgendaRepository extends JpaRepository<UsuarioAgendaModel, Long> {
    List<UsuarioAgendaModel> findByUsuarioId(Long usuarioId);
}
