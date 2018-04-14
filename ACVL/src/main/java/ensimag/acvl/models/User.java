package ensimag.acvl.models;

import java.util.Arrays;

public class User {

    private final String name;
    private final byte[] pwd;

    public User(String name, byte[] pwd) {
        this.name = name;
        this.pwd = Arrays.copyOf(pwd, 32);
    }

    public String getName() {
        return this.name;
    }
    
    public boolean passwordMatch(byte[] password) {
        return Arrays.equals(this.pwd, password);
    }

    @Override
    public String toString() {
        return "User{name=" + name + "\"}";
    }
}
