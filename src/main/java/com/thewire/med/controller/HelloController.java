package com.thewire.med.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/hello")
public class HelloController
{
	@ResponseBody
	public Map<String, Object> getData() {
		Map<String, Object> data = new HashMap<>();
		data.put("message", "Hello World");
		return data;
	}
}
