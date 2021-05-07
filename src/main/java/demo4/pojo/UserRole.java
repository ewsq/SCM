package demo4.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserRole {
    //用户权限，id：用户id，username：用户名，about：用户账户权限
    private int id;
    private String username;
    private String about;
}
