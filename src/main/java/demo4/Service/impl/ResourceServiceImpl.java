package demo4.Service.impl;

import demo4.Mapper.ResourceMapper;
import demo4.Service.ResourceService;
import demo4.pojo.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service
@Repository
public class ResourceServiceImpl implements ResourceService {
    private Logger logger = LoggerFactory.getLogger(ResourceServiceImpl.class);

    @Autowired
    private ResourceMapper resourceMapper;

    @Override
    public List<Resource> getAllResource() {
        logger.info("getAllResource查询开始");
        List<Resource> resourceList = resourceMapper.getAllResource();
        if(resourceList==null||resourceList.equals("")) {
            logger.info("getAllResource查询失败");
            throw new NullPointerException("getAllResource查询结果返回为空，查询错误");
        }
        logger.info("getAllResource查询成功");
        return resourceList;
    }

    @Override
    public void deleteResource(int id) {
        String sysPath = System.getProperty("user.dir")+"\\src\\main\\resources\\public\\";
        String fileName = resourceMapper.getPathById(id);
        File dest = new File(sysPath, fileName);
        if (dest.exists()) {
            dest.delete();
        }
        logger.info("deleteResource资源删除开始");
        resourceMapper.deleteResource(id);
        logger.info("getAllResource查询成功");
    }

    @Override
    public void updateResource(Resource resource) {
        if(resource==null||resource.equals("")) {
            logger.info("updateResource传入参数为空，修改失败");
            throw new NullPointerException("updateResource传入参数为空");
        }
        String sysPath = System.getProperty("user.dir")+"\\src\\main\\resources\\public\\";
        String fileName = resource.getFilepath();
        File dest = new File(sysPath, fileName);
        if (dest.exists()) {
            dest.delete();
        }
        logger.info("updateResource资源修改开始");
        resourceMapper.updateResource(resource);
        logger.info("updateResource资源修改成功");
    }

    @Override
    public void addResource(Resource resource) {
        if(resource==null||resource.equals("")) {
            logger.info("updateResource传入参数为空，修改失败");
            throw new NullPointerException("updateResource传入参数为空");
        }
        logger.info("addResource资源添加开始");
        resourceMapper.addResource(resource);
        logger.info("addResource资源添加成功");
    }

    @Override
    public String getPath(int id) {
        logger.info("getPathById资源路径查询开始");
        String path = resourceMapper.getPathById(id);
        logger.info("getPathById资源路径查询成功");
        return path;
    }
}
