package demo4.Mapper;

import demo4.pojo.Resource;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface ResourceMapper {
    List<Resource> getAllResource();

    void deleteResource(int id);

    void updateResource(Resource resource);

    void addResource(Resource resource);

    String getPathById(int id);

    String getFileById(int id);
}
