package tk.mybatis.simple.mapper;

import org.apache.ibatis.annotations.CacheNamespaceRef;
import tk.mybatis.simple.model.SysRole;

import java.util.List;

@CacheNamespaceRef(RoleMapper.class)
public interface RoleMapper {

    SysRole selectRoleById(Long roleId);

    List<SysRole> selectAllRoleAndPrivileges();

    List<SysRole> selectRoleByUserId(Long userId);

    List<SysRole> selectRoleByUserIdChoose(Long userId);

    int updateByIdSelective(SysRole sysRole);
}
