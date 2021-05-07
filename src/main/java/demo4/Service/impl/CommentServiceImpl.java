package demo4.Service.impl;

import demo4.Mapper.CommentMapper;
import demo4.Mapper.StudentMapper;
import demo4.Service.CommentService;
import demo4.pojo.AddComment;
import demo4.pojo.Comment;
import demo4.pojo.Student;
import demo4.pojo.UserComment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    private final static Logger logger = LoggerFactory.getLogger(CommentServiceImpl.class);

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private StudentMapper studentMapper;

    @Override
    public List<Comment> getAllMsg() {
        logger.info("getAllMsg，查询所有留言");
        List<Comment> comList = commentMapper.getAllMsg();
        if(comList==null||comList.equals("")) {
            throw new NullPointerException("查询所有留言getAllMsg返回为空，查询失败");
        }
        return comList;
    }

    @Override
    public int addComment(AddComment addComment) {
        logger.info("addComment，添加回复");
        commentMapper.addComment(addComment);
        return 0;
    }

    @Override
    public int comment(UserComment userComment) throws ParseException {
        if(userComment==null||userComment.equals("")) {
            throw new NullPointerException("前台传入参数usercomment为空，留言失败");
        }
        logger.info("comment，留言");
        Student student = studentMapper.getStudentByName(userComment.getUsername());
        if(student==null||student.equals("")) {
            throw new NullPointerException("getStudentByName返回结果为空，留言失败");
        }
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
        String time = ft.format(Calendar.getInstance().getTime());
        Date date = ft.parse(time);
        Comment comment = new Comment();
        comment.setUid(student.getId());
        comment.setName(student.getName());
        comment.setText(userComment.getComment());
        comment.setTime(date);
        System.out.println(comment.getUid());
        Comment com = commentMapper.selectCommentById(comment.getUid());
        if(com!=null) {
            commentMapper.updateComment(comment);
        }else {
            commentMapper.comment(comment);
        }
        return 0;
    }

    @Override
    public void deleteCommnet(int id) {
        logger.info("删除留言信息开始");
        commentMapper.deleteCommentById(id);
        logger.info("删除留言信息成功");
    }

    @Override
    public void updateComment(Comment comment) {
        if(comment==null||comment.equals("")) {
            throw new NullPointerException("updateComment入参数为空，留言信息修改失败");
        }
        commentMapper.updateComment(comment);
        logger.info("留言信息修改成功");
    }
}
