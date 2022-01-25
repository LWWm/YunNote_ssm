package mystudy.service;

import mystudy.po.Note;
import mystudy.po.NoteType;
import mystudy.utils.Page;
import mystudy.vo.NoteVo;
import mystudy.vo.ResultInfo;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface NoteService {
	Page<Note> findNoteListByPage(String pageNumStr, String pageSizeStr, Integer userId,
								  String title, String date, String typeId) throws IOException;

	List<NoteVo> findNoteCountByDate(Integer userId) throws IOException;

	List<NoteVo> findNoteCountByType(Integer userId) throws IOException;

	Note findNodeById(Integer noteId) throws IOException;

	List<NoteType> findTypeList(Integer userId) throws IOException;

	ResultInfo<Note> addOrUpdate(Integer typeId, String title, String content, Integer noteId, Float lon, Float lat) throws IOException;

	Note findNoteById(Integer noteId) throws IOException;

	Integer deleteNote(Integer noteId) throws IOException;

	ResultInfo<Map<String, Object>> queryNoteCountByMonth(Integer userId) throws IOException;

	ResultInfo<List<Note>> queryNoteLonAndLat(Integer userId) throws IOException;
}
