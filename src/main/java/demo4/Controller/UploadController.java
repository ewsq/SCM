package demo4.Controller;

import demo4.Service.StudentService;
import demo4.Service.UserService;
import demo4.Util.ExcelReader;
import demo4.pojo.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Controller
public class UploadController {
    private final static Logger logger = LoggerFactory.getLogger(UploadController.class);

    @Autowired
    private StudentService studentService;

    //资源上传
    @RequestMapping(value="excelUpload",method= RequestMethod.POST,produces="application/json;charset=utf-8")
    @ResponseBody//json交互注解
    public Map<String, Object> uploadResource(HttpServletRequest request, HttpServletResponse response, HttpSession session, MultipartFile file) throws Exception{
        if (null == file||file.isEmpty()) {
            logger.warn("前台上传Excel文件为空");
            throw new NullPointerException("上传失败");
        }
        Map<String, Object> resqDate = new HashMap<String, Object>();
        String fileName = file.getOriginalFilename();
        logger.info(System.getProperty("user.dir") );
        /*String filePath = "E:\\"+"001.png";*/
        String filePath = System.getProperty("user.dir") + "\\src\\main\\resources\\public\\\\ExcelUpload";
        File dest = new File(filePath,fileName);
        if(!dest.exists()){
            dest.mkdirs();
        }
        try {
            file.transferTo(dest);
            List<Student> parsedResult = ExcelReader.readExcel(filePath+"\\"+fileName);
            String result = studentService.uploadStudentList(parsedResult);
            logger.info("文件：\""+fileName+"\"上传成功，存放路径是：\""+filePath+"\"");
            logger.info("学生数据插入数据库成功");
            resqDate.put("status",result);
            resqDate.put("file",filePath+fileName);

            return resqDate;

        } catch (Exception e) {
            e.printStackTrace();
            logger.info("上传失败");
            resqDate.put("status","fail");
            resqDate.put("file","N/A");
            return resqDate;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public static void download(HttpServletResponse response ) throws IOException, IllegalAccessException {
        String filePath = System.getProperty("user.dir")+"\\src\\main\\resources\\public\\";
        String fileName = "学生信息上传模板.xlsx";
        byte[] fileNameBytes = fileName.getBytes(StandardCharsets.UTF_8);
        fileName = new String(fileNameBytes, 0, fileNameBytes.length, StandardCharsets.ISO_8859_1);
        response.setCharacterEncoding("utf-8");
        OutputStream stream = response.getOutputStream();
        try {
        //清空下载文件的空白行（空白行是因为有的前端代码编译后产生的）
        response.reset();
        //解决编码问题
        response.setContentType("application/octet-stream; charset=utf-8");
        //设置响应头，把文件名字设置好
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
        //解决编码问题
        response.setContentType("application/octet-stream; charset=utf-8");
        //创建存储的文件对象（文件存储的路径和文件名字）
        File targetFile = new File(filePath,fileName);
/*        //输出流开始写出文件（FileUtils是Apache下的工具类可以直接调用）
        stream.write(FileUtils.readFileToByteArray(targetFile));*/
        //刷新流
        stream.flush();
        } finally {
            stream.close();
        }

    }


}