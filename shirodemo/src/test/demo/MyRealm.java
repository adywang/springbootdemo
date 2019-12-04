package demo;

import org.apache.shiro.authc.*;
import org.apache.shiro.realm.Realm;

public class MyRealm implements Realm {
    @Override
    public String getName() {
        return "myrealm1";
    }

    @Override
    public boolean supports(AuthenticationToken authenticationToken) {
        return  authenticationToken instanceof UsernamePasswordToken;
    }

    @Override
    public AuthenticationInfo getAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String username= (String) authenticationToken.getPrincipal();
        String obj=new String((char[])(authenticationToken.getCredentials()));
        String password= obj;
        if(!username.equals("zhang")){
            throw new UnknownAccountException("用户{"+username+"}不存在");
        }
        if(!password.equals("123456")){
            throw new  IncorrectCredentialsException("密码错误");
        }
        return new SimpleAuthenticationInfo(username,password,getName());
    }
}
