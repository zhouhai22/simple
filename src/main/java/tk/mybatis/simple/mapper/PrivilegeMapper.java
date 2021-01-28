package tk.mybatis.simple.mapper;

import tk.mybatis.simple.model.SysPrivilege;

import java.util.List;

public interface PrivilegeMapper {

    List<SysPrivilege> selectPrivilegeByRoleId(Integer id);
}
