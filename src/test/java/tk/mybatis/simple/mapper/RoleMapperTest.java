package tk.mybatis.simple.mapper;

import org.apache.ibatis.session.SqlSession;
import org.junit.Assert;
import org.junit.Test;
import tk.mybatis.simple.model.SysPrivilege;
import tk.mybatis.simple.model.SysRole;
import tk.mybatis.simple.type.Enabled;

import java.util.List;

public class RoleMapperTest extends BaseMapperTest {

    @Test
    public void testSelectRoleById() {
        SqlSession sqlSession = getSqlSession();
        try {
            final RoleMapper mapper = sqlSession.getMapper(RoleMapper.class);
            final SysRole sysRole = mapper.selectRoleById(1L);
            Assert.assertNotNull(sysRole);
        }finally {
            sqlSession.close();
        }
    }

    @Test
    public void testSelectAllRoleAndPrivileges() {
        final SqlSession sqlSession = getSqlSession();
        try {
            final RoleMapper mapper = sqlSession.getMapper(RoleMapper.class);
            final List<SysRole> sysRoles = mapper.selectAllRoleAndPrivileges();
            Assert.assertEquals(2,sysRoles.size());
        }finally {
            sqlSession.close();
        }
    }

    @Test
    public void testSelectRoleByUserId() {
        final SqlSession sqlSession = getSqlSession();
        try {
            final RoleMapper mapper = sqlSession.getMapper(RoleMapper.class);
            final List<SysRole> sysRoles = mapper.selectRoleByUserId(1001L);
            Assert.assertNotNull(sysRoles);
            Assert.assertEquals(1,sysRoles.size());
            for (SysRole sysRole : sysRoles) {
                Assert.assertNotNull(sysRole.getPrivilegeList());
            }
        }finally {
            sqlSession.close();
        }
    }

    @Test
    public void testUpdateByIdSelective() {
        final SqlSession sqlSession = getSqlSession();
        try {
            final RoleMapper mapper = sqlSession.getMapper(RoleMapper.class);
            final SysRole sysRole = mapper.selectRoleById(1L);
            Assert.assertEquals(Enabled.enabled, sysRole.getEnabled());
            sysRole.setEnabled(Enabled.disabled);
            mapper.updateByIdSelective(sysRole);
        }finally {
            sqlSession.rollback();
            sqlSession.close();
        }
    }

    @Test
    public void testSelectRoleByUserIdChoose() {
        final SqlSession sqlSession = getSqlSession();
        try {
            final RoleMapper mapper = sqlSession.getMapper(RoleMapper.class);
            final SysRole sysRole = mapper.selectRoleById(2L);
            sysRole.setEnabled(Enabled.disabled);
            sysRole.setRoleName("测试用户");
            mapper.updateByIdSelective(sysRole);
            final List<SysRole> sysRoles = mapper.selectRoleByUserIdChoose(1L);
            for (SysRole role : sysRoles) {
                System.out.println("角色名：" + role.getRoleName());
                if (role.getId().equals(1L)) {
                    Assert.assertNotNull(role.getPrivilegeList());
                } else if (role.getId().equals(2L)) {
                    Assert.assertNull(role.getPrivilegeList());
                    continue;
                }
                for (SysPrivilege sysPrivilege : role.getPrivilegeList()) {
                    System.out.println("权限名：" + sysPrivilege.getPrivilegeName());
                }
            }
        }finally {
            sqlSession.rollback();
            sqlSession.close();
        }
    }
}
