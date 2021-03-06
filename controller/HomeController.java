package com.boot.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.boot.entity.Users;

import com.boot.services.AdminServices;
import com.boot.services.UserServices;

/**
 * Main controller which process main requests of login and index page request
 * 
 * @author admin
 *
 */
@Controller
public class HomeController {

	@Autowired
	private UserServices userService;
	@Autowired
	private AdminServices contactDao;
	
	@RequestMapping("/")
	public String homeselection(HttpServletRequest request) {
		HttpSession session = request.getSession();
		Users user = (Users) session.getAttribute("currentUser");
		if (user != null)
			return "redirect:/home";
		return "startpage";
	}
	
	@RequestMapping("/login")
	public String index(HttpServletRequest request) {
		HttpSession session = request.getSession();
		Users user = (Users) session.getAttribute("currentUser");
		if (user != null)
			return "redirect:/home";
		return "index";
	}
	
	@GetMapping("/home")
	public String getHomePage(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		Users user = (Users) session.getAttribute("currentUser");
		if (user == null)
			return "redirect:/";
		else {
			model.addAttribute("contacts", contactDao.getUserContacts(user));
		}
		return "home";
		
	}
	
	@GetMapping("/AdminHome")
	public String getAdminHomePage(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		Users user = (Users) session.getAttribute("currentUser");
		if (user == null)
			return "redirect:/";
		else {
			return "AdminHome";
		}
		
	}
	
	@PostMapping("/login")
	public String loginUser(@RequestParam(value = "login") String email, @RequestParam("password") String password,
			HttpServletRequest request, Model model) {
		String page = null;
		HttpSession session = request.getSession();
		Users user = userService.findByEmailAndPassword(email, password);
		System.out.println(user.getEmail());
		if (user != null) {
			
			if((user.getEmail()).equals("rasikhhaider98@yahoo.com")) {
				System.out.println("C");
				session.setAttribute("currentUser", user);
				page = "redirect:/AdminHome";
				
			}
			else {
			session.setAttribute("currentUser", user);
			page = "redirect:/home";
			}
			
			
		} else {
			model.addAttribute("loginError", true);
			model.addAttribute("loginValue", email);
			page = "index";
		}
		return page;
	}
	@GetMapping("/logout")
	public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.invalidate();
		return "redirect:/";
	}
	

}
