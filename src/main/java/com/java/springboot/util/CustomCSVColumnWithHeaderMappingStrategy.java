package com.java.springboot.util;

import com.opencsv.bean.ColumnPositionMappingStrategy;

/**
 *  Extending "ColumnPositionMappingStrategy" to add custom column headers.
 * @param <T>
 */
public class CustomCSVColumnWithHeaderMappingStrategy<T> extends ColumnPositionMappingStrategy<T> {

    private String[] header = null;

    public CustomCSVColumnWithHeaderMappingStrategy(String[] header){
        super();
        this.header = header;
    }

    public CustomCSVColumnWithHeaderMappingStrategy(){
        super();
    }

    @Override
    public String[] generateHeader(T bean){
        return this.header;
    }

}
