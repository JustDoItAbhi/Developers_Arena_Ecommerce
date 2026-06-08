package ecommerce_backend.userservice.userservice;

import ecommerce_backend.userservice.userdto.request.UserRequestDTO;
import ecommerce_backend.userservice.userdto.response.UserResponseDto;

import java.util.List;

public interface UserService {
    UserResponseDto createUser(UserRequestDTO dto);
    UserResponseDto getUserByID(long id);
    boolean deleteUser(long id);
    List<UserResponseDto>getallUsers(int page, int pageSize);
    UserResponseDto updateUser(long id, UserRequestDTO dto);
    String login(String username, String password);
    UserResponseDto getProfile();
}
