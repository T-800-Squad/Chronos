package moduloGestionUsuarios.UserManagement.service;

import moduloGestionUsuarios.UserManagement.repository.StudentRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService implements UserServiceInterface {

    @Autowired
    private StudentRepositoryJPA studentRepo;


    public void deleteStudent(String idStudent) {
        studentRepo.deleteById(idStudent);
    }

}
