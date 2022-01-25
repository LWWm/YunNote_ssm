package mystudy.controller;


import mystudy.po.Note;
import mystudy.po.User;
import mystudy.service.NoteService;
import mystudy.utils.Page;
import mystudy.vo.NoteVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/index")
public class IndexController {

	@Autowired
	private NoteService noteService;

	@RequestMapping("/searchTitle")
	private String  searchTitle(String title, HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setAttribute("menu_page", "index");
		request.setAttribute("action", "searchTitle");
		request.setAttribute("title", title);
		noteList(request, response, title , null, null);
		request.setAttribute("changePage", "/note/list.jsp");
		return "WEB-INF/index";
	}

	@RequestMapping("/searchDate")
	private String searchDate(String date, HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setAttribute("menu_page", "index");
		request.setAttribute("action", "searchDate");
		request.setAttribute("date", date);
		noteList(request, response, null, date, null);
		request.setAttribute("changePage", "/note/list.jsp");
		return "WEB-INF/index";
	}

	@RequestMapping("/searchType")
	private String searchType(String typeId, HttpServletRequest request, HttpServletResponse response ) throws IOException {
		request.setAttribute("menu_page", "index");
		request.setAttribute("action", "searchType");
		request.setAttribute("typeId", typeId);
		noteList(request, response, null, null,typeId);
		request.setAttribute("changePage", "/note/list.jsp");
		return "WEB-INF/index";
	}

	@RequestMapping("/searchAll")
	private String searchAll(HttpServletRequest request, HttpServletResponse response ) throws IOException {
		request.setAttribute("menu_page", "index");
		request.setAttribute("action", "searchAll");
		noteList(request, response, null, null,null);
		request.setAttribute("changePage", "/note/list.jsp");
		return "WEB-INF/index";
	}

	private void noteList(HttpServletRequest request, HttpServletResponse response,
						  String title, String date, String typeId) throws IOException {
		String pageNum = request.getParameter("pageNum");
		String pageSize = request.getParameter("pageSize");
		User user = (User) request.getSession().getAttribute("user");
		Page<Note> page = noteService.findNoteListByPage(pageNum, pageSize, user.getUserId(), title, date, typeId);
		request.setAttribute("page", page);

		List<NoteVo> dataInfo = noteService.findNoteCountByDate(user.getUserId());
		request.getSession().setAttribute("dataInfo", dataInfo);

		List<NoteVo> typeInfo = noteService.findNoteCountByType(user.getUserId());
		request.getSession().setAttribute("typeInfo", typeInfo);

	}

}
