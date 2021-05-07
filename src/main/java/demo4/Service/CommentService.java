package demo4.Service;

import demo4.pojo.AddComment;
import demo4.pojo.Comment;
import demo4.pojo.UserComment;

import java.text.ParseException;
import java.util.List;

public interface CommentService {
    List<Comment> getAllMsg();

    int addComment(AddComment addComment);

    int comment(UserComment userComment) throws ParseException;

    void deleteCommnet(int id);

    void updateComment(Comment comment);
}
