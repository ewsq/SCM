package demo4.pojo;

import demo4.Mapper.UserMapper;
import demo4.Service.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author hb
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {
    private int id;
    private String username;
    private String password;
    private boolean enabled;
    private boolean locked;

    private List<Role> roleList;

    @Autowired
    private UserMapper userMapper;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<Role> roles = this.getRoleList();
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for(Role role : roles)
        {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return authorities;
        /*return Collections.emptyList();*/
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    //!!!!!!    !locked
    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    //!!!!!!    enable
    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
