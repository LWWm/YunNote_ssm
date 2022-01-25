package mystudy.controller;

import mystudy.po.Note;
import mystudy.po.User;
import mystudy.service.NoteService;
import mystudy.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/report")
public class ReportController {

	@Autowired
	private NoteService noteService;

	@RequestMapping("/info")
	private String reportInfo(HttpServletRequest request ) {
		request.setAttribute("menu_page", "report");
		request.setAttribute("changePage", "/report/info.jsp");
		return "WEB-INF/index";
	}

	@RequestMapping("/month")
	@ResponseBody
	private ResultInfo<Map<String, Object>> queryNoteCountByMonth(HttpServletRequest request) throws IOException {
		request.setAttribute("menu_page", "report");
		User user = (User) request.getSession().getAttribute("user");
		ResultInfo<Map<String, Object>> resultInfo = noteService.queryNoteCountByMonth(user.getUserId());
		return resultInfo;
	}

	@RequestMapping("/location")
	@ResponseBody
	private ResultInfo<List<Note>> queryNoteLonAndLat(HttpServletRequest request) throws IOException {
		request.setAttribute("menu_page", "report");
		User user = (User) request.getSession().getAttribute("user");
		ResultInfo<List<Note>> resultInfo = noteService.queryNoteLonAndLat(user.getUserId());
		return resultInfo;
	}
}
