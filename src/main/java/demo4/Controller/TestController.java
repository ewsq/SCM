package demo4.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

@Controller
public class TestController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private Logger logger = LoggerFactory.getLogger(TestController.class);


    @GetMapping("/log")
    public Object index() {
        logger.info("访问/log接口，日志测试");
        logger.debug("debug");
        logger.info("info");
        logger.warn("warn");
        logger.error("error");
        return "success";
    }

    @GetMapping("/getallUser")
    public List<Map<String, Object>>  getAllUser() {
        logger.info("访问/getallUser接口，select * from user");
        String sql = "select * from user";
        List<Map<String, Object>> result = jdbcTemplate.queryForList(sql);
        return result;
    }

    @GetMapping("/deleteUserById/{id}")
    public String delete(@PathVariable("id")int id) {
        logger.info("访问/getallUser接口，from user where id=?");
        String sql = "delete from user where id=?";
        Object[]  args = new Object[1];
        args[0] = "4";
        jdbcTemplate.update(sql, args);
        return "sucess";
    }

    @RequestMapping(value = "/test22")
    public String index(Model model){
        String students ="刘洋";
        model.addAttribute("s",students);
        return "test";
    }



}
