package demo4.Controller;

import com.alibaba.fastjson.JSON;
import demo4.Service.CommentService;

import demo4.pojo.AddComment;
import demo4.pojo.Comment;
import demo4.pojo.UserComment;
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

@Controller
@ResponseBody
public class CommentController {
    @Autowired
    private CommentService commentService;

    @GetMapping("/getAllMsg")
    public String getAllMsg() {
        List<Comment> msgList =  commentService.getAllMsg();
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("code", "0");
        resultMap.put("", "");
        resultMap.put("count", "100");
        resultMap.put("data", msgList);
        String resq = JSON.toJSONString(resultMap);
        return resq;
    }

    @PostMapping("/addComment")
    public int addComment(@RequestBody AddComment addComment) {
        commentService.addComment(addComment);
        return 0;
    }

    @PostMapping("/comment")
    public int comment(@RequestBody UserComment userComment) throws ParseException {
        commentService.comment(userComment);
        return 0;
    }

    @PostMapping("/deleteComment")
    public String deleteRole(@RequestBody String param) {
        int id = Integer.parseInt(param);
        commentService.deleteCommnet(id);
        return "200";
    }

    @RequestMapping("/editComment")
    private ModelAndView editStudent(@RequestParam("userid") int id, @RequestParam("username") String name, @RequestParam("usercomment") String text,
                                     @RequestParam("commenttime") String time, @RequestParam("leavemsg") String leaveMsg) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        try {
            //使用SimpleDateFormat的parse()方法生成Date
            date = sf.parse(time);
            //打印Date
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Comment comment = new Comment(id,name,text,date,leaveMsg);
        commentService.updateComment(comment);
        ModelAndView mv = new ModelAndView();
        mv.addObject("status","success");
        mv.setViewName("/view/comment");
        return mv;
    }
}
