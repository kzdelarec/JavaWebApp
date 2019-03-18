package hr.java.web.zdelarec.moneyapp.controllers;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CustomErrorController implements ErrorController{

	@GetMapping("error")
	public String errorSite(Model model) {
		return "redirect:/home?error";
	}

	@Override
	public String getErrorPath() {
		return "error";
	}

}
