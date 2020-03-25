@Autowired
	RecordHandle ReHandle;
	
	/* 이용권 구매 페이지 */
	@RequestMapping(value = "/rstTicket", method = RequestMethod.GET)
	public void rstTicket(HttpServletResponse response, Model model) {		
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = null;
		
		try {
			out = response.getWriter();
			String jsonStr = ReHandle.selectTicket();
			if(jsonStr != null) {
				out.print(jsonStr);
				out.flush();
			}
		} catch (IOException e) {
			out.print(e.getMessage());
		}
	}
	
	
	
	@RequestMapping(value = "/checkBuyTicketInfo", method = RequestMethod.POST,produces = "application/json; charset=utf8")
	public void checkbuyticket(HttpServletResponse response, HttpServletRequest request, Model model) {
		//ModelAndView view = new ModelAndView("map");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = null;
		String m_id = request.getParameter("m_id");
		
		try {
			out = response.getWriter();
			//URLDecoder.decode(name, "UTF-8")

			String jsonStr = ReHandle.checkBuyTicket(m_id);
			if(jsonStr != null) {
				out.print(jsonStr);
				out.flush();
			}
		} catch (IOException e) {
			out.print(e.getMessage());
		}
		
	}
	
	@RequestMapping(value = "/checkRuseInfo", method = RequestMethod.POST,produces = "application/json; charset=utf8")
	public void checkruse(HttpServletResponse response, HttpServletRequest request, Model model) {
		//ModelAndView view = new ModelAndView("map");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = null;
		String m_id = request.getParameter("m_id");
		
		try {
			out = response.getWriter();
			//URLDecoder.decode(name, "UTF-8")

			String jsonStr = ReHandle.checkRuse(m_id);
			if(jsonStr != null) {
				out.print(jsonStr);
				out.flush();
			}
		} catch (IOException e) {
			out.print(e.getMessage());
		}
		
	}
	
	
	@RequestMapping(value = "/insertBuyTicketInfo", method = RequestMethod.POST,produces = "application/json; charset=utf8")
	public void insertbuyticket(HttpServletResponse response, HttpServletRequest request, Model model) {
		//ModelAndView view = new ModelAndView("map");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = null;
		String m_id = request.getParameter("m_id");
		int t_day = Integer.parseInt(request.getParameter("t_day"));
		String type_id = request.getParameter("type_id");
		String insurance_yn = request.getParameter("insurance_yn");
		String t_id = request.getParameter("t_id");
		int money = Integer.parseInt(request.getParameter("money"));
		
		try {
			out = response.getWriter();
			//URLDecoder.decode(name, "UTF-8")

			String jsonStr = ReHandle.insertBuy(m_id, t_day, type_id, insurance_yn, money);
			if(jsonStr != null) {
				out.print(jsonStr);
				out.flush();
			}
		} catch (IOException e) {
			out.print(e.getMessage());
		}
		
	}
	
