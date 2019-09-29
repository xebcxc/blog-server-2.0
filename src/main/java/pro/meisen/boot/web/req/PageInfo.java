package pro.meisen.boot.web.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author meisen
 * 2019-07-13
 */
@ApiModel
public class PageInfo {
    @ApiModelProperty("当前页")
    private Integer pageNum;
    @ApiModelProperty("当前页码")
    private Integer pageSize;

    public PageInfo() {
    }

    public PageInfo(Integer pageNum, Integer pageSize) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

}
