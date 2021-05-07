package demo4.Service;


import demo4.pojo.UserRole;
import org.springframework.stereotype.Service;

import java.util.List;

public interface RoleService {
    List<UserRole> getAllRole();

    int editRole(UserRole userRole);

    void deleteRole(int id);
}
