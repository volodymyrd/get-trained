package online.gettrained.backend;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@Transactional
@SpringBootTest(classes = SharedModule.class)
@AutoConfigureTestDatabase(replace = NONE)
@Sql({"file:../config/integration-tests/sql/test-user-data.sql"})
//@TestPropertySource(locations = {
//"file:../config/integration-tests/application-backend-test.properties",
//"file:../config/integration-tests/application-integration-test-mysql.properties",
//"file:../config/integration-tests/application-integration-test-postgresql.properties"
//})
public class BaseITest {

}
