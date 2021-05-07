package demo4.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    private int uid;
    private String name;
    private String text;
    @JSONField(format = "yyyy-MM-dd")
    private Date time;
    private String leaveMsg;
}
