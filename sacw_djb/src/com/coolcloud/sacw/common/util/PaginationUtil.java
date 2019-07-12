package com.coolcloud.sacw.common.util;

import com.coolcloud.sacw.common.BasePaginationForm;
import com.github.pagehelper.PageHelper;

public class PaginationUtil {

    /**
     * 需要分页时开启分页
     * 
     * @param paginationForm
     *            分页表单值
     */
    public static void startPageIfNeed(BasePaginationForm paginationForm) {

        Integer page = paginationForm.getPage();
        Integer rows = paginationForm.getRows();

        if (page != null && rows != null) {
            PageHelper.startPage(page, rows);
        }
    }

}
