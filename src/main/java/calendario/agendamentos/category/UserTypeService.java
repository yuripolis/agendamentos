package calendario.agendamentos.category;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserTypeService {

    private final UserTypeRepository userTypeRepository;

    public UserTypeService(UserTypeRepository userTypeRepository) {
        this.userTypeRepository = userTypeRepository;
    }

    public List<UserType> getAllCategories() {
        return userTypeRepository.findAll();
    }

    public void saveCategory(UserType userType) {
    	userTypeRepository.save(userType);
    }

    public UserType getCategoryById(Long id) {
        return userTypeRepository.findById(id).orElse(null);
    }
}
