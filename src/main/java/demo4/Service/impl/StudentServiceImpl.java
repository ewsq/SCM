package demo4.Service.impl;

import demo4.Mapper.RoleMapper;
import demo4.Mapper.StudentMapper;
import demo4.Mapper.UserMapper;
import demo4.Service.StudentService;
import demo4.pojo.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StudentServiceImpl implements StudentService {
    private final static Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public String uploadStudentList(List<Student> studentList) {
        if(studentList==null||studentList.equals("")) {
            logger.error("上传Excel文件内容为空，上传失败！");
            throw new NullPointerException("上传学习信息列表为空，插入失败");
        }
        for(Student student: studentList) {
            Map<String, Object> params = new HashMap<>();
            params.put("uid",student.getId());
            params.put("rid",3);
            roleMapper.addRole(params);
        }
        logger.info("学生信息上传成功");
        studentMapper.uploadStudent(studentList);
        return "success";
    }

    @Override
    public List<Student> getAllStudent() {
        List<Student> studentList = studentMapper.getAllStudent();
        if(studentList==null||studentList.equals("")) {
            throw new NullPointerException("查询错误，getAllStudent返回信息为空");
        }
        logger.info("查询学生信息成功");
        return studentList;
    }

    @Override
    public void updateStudent(Student student) {
        if(student==null||student.equals("")) {
            throw new NullPointerException("updateStudent传入参数为空，学生信息更新失败");
        }
        studentMapper.updateStudent(student);
        logger.info("学生信息修改成功");
    }

    @Override
    public void addStudent(Student student) {
        if(student==null||student.equals("")) {
            throw new NullPointerException("addStudent传入为空，添加学生失败");
        }
        Map<String, Object> stringObjectMap = new HashMap<>();
        stringObjectMap.put("id", student.getId());
        stringObjectMap.put("name", student.getName());
        stringObjectMap.put("sex", student.getSex());
        stringObjectMap.put("age", student.getAge());
        stringObjectMap.put("birth", student.getBirth());
        stringObjectMap.put("rid", "3");
        stringObjectMap.put("password","$2a$10$RMuFXGQ5AtH4wOvkUqyvuecpqUSeoxZYqilXzbz50dceRsga.WYiq");
        stringObjectMap.put("enabled", "1");
        stringObjectMap.put("locked", "0");
        studentMapper.addStudent(stringObjectMap);
        roleMapper.addStudentRole(stringObjectMap);
        userMapper.addStuUser(stringObjectMap);
        logger.info("添加学生信息成功");
    }

    @Override
    public void deleteStudent(int id) {
        logger.info("删除学生信息开始");
        studentMapper.deleteStudentById(id);
        roleMapper.deleteRole(id);
        userMapper.deleteById(id);
        logger.info("删除学生信息成功");

    }

    @Override
    public Student getStudentById(int id) {
        logger.info("getStudentById,查询学生信息开始");
        Student student = studentMapper.selectStudentById(id);
        if(student==null||student.equals("")) {
            throw new NullPointerException("selectStudentById返回为空，查询错误");
        }
        logger.info("查询学生信息成功");
        return student;
    }
}
