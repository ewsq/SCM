package demo4.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Resource {
    private int id;
    private String title;
    private String title2;
    private String intro;
    private String filepath;

    public Resource(String title, String title2, String intro, String filepath) {
        this.title = title;
        this.title2 = title2;
        this.intro = intro;
        this.filepath = filepath;
    }
}
