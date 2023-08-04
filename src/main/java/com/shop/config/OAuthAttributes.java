package com.shop.config;

import com.shop.constant.APIRole;
import com.shop.entity.User;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;


@Getter
public class OAuthAttributes {

    private Map<String, Object> attributes;

    private String nameAttributeKey;

    private String name;

    private String email;

    private String picture;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name, String email, String picture){
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
        this.picture = picture;
    }

    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes){

        if("naver".equals(registrationId)){
            return ofNaver("id", attributes);
        }
        if("kakao".equals(registrationId)){
            return ofKaKao(userNameAttributeName, attributes);
        }
        return ofGoogle(userNameAttributeName, attributes);
    }

    private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String,Object> attributes){
        return OAuthAttributes.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .picture((String) attributes.get("picture"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    private static OAuthAttributes ofNaver(String userNameAttributeName, Map<String,Object> attributes){
        Map<String,Object> response = (Map<String, Object>) attributes.get("response");

        return OAuthAttributes.builder()
                .name((String)response.get("name"))
                .email((String)response.get("email"))
                .picture((String) response.get("profile_image"))
                .attributes(response)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    private static OAuthAttributes ofKaKao(String userNameAttributeName,
                                           Map<String,Object> attributes){
        Map<String, Object> kakao_account = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> profile = (Map<String, Object>) kakao_account.get("profile");

        for(String key : kakao_account.keySet()){
            System.out.println(key+"#######");
            System.out.println(kakao_account.get(key)+"*********");
        }

        for(String str : profile.keySet()){
            System.out.println(str+"!!!!!!!!!");
            System.out.println(profile.get(str)+"$$$$$$$$");
        }

        return OAuthAttributes.builder()
                .name((String) profile.get("nickname"))
                .email((String) kakao_account.get("email"))
                .picture((String) profile.get("profile_image_url"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    public User toEntity(){
        return User.builder()
                .name(name)
                .email(email)
                .picture(picture)
                .role(APIRole.USER)
                .build();
    }
}
