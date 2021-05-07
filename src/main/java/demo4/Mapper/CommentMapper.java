package demo4.Mapper;

import demo4.pojo.AddComment;
import demo4.pojo.Comment;
import demo4.pojo.UserComment;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface CommentMapper {
    List<Comment> getAllMsg();

    int addComment(AddComment addComment);

    int comment(Comment comment);

    void deleteCommentById(int id);

    void updateComment(Comment comment);

    void updateProfile(Map<String, Object> profileMap);

    Comment selectCommentById(int uid);
}
