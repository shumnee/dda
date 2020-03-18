package com.three.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

	@Autowired
	DataSource dataSource;
	
	@Autowired
	BycHandle BDBHandle;
	
	@Autowired
	StationHandle SDBHandle;
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "home";
	}
	
	
	@RequestMapping(value = "/chart", method = RequestMethod.GET)
	public String chart(Locale locale, Model model) {
		
		return "chart";
	}
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public String chartview(Locale locale, Model model) {
		
		return "test";
	}
	
	@RequestMapping(value = "/rstRest", method = RequestMethod.GET)
	public void rstRest(HttpServletResponse response, Model model) {		
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = null;
		
		try {
			out = response.getWriter();
			String jsonStr = BDBHandle.makeJson();
			if(jsonStr != null) {
				out.print(jsonStr);
				out.flush();
			}
		} catch (IOException e) {
			out.print(e.getMessage());
		}
	}
	
	@RequestMapping(value = "/rstPost", method = RequestMethod.GET)
	public void rstPost(HttpServletResponse response, Model model) {		
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = null;
		
		try {
			out = response.getWriter();
			String jsonStr = BDBHandle.makePostJson();
			if(jsonStr != null) {
				out.print(jsonStr);
				out.flush();
			}
		} catch (IOException e) {
			out.print(e.getMessage());
		}
	}
	@RequestMapping(value = "/map", method = RequestMethod.GET)
	public String mapFn(Locale locale, Model model) {
		
		return "map";
	}
	
	@RequestMapping(value = "/rstTest", method = RequestMethod.GET)
	public void rstRegion(HttpServletResponse response, Model model) {		
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = null;
		
		try {
			out = response.getWriter();
			String jsonStr = SDBHandle.makeJson();
			if(jsonStr != null) {
				out.print(jsonStr);
				out.flush();				
			}
		} catch (IOException e) {
			out.print(e.getMessage());
		}
	}
}
