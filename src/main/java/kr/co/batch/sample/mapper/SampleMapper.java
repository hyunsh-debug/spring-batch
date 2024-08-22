package kr.co.batch.sample.mapper;

import java.util.List;
import java.util.Map;

import kr.co.batch.sample.dvo.ReadDvo;
import kr.co.batch.sample.dvo.WriteDvo;

public interface SampleMapper {
	List<ReadDvo> selectItems();
	int updateItems(WriteDvo dvo);
}
