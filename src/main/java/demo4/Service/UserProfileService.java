package demo4.Service;

import demo4.pojo.User;

import java.util.Map;

public interface UserProfileService {
    String editProfile(Map<String, Object> profileMap);

    User getUserById(int id);
}
