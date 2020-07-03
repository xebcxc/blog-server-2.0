package fun.baozi.boot.web.res;

import fun.baozi.boot.web.req.PageInfo;

import java.util.List;

/**
 * 分页数据
 * @author baozi
 * 2019-07-13
 */
public class PageData<T> {

    private List<T> data;

    private Long count;

    private PageInfo pageInfo;

    public PageData() {
    }

    public PageData(List<T> data, Long count) {
        this.data = data;
        this.count = count;
    }

    public PageData(List<T> data, Long count, PageInfo pageInfo) {
        this.data = data;
        this.count = count;
        this.pageInfo = pageInfo;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public PageInfo getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }
}
