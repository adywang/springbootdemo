
import com.alibaba.druid.pool.DruidDataSource;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.DefaultPasswordService;
import org.apache.shiro.authc.pam.AtLeastOneSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.authz.ModularRealmAuthorizer;
import org.apache.shiro.authz.permission.WildcardPermissionResolver;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.*;
import org.apache.shiro.crypto.hash.format.DefaultHashFormatFactory;
import org.apache.shiro.crypto.hash.format.Shiro1CryptFormat;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.Factory;
import org.junit.Assert;
import org.junit.Test;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.DecimalMax;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static org.assertj.core.util.Arrays.*;

public class TestDemo1 {
    @Test
    public void  test1(){
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");
       SecurityManager securityManager= factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);
        Subject subject=SecurityUtils.getSubject();
        UsernamePasswordToken usernamePasswordToken=new UsernamePasswordToken("zhang1","123456");
        try{
            subject.login(usernamePasswordToken);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        Assert.assertTrue(subject.isAuthenticated());
        subject.logout();
    }
    @Test
    public void  test2(){
        Factory<SecurityManager> factory=new IniSecurityManagerFactory("classpath:shiro-realm.ini");
        SecurityManager securityManager= factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);
        Subject subject=SecurityUtils.getSubject();
        UsernamePasswordToken usernamePasswordToken=new UsernamePasswordToken("zhang","123456");
        try{
            subject.login(usernamePasswordToken);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        Assert.assertTrue(subject.isAuthenticated());
        subject.logout();
    }
    @Test
    public void  testJdbc(){
        Factory<SecurityManager> factory=new IniSecurityManagerFactory("classpath:shiro-jdbc.ini");
        SecurityManager securityManager= factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);
         UsernamePasswordToken usernamePasswordToken=new UsernamePasswordToken("zhang","123456");
         Subject subject=SecurityUtils.getSubject();
         try{

             subject.login(usernamePasswordToken);
         }catch (Exception ex){
             ex.printStackTrace();
         }
    }
    @Test
    public  void  testRole(){
        Factory<SecurityManager> factory=new IniSecurityManagerFactory("classpath:shiro-role.ini");
        SecurityManager securityManager= factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);
        UsernamePasswordToken usernamePasswordToken=new UsernamePasswordToken("zhang","123");
        Subject subject=SecurityUtils.getSubject();
        subject.login(usernamePasswordToken);
        Assert.assertTrue(subject.hasRole("role1"));
        Assert.assertTrue(subject.hasRole("role2"));
        Assert.assertTrue(subject.hasRole("role32"));
    }
    @Test
    public  void  testPermission(){
        Factory<SecurityManager> factory=new IniSecurityManagerFactory("classpath:shiro-perm.ini");
        SecurityManager securityManager= factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);
        UsernamePasswordToken usernamePasswordToken=new UsernamePasswordToken("zhang","123");
        Subject subject=SecurityUtils.getSubject();
        subject.login(usernamePasswordToken);
        System.out.println(subject.isPermitted("user:create"));
        System.out.println(subject.isPermittedAll("user:create","user:update"));
