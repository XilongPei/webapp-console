package io.whisper.console.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import jxl.read.biff.BiffException;
import org.apache.commons.io.FileUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import io.whisper.console.controller.base.BaseController;
import io.whisper.console.entity.Constants;
import io.whisper.console.shiro.entity.Role;
import io.whisper.console.shiro.entity.User;
import io.whisper.console.shiro.exception.UserDuplicateException;
import io.whisper.console.shiro.service.RoleService;
import io.whisper.console.shiro.service.UserExcelService;
import io.whisper.console.shiro.service.UserService;
import io.whisper.console.utils.Servlets;

@Controller
@RequestMapping("/dashboard")
public class DashboardController extends BaseController {

	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public String getdashboard(Model model, @RequestParam(value="pageNumber",defaultValue="1")int pageNumber,
			@RequestParam(value = "page.size", defaultValue = Constants.PAGE_SIZE+"") int pageSize,
			@RequestParam(value = "sortType", defaultValue = "auto") String sortType) {
		/*
		Page<User> userPage=userService.findAll(buildPageRequest(pageNumber));
		model.addAttribute("users", userPage.getContent());
		model.addAttribute("page", userPage);
		*/
		//model.addAttribute("searchParams", "");
		return "dashboard/index";
	}

}
