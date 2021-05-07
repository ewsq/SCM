package demo4.Controller;

import com.alibaba.fastjson.JSON;
import demo4.Service.RoleService;
import demo4.pojo.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@ResponseBody
public class RoleController {
    @Autowired
    private RoleService roleService;


    @RequestMapping("/getRole")
    public String user_role(){
        List<UserRole> userRoles = roleService.getAllRole();
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("code", "0");
        resultMap.put("", "");
        resultMap.put("count", "100");
        resultMap.put("data", userRoles);
        String resq = JSON.toJSONString(resultMap);
        return resq;
     }

     @RequestMapping("/editRole")
     public ModelAndView editRole(@RequestParam("id") int id, @RequestParam("username") String username, @RequestParam("about") String about) {
        UserRole userRole = new UserRole(id, username, about);
        roleService.editRole(userRole);
        ModelAndView mv = new ModelAndView();
        mv.addObject("status","success");
        mv.setViewName("/view/role");
        return mv;
     }

    @PostMapping("/deleteRole")
    public String deleteRole(@RequestBody String param) {
        int id = Integer.parseInt(param);
        roleService.deleteRole(id);
        return "200";
    }
}
