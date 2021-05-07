package demo4.Controller;

import com.alibaba.fastjson.JSON;
import demo4.Service.ResourceService;
import demo4.pojo.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ResponseBody
@Controller
public class ResourceController {
    @Autowired
    private ResourceService resourceService;

    @GetMapping("/getResource")
    public String getAllResource() {
        List<Resource> resourceList = resourceService.getAllResource();
        String resq = JSON.toJSONString(resourceList);
        return resq;
    }

    @PostMapping("/deleteResource")
    public String deleteResource(@RequestBody int id) {
        resourceService.deleteResource(id);
        return "200";
    }

    @GetMapping("/getResourceTable")
    public String getResourceTable() {
        List<Resource> resourceList = resourceService.getAllResource();
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("code", "0");
        resultMap.put("", "");
        resultMap.put("count", "100");
        resultMap.put("data", resourceList);
        String resq = JSON.toJSONString(resultMap);
        return resq;
    }

    @RequestMapping("/editResource")
    public ModelAndView editResource(@RequestParam("resid") int id, @RequestParam("resname") String title,
                                     @RequestParam("resname2") String title2, @RequestParam("resintro") String intro,
                                     @RequestParam("file") String file) {

        Resource resource = new Resource(id,title,title2,intro,file);
        resourceService.updateResource(resource);
        ModelAndView mv = new ModelAndView();
        mv.addObject("status","success");
        mv.setViewName("/view/resourceManage");
        return mv;
    }

    @RequestMapping("/addResource")
    private ModelAndView addResource(@RequestParam("resname") String title, @RequestParam("resname2") String title2,
                                    @RequestParam("resintro") String intro, @RequestParam("file") String file) {
        Resource resource = new Resource(title, title2, intro, file);
        resourceService.addResource(resource);
        ModelAndView mv = new ModelAndView();
        mv.addObject("status","success");
        mv.setViewName("/view/resourceManage");
        return mv;
    }

    @GetMapping("/downloadRes/{id}")
    public String delete(@PathVariable("id")int id, HttpServletResponse response ) throws IOException, IllegalAccessException {
        String sysPath = System.getProperty("user.dir")+"\\src\\main\\resources\\public\\";
        String fileName = resourceService.getPath(id);
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
            File targetFile = new File(sysPath,fileName);
/*        //输出流开始写出文件（FileUtils是Apache下的工具类可以直接调用）
        stream.write(FileUtils.readFileToByteArray(targetFile));*/
            //刷新流
            stream.flush();
        } finally {
            stream.close();
        }
        return "sucess";
    }

    @RequestMapping(value="uploadRes",method= RequestMethod.POST,produces="application/json;charset=utf-8")
    public Map<String, Object> uploadResource(HttpServletRequest request, HttpServletResponse response, HttpSession session, MultipartFile file) throws Exception {
        if (null == file || file.isEmpty()) {
            throw new NullPointerException("上传文件为空，上传失败");
        }
        Map<String, Object> resq = new HashMap<String, Object>();
        String fileName = file.getOriginalFilename();
        String filePath = System.getProperty("user.dir")+"\\src\\main\\resources\\public\\";
        File dest = new File(filePath, fileName);
        if (!dest.exists()) {
            dest.mkdirs();
        }
        try {
            file.transferTo(dest);
            resq.put("status", "success");
            resq.put("fileName", fileName);
            return resq;

        } catch (Exception e) {
            e.printStackTrace();
            resq.put("status", "fail");
            return resq;
        }
    }

    @RequestMapping(value="uploadResAgain",method= RequestMethod.POST,produces="application/json;charset=utf-8")
    public Map<String, Object> uploadResAgain(HttpServletRequest request, HttpServletResponse response, HttpSession session, MultipartFile file) throws Exception {
        if (null == file || file.isEmpty()) {
            throw new NullPointerException("上传文件为空，上传失败");
        }
        Map<String, Object> resq = new HashMap<String, Object>();
        String fileName = file.getOriginalFilename();
        String filePath = System.getProperty("user.dir")+"\\src\\main\\resources\\public\\";
        File dest = new File(filePath, fileName);
        if (!dest.exists()) {
            dest.mkdirs();
        }
        try {
            file.transferTo(dest);
            resq.put("status", "success");
            resq.put("fileName", fileName);
            return resq;

        } catch (Exception e) {
            e.printStackTrace();
            resq.put("status", "fail");
            return resq;
        }
    }

}
