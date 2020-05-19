package com.example.springbootdesign.component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.xml.txw2.NamespaceResolver;
import org.hibernate.collection.internal.PersistentIdentifierBag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

//加密组件
@Component
public class EncryptComponent {
    @Autowired
    private ObjectMapper objectMapper;
    @Value("${my.secretkey}")
    private String secretKey;
    @Value("${my.salt}")
    private String salt;
//  在将密码 和 盐值导入进来之前不可以导入加密算法
    @Autowired
    private TextEncryptor textEncryptor;
    @Bean
    public TextEncryptor getTextEncryptor(){
        return Encryptors.text(secretKey, salt);
    }

    public String encryptToken(MyToken token){
        try {
            String json = objectMapper.writeValueAsString(token);
            return textEncryptor.encrypt(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw  new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"服务器异常");
        }


    }
    public MyToken decryptToken(String json){
        try {
            json=textEncryptor.decrypt(json);
            MyToken myToken=objectMapper.readValue(json, MyToken.class);
            return myToken;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"没有权限");
        }
    }

}
