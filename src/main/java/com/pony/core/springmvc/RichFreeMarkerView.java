package com.pony.core.springmvc;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.view.freemarker.FreeMarkerView;

import com.pony.core.Constants;

public class RichFreeMarkerView extends FreeMarkerView {
	
	@SuppressWarnings({ "unchecked"})
	protected void exposeHelpers(Map model, HttpServletRequest request)
			throws Exception {
		super.exposeHelpers(model, request);
		model.put(Constants.CONTEXT_PATH, request.getContextPath());
		model.put(Constants.DEF_PAGE_SIZE_KEY, Constants.DEF_PAGE_SIZE);
	}
	
	
}
