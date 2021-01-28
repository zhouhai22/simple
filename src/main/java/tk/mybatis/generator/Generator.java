package tk.mybatis.generator;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Generator {
    public static void main(String[] args) throws Exception {
        List<String> warnings = new ArrayList<>();
        InputStream is = Generator.class.getResourceAsStream("/generator/generatorConfig.xml");
        Configuration configuration = new ConfigurationParser(warnings).parseConfiguration(is);
        is.close();

        DefaultShellCallback callback = new DefaultShellCallback(true);
        new MyBatisGenerator(configuration,callback,warnings).generate(null);

        for (String warning : warnings) {
            System.out.println(warning);
        }
    }
}
