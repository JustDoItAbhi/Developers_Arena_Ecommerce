package ecommerce_backend.userservice.userservice;

import ecommerce_backend.categoryservice.exceptions.UserNotFoundException;
import ecommerce_backend.userservice.userrepository.UserRepository;
import ecommerce_backend.userservice.entity.Address;
import ecommerce_backend.userservice.entity.User;
import ecommerce_backend.userservice.jwt.JwtService;
import ecommerce_backend.userservice.userdto.request.UserRequestDTO;
import ecommerce_backend.userservice.userdto.response.UserResponseDto;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public UserResponseDto createUser(UserRequestDTO dto) {
        Optional<User> exsistingUser=userRepository.findByEmail(dto.getEmail());
        if(exsistingUser.isPresent()){
            UserResponseDto responseDto=mapper.map(exsistingUser.get(),UserResponseDto.class);
            return responseDto;
        }
        User user=new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setContactNumber(dto.getContactNumber());
        Address address=new Address();
        address.setCity(dto.getAddress().getCity());
        address.setState(dto.getAddress().getState());
        address.setCountry(dto.getAddress().getCountry());
        address.setPostalCode(dto.getAddress().getPostalCode());
        user.setAddress(address);
        user.setRoles(dto.getRoles());
        userRepository.save(user);

        UserResponseDto responseDto=mapper.map(user,UserResponseDto.class);
        return responseDto;
    }

    @Override
    public UserResponseDto getUserByID(long id) {
        User oldUser=userRepository.findById(id).orElseThrow(
                ()->new UserNotFoundException("USER NOT FOUND "+ id));
        UserResponseDto responseDto=mapper.map(oldUser,UserResponseDto.class);
        return responseDto;
    }

    @Override
    public boolean deleteUser(long id) {
        userRepository.deleteById(id);
        return true;
    }

    @Override
    public List<UserResponseDto> getallUsers(int page, int pageSize) {
        Pageable pageable= PageRequest.of(page,pageSize);
        Page<User>users=userRepository.findAll(pageable);
        List<UserResponseDto>responseDtos=new ArrayList<>();
        for(User user:users){
            responseDtos.add(mapper.map(user,UserResponseDto.class));
        }
        return responseDtos;
    }

    @Override
    public UserResponseDto updateUser(long id, UserRequestDTO dto) {
        User oldUser=userRepository.findById(id).orElseThrow(
                ()->new UserNotFoundException("USER NOT FOUND "+ id));
        User user=oldUser;
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setContactNumber(dto.getContactNumber());
        Address address=new Address();
        address.setCity(dto.getAddress().getCity());
        address.setState(dto.getAddress().getState());
        address.setCountry(dto.getAddress().getCountry());
        address.setPostalCode(dto.getAddress().getPostalCode());
        user.setAddress(address);
        user.setRoles(dto.getRoles());
        UserResponseDto responseDto=mapper.map(user,UserResponseDto.class);
        return responseDto;
    }

    @Override
    public String login(String username, String password) {
        Authentication authentication= authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        username,password));
            UserDetails userDetails= (UserDetails) authentication.getPrincipal();
           return jwtService.generateToken(new HashMap<>(),userDetails);
    }
}
