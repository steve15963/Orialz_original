package com.orialz.backend.blur.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.orialz.backend.blur.Config.Mapper;
import com.orialz.backend.blur.domain.entity.FindKeyword;
import com.orialz.backend.blur.domain.repository.FindKeywordRepository;
import com.orialz.backend.blur.dto.response.FindKeywordGetResponse;
import com.orialz.backend.blur.dto.response.ResponseSet;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class FindKeywordService {
	FindKeywordRepository keywordRepository;
	private final Mapper mapper;
	public Map<String,List<ResponseSet>> findKeywordList(long mId, long vId, long per){
		ModelMapper mp = mapper.FindKeywordToFindKeywordReponseMapper();
		Map<Double,List<FindKeywordGetResponse>> map = new TreeMap<>();
		List<FindKeyword> findList = keywordRepository.findAllBymIdAndvId(mId, vId, ((double)per) / 100.0);

		for(FindKeyword t : findList){

			Double time = t.getTime();
			if(map.containsKey(time)){
				List<FindKeywordGetResponse> timelist = map.get(time);
				timelist.add(mp.map(t,FindKeywordGetResponse.class));
			}
			else{
				List<FindKeywordGetResponse> timelist = new ArrayList<>();
				System.out.println(t);
				FindKeywordGetResponse map1 = mp.map(t, FindKeywordGetResponse.class);
				System.out.println(map1);
				timelist.add(map1);
				map.put(time,timelist);
			}
		}
		Map<String,List<ResponseSet>> response = new TreeMap<>();
		List<ResponseSet> data = new ArrayList<>();
		response.put(
			"data",
			data
		);
		map.forEach((key, value)->{
			data.add(
				ResponseSet.builder()
					.time(key)
					.objects(value)
					.build()
			);
		});
		return response;
	}
}
