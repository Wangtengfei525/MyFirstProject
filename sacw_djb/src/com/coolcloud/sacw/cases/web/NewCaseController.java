package com.coolcloud.sacw.cases.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.coolcloud.sacw.cases.entity.NewCase;
import com.coolcloud.sacw.cases.service.NewCaseService;
import com.coolcloud.sacw.operation.entity.NewSearchVo;

@RestController
@RequestMapping("/newcase")
public class NewCaseController {
	
	@Autowired
	public NewCaseService newcaseservice;
	
	@PostMapping("/newcaseinsert")
	public String newinsert(@RequestBody NewCase newcase) {
		return newcaseservice.newinsert(newcase);
	}
	
	@GetMapping("/getcaseinfo")
	public NewCase getCaseInfo(@RequestParam String id) {
		return newcaseservice.getCase(id);
	}
	
	@GetMapping("/putfilecaseid")
	public String putFileCaseId(@RequestParam String id,@RequestParam String case_id) {
		return newcaseservice.putFileCaseId(id, case_id);
	}
	
	@GetMapping("/getallcaseinfo")
	public List<NewCase> getAllCaseInfo(NewSearchVo newSearchVo){
		List<NewCase> caselist=null;
		return caselist;
	}
}
