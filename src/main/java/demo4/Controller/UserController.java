package demo4.Controller;

import com.alibaba.fastjson.JSON;
import demo4.Mapper.UserMapper;
import demo4.Service.StudentService;
import demo4.Service.UserProfileService;
import demo4.pojo.Student;
import demo4.pojo.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class UserController {
    private final static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserProfileService userProfileService;

    @Autowired
    private StudentService studentService;

    @ResponseBody
    @GetMapping("/all")
    public List<User> getAll() {
        logger.info("访问/all接口，调用getAllUser");
        List<User> userList = userMapper.getAllUser();
        return userList;
    }

    @ResponseBody
    @GetMapping("/delete")
    public List<User> deleteUser() {
        logger.info("访问/delete接口，调用deleteById");
        userMapper.deleteById(88);
        List<User> userList = userMapper.getAllUser();
        return userList;
    }

    @ResponseBody
    @GetMapping("/add")
    public List<User> addUser() {
        logger.info("访问/add接口，调用addUser");
        User user = new User();
        user.setId(88);
        user.setUsername("haha");
        user.setPassword("123");
        userMapper.addUser(user);
        List<User> userList = userMapper.getAllUser();
        return userList;
    }

    @RequestMapping("/editProfile")
    public String editProfile(@RequestParam("userid") int id, @RequestParam("username") String name,
                                    @RequestParam("sex") String sex, @RequestParam("age") int age,
                                    @RequestParam("birth") String birth, @RequestParam("password") String password,
                                    Model model){
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        try {
            //使用SimpleDateFormat的parse()方法生成Date
            date = sf.parse(birth);
            //打印Date
        } catch (ParseException e) {
            e.printStackTrace();
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        Map<String, Object> objectMap =  new HashMap<>();
        objectMap.put("id", id);
        objectMap.put("name", name);
        objectMap.put("sex", sex);
        objectMap.put("age", age);
        objectMap.put("birth", birth);
        objectMap.put("password", "$2a$10$RMuFXGQ5AtH4wOvkUqyvuecpqUSeoxZYqilXzbz50dceRsga.WYiq");
        userProfileService.editProfile(objectMap);
        model.addAttribute("id",id);
        model.addAttribute("status","success");
        return "view/profile";
    }

    @ResponseBody
    @RequestMapping(value = "/getCurrentUser")
    public String currentUserNameSimple(HttpServletRequest request, Model model) throws NoSuchFieldException {
        SecurityContextImpl securityContextImpl = (SecurityContextImpl) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");

        User user = (User) securityContextImpl.getAuthentication().getPrincipal();
        int id = user.getId();
        String resq = JSON.toJSONString(id);
        return resq;
    }
    @ResponseBody
    @PostMapping("/getUserDetail")
    public String getUserDetail(@RequestBody String param){
        System.out.println(param);
        int id = Integer.parseInt(param);
        User user = userProfileService.getUserById(id);
        Student student = studentService.getStudentById(id);
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        Date date = student.getBirth();
        String birth = simpleDateFormat.format(date);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("id", user.getId());
        resultMap.put("name", user.getUsername());
        resultMap.put("sex", student.getSex());
        resultMap.put("age", student.getAge());
        resultMap.put("birth", birth);
        resultMap.put("password", user.getPassword());
        String resq = JSON.toJSONString(resultMap);
        return resq;
    }
}
