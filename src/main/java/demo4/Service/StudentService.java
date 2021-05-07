package demo4.Service;

import demo4.pojo.Student;

import java.util.List;


public interface StudentService {
    String uploadStudentList(List<Student> studentList);

    List<Student> getAllStudent();

    void updateStudent(Student student);

    void addStudent(Student student) ;

    void deleteStudent(int id);

    Student getStudentById(int id);
}
