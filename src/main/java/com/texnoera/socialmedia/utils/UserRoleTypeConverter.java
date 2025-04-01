package com.texnoera.socialmedia.utils;


import com.texnoera.socialmedia.security.enums.SocialMediaUserRole;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class UserRoleTypeConverter implements AttributeConverter<SocialMediaUserRole,String> {

    @Override
    public String convertToDatabaseColumn(SocialMediaUserRole socialMediaUserRole) {
        return socialMediaUserRole.name();
    }

    @Override
    public SocialMediaUserRole convertToEntityAttribute(String s) {
        return SocialMediaUserRole.fromName(s);
    }
}
