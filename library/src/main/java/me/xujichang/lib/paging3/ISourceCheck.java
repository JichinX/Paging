package me.xujichang.lib.paging3;


import java.util.List;

public interface ISourceCheck<IN> {

    void checkMoreData(List<?> pList, int size);

    void checkResult(IN pIN) throws Exception;

}
