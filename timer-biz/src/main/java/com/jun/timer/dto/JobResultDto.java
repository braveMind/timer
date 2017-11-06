package com.jun.timer.dto;



import java.io.Serializable;
import java.util.List;

/**
 * Created by xunaiyang on 2017/7/11.
 */
public class JobResultDto implements Serializable {
    private List<JobDto> jobDtoList;
    private Page page;

    public List<JobDto> getJobDtoList() {
        return jobDtoList;
    }

    public void setJobDtoList(List<JobDto> jobDtoList) {
        this.jobDtoList = jobDtoList;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }
}
