package com.example.demo.controller;

import com.example.demo.entity.Lesson;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.response.JwtResponse;
import com.example.demo.response.MessageResponse;
import com.example.demo.entity.ERole;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.model.SigninModel;
import com.example.demo.model.SignupModel;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JwtUtils;
import com.example.demo.service.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
    AuthenticationManager authenticationManager;

	@Autowired
    UserRepository userRepository;

	@Autowired
    RoleRepository roleRepository;

	@Autowired
    PasswordEncoder encoder;

	@Autowired
    JwtUtils jwtUtils;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody SigninModel signinModel) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(signinModel.getUsername(), signinModel.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());

		return ResponseEntity.ok(new JwtResponse(jwt,
												 userDetails.getId(), 
												 userDetails.getUsername(),
												 roles));
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupModel signupModel) {
		if (userRepository.existsByUsername(signupModel.getUsername())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Username is already taken!"));
		}

		// Create new user's account
		User user = new User(signupModel.getUsername(),
							 encoder.encode(signupModel.getPassword()));

		Set<String> strRoles = signupModel.getRole();
		Set<Role> roles = new HashSet<>();

		if (strRoles == null) {
			Role userRole = roleRepository.findByName(ERole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "admin":
					Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(adminRole);

					break;
				case "mod":
					Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(modRole);

					break;
				default:
					Role userRole = roleRepository.findByName(ERole.ROLE_USER)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(userRole);
				}
			});
		}

		user.setRoles(roles);
		userRepository.save(user);

		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}

	@GetMapping("/users")
	public ResponseEntity<List <User>> getAllUser()
			throws ResourceNotFoundException {
		List<User> users = userRepository.findAll();
		if (users.isEmpty()) {
			new ResourceNotFoundException("Don't have users");
		}
		return new ResponseEntity<>(users, HttpStatus.OK);
	}

	@PutMapping("/users/{id}")
	public ResponseEntity<User> updateLesson(@PathVariable(value = "id") Long userID,
											   @Valid @RequestBody User userDetails) throws ResourceNotFoundException {
		User user = userRepository.findById(userID)
				.orElseThrow(() -> new ResourceNotFoundException("User này không tồn tại: " + userID));
		user.setUsername(userDetails.getUsername());
		user.setPassword(userDetails.getPassword());
		user.setRoles(userDetails.getRoles());

		final User updatedUser  = userRepository.save(user);

		return ResponseEntity.ok(updatedUser);
	}

	@DeleteMapping("/users/{id}")
	public Map<String, Boolean> deleteLesson(@PathVariable(value = "id") Long userID)
			throws ResourceNotFoundException {

		User user = userRepository.findById(userID)
				.orElseThrow(() -> new ResourceNotFoundException("User này không tồn tại: " + userID));

		userRepository.delete(user);
		Map<String, Boolean> response = new HashMap<>();
		response.put("Delete successfully!", Boolean.TRUE);
		return response;
	}
}
