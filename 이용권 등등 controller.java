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
  
  
  
