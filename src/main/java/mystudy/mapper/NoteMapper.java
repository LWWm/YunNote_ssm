package mystudy.mapper;

import mystudy.po.Note;
import mystudy.po.NoteType;
import mystudy.vo.NoteVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface NoteMapper {
	long findNoteCount(@Param("userId") Integer userId, @Param("title") String title,
					   @Param("date") String date, @Param("typeId") String typeId);

	List<Note> findNoteListByPage(@Param("userId") Integer userId, @Param("index") Integer index,
								  @Param("pageSize") Integer pageSize, @Param("title") String title,
								  @Param("date") String date, @Param("typeId") String typeId);

	List<Note> findNoteListOnPage(@Param("userId") Integer userId, @Param("title") String title,
								  @Param("date") String date, @Param("typeId") String typeId);

	List<NoteVo> findNoteCountByDate(Integer userId);

	List<NoteVo> findNoteCountByType(Integer userId);

	Note findNoteById(Integer noteId);

	List<NoteType> findTypeListByUserId(Integer userId);

	int addOrUpdate(Note note);

	int insertNote(Note note);

	int updateNote(Note note);

	Integer deleteNoteById(Integer noteId);

	List<Note> queryNoteList(Integer userId);
}
