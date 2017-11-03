package com.jun.timer.common;

import java.io.Serializable;

/**
 * Created by jun
 * 17/7/23 上午11:14.
 * des:
 */
public class ClientException extends Exception implements Serializable{

    private static final long serialVersionUID = 1553166154182291086L;

    public ClientException(String addr,Exception cause){
    super("send request to <" + addr + "> failed", cause);

}
}
