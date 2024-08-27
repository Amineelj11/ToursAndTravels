package com.toursandtravel.ressource;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.toursandtravel.resource.UserResource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.toursandtravel.dto.CommonApiResponse;
import com.toursandtravel.dto.RegisterUserRequestDto;
import com.toursandtravel.entity.User;
import com.toursandtravel.service.UserService;
import com.toursandtravel.utility.Constants.ActiveStatus;
import com.toursandtravel.utility.Constants.UserRole;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserResourceTest {

    @InjectMocks
    private UserResource userResource;

    @Mock
    private UserService userService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /*@Test
    public void testRegisterAdmin_UserAlreadyExists() {
        RegisterUserRequestDto registerRequest = new RegisterUserRequestDto();
        registerRequest.setEmailId("test@test.com");

        when(userService.getUserByEmailAndStatus("test@test.com", ActiveStatus.ACTIVE.value()))
                .thenReturn(new User());

        ResponseEntity<CommonApiResponse> response = userResource.registerAdmin(registerRequest);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertFalse(response.getBody().isSuccess());
        assertEquals("User already register with this Email", response.getBody().getResponseMessage());

        verify(userService, times(1)).getUserByEmailAndStatus("test@test.com", ActiveStatus.ACTIVE.value());
    }*/

    @Test
    public void testRegisterAdmin_Success() {
        RegisterUserRequestDto registerRequest = new RegisterUserRequestDto();
        registerRequest.setEmailId("test@test.com");
        registerRequest.setPassword("password");

        when(userService.getUserByEmailAndStatus("test@test.com", ActiveStatus.ACTIVE.value()))
                .thenReturn(null);
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");

        User user = RegisterUserRequestDto.toUserEntity(registerRequest);
        user.setPassword("encodedPassword");
        user.setRole(UserRole.ROLE_ADMIN.value());
        when(userService.addUser(any(User.class))).thenReturn(user);

        ResponseEntity<CommonApiResponse> response = userResource.registerAdmin(registerRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isSuccess());
        assertEquals("Admin registered Successfully", response.getBody().getResponseMessage());

        verify(userService, times(1)).getUserByEmailAndStatus("test@test.com", ActiveStatus.ACTIVE.value());
        verify(passwordEncoder, times(1)).encode("password");
        verify(userService, times(1)).addUser(any(User.class));
    }
}
