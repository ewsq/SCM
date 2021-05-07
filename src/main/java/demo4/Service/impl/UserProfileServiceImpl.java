package demo4.Service.impl;

import demo4.Mapper.CommentMapper;
import demo4.Mapper.StudentMapper;
import demo4.Mapper.UserMapper;
import demo4.Service.UserProfileService;
import demo4.pojo.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserProfileServiceImpl implements UserProfileService {
    private Logger logger = LoggerFactory.getLogger(UserProfileServiceImpl.class);

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public String editProfile(Map<String, Object> profileMap) {
        logger.info("执行userMapper.updateProfile");

        userMapper.updateProfile(profileMap);

        logger.info("userMapper.updateProfile执行成功----");
        logger.info("执行studentMapper.updateProfile");

        studentMapper.updateProfile(profileMap);

        logger.info("studentMapper.updateProfile执行成功----");
        logger.info("执行commentMapper.updateProfile");

        commentMapper.updateProfile(profileMap);

        logger.info("commentMapper.updateProfile执行成功----");
        return "success";
    }

    @Override
    public User getUserById(int id) {
        User user = userMapper.selectUserById(id);
        if(user==null||user.equals("")) {
            throw new NullPointerException("selectUserById查询返回结果为空，查询错误");
        }
        user.setPassword("");
        return user;
    }
}
