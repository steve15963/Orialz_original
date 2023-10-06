package com.orialz.backend.blur.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FindKeywordGetResponse {
	String label;
	double x;
	double y;
	double w;
	double h;
}
