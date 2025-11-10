package com.car.dekho.car.dekho.Controller;

import com.car.dekho.car.dekho.Dto.*;
import com.car.dekho.car.dekho.Entty.UserEntity;
import com.car.dekho.car.dekho.Exeptions.*;
import com.car.dekho.car.dekho.Jwt.JwtUtil;
import com.car.dekho.car.dekho.Jwt.OtpUtil;
import com.car.dekho.car.dekho.Repository.UserRepository;
import com.car.dekho.car.dekho.Service.EmailSander;
import com.car.dekho.car.dekho.Service.ProfileService;
import com.car.dekho.car.dekho.Service.UserPersonalDetailsService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Optional;
@RestController
public class UserController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final OtpUtil otpUtil;
    private final EmailSander emailSenderService;
    private final UserPersonalDetailsService userPersonalDetailsService;
    private final ProfileService profileService;


    public UserController(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtUtil jwtUtil, OtpUtil otpUtil, EmailSander emailSenderService, UserPersonalDetailsService userPersonalDetailsService, ProfileService profileService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.otpUtil = otpUtil;
        this.emailSenderService = emailSenderService;
        this.userPersonalDetailsService = userPersonalDetailsService;
        this.profileService = profileService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthRequestDto requestDto){
        try {
            Optional<UserEntity> optionalUserEntity=userRepository.findByUsername(requestDto.getUsername());
            if (optionalUserEntity.isPresent()){
                throw new UserAlreadyExistExeption("Username is already taken");
            }

            UserEntity user=new UserEntity();
            user.setUsername(requestDto.getUsername());
            user.setPassword(passwordEncoder.encode(requestDto.getPassword()));
            user.setEmail(requestDto.getEmail());
            user.setRole("USER");

            String otp= otpUtil.generateOtp();
            long expiretime=otpUtil.expiretime(10);

            emailSenderService.otpSanderForAccountVerification(user.getEmail(),otp);
            user.setOtp(otp);
            user.setOtpExpire(expiretime);
            user.setIsValid(false);
            userRepository.save(user);
            return ResponseEntity.ok("Successfully register please check your email for otp");
        }catch (Exception e){
            System.out.println(e.getMessage());
            throw new SomethingIsWrongExeption(e.getMessage());
        }
    }

    @PostMapping("/admin/register")
    public ResponseEntity<?> adminRegister(@RequestBody AuthRequestDto requestDto){
        try {
            Optional<UserEntity> optionalUserEntity=userRepository.findByUsername(requestDto.getUsername());
            if (optionalUserEntity.isPresent()){
                throw new UserAlreadyExistExeption("Username is already taken");
            }

            UserEntity user=new UserEntity();
            user.setUsername(requestDto.getUsername());
            user.setPassword(passwordEncoder.encode(requestDto.getPassword()));
            user.setEmail(requestDto.getEmail());
            user.setRole("ADMIN");

            String otp= otpUtil.generateOtp();
            long expiretime=otpUtil.expiretime(10);

            emailSenderService.otpSanderForAccountVerification(user.getEmail(),otp);
            user.setOtp(otp);
            user.setOtpExpire(expiretime);
            user.setIsValid(false);

            userRepository.save(user);
            return ResponseEntity.ok("Successfully register");
        }catch (Exception e){
            throw new SomethingIsWrongExeption(e.getMessage());
        }
    }

    @PostMapping("/account-verification")
    public ResponseEntity<?> accountverification(@RequestBody AccountVerificationDto accVerification){
        try {
            UserEntity user=userRepository.findByUsername(accVerification.getUsername())
                    .orElseThrow(()-> new UserNotFoundExeption("Username not found"));

            if (user.getIsValid().equals(true)){
                return ResponseEntity.ok("User is already verified please log in");
            }

            String storeOtp=user.getOtp();
            Long expireDate=user.getOtpExpire();

            if (storeOtp==null || expireDate==null){
                throw new SomethingIsWrongExeption("Something is wrong please request new otp");
            }

            if (!storeOtp.equals(accVerification.getOtp())){
                throw new OtpExeption("Wrong otp check otp again");
            }
            if ( accVerification.getOtp()==null||accVerification.getOtp().isEmpty() ){
                throw new OtpExeption("Please enter otp before processing");
            }

            if (expireDate<System.currentTimeMillis()){
                throw new OtpExeption("otp is expire");
            }

            user.setOtp(null);
            user.setOtpExpire(null);
            user.setIsValid(true);

            userRepository.save(user);

            return ResponseEntity.ok("Account is verified you can log in");
        }catch (Exception e){
            throw new SomethingIsWrongExeption(e.getMessage());
        }
    }



    @PostMapping("/otp-request")
    public ResponseEntity<?> otpRequest(@RequestBody OtpRequestDto otpRequestDto){
        try{
            UserEntity user=userRepository.findByUsername(otpRequestDto.getUsername())
                    .orElseThrow(()-> new UsernameNotFoundException("Usernot found "));

            user.setOtp(null);
            user.setOtpExpire(null);

            String otp=otpUtil.generateOtp();
            long expireTime=otpUtil.expiretime(10);

            emailSenderService.otpForPasswordReset(user.getEmail(),otp);

            user.setOtp(otp);
            user.setOtpExpire(expireTime);
            userRepository.save(user);

            return ResponseEntity.ok("Otp is sanded to "+ user.getEmail());
        }catch(Exception e){
            throw new SomethingIsWrongExeption(e.getMessage());
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody PasswordResetDto passwordChangeDto){
        try {
            UserEntity user=userRepository.findByUsername(passwordChangeDto.getUsername())
                    .orElseThrow(()-> new UsernameNotFoundException("Username not found"));

            String savedOtp= user.getOtp();
            Long expire=user.getOtpExpire();


            if (savedOtp==null || expire==null){
                throw new SomethingIsWrongExeption("Something is wrong please request otp again");
            }
            if (passwordChangeDto.getOtp()==null || passwordChangeDto.getOtp().isEmpty()){
                throw new OtpExeption("Please enter otp first");
            }
            if (!savedOtp.equals(passwordChangeDto.getOtp())){
                throw new OtpExeption("Wrong otp please check otp again");
            }

            if (expire< System.currentTimeMillis()){
                throw new OtpExeption("Otp is expire please try again with new one ");
            }

            user.setOtp(null);
            user.setOtpExpire(null);
            user.setPassword(passwordEncoder.encode(passwordChangeDto.getPassword()));
            userRepository.save(user);

            return ResponseEntity.ok("New password is saved successfully you can log in with new password now ");
        }catch (ResourceNotFoundExeption | UsernameNotFoundException | OtpExeption e) {
            throw e;
        }catch (Exception e){
            throw new SomethingIsWrongExeption(e.getMessage());
        }
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequestDto authRequestDto){
        try {
            UserEntity user=userRepository.findByUsername(authRequestDto.getUsername())
                    .orElseThrow(()-> new UsernameNotFoundException("Username not found"));

            if (user.getIsValid().equals(false)){
                throw new SomethingIsWrongExeption("User not verified");
            }
            try {
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(authRequestDto.getUsername(),authRequestDto.getPassword())
                );
            }catch (Exception e){
                throw new SomethingIsWrongExeption(e.getMessage());
            }

            String token=jwtUtil.generateToken(authRequestDto.getUsername());
            return ResponseEntity.ok(new AuthResponseDto(token));
        }catch (Exception e){
            throw new SomethingIsWrongExeption(e.getMessage());
        }
    }

    @GetMapping("/about-me")
    public ResponseEntity<?> aboutMe(Authentication authentication){
        try {
           ProfileDto profileDto= profileService.getPersonalDetails(authentication);
           return ResponseEntity.ok(profileDto);
        }catch (Exception e){
            throw new SomethingIsWrongExeption(e.getMessage());
        }
    }
    @GetMapping("/test-token")
    public ResponseEntity<String> testToken(@RequestHeader("Authorization") String header) {
        String token = header.substring(7);
        String username = jwtUtil.extractUsernameFromToken(token);
        return ResponseEntity.ok("Extracted username: " + username);
    }
}
