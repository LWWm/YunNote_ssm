package mystudy.service.impl;

import mystudy.mapper.NoteMapper;
import mystudy.po.Note;
import mystudy.po.NoteType;
import mystudy.service.NoteService;
import mystudy.utils.Page;
import mystudy.vo.NoteVo;
import mystudy.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class NoteServiceImpl implements NoteService {

	@Autowired
	private NoteMapper noteMapper;

	public Page<Note> findNoteListByPage(String pageNumStr, String pageSizeStr, Integer userId,
										  String title, String date, String typeId) throws IOException {
		Integer pageNum = 1;
		Integer pageSize = 10;

		if (pageNumStr != null) {
			pageNum = Integer.parseInt(pageNumStr);
		}
		if (pageSizeStr != null) {
			pageSize = Integer.parseInt(pageSizeStr);
		}

		long count = noteMapper.findNoteCount(userId, title, date, typeId); // 【难点hmf】

		if (count < 1) {
			return null;
		}

		Page<Note> page = new Page<Note>(pageNum, pageSize, count);

		Integer index = (pageNum - 1) * pageSize;

		List<Note> noteList = noteMapper.findNoteListByPage(userId, index, pageSize, title, date, typeId); // 【难点hmf】

		page.setDataList(noteList);

		return page;
	}

	@Override
	public List<NoteVo> findNoteCountByDate(Integer userId) throws IOException {
		List<NoteVo> noteCountByDate = noteMapper.findNoteCountByDate(userId);
		return noteCountByDate;
	}

	@Override
	public List<NoteVo> findNoteCountByType(Integer userId) throws IOException {
		List<NoteVo> noteCountByType = noteMapper.findNoteCountByType(userId);
		return noteCountByType;
	}

	@Override
	public Note findNodeById(Integer noteId) throws IOException {
		if (noteId ==null)
			return null;
		Note note = noteMapper.findNoteById(noteId);
		return note;
	}

	@Override
	public List<NoteType> findTypeList(Integer userId) throws IOException {
		if (userId == null)
			return null;
		List<NoteType> typeList = noteMapper.findTypeListByUserId(userId);
		return typeList;
	}

	@Override
	public ResultInfo<Note> addOrUpdate(Integer typeId, String title, String content, Integer noteId, Float lon, Float lat) throws IOException {
		ResultInfo<Note> resultInfo = new ResultInfo<>();
		if (typeId ==null) {
			resultInfo.setCode(0);
			resultInfo.setMsg("请选择云记类型！");
			return resultInfo;
		}
		if (title ==null) {
			resultInfo.setCode(0);
			resultInfo.setMsg("云记标题不能为空！");
			return resultInfo;
		}
		if (typeId ==null) {
			resultInfo.setCode(0);
			resultInfo.setMsg("云记内容不能为空！");
			return resultInfo;
		}
		if (lon == 0.0f || lat == 0.0f) {
			lon = 116.404f;
			lat = 39.915f;
		}

		Note note = new Note();
		note.setTypeId(typeId);
		note.setTitle(title);
		note.setContent(content);
		note.setLat(lat);
		note.setLon(lon);

		if (noteId != null) {
			note.setNoteId(noteId);
		}

		int row;
		if (noteId == null) {
			row = noteMapper.insertNote(note);
		} else {
			row = noteMapper.updateNote(note);
		}

		if (row > 0) {
			resultInfo.setCode(1);
		} else {
			resultInfo.setCode(0);
			resultInfo.setMsg("更新失败");
			resultInfo.setResult(note);
		}

		return resultInfo;
	}

	@Override
	public Note findNoteById(Integer noteId) throws IOException {
		if (noteId ==null )
			return null;
		Note note = noteMapper.findNoteById(noteId);
		return note;
	}

	@Override
	public Integer deleteNote(Integer noteId) throws IOException {
		if (noteId == null)
			return null;
		Integer code = noteMapper.deleteNoteById(noteId);
		return code;
	}

	@Override
	public ResultInfo<Map<String, Object>> queryNoteCountByMonth(Integer userId) throws IOException {
		ResultInfo<Map<String, Object>> resultInfo = new ResultInfo<>();

		List<NoteVo> noteVoList = noteMapper.findNoteCountByDate(userId);
		if (noteVoList != null && noteVoList.size() > 0) {
			ArrayList<String> monthList = new ArrayList<>();
			ArrayList<Integer> noteCountList = new ArrayList<>();
			for (NoteVo noteVo : noteVoList) {
				monthList.add(noteVo.getGroupName());
				noteCountList.add((int) noteVo.getNoteCount());
			}
			HashMap<String, Object> map = new HashMap<>();
			map.put("monthArray", monthList); // key值，要与前台取的一致
			map.put("dataArray", noteCountList); // key值，要与前台取的一致

			// 将map对象设置到ResultInfo对象中
			resultInfo.setCode(1);
			resultInfo.setResult(map);
		}

		return resultInfo;
	}

	@Override
	@ResponseBody
	public ResultInfo<List<Note>> queryNoteLonAndLat(Integer userId) throws IOException {
		ResultInfo<List<Note>> resultInfo = new ResultInfo<>();

		List<Note> noteList = noteMapper.queryNoteList(userId);
		if (noteList != null && noteList.size() > 0) {
			resultInfo.setCode(1);
			resultInfo.setResult(noteList);
		}

		return resultInfo;
	}
}
