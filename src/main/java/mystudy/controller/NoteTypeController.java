package mystudy.controller;

import mystudy.po.NoteType;
import mystudy.po.User;
import mystudy.service.NoteTypeService;
import mystudy.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/type")
public class NoteTypeController {

	@Autowired
	private NoteTypeService noteTypeService;

	@RequestMapping("/list")
	private String list(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setAttribute("menu_page", "type");
		User user = (User) request.getSession().getAttribute("user");
		List<NoteType> typeList = noteTypeService.findTypeList(user.getUserId());
		request.setAttribute("typeList", typeList);
		request.setAttribute("changePage", "/type/list.jsp");
		return "WEB-INF/index";
	}


	@RequestMapping("/delete")
	@ResponseBody
	private ResultInfo delete(HttpServletRequest request,Integer typeId ) throws IOException {
		request.setAttribute("menu_page", "type");
		ResultInfo<NoteType> resultInfo = noteTypeService.deleteType(typeId);
		return resultInfo;
	}

	@RequestMapping("/addOrUpdate")
	@ResponseBody
	private ResultInfo addOrUpdate(HttpServletRequest request, Integer typeId, String typeName) throws IOException {
		request.setAttribute("menu_page", "type");
		User user = (User) request.getSession().getAttribute("user");
		ResultInfo<Integer> resultInfo = noteTypeService.addOrUpdate(typeId, typeName, user.getUserId());
		return resultInfo;
	}

}
