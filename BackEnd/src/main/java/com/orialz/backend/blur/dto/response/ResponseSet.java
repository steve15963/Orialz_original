package com.orialz.backend.blur.dto.response;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class ResponseSet {
	double time;
	List<FindKeywordGetResponse> objects;

}