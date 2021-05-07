package demo4.Mapper;

import demo4.pojo.Role;
import demo4.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Mapper
@Repository
public interface UserMapper {

    List<User> getAllUser();

    int deleteById(int id);

    int addUser(User user);

    User selectUserByName(String name);

    List<Role> getRoleById(int id);

    void addStuUser(Map<String, Object> stringObjectMap);

    void updateProfile(Map<String, Object> profileMap);

    User selectUserById(int id);
}

