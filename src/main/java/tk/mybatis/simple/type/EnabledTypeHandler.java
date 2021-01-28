package tk.mybatis.simple.type;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class EnabledTypeHandler implements TypeHandler<Enabled> {

    private final Map<Integer, Enabled> enabledMap = new HashMap<>();

    public EnabledTypeHandler() {
        for (Enabled enabled : Enabled.values()) {
            enabledMap.put(enabled.getValue(), enabled);
        }
    }

    @Override
    public void setParameter(PreparedStatement preparedStatement, int i, Enabled enabled, JdbcType jdbcType) throws SQLException {
        preparedStatement.setInt(i, enabled.getValue());
    }

    @Override
    public Enabled getResult(ResultSet resultSet, String s) throws SQLException {
        return enabledMap.get(resultSet.getInt(s));
    }

    @Override
    public Enabled getResult(ResultSet resultSet, int i) throws SQLException {
        return enabledMap.get(resultSet.getInt(i));
    }

    @Override
    public Enabled getResult(CallableStatement callableStatement, int i) throws SQLException {
        return enabledMap.get(callableStatement.getInt(i));
    }
}
