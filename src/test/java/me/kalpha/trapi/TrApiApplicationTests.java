package me.kalpha.trapi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
class TrApiApplicationTests {
    @Autowired
    DataSource dataSource;

    @Test
    public void contextLoads() throws Exception{
        System.out.println("============================================================");
        System.out.println(">>>>>>> Hikari Datasource: {}" + dataSource.getConnection().getMetaData());
        System.out.println("============================================================");
    }
}