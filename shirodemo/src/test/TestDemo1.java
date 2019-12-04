
import com.alibaba.druid.pool.DruidDataSource;
import org.apache.catalina.realm.JDBCRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Assert;
import org.junit.Test;

import javax.validation.constraints.AssertTrue;

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
        System.out.println(subject.isPermittedAll("user:create","user:update1"));
//        subject.checkPermission();
    }
}
