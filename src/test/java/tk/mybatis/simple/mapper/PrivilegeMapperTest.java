package tk.mybatis.simple.mapper;

import org.apache.ibatis.session.SqlSession;
import org.junit.Assert;
import org.junit.Test;
import tk.mybatis.simple.model.SysPrivilege;

import java.util.List;

public class PrivilegeMapperTest extends BaseMapperTest {

    @Test
    public void testSelectPrivilegeByRoleId() {
        final SqlSession sqlSession = getSqlSession();
        try {
            final PrivilegeMapper mapper = sqlSession.getMapper(PrivilegeMapper.class);
            final List<SysPrivilege> sysPrivileges = mapper.selectPrivilegeByRoleId(1);
            Assert.assertNotNull(sysPrivileges);
            Assert.assertEquals(3, sysPrivileges.size());
        }finally {
            sqlSession.close();
        }
    }
}
