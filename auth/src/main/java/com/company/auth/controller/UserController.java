package com.company.auth.controller;

import com.company.auth.domain.User;
import com.company.auth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

	private final UserService userService;

	@GetMapping(
		value = "/current",
		produces = APPLICATION_JSON_VALUE)
	public Principal getUser(Principal principal) {
		return principal;
	}

	@PostMapping("/")
	@PreAuthorize("#oauth2.hasAnyScope('api', 'ui')")
	public User createUser(@Valid @RequestBody User user) {
		return userService.createUser(user);
	}
}
