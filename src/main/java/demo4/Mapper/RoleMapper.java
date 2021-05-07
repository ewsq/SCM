package demo4.Mapper;


import demo4.pojo.UserRole;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface RoleMapper {
    int addRole(Map<String, Object> map);

    List<UserRole> getAllRole();

    int editRole(UserRole userRole);

    void deleteRole(int id);

    void addStudentRole(Map<String, Object> roleMap);
}
