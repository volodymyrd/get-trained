package online.gettrained.frontend;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_CLASS;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import javax.servlet.http.HttpServletRequest;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.DatabaseException;
import liquibase.integration.spring.SpringLiquibase;
import liquibase.util.StringUtils;
import online.gettrained.backend.domain.user.User;
import online.gettrained.backend.repositories.localization.LanguageRepository;
import online.gettrained.backend.repositories.localization.LocalizationRepository;
import online.gettrained.backend.repositories.user.UserRepository;
import online.gettrained.backend.services.auth.AuthService;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.cache.CacheManager;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

/**
 * gradle :frontend:integrationTest --tests com.berize.frontend.BaseIntegrationTest -info
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@TestPropertySource(locations = {
    "classpath:application-integration-test.properties",
    "file:../config/integration-tests/application-integration-test-postgresql.properties"
})
@AutoConfigureTestDatabase(replace = NONE)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@DirtiesContext(classMode = AFTER_CLASS)
public class BaseIntegrationTest {

  protected static final long TRAINER_USER_ID = -2;
  protected static final long TRAINEE_USER_ID = -3;
  protected static final long TRAINEE_USER_ID_101 = -101;

  protected String TRAINEE_EMAIL = "trainee@get-trained.online";

  protected static final int HTTP_OK = 200;
  protected static final int HTTP_BAD_REQUEST = 400;
  protected static final int HTTP_UNAUTHORIZED = 401;

  @MockBean
  private CacheManager cacheManager;
  @SpyBean
  protected AuthService authService;

  @MockBean
  protected HttpServletRequest request;

  @Autowired
  protected UserRepository userRepository;
  @Autowired
  protected LocalizationRepository localizationRepository;
  @Autowired
  protected LanguageRepository languageRepository;

  @BeforeClass
  public static void setUpEnv() {
    System.setProperty("testEnvironmentCommon", "TEST-COMMON");
  }

  protected User user;

  protected long getUserId() {
    return TRAINER_USER_ID;
  }

  protected void setUser(long id) {
    user = userRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Not found user with id:" + id));
    doReturn(user).when(authService).getCurrentUserOrException();
    user.setLoginLang(languageRepository.findById("EN")
        .orElseThrow(() -> new RuntimeException("Not found lang with code EN")));
  }

  @Before
  public void setUp() {
    long userId = getUserId();
    user = userRepository.findById(userId)
        .orElseThrow(() -> new RuntimeException("Not found user with id:" + userId));
    doReturn(user).when(authService).getCurrentUserOrException();
    doNothing().when(authService).setCurrentUser(any());
    when(request.getScheme()).thenReturn("http");
    when(request.getServerName()).thenReturn("localhost");
    when(request.getServerPort()).thenReturn(80);
  }

  @Autowired
  private SpringLiquibase springLiquibase;

  @Test
  public void zTearDowns() {
    Liquibase liquibase = null;
    try {
      Connection c = springLiquibase.getDataSource().getConnection();
      SpringLiquibase.SpringResourceOpener resourceAccessor =
          springLiquibase.new SpringResourceOpener(springLiquibase.getChangeLog());
      Database database = DatabaseFactory.getInstance()
          .findCorrectDatabaseImplementation(new JdbcConnection(c));
      if (StringUtils.trimToNull(springLiquibase.getDefaultSchema()) != null) {
        database.setDefaultSchemaName(springLiquibase.getDefaultSchema());
      }
      liquibase = new Liquibase(springLiquibase.getChangeLog(), resourceAccessor, database);
      liquibase.dropAll();
    } catch (Exception e) {

    } finally {
      Database database = null;
      if (liquibase != null) {
        database = liquibase.getDatabase();
      }
      if (database != null) {
        try {
          database.close();
        } catch (DatabaseException e) {
          e.printStackTrace();
        }
      }
    }
  }

  @Test
  @Ignore
  public void test_000_empty() {
  }

  protected static MultipartFile getMultipartFile(File file, String contentType) {
    return new MultipartFile() {
      @Override
      public String getName() {
        return file.getName();
      }

      @Override
      public String getOriginalFilename() {
        return file.getName();
      }

      @Override
      public String getContentType() {
        return contentType;
      }

      @Override
      public boolean isEmpty() {
        return false;
      }

      @Override
      public long getSize() {
        return file.length();
      }

      @Override
      public byte[] getBytes() throws IOException {
        return Files.readAllBytes(Paths.get(file.toURI()));
      }

      @Override
      public InputStream getInputStream() {
        return null;
      }

      @Override
      public void transferTo(File dest) throws IllegalStateException {

      }
    };
  }
}
