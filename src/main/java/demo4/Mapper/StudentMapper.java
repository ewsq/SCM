package demo4.Mapper;

import demo4.pojo.Student;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface StudentMapper {
    int uploadStudent(List<Student> studentList);

    List<Student> getAllStudent();

    Student getStudentByName(String name);

    int updateStudent(Student student);

    void addStudent(Map<String, Object> stringObjectMap);

    void deleteStudentById(int id);

    void updateProfile(Map<String, Object> stringObjectMap);

    Student selectStudentById(int id);
}
