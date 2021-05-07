package demo4.Service;


import demo4.Mapper.UserMapper;
import demo4.pojo.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


@Service

public class UserService implements UserDetailsService {

    private final static Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 通过用户名从数据库获取用户信息
        User user = userMapper.selectUserByName(username);
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        user.setRoleList(userMapper.getRoleById(user.getId()));
        logger.info("登录用户信息：userid："+user.getId()+",username:"+user.getUsername()+",password:"+
                user.getPassword()+",rolelists:"+user.getRoleList());
        return user;
    }

}
