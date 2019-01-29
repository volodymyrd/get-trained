package online.gettrained.frontend.config;

import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.authentication.rememberme.RememberMeAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

/**
 * Configure Spring Security
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  private static final Logger LOG = LoggerFactory.getLogger(SecurityConfiguration.class);

  private final UserDetailsService userDetailsService;
  private final DataSource dataSource;
  //private final UserDetailsChecker postAuthenticationChecks;
  private final PasswordEncoder passwordEncoder;
  private final AuthenticationEntryPoint authenticationEntryPoint;
  private final AuthenticationEventPublisher authenticationEventPublisher;

  public SecurityConfiguration(
      UserDetailsService userDetailsService,
      DataSource dataSource,
      PasswordEncoder passwordEncoder,
      AuthenticationEntryPoint authenticationEntryPoint,
      AuthenticationEventPublisher authenticationEventPublisher) {
    this.userDetailsService = userDetailsService;
    this.dataSource = dataSource;
    this.passwordEncoder = passwordEncoder;
    this.authenticationEntryPoint = authenticationEntryPoint;
    this.authenticationEventPublisher = authenticationEventPublisher;
  }

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Bean
  public PersistentTokenRepository persistentTokenRepository() {
    JdbcTokenRepositoryImpl db = new JdbcTokenRepositoryImpl();
    db.setDataSource(dataSource);
    return db;
  }

  @Bean
  public AuthenticationProvider daoAuthenticationProvider() {
    DaoAuthenticationProvider ap = new DaoAuthenticationProvider();
    //ap.setPostAuthenticationChecks(postAuthenticationChecks);
    ap.setUserDetailsService(userDetailsService);
    ap.setPasswordEncoder(passwordEncoder);

    return ap;
  }

  @Override
  public void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.authenticationEventPublisher(authenticationEventPublisher);

    auth.authenticationProvider(daoAuthenticationProvider());
  }

  @Bean
  public PersistentTokenBasedRememberMeServices tokenBasedRememberMeService() {
//        CustomPersistentTokenBasedRememberMeServices service =
//                new CustomPersistentTokenBasedRememberMeServices("qwqwqwqw", userDetailsService, persistentTokenRepository());
    PersistentTokenBasedRememberMeServices service =
        new PersistentTokenBasedRememberMeServices(
            "qwqwqwqw",
            userDetailsService,
            persistentTokenRepository());
    service.setAlwaysRemember(true);
    //set an one day for the token
    service.setTokenValiditySeconds(86400);
    return service;
  }

  @Bean
  public RememberMeAuthenticationFilter rememberMeAuthenticationFilter() throws Exception {
    return new RememberMeAuthenticationFilter(authenticationManager(),
        tokenBasedRememberMeService());
  }

  @Bean
  public BasicAuthenticationFilter basicAuthenticationFilter() throws Exception {
    BasicAuthenticationFilter filter = new BasicAuthenticationFilter(authenticationManagerBean(),
        authenticationEntryPoint);
    filter.setRememberMeServices(tokenBasedRememberMeService());
    return filter;
  }

  @Override
  public void configure(HttpSecurity http) throws Exception {
    http
        //.cors().disable()
        .csrf().disable()
        .authorizeRequests()
        .antMatchers("/fe/auth/checkemailexist").permitAll()
        .antMatchers("/fe/auth/signup").permitAll()
        .antMatchers("/fe/auth/signin").permitAll()
        .antMatchers("/fe/auth/regconfirmation").permitAll()
        .antMatchers("/fe/auth/passwordrestore").permitAll()
        .antMatchers(HttpMethod.OPTIONS).permitAll()
        .antMatchers("/fe/**").authenticated()
        .antMatchers("/actuator/**").hasRole("ADMIN")
        .antMatchers("/**").permitAll()
        //.antMatchers("static/**").permitAll()
        //.antMatchers("metadata/**").permitAll()
        //.and().csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
        .and()
        .addFilter(basicAuthenticationFilter())
        .addFilterBefore(rememberMeAuthenticationFilter(), BasicAuthenticationFilter.class)
        .headers().disable();
  }
}
