package ecommerce_backend.userservice.usermapper;

import ecommerce_backend.userservice.entity.User;
import ecommerce_backend.userservice.userdto.response.UserResponseDto;
import org.modelmapper.ModelMapper;

public class UserMapper {
    public final ModelMapper mapper;

    public UserMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }
    public UserResponseDto fromEntity(User user){
        UserResponseDto dto=mapper.map(user,UserResponseDto.class);
        return dto;
    }
}
