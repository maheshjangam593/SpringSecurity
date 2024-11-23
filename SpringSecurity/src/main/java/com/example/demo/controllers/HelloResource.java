package com.example.demo.controllers;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloResource {

	@RequestMapping("/login")
	public String login(@RequestParam(value = "error", defaultValue = "false") boolean loginError,
			@RequestParam(value = "invalid-session", defaultValue = "false") boolean invalidSession,
			final Model model) {

		if (loginError) {
			System.out.println("loggin error haapnd");
		}
		if (invalidSession) {
			model.addAttribute("invalidSession", "Session Expired");
		}

		return "session Validated";

	}

	@RequestMapping("/")
	public String home() {

		return ("<h1> hey hello <h1>");
	}

	@RequestMapping("/success")

	public String sucessurl() {
		return "succes url executed";
	}

	@RequestMapping("/user")
	public String user() {

		return ("<h1> hey hello user <h1>");
	}

	@RequestMapping("/admin")
	public String admin() {

		return ("<h1> hey hello admin <h1>");
	}

}