//        subject.checkPermission();
    }
    @Test
    public  void  testEncode(){
       System.out.println(Base64.encodeToString("12345".getBytes()));
       System.out.println(Base64.encodeToString("admin123456".getBytes()));
    }
    @Test
    public void  md5encode(){
        String str="hello";
        String salt="123456";
        Md5Hash md5Hash=new Md5Hash();
        md5Hash.setBytes(str.getBytes());
        md5Hash.setSalt(ByteSource.Util.bytes(salt));
        md5Hash.setIterations(2);
        System.out.println(md5Hash.toString());
        System.out.println(md5Hash.toHex());
        System.out.println(md5Hash.toBase64());
    }
    @Test
    public  void  simpleHashDemo(){
        String str="hello";
        String salt="123456";
        SimpleHash simpleHash=new SimpleHash("md5",str,salt,2);
        System.out.println(simpleHash.toHex());
    }
    @Test
    public void hashserviceDemo(){
        String str="hello";
        String salt="123456";
        DefaultHashService defaultHashService=new DefaultHashService();
        defaultHashService.setPrivateSalt(ByteSource.Util.bytes(salt));
        defaultHashService.setGeneratePublicSalt(true);
        defaultHashService.setRandomNumberGenerator(new SecureRandomNumberGenerator());
        defaultHashService.setHashIterations(2);
        HashRequest hashRequest=new HashRequest.Builder().setSource(ByteSource.Util.bytes(str))
                .setAlgorithmName("md5").setIterations(2)
                .setSalt(ByteSource.Util.bytes(salt)).build();
        String hex=defaultHashService.computeHash(hashRequest).toHex();
        System.out.println(hex);
    }
    @Test
    public  void  initTest1(){
        DefaultSecurityManager securityManager=new DefaultSecurityManager();
        ModularRealmAuthenticator authenticator=new ModularRealmAuthenticator();
        authenticator.setAuthenticationStrategy(new AtLeastOneSuccessfulStrategy());
        securityManager.setAuthenticator(authenticator);


        ModularRealmAuthorizer authorizer=new ModularRealmAuthorizer();
        authorizer.setPermissionResolver(new WildcardPermissionResolver());
        securityManager.setAuthorizer(authorizer);

        DruidDataSource dataSource=new DruidDataSource();
        dataSource.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        dataSource.setUsername("sa");
        dataSource.setPassword("123456");
        dataSource.setUrl("jdbc:sqlserver://192.168.1.8:1433;DatabaseName=OverViewDB");
        JdbcRealm jdbcRealm=new JdbcRealm();
        jdbcRealm.setDataSource(dataSource);
        jdbcRealm.setPermissionsLookupEnabled(true);
        authenticator.setRealms(Arrays.<Realm>asList(jdbcRealm));
        SecurityUtils.setSecurityManager(securityManager);

        Subject subject=SecurityUtils.getSubject();
        subject.login(new UsernamePasswordToken("zhang","123456"));
        System.out.println(subject.isAuthenticated());
    }
    @Test
    public  void  iniConfigTest(){
        IniSecurityManagerFactory factory=new IniSecurityManagerFactory("classpath:shiro-config.ini");
        SecurityManager securityManager=factory.createInstance();
        SecurityUtils.setSecurityManager(securityManager);
        Subject subject=SecurityUtils.getSubject();
        subject.login(new UsernamePasswordToken("zhang","123456"));
        System.out.println(subject.isAuthenticated());
    }
    @Test
    public  void  encodeTest(){
        String str="hello world";
        String base64Str=Base64.encodeToString(str.getBytes());
        System.out.println(base64Str);
        System.out.println(Base64.decodeToString(base64Str));
    }
    @Test
    public  void  hasTest(){
        SimpleHash simpleHash=new SimpleHash("md5","123456","123456",2);
        System.out.println(simpleHash.toHex());
    }
    @Test
    public  void  hashServiceTest(){
        DefaultHashService hashService=new DefaultHashService();
        hashService.setHashIterations(2);
        hashService.setHashAlgorithmName("md5");
        hashService.setGeneratePublicSalt(false);
        hashService.setPrivateSalt(ByteSource.Util.bytes("123456"));

        HashRequest hashRequest=new HashRequest.Builder()
                .setSalt(ByteSource.Util.bytes("123456"))
                .setIterations(2)
                .setAlgorithmName("md5")
                .setSource(ByteSource.Util.bytes("123456"))
                .setSalt("123456").build();
        String md5str=hashService.computeHash(hashRequest).toHex();
        System.out.println(md5str);
    }
    @Test
    public  void  passwordServiceTest(){
        DefaultHashService hashService=new DefaultHashService();
        hashService.setHashIterations(2);
        hashService.setHashAlgorithmName("md5");
        hashService.setGeneratePublicSalt(false);
        hashService.setPrivateSalt(ByteSource.Util.bytes("123456"));
        DefaultPasswordService passwordService=new DefaultPasswordService();

        passwordService.setHashService(hashService);
        passwordService.setHashFormat(new Shiro1CryptFormat());
        passwordService.setHashFormatFactory(new DefaultHashFormatFactory());
        String pwd=passwordService.encryptPassword(ByteSource.Util.bytes("123456"));
        System.out.println(pwd);
        SimpleAuthenticationInfo authenticationInfo=new SimpleAuthenticationInfo("admin",passwordService.encryptPassword("123456"),"admin");
        System.out.println(authenticationInfo.getPrincipals());
        System.out.println(authenticationInfo.getCredentials());
    }
    @Test
    public  void  passwordHashTest1(){
       System.out.println(passwordHelper("123456","admin"));
    }
    public String passwordHelper(String pwd,String salt){
        DefaultHashService hashService=new DefaultHashService();
        hashService.setPrivateSalt(ByteSource.Util.bytes(salt));
        hashService.setGeneratePublicSalt(false);
        hashService.setHashAlgorithmName("md5");
        hashService.setHashIterations(1);

        HashRequest request=new HashRequest.Builder()
                .setSalt(ByteSource.Util.bytes(salt))
                .setAlgorithmName("md5")
                .setSource(ByteSource.Util.bytes(pwd))
                .setIterations(3).build();
        return hashService.computeHash(request).toHex();
    }

}
