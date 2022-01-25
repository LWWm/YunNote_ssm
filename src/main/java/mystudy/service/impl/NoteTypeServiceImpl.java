package mystudy.service.impl;

import mystudy.mapper.NoteTypeMapper;
import mystudy.po.NoteType;
import mystudy.service.NoteTypeService;
import mystudy.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class NoteTypeServiceImpl implements NoteTypeService {


	@Autowired
	private NoteTypeMapper noteTypeMapper;


	@Override
	public List<NoteType> findTypeList(Integer userId) throws IOException {
		List<NoteType> list = noteTypeMapper.queryRows(userId);
		return list;
	}

	@Override
	public ResultInfo<NoteType> deleteType(Integer typeId) throws IOException {
		ResultInfo<NoteType> noteTypeResultInfo = new ResultInfo<>();
		if (typeId ==null) {
			noteTypeResultInfo.setCode(0);
			noteTypeResultInfo.setMsg("系统异常，请重试");
			return noteTypeResultInfo;
		}

		long noteCount = noteTypeMapper.findNoteCountByTypeId(typeId);
		if (noteCount > 0) {
			noteTypeResultInfo.setCode(0);
			noteTypeResultInfo.setMsg("该类型存在子记录，不可删除！");
			return noteTypeResultInfo;
		}

		int row = noteTypeMapper.deleteTypeById(typeId);

		if (row > 0) {
			noteTypeResultInfo.setCode(1);
		} else {
			noteTypeResultInfo.setCode(0);
			noteTypeResultInfo.setMsg("删除失败！");
		}
		return noteTypeResultInfo;
	}

	@Override
	public ResultInfo<Integer> addOrUpdate(Integer typeId, String typeName, Integer userId) throws IOException {

		ResultInfo<Integer> resultInfo = new ResultInfo<>();
		if (typeName ==null ) {
			resultInfo.setCode(0);
			resultInfo.setMsg("类型名称不能为空！");
			return resultInfo;
		}
		//检验名称是否冲突
		NoteType noteType = noteTypeMapper.checkTypeName(typeId, typeName, userId);

		int code = 0;
		if (noteType == null) {
			code = 1;
		}else {
			if (typeId == noteType.getTypeId()) {
				System.out.println("typeId == noteType.getTypeId()");
				code = 1;
			}
		}

		if (code == 0) {
			resultInfo.setCode(0);
			resultInfo.setMsg("类型名称已经存在，请重新输入！");
			System.out.println("类型名称已经存在");
			return resultInfo;
		}

		Integer key = null;
		if (typeId ==null) {
			NoteType temp = new NoteType();
			temp.setTypeName(typeName);
			temp.setUserId(userId);
			noteTypeMapper.addType(temp);
			key = temp.getTypeId();
		} else {
			key = noteTypeMapper.updateType(typeName, typeId);
		}

		if (key > 0) {
			resultInfo.setCode(1);
			resultInfo.setResult(key);
		} else {
			resultInfo.setCode(0);
			resultInfo.setMsg("更新失败！");
		}
		return resultInfo;
	}
}
