package ensimag.acvl.models;

import java.util.Arrays;

public class User {

    private final String name;
    private final byte[] pwd;
    private final String addr;

    public User(String name, byte[] pwd, String addr) {
        this.name = name;
        this.pwd = Arrays.copyOf(pwd, 32);
        this.addr = addr;
    }

    public String getName() {
        return this.name;
    }
    
    public String getAddr() {
        return this.addr;
    }
    
    public boolean passwordMatch(byte[] password) {
        return Arrays.equals(this.pwd, password);
    }

    @Override
    public String toString() {
        return "User{name=" + name + "\"}";
    }
}
