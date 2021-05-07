package demo4.Service;

import demo4.pojo.Resource;

import java.util.List;

public interface ResourceService {
    List<Resource> getAllResource();

    void deleteResource(int id);

    void updateResource(Resource resource);

    void addResource(Resource resource);

    String getPath(int id);
}
