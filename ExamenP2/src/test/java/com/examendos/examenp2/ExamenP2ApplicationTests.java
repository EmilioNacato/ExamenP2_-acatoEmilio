package com.examendos.examenp2;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = {
    "spring.data.mongodb.host=localhost",
    "spring.data.mongodb.port=27019",
    "spring.data.mongodb.database=test_db",
    "spring.mongodb.embedded.version=4.0.21",
    "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration"
})
class ExamenP2ApplicationTests {

    @Test
    void contextLoads() {
    }

}
