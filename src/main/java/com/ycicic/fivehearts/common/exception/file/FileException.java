package com.ycicic.fivehearts.common.exception.file;

import com.ycicic.fivehearts.common.exception.base.BaseException;

/**
 * 文件信息异常类
 * 
 * @author ycicic
 */
public class FileException extends BaseException
{
    private static final long serialVersionUID = 1L;

    public FileException(String code, Object[] args)
    {
        super("file", code, args, null);
    }

}
