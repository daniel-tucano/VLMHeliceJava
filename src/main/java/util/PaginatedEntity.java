package util;

import java.util.List;

public class PaginatedEntity<T> {
    public List<T> docs;
    public Integer totalDocs;
    public Integer limit;
    public Integer totalPages;
    public Integer page;
    public Integer pagingCounter;
    public Boolean hasPrev;
    public Integer prevPage;
    public Boolean hasNext;
    public Integer nextPage;

    public PaginatedEntity(){}
}
