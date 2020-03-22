package com.three.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import lombok.extern.slf4j.Slf4j;

/**
 * Handles requests for the application home page.
 */
@Slf4j
@Controller
public class HomeController {

	@Autowired
	DataSource dataSource;
	
	@Autowired
	memberDBHandle DBHandle;

	@Autowired
	StationDBHandle STDBHandle;

	@Autowired
	TicketDBHandle TDBHandle;
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		log.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "home";
	}
	

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Locale locale, Model model) {
		return "login";
	}
	
	@RequestMapping(value = "/loginPost", method = RequestMethod.POST)
	public String loginPro(HttpSession session, HttpServletRequest request, HttpServletResponse response, Model model) {		

		String m_id = request.getParameter("userId");
		String m_pwd = request.getParameter("userPwd");

		session.setAttribute("sessionUserName", m_id);
		boolean check = DBHandle.checkPassword(m_id, m_pwd);

		//model.addAttribute("userId",m_id);
		//model.addAttribute("userPwd",m_pwd);
		model.addAttribute("check",check);
		
		PrintWriter out =  null;
		String url = "index";
		try {
			if(check == false) {
				response.setContentType("text/html; charset=UTF-8");
				out = response.getWriter();
				out.println("<script>alert('로그인 정보를 확인해주세요.'); history.go(-1); </script>");
				out.flush();
			}
		} catch (Exception e) {
				e.printStackTrace();
		}
		
		return url;
	}
	
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(Locale locale, Model model) {
		return "index";
	}	

	@RequestMapping(value = "/addMember", method = RequestMethod.GET)
	public String addMember(Locale locale, Model model) {
		return "addMember";
	}	
	
	@RequestMapping(value = "/addMemberPro", method = RequestMethod.POST)
	public String addMemberPro(HttpServletRequest request, HttpServletResponse response, Model model) {		
		response.setContentType("text/html; charset=UTF-8");

		String m_id = request.getParameter("id");
		String m_name = request.getParameter("name");
		String m_age = request.getParameter("age");
		String m_pwd = request.getParameter("pwd");
		String r_pwd = request.getParameter("r_pwd");
		String phone = request.getParameter("phone");
		String s_id = request.getParameter("state");

		try {
			DBHandle.insertMember(m_id, m_name, m_age, m_pwd, r_pwd, phone, s_id);
		} catch (Exception e) {
				e.printStackTrace();
		}
		
		return "login";
	}

	@RequestMapping(value = "/dulCheck", method = RequestMethod.GET)
	public void dulCheck(HttpServletRequest request, HttpServletResponse response, Model model) {		
		response.setContentType("text/html; charset=UTF-8");

		String m_id = request.getParameter("id");
		PrintWriter out = null;
		
		try {
			out = response.getWriter();
			String jsonStr = DBHandle.selectMemberId(m_id);
			if(jsonStr != null) {
				out.print(jsonStr);
				out.flush();				
			}
		} catch (IOException e) {
			out.print(e.getMessage());
		}
	}
	
	@RequestMapping(value = "/searchState", method = RequestMethod.GET)
	public String searchState(Locale locale, Model model) {
		return "searchState";
	}	
	
	@RequestMapping(value = "/searchPro", method = RequestMethod.GET)
	public void searchPro(HttpServletRequest request, HttpServletResponse response, Model model) {		
		response.setContentType("text/html; charset=UTF-8");

		String keyword = request.getParameter("keyword");
		PrintWriter out = null;
		
		try {
			out = response.getWriter();
			String jsonStr = STDBHandle.searchStation(keyword);
			if(jsonStr != null) {
				out.print(jsonStr);
				out.flush();				
			}
		} catch (IOException e) {
			out.print(e.getMessage());
		}
	}

	@RequestMapping(value = "/ticket", method = RequestMethod.GET)
	public String ticketRecord(Locale locale, Model model) {
		return "ticketRecord";
	}	
	
	@RequestMapping(value = "/ticketPro", method = RequestMethod.GET)
	public void ticketPro(HttpSession session, HttpServletRequest request, HttpServletResponse response, Model model) {		
		response.setContentType("text/html; charset=UTF-8");
		session = request.getSession();
		String m_id = (String)session.getAttribute("sessionUser");

		PrintWriter out = null;
		
		try {
			out = response.getWriter();
			String jsonStr = TDBHandle.selectTicket("soo");
			if(jsonStr != null) {
				out.print(jsonStr);
				out.flush();				
			}
		} catch (IOException e) {
			out.print(e.getMessage());
		}
	}
}
