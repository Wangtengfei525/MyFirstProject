package com.coolcloud.sacw.disposal.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coolcloud.sacw.common.Result;
import com.coolcloud.sacw.disposal.entity.DisposalUnit;
import com.coolcloud.sacw.disposal.entity.DisposalUnitExample;
import com.coolcloud.sacw.disposal.service.DisposalUnitService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@RequestMapping("/disposalUnit")
@RestController
public class DisposalUnitController {
	@Autowired
    private DisposalUnitService disposalUnitService;
	 @GetMapping("/list")
	    public Result list(DisposalUnitExample disposalUnitExample) {
	        Integer page = disposalUnitExample.getPage();
	        Integer rows = disposalUnitExample.getRows();
	        if (page != null && rows != null) {
	            PageHelper.startPage(page, rows);
	        }
	        List<DisposalUnit> disposalUnits = disposalUnitService.getByExample(disposalUnitExample);
	        PageInfo<DisposalUnit> pageInfo = new PageInfo<>(disposalUnits);
	        return Result.success().total(pageInfo.getTotal()).rows(pageInfo.getList());
	    }
}
