package com.userfront.controller;



import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.userfront.dao.RoleDao;
import com.userfront.domain.PrimaryAccount;
import com.userfront.domain.SavingsAccount;
import com.userfront.domain.User;
import com.userfront.domain.security.UserRole;
import com.userfront.service.UserService;

@Controller
public class HomeController {

	@Autowired
	private UserService userService;

	@Autowired
	private RoleDao roleDao;

	@RequestMapping("/")
	public String home() {
		return "redirect:/index";
	}

	@RequestMapping("/index")
	public String index() {
		return "index";
	}

	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String signup(Model model) {
		User user = new User();

		model.addAttribute("user", user);

		return "signup";
	}

	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String signupPost(@ModelAttribute("user") User user,  Model model) {

		if(userService.checkUserExists(user.getUsername(), user.getEmail()))  {

			if (userService.checkEmailExists(user.getEmail())) {
				model.addAttribute("emailExists", true);
			}

			if (userService.checkUsernameExists(user.getUsername())) {
				model.addAttribute("usernameExists", true);
			}

			return "signup";
		} else {
			Set<UserRole> userRoles = new HashSet<>();
			userRoles.add(new UserRole(user, roleDao.findByName("ROLE_USER")));

			userService.createUser(user, userRoles);

			return "redirect:/";
		}
	}

	/*
	@RequestMapping(value="/signup",method= RequestMethod.POST)
	public String signupPost(@ModelAttribute("user")User user,Model model) //modelAttribute will retrieve variable called user
	//from the html form we submitted and give the value to the object User we defined here
	{
		
		if (userService.checkUserExists(user.getUsername(),user.getEmail()))
		{
			if (userService.checkEmailExists(user.getEmail()))
			{
				model.addAttribute("emailExists",true);
			}
			if (userService.checkEmailExists(user.getUsername()))
			{
				model.addAttribute("usernameExists",true);
			}
			return "signup";
		}
		else
		{
			Set<UserRole> userRoles = new HashSet<>();
			userRoles.add(new UserRole(user,roleDao.findByName("ROLE_USER")));
			//userService.createUser(user,userRoles);
			userService.createUser(user,userRoles);
			return "redirect:/";
		}
		
	}

	*/
	/*
	@RequestMapping("/userFront")
	public String userFront(Principal principal, Model model) //principal is the user that logged in 
	{
		User user = userService.findByUsername(principal.getName()); // extracting info from the principal object
		PrimaryAccount primaryAccount = user.getPrimaryAccount(); // creating instance of the user primary account
		SavingsAccount savingsAccount = user.getSavingsAccount(); // creating instance of the user savings account
		
		model.addAttribute("primaryAccount", primaryAccount); //adding the model an attribute that can be seen on the userfront.html page
		model.addAttribute("savingsAccount", savingsAccount);
		
		return "userFront"; //returns the userFront html page
	}
*/
	@RequestMapping("/userFront")
	public String userFront(Principal principal, Model model) {
		User user = userService.findByUsername(principal.getName());
		PrimaryAccount primaryAccount = user.getPrimaryAccount();
		SavingsAccount savingsAccount = user.getSavingsAccount();

		model.addAttribute("primaryAccount", primaryAccount);
		model.addAttribute("savingsAccount", savingsAccount);

		return "userFront";
	}
	
}
