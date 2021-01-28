package tk.mybatis.simple.mapper;

import org.apache.ibatis.annotations.Param;
import tk.mybatis.simple.model.SysRole;
import tk.mybatis.simple.model.SysUser;

import java.util.List;
import java.util.Map;

public interface UserMapper {

    SysUser selectById(Long id);

    List<SysUser> selectAll();

    List<SysRole> selectRolesByUserId(Long userId);

    SysUser selectUserAndRoleById(Long userId);

    List<SysUser> selectAllUserAndRoles();

    SysUser selectAllUserAndRolesSelect(Long userId);

    void selectUserById(SysUser user);

    List<SysUser> selectUserPage(Map<String, Object> params);

    int insertUserAndRoles(@Param("user") SysUser user, @Param("roleIds") String roleIds);

    int deleteUserById(Long UserId);
}
