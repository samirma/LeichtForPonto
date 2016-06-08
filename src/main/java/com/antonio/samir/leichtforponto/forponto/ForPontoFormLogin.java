package com.antonio.samir.leichtforponto.forponto;

import com.antonio.samir.leichtforponto.FormLogin;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class ForPontoFormLogin implements FormLogin {

    private final static String CONFIG_FILE = "forponto.properties";
    public static final String TAG = ForPontoFormLogin.class.getName();

    private PropertiesConfiguration config;

    private String login;
    private String password;
    private String server;

    @Autowired
    public Environment env;

    @PostConstruct
    public void init() {
        try {
            config = new PropertiesConfiguration(CONFIG_FILE);
        } catch (ConfigurationException ex) {
            Logger.getLogger(TAG).log(Level.SEVERE, ex.getMessage(), ex);
        }

        login = config.getString("login");
        password = config.getString("password");
        server = config.getString("server");

    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getServer() {
        return server;
    }

}
