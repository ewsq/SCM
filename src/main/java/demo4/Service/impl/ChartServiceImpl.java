package demo4.Service.impl;

import com.alibaba.fastjson.JSON;
import demo4.Mapper.StudentMapper;
import demo4.Service.ChartService;
import demo4.pojo.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ChartServiceImpl implements ChartService {
    @Autowired
    private StudentMapper studentMapper;

    @Override
    public List<Map<String, Object>> getSex() {
        List<Student> studentList = studentMapper.getAllStudent();
        List<Map<String, Object>> maps = new ArrayList<>();
        Map<String, Object> map1 = new HashMap<>();
        Map<String, Object> map2 = new HashMap<>();
        int men = 0;
        int women = 0;
        for(Student student : studentList) {
            if(student.getSex().equals("男")) {
                men++;
            }
            if(student.getSex().equals("女")) {
                women++;
            }
        }
        map1.put("value",men);
        map1.put("name","男");
        map2.put("value",women);
        map2.put("name","女");
        maps.add(map1);
        maps.add(map2);
        return maps;
    }

    @Override
    public String getAge() {
        List<Student> studentList = studentMapper.getAllStudent();
        Map<String, Object> resultMap = new HashMap<>();
        int age16 = 0;
        int age16To18 = 0;
        int age18To20 = 0;
        int age20To = 0;
        int age22 = 0;
        for(Student student : studentList) {
            if(student.getAge()<=16) {
                age16++;
            }
            if(student.getAge()>16&&student.getAge()<=18) {
                age16To18++;
            }
            if(student.getAge()>18&&student.getAge()<=20) {
                age18To20++;
            }
            if(student.getAge()>20&&student.getAge()<=22) {
                age20To++;
            }
            if(student.getAge()>22) {
                age22++;
            }
        }
        String [] categories = new String[]{"16以下","16-18","18-20","20-22","22以上"};
        int [] data = new int[]{age16,age16To18,age18To20,age20To,age22};
        resultMap.put("categories",categories);
        resultMap.put("data",data);
        String resq = JSON.toJSONString(resultMap);
        return resq;
    }
}
