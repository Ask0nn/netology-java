package com.ask0n;

@FunctionalInterface
public interface OnTaskErrorListener {
    void onError(String error);
}
