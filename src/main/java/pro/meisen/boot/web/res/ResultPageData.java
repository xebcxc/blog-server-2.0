package pro.meisen.boot.web.res;

import pro.meisen.boot.web.req.PageRequest;

import java.util.List;

/**
 * @author meisen
 * 2019-07-13
 */
public class ResultPageData<T> {

    private List<T> data;

    private Long count;

    private PageRequest pageRequest;

    public ResultPageData(List<T> data, Long count, PageRequest pageRequest) {
        this.data = data;
        this.count = count;
        this.pageRequest = pageRequest;
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

    public PageRequest getPageRequest() {
        return pageRequest;
    }

    public void setPageRequest(PageRequest pageRequest) {
        this.pageRequest = pageRequest;
    }
}
