package com.ecomerce.Shoes_Shop.ServiceImpl;

import com.ecomerce.Shoes_Shop.Contents.Constants;
import com.ecomerce.Shoes_Shop.Dao.UserDao;
import com.ecomerce.Shoes_Shop.JWT.JwtUntil;
import com.ecomerce.Shoes_Shop.POJO.*;
import com.ecomerce.Shoes_Shop.Service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserDao userDao;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JwtUntil jwtUntil;

    @Override
    public ResponseEntity<ResponseMess> register(Map<String, String> requestMap) {
        try {
            log.info(requestMap.toString());
            Optional<User> userCheck = userDao.findByEmail(requestMap.get("email"));

            if(userCheck.isEmpty()) {
                var user = User
                        .builder()
                        .username(requestMap.get("username"))
                        .email(requestMap.get("email"))
                        .password(passwordEncoder.encode(requestMap.get("password")))
                        .status(requestMap.get("status"))
                        .role(Role.valueOf(requestMap.get("role")))
                        .build();
                userDao.save(user);
               return ResponseEntity.status(HttpStatus.OK).body(new ResponseMess(true, "Register Successfully"));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMess(false, "User with this email already exists"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseMess(false, Constants.SOMETHING_WENT_WRONG));
    }

    @Override
    public ResponseEntity<AuthResponse> login(LoginRequest requestMap) {
       try {

           User userCheck = userDao.findUserByEmail(requestMap.getEmail());

           if (userCheck != null) {
               var checkPassword = passwordEncoder.matches(requestMap.getPassword(), userCheck.getPassword());
               if(checkPassword) {
                   authenticationManager.authenticate(
                           new UsernamePasswordAuthenticationToken(
                                   requestMap.getEmail(),
                                   requestMap.getPassword()
                           )
                   );
                   var user = userDao.findByEmail(requestMap.getEmail()).orElseThrow();
                   var jwtToken = jwtUntil.generateToken(user);
                   return ResponseEntity.status(HttpStatus.OK).body(new AuthResponse(false, "LOGIN SUCCESSFULLY", jwtToken));
               } else {
                   return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new AuthResponse(false, "INCORRECT PASSWORD", ""));
               }
           } else {
               return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new AuthResponse(false, "INCORRECT EMAIL", ""));
           }
       } catch (Exception ex) {
           ex.printStackTrace();
       }
       return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new AuthResponse(false, Constants.SOMETHING_WENT_WRONG, ""));
    }
}
