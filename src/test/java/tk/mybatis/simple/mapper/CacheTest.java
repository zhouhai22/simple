package tk.mybatis.simple.mapper;

import org.apache.ibatis.session.SqlSession;
import org.junit.Assert;
import org.junit.Test;
import tk.mybatis.simple.model.SysRole;
import tk.mybatis.simple.model.SysUser;

public class CacheTest extends BaseMapperTest {

    @Test
    public void testL1Cache() {
        SqlSession sqlSession = getSqlSession();
        SysUser user1;
        try {
            final UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            user1 = mapper.selectById(1L);
            user1.setUserName("NEW NAME");
            final SysUser user2 = mapper.selectById(1L);
            Assert.assertEquals("NEW NAME", user2.getUserName());
            Assert.assertEquals(user1, user2);
        } finally {
            sqlSession.close();
        }

        System.out.println("开启新的SqlSession");
        sqlSession = getSqlSession();

        try {
            final UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            final SysUser user2 = mapper.selectById(1L);
            Assert.assertNotEquals("NEW NAME", user2.getUserName());
            Assert.assertNotEquals(user1, user2);
            mapper.deleteUserById(2L);
            final SysUser user3 = mapper.selectById(1L);
            Assert.assertNotEquals(user2, user3);
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testL2Cache() {
        SqlSession sqlSession = getSqlSession();
        SysRole role1;
        try {
            final RoleMapper mapper = sqlSession.getMapper(RoleMapper.class);
            role1 = mapper.selectRoleById(1L);
            role1.setRoleName("NEW NAME");
            final SysRole role2 = mapper.selectRoleById(1L);
            Assert.assertEquals("NEW NAME", role2.getRoleName());
            Assert.assertEquals(role1, role2);
        } finally {
            sqlSession.close();
        }
        System.out.println("开启新的SqlSession");
        sqlSession = getSqlSession();
        try {
            final RoleMapper mapper = sqlSession.getMapper(RoleMapper.class);
            final SysRole role2 = mapper.selectRoleById(1L);
            Assert.assertNotEquals(role1, role2);
            final SysRole role3 = mapper.selectRoleById(1L);
            Assert.assertNotEquals(role2, role3);
        } finally {
            sqlSession.close();
        }
    }
}
