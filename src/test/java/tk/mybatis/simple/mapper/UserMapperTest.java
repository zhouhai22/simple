package tk.mybatis.simple.mapper;

import org.apache.ibatis.session.SqlSession;
import org.junit.Assert;
import org.junit.Test;
import tk.mybatis.simple.model.SysPrivilege;
import tk.mybatis.simple.model.SysRole;
import tk.mybatis.simple.model.SysUser;

import java.util.HashMap;
import java.util.List;

public class UserMapperTest extends BaseMapperTest {

    @Test
    public void testSelectById() {
        try (SqlSession sqlSession = getSqlSession()) {
            final UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            final SysUser sysUser = userMapper.selectById(1L);
            Assert.assertNotNull(sysUser);
            Assert.assertEquals("admin",sysUser.getUserName());
        }
    }

    @Test
    public void testSelectAll() {
        try (SqlSession sqlSession = getSqlSession()) {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            List<SysUser> userList = userMapper.selectAll();
            Assert.assertNotNull(userList);
            Assert.assertTrue(userList.size() > 0);
        }
    }

    @Test
    public void testSelectRolesByUserId() {
        try (SqlSession sqlSession = getSqlSession()) {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            List<SysRole> roleList = userMapper.selectRolesByUserId(1L);
            for (SysRole sysRole : roleList) {
                System.out.println("sysRole = " + sysRole);
            }
        }
    }

    @Test
    public void testSelectUserAndRoleById() {
        SqlSession sqlSession = getSqlSession();
        try {
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            SysUser sysUser = mapper.selectUserAndRoleById(1001L);
            Assert.assertNotNull(sysUser);
            Assert.assertNotNull(sysUser.getRole());
        }finally {
            sqlSession.close();
        }
    }

    @Test
    public void testSelectAllUserAndRoles() {
        final SqlSession sqlSession = getSqlSession();
        try {
            final UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            final List<SysUser> sysUsers = mapper.selectAllUserAndRoles();
            System.out.println("用户数：" + sysUsers.size());
            for (SysUser sysUser : sysUsers) {
                System.out.println("用户名：" + sysUser.getUserName());
                final List<SysRole> roleList = sysUser.getRoleList();
                for (SysRole sysRole : roleList) {
                    System.out.println("角色名：" + sysRole.getRoleName());
                    for (SysPrivilege sysPrivilege : sysRole.getPrivilegeList()) {
                        System.out.println("权限名：" + sysPrivilege.getPrivilegeName());
                    }
                }

            }
        }finally {
            sqlSession.close();
        }
    }

    @Test
    public void testSelectAllUserAndRolesSelect() {
        final SqlSession sqlSession = getSqlSession();
        try {
            final UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            final SysUser sysUser = mapper.selectAllUserAndRolesSelect(1L);
            Assert.assertNotNull(sysUser);
            final List<SysRole> roleList = sysUser.getRoleList();
            Assert.assertNotNull(roleList);
            Assert.assertTrue(roleList.size() > 0);
            for (SysRole sysRole : roleList) {
                final List<SysPrivilege> privilegeList = sysRole.getPrivilegeList();
                Assert.assertNotNull(privilegeList);
                Assert.assertTrue(privilegeList.size() > 0);
            }
        }finally {
            sqlSession.close();
        }
    }

    @Test
    public void testSelectUserById() {
        final SqlSession sqlSession = getSqlSession();
        try {
            final UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            final SysUser user = new SysUser();
            user.setId(1L);
            mapper.selectUserById(user);
            Assert.assertNotNull(user.getUserName());
            System.out.println("用户名：" + user.getUserName());
        }finally {
            sqlSession.close();
        }
    }

    @Test
    public void testSelectUserPage() {
        final SqlSession sqlSession = getSqlSession();
        try {
            final UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            final HashMap<String, Object> params = new HashMap<>();
            params.put("userName", "ad");
            params.put("offset", 0);
            params.put("limit", 10);
            final List<SysUser> sysUsers = mapper.selectUserPage(params);
            final Long total = (Long) params.get("total");
            System.out.println("总数：" + total);
            for (SysUser sysUser : sysUsers) {
                System.out.println("用户名：" + sysUser.getUserName());
            }
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testInsertUserAndRoles() {
        final SqlSession sqlSession = getSqlSession();
        try {
            final UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            final SysUser sysUser = new SysUser();
            sysUser.setUserName("test1");
            sysUser.setUserPassword("123456");
            sysUser.setUserEmail("test@mybatis.tk");
            sysUser.setUserInfo("test info");
            sysUser.setHeadImg(new byte[]{1,2,3});
            mapper.insertUserAndRoles(sysUser,"1,2");
            Assert.assertNotNull(sysUser.getId());
            Assert.assertNotNull(sysUser.getCreateTime());
            sqlSession.commit();
            mapper.deleteUserById(sysUser.getId());
        }finally {
            sqlSession.close();
        }
    }
}
