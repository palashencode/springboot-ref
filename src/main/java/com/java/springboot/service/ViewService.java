package com.java.springboot.service;

import com.java.springboot.model.RowData;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ViewService {
    public long getTime();
    public List<RowData> getFeatures();
}
