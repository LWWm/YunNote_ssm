package mystudy.service;

import mystudy.po.NoteType;
import mystudy.vo.ResultInfo;

import java.io.IOException;
import java.util.List;

public interface NoteTypeService {
	List<NoteType> findTypeList(Integer userId) throws IOException;

	ResultInfo<NoteType> deleteType(Integer typeId) throws IOException;

	ResultInfo<Integer> addOrUpdate(Integer typeId, String typeName, Integer userId) throws IOException;
}
