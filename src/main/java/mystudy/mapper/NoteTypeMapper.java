package mystudy.mapper;

import mystudy.po.NoteType;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NoteTypeMapper {
	List<NoteType> queryRows(Integer userId);

	long findNoteCountByTypeId(Integer typeId);

	int deleteTypeById(Integer typeId);

	NoteType checkTypeName(@Param("typeId") Integer typeId, @Param("typeName") String typeName, @Param("userId") Integer userId);

	Integer updateType(@Param("typeName") String typeName, @Param("typeId") Integer typeId);

	int addType(NoteType temp);
}
