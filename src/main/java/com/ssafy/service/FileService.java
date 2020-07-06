package com.ssafy.service;

import com.ssafy.vo.Member;

import java.io.IOException;

public interface FileService<T> {
    public int fileDelete(T vo);
    public void fileSave(T vo) throws IOException;
}
