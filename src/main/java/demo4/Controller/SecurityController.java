package demo4.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SecurityController {
    private final static Logger logger = LoggerFactory.getLogger(SecurityController.class);

    @RequestMapping({"/", "/index"})
    public String goIndex() {
        logger.info("访问主页，跳转主页");
        return "index";
    }

    @RequestMapping("/level1/{id}")
    public String goView1(@PathVariable("id") int id) {
        return "/view/level1/" + id;
    }

    @RequestMapping("/level2/{id}")
    public String goView2(@PathVariable("id") int id) {
        return "/view/level2/" + id;
    }

    @RequestMapping("/level3/{id}")
    public String goView3(@PathVariable("id") int id) {
        return "/view/level3/" + id;
    }

    @RequestMapping("/login")
    public String login(String username, String password, Model model) {
        logger.info("访问/login，进行登录验证，username："+username+"，password："+password);
        model.addAttribute("username", username);

        return "login";
    }

    @RequestMapping("/charts")
    public String charts() {
        logger.info("访问/charts，学生表格页面");
        return "view/charts";
    }

    @RequestMapping("/tables")
    public String tables() {
        logger.info("访问/tables，学生数据页面");
        return "view/tables";
    }

    @RequestMapping("/dashboard")
    public String dashboard() {
        logger.info("访问/dashboard，老师管理后台主页面");
        return "view/dashboard";
    }

    @RequestMapping("/lesson1")
    public String study() {
        logger.info("访问/lesson，学生课程页面");
        return "view/lesson1";
    }

    @RequestMapping("/upload")
    public String upload() {
        logger.info("访问/upload，信息上传页面");
        return "view/upload";
    }

    @RequestMapping("/message")
    public String message() {
        logger.info("访问/message，社区交流页面");
        return "view/message";
    }

    @RequestMapping("/role")
    public String role() {
        logger.info("访问/role，账户权限授权页面");
        return "view/role";
    }

    @RequestMapping("/commentM")
    public String toComment() {
        logger.info("访问/commentG，留言管理页面");
        return "view/comment";
    }

    @RequestMapping("/profile")
    public String toProfile() {
        logger.info("访问/profile，个人信息修改页面");
        return "view/profile";
    }

    @RequestMapping("/lesson")
    public String toLesson() {
        logger.info("访问/lesson，课程页面");
        return "view/lesson";
    }

    @RequestMapping("/resource")
    public String toResource() {
        logger.info("访问/resource，资源下载页面");
        return "view/resource";
    }

    @RequestMapping("/resourceManage")
    public String toResourceManage() {
        logger.info("访问/resourceManage，资源管理页面");
        return "view/resourceManage";
    }

}
