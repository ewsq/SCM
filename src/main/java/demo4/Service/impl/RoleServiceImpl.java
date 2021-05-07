package demo4.Service.impl;

import demo4.Mapper.RoleMapper;
import demo4.Mapper.StudentMapper;
import demo4.Mapper.UserMapper;
import demo4.Service.RoleService;
import demo4.pojo.UserRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    private final static Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private StudentMapper studentMapper;

    @Override
    public List<UserRole> getAllRole() {
        logger.info("执行getAllRole查询");
        List<UserRole> roles = roleMapper.getAllRole();
        if(roles==null||roles.equals("")) {
            throw new NullPointerException("getAllRole查询返回为空，查询错误");
        }
        return roles;
    }

    @Override
    public int editRole(UserRole userRole) {
        logger.info("执行editRole查询");
        if(userRole==null||userRole.equals("")) {
            throw new NullPointerException("修改账户权限传入参数为空，修改错误");
        }
        roleMapper.editRole(userRole);

        return 0;
    }

    @Override
    public void deleteRole(int id) {
        logger.info("执行deleteRole操作");
        roleMapper.deleteRole(id);
        userMapper.deleteById(id);
        studentMapper.deleteStudentById(id);
        logger.info("账户删除成功");
    }
}
