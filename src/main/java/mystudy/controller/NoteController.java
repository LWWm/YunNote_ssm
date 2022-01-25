package mystudy.controller;

import mystudy.po.Note;
import mystudy.po.NoteType;
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

@Controller
@RequestMapping("/note")
public class NoteController {
	@Autowired
	private NoteService noteService;

	@RequestMapping("/view")
	private String view(HttpServletRequest request, Integer noteId ) throws IOException {
		request.setAttribute("menu_page", "note");
		Note note = noteService.findNodeById(noteId);
		request.setAttribute("noteInfo", note);

		User user = (User) request.getSession().getAttribute("user");
		List<NoteType> typeList = noteService.findTypeList(user.getUserId());
		request.setAttribute("typeList", typeList);

		// 4.设置首页动态包含的右侧页面值
		request.setAttribute("changePage", "/note/view.jsp");
		return "WEB-INF/index";
	}

	@RequestMapping("/addOrUpdateView")
	private String addOrUpdateView(HttpServletRequest request,Integer typeId,String title,
								   String content,Float lon,Float lat,Integer noteId ) throws IOException {
		request.setAttribute("menu_page", "note");
		ResultInfo<Note> resultInfo = noteService.addOrUpdate(typeId, title, content, noteId, lon, lat);
		if (resultInfo.getCode() == 1) {
			//更新成功
			return "redirect:/index/searchAll";
		} else {
			request.setAttribute("resultInfo", resultInfo);
			if (noteId ==null) {
				return "note/view";
			} else {
				return "note/view?noteId="+noteId;
			}
		}
	}

	@RequestMapping("/detail")
	private String noteDetail(HttpServletRequest request, Integer noteId) throws IOException {
		request.setAttribute("menu_page", "note");
		Note note = noteService.findNoteById(noteId);
		request.setAttribute("note", note);
		request.setAttribute("changePage", "/note/detail.jsp");
		return "WEB-INF/index";
	}

	@RequestMapping("/delete")
	@ResponseBody
	private String noteDelete(HttpServletRequest request, Integer noteId) throws IOException {
		request.setAttribute("menu_page", "note");
		Integer code = noteService.deleteNote(noteId);
		return code + "";
	}


}
