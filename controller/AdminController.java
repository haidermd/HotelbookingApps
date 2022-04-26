 package com.boot.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.boot.entity.Admin;
import com.boot.entity.Users;
import com.boot.services.AdminServices;
import com.boot.services.UserServices;

/**
 * Serves CRUD contact operations
 * 
 * @author admin
 *
 */
@Controller
public class AdminController {

	@Autowired
	private AdminServices contactDao;
    @Autowired
    private UserServices userDao;
	
	
	/*@GetMapping("/add-users-list")
	
	public String findAll(Model model) {
		Admin list = new Admin();
		model.addAttribute("userList", list);
		model.addAttribute("adding", true);
		return "usersList";
	}*/

	/*@PostMapping("/add-users-list")
	public String showNewUsers(@ModelAttribute("newUsers") @Valid Admin userslist, BindingResult result,
			HttpServletRequest request, Model model) {
		if (result.hasErrors()) {
			model.addAttribute("adding", true);
			return "usersList";
		}
		
		HttpSession session = request.getSession();
		Users user = (Users) session.getAttribute("currentUser");
		if (userslist != null) {
			System.out.println("working userlist");
			userslist.setUserId(user);
			userDao.findAll(user);
		}
		
		return "redirect:/home";
	}*/
	
	@GetMapping("/add-users-list")
	public String getUserList(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		Users user = (Users) session.getAttribute("currentUser");
		if (user == null)
			return "redirect:/";
		else {
			model.addAttribute("users", userDao.findAll(user));
		}
		return "usersList";
		
	}
	/*@GetMapping("/loginupdateItm")
	public String index(Model model,HttpServletRequest request) {
		Admin contact = new Admin();
		model.addAttribute("newRights", contact);
		model.addAttribute("adding", true);
		return "usersList";
	}

	@PostMapping("/loginupdateItm")
	public String save(@ModelAttribute("newRights") @Valid String login, BindingResult result,
			HttpServletRequest request, Model model) {
		if (result.hasErrors()) {
			model.addAttribute("adding", true);
			return "usersList";
		}
		
		HttpSession session = request.getSession();
		String user = (String) session.getAttribute("currentUser");
		if (login != null) {
			//login.setLogin(user);
			//userDao.updateStatus(login);
		}
		
		return "redirect:/home";
	}
	*/
	
	@GetMapping("/add-contact")
	public String getCreateContactPage(Model model) {
		Admin contact = new Admin();
		
		model.addAttribute("newContact", contact);
		model.addAttribute("adding", true);
		return "addAdmin";
	}

	@PostMapping("/add-contact")
	public String addNewContact(@ModelAttribute("newContact") @Valid Admin contact, BindingResult result,
			HttpServletRequest request, Model model) {
		if (result.hasErrors()) {
			model.addAttribute("adding", true);
			return "addAdmin";
		}
		
		HttpSession session = request.getSession();
		Users user = (Users) session.getAttribute("currentUser");
		if (contact != null) {
			contact.setUserId(user);
			//contactDao.createContact(contact);
		}
		
		return "redirect:/home";
	}

	@GetMapping("/update")
	public String updateLoginStatus(@RequestParam("userId") long userId) {
		if (userId != 0) {
			userDao.updateStatus(userDao.findUsersById(userId));
		}
		return "redirect:/home";
	}
	
	@GetMapping("/delete")
	public String editContact(@RequestParam("contactId") Integer contactId) {
		if (contactId != null) {
			contactDao.deleteContact(contactDao.findContactById(contactId));
		}
		return "redirect:/home";
	}

	@GetMapping("/edit")
	public String getEditContactPage(@RequestParam("contactId") Integer contactId, Model model) {
		model.addAttribute("newContact", contactDao.findContactById(contactId));
		model.addAttribute("editing", true);
		return "addAdmin";
	}

	@PostMapping("/edit")
	public String editContact(@ModelAttribute("newContact") @Valid Admin contact, BindingResult result,
			HttpServletRequest request, Model model) {
		if (result.hasErrors()){
			model.addAttribute("editing", true);
			return "addAdmin";
		}
			
		HttpSession session = request.getSession();
		Users user = (Users) session.getAttribute("currentUser");
		if (contact != null) {
			contact.setUserId(user);
			contactDao.editContact(contact);
		}
		return "redirect:/home";
	}
	 
	

/*	@GetMapping("/loginupdateItm")
	public String getEditUserListPage(@RequestParam("userId") long userId, Model model) {
		model.addAttribute("newRights", userDao.findUsersById(userId));
		model.addAttribute("editing", true);
		return "usersList";
	}

	@PostMapping("/loginupdateItm")
	public String editAdminRights(@ModelAttribute("newRights") @Valid String login, BindingResult result,
			HttpServletRequest request, Model model) {
		if (result.hasErrors()){
			model.addAttribute("editing", true);
			return "usersList";
		}
			
	HttpSession session = request.getSession();
		Users user = (Users) session.getAttribute("currentUser");
			if (login != null) {
			login.setLogin(user);
			userDao.updateStatus(login);
		}
	return "redirect:/home";
	}*/

}
