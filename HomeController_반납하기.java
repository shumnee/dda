package com.three.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

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
	BycHandle BDBHandle;
	
	@Autowired
	StationHandle SDBHandle;
	
	@Autowired
	RentalHandle RHandle;
	
	@Autowired
	ReturnHandle RRHandle;
	
	@Autowired
	RecordHandle ReHandle;
	
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
	
	/*
	@RequestMapping(value = "/chart", method = RequestMethod.GET)
	public String chart(Locale locale, Model model) {
		
		return "chart";
	}
	
	@RequestMapping(value = "/chartUser", method = RequestMethod.GET)
	public String chartUser(Locale locale, Model model) {
		
		return "chartUser";
	}
	
	@RequestMapping(value = "/chartStation", method = RequestMethod.GET)
	public String chartStation(Locale locale, Model model) {
		
		return "chartStation";
	}
	
	
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public String chartview(Locale locale, Model model) {
		
		return "test";
	}
	*/

	
	@RequestMapping(value = "/rstPost", method = RequestMethod.GET)
	public void rstPost(HttpServletResponse response, Model model) {		
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = null;
		
		try {
			out = response.getWriter();
			String jsonStr = BDBHandle.makePostSJson();
			if(jsonStr != null) {
				out.print(jsonStr);
				out.flush();
			}
		} catch (IOException e) {
			out.print(e.getMessage());
		}
	}
	

	
	@RequestMapping(value = "/rstUser", method = RequestMethod.GET)
	public void rstUser(HttpServletResponse response, Model model) {		
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = null;
		
		try {
			out = response.getWriter();
			String jsonStr = BDBHandle.makeUserJson();
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
	
	@RequestMapping(value = "/chartview", method = RequestMethod.GET)
	public String boot(Locale locale, Model model) {
		
		return "chartview";
	}
	
	@RequestMapping(value = "/map_return", method = RequestMethod.GET)
	public String maprRn(Locale locale, Model model) {
		
		return "map_return";
	}
	
	@RequestMapping(value = "/buyticketview", method = RequestMethod.GET)
	public String movebuy(Locale locale, Model model) {
		
		return "buyticketview";
	}
	
	@RequestMapping(value = "/rsuccessview", method = RequestMethod.GET)
	public String movesuccess(Locale locale, Model model) {
		
		return "rsuccessview";
	}
	
	@RequestMapping(value = "/userecordview", method = RequestMethod.GET)
	public String moverecord(Locale locale, Model model) {
		
		return "userecordview";
	}
	
	@RequestMapping(value = "/faq", method = RequestMethod.GET)
	public String faq(Locale locale, Model model) {
		
		return "faq";
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
	
	@RequestMapping(value = "/rstStation", method = RequestMethod.GET)
	public void rstRest(HttpServletResponse response, Model model) {		
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = null;
		
		try {
			out = response.getWriter();
			String jsonStr = BDBHandle.makeStationSJson();
			if(jsonStr != null) {
				out.print(jsonStr);
				out.flush();
			}
		} catch (IOException e) {
			out.print(e.getMessage());
		}
	}
	
	
	@RequestMapping(value = "/rstst", method = RequestMethod.GET)
	public void rstst(HttpServletResponse response, Model model) {		
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = null;
		
		try {
			out = response.getWriter();
			String jsonStr = SDBHandle.makeStJson();
			if(jsonStr != null) {
				out.print(jsonStr);
				out.flush();				
			}
		} catch (IOException e) {
			out.print(e.getMessage());
		}
	}
	
	// 반환 때 map 찍기
	@RequestMapping(value = "/rstst2", method = RequestMethod.GET)
	public void rstst2(HttpServletResponse response,HttpServletRequest request, Model model) {		
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = null;

		try {
			out = response.getWriter();
			String jsonStr = RRHandle.makeRestStock();
			if(jsonStr != null) {
				out.print(jsonStr);
				out.flush();				
			}
		} catch (IOException e) {
			out.print(e.getMessage());
		}
	}
	/* 지도 데이터 불러옴 */
	@RequestMapping(value = "/selectedInfo", method = RequestMethod.POST) 
	public void selectedSt(HttpServletResponse response, HttpServletRequest request, Model model) {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = null;
		String s_id = request.getParameter("s_id");
		
		try {
			out = response.getWriter();
			String jsonStr = SDBHandle.selectST(s_id);
			if(jsonStr != null) {
				out.print(jsonStr);
				out.flush();
			}
		} catch (IOException e) {
			out.print(e.getMessage());
		}
		
	}
	
	/* 첫 이용권 데이터 접근 및 출력 */
	@RequestMapping(value = "/ticketInfo", method = RequestMethod.POST,produces = "application/json; charset=utf8")
	public void ticket(HttpServletResponse response, HttpServletRequest request, Model model) {
		//ModelAndView view = new ModelAndView("map");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = null;
		String m_id = request.getParameter("m_id");
		
		try {
			out = response.getWriter();
			//URLDecoder.decode(name, "UTF-8")

			String jsonStr = RHandle.checkReserve(m_id);
			if(jsonStr != null) {
				out.print(jsonStr);
				out.flush();
			}
		} catch (IOException e) {
			out.print(e.getMessage());
		}
		
	}
	
	/* 유효한 이용권을 가진 회원인지 확인 && 자전거 상태 확인 등 삽입 전 확인 */
	@RequestMapping(value = "/ticketCheckInfo", method = RequestMethod.POST) 
	public void checkActiveT(HttpServletResponse response, HttpServletRequest request, Model model) {
		//ModelAndView view = new ModelAndView("map");
		PrintWriter out = null;
		String m_id = request.getParameter("mi");
		String s_id = request.getParameter("si");
		String b_id = request.getParameter("bi");
		try {
			out = response.getWriter();
			String jsonStr = RHandle.checkRental(m_id,s_id,b_id);
			if(jsonStr != null) {
				out.print(jsonStr);
				out.flush();
			}
		} catch (IOException e) {
			out.print(e.getMessage());
		}		
	}
	
	/* 대여 데이터 최종 삽입 결정 */
	@RequestMapping(value = "/insertRentalInfo", method = RequestMethod.POST) 
	public void insertR(HttpServletResponse response, HttpServletRequest request, Model model) {
		PrintWriter out = null;
		String m_id = request.getParameter("mi");
		String t_id = request.getParameter("ti");
		String s_id = request.getParameter("si");
		String b_id = request.getParameter("bi");
		try {
			out = response.getWriter();
			String jsonStr = RHandle.insertRental(m_id,t_id,s_id,b_id);
			if(jsonStr != null) {
				out.print(jsonStr);
				out.flush();
			}
		} catch (IOException e) {
			out.print(e.getMessage());
		}
		
	}
	
	
	/* r_use 사용중인지 확인 && 거치대 수 확인 등 반납 삽입 전 확인 */
	@RequestMapping(value = "/returnCheckInfo", method = RequestMethod.POST) 
	public void checkreturnT(HttpServletResponse response, HttpServletRequest request, Model model) {
		PrintWriter out = null;
		String m_id = request.getParameter("mi");
		try {
			out = response.getWriter();
			String jsonStr = RRHandle.getRental(m_id);
			if(jsonStr != null) {
				out.print(jsonStr);
				out.flush();
			}
		} catch (IOException e) {
			out.print(e.getMessage());
		}		
	}
	
	/* 반납 내역 삽입 재고 +1, bi cond, r_use 2면서 반납시간 체크 */
	@RequestMapping(value = "/insertReturnInfo", method = RequestMethod.POST) 
	public void returnR(HttpServletResponse response, HttpServletRequest request, Model model) {
		PrintWriter out = null;
		String r_id = request.getParameter("ri");
		String m_id = request.getParameter("mi");
		String t_id = request.getParameter("ti");
		String s_id = request.getParameter("si");
		String b_id = request.getParameter("bi");
		try {
			out = response.getWriter();
			String jsonStr = RRHandle.insertReturn(r_id,m_id,t_id,s_id,b_id);
			if(jsonStr != null) {
				out.print(jsonStr);
				out.flush();
			}
		} catch (IOException e) {
			out.print(e.getMessage());
		}
		
	}
	
	
	/* use_time > 60, 추가 납입 */
	@RequestMapping(value = "/insertPaymentInfo", method = RequestMethod.POST) 
	public void insertPayment(HttpServletResponse response, HttpServletRequest request, Model model) {
		PrintWriter out = null;
		String ri = request.getParameter("ri");
		String mi = request.getParameter("mi");
		String ti = request.getParameter("ti");
		int use_time = Integer.parseInt(request.getParameter("use_time"));

		try {
			out = response.getWriter();
			String jsonStr = RRHandle.insertPayment(ri,mi,ti,use_time);
			if(jsonStr != null) {
				out.print(jsonStr);
				out.flush();
			}
		} catch (IOException e) {
			out.print(e.getMessage());
		}
		//model.addAttribute("s_id",s_id);
		
	}
	
	
	/*  데이터 체크용 select, 버튼과 연결  */
	
	@RequestMapping(value = "/selectBicycleInfo", method = RequestMethod.POST) 
	public void selectBicycleT(HttpServletResponse response, HttpServletRequest request, Model model) {
		PrintWriter out = null;
		
		try {
			out = response.getWriter();
			String jsonStr = ReHandle.selectTbicycle();
			if(jsonStr != null) {
				out.print(jsonStr);
				out.flush();
			}
		} catch (IOException e) {
			out.print(e.getMessage());
		}		
	}
	
	@RequestMapping(value = "/selectStationInfo", method = RequestMethod.POST) 
	public void selectStationT(HttpServletResponse response, HttpServletRequest request, Model model) {
		PrintWriter out = null;
		
		try {
			out = response.getWriter();
			String jsonStr = ReHandle.selectTstation();
			if(jsonStr != null) {
				out.print(jsonStr);
				out.flush();
			}
		} catch (IOException e) {
			out.print(e.getMessage());
		}		
	}
	
	@RequestMapping(value = "/selectUseTicketInfo", method = RequestMethod.POST) 
	public void selectuseTicketT(HttpServletResponse response, HttpServletRequest request, Model model) {
		PrintWriter out = null;
		
		try {
			out = response.getWriter();
			String jsonStr = ReHandle.selectTuse_ticket();
			if(jsonStr != null) {
				out.print(jsonStr);
				out.flush();
			}
		} catch (IOException e) {
			out.print(e.getMessage());
		}		
	}
}
