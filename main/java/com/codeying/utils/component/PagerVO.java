package com.codeying.utils.component;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;

public class PagerVO<T> extends Page<T> {
   private String statisticInfo;

    public PagerVO(IPage<T> page) {
        BeanUtils.copyProperties(page,this);
    }

    public String getStatisticInfo() {
        return statisticInfo;
    }

    public void setStatisticInfo(String statisticInfo) {
        this.statisticInfo = statisticInfo;
    }
}
