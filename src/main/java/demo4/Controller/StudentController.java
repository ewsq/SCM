package demo4.Controller;

import com.alibaba.fastjson.JSON;
import demo4.Service.StudentService;
import demo4.pojo.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ResponseBody
@Controller
public class StudentController {

    @Autowired
    private StudentService studentService;


    @RequestMapping("/getStudent")
    public String getAllStudent() {
        List<Student> studentList = studentService.getAllStudent();
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("code", "0");
        resultMap.put("", "");
        resultMap.put("count", "100");
        resultMap.put("data", studentList);
        String resq = JSON.toJSONString(resultMap);
        return resq;
    }

    @RequestMapping("/editStudent")
    private ModelAndView editStudent(@RequestParam("userid") int id, @RequestParam("username") String name, @RequestParam("usersex") String sex,
                                     @RequestParam("userage") int age, @RequestParam("userbirth") String birth) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        try {
            //使用SimpleDateFormat的parse()方法生成Date
            date = sf.parse(birth);
            //打印Date
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Student student = new Student(id,name,sex,age,date);
        studentService.updateStudent(student);
        ModelAndView mv = new ModelAndView();
        mv.addObject("status","success");
        mv.setViewName("/view/tables");
        return mv;
    }

    @RequestMapping("/addStudent")
    private ModelAndView addStudent(@RequestParam("userid") int id, @RequestParam("username") String name, @RequestParam("usersex") String sex,
                                     @RequestParam("userage") int age, @RequestParam("userbirth") String birth) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        try {
            //使用SimpleDateFormat的parse()方法生成Date
            date = sf.parse(birth);
            //打印Date
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Student student = new Student(id,name,sex,age,date);
        studentService.addStudent(student);
        ModelAndView mv = new ModelAndView();
        mv.addObject("status","success");
        mv.setViewName("/view/tables");
        return mv;
    }

    @PostMapping("/deleteStudent")
    public String deleteRole(@RequestBody String param) {
        int id = Integer.parseInt(param);
        studentService.deleteStudent(id);
        return "200";
    }
}
