package pro.meisen.boot.web.res;

import pro.meisen.boot.web.req.PageModel;

import java.util.List;

/**
 * @author meisen
 * 2019-07-13
 */
public class ResultPageData<T> {

    private List<T> data;

    private Long count;

    private PageModel pageModel;

    public ResultPageData() {
    }

    public ResultPageData(List<T> data, Long count) {
        this.data = data;
        this.count = count;
    }

    public ResultPageData(List<T> data, Long count, PageModel pageModel) {
        this.data = data;
        this.count = count;
        this.pageModel = pageModel;
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

    public PageModel getPageModel() {
        return pageModel;
    }

    public void setPageModel(PageModel pageModel) {
        this.pageModel = pageModel;
    }
}
