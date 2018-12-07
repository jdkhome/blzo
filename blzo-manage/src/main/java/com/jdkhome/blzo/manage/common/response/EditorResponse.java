package com.jdkhome.blzo.manage.common.response;

/**
 * Created with IntelliJ IDEA.
 * User: wxb
 * CreatedTime: 2018/6/7 下午3:31
 * Description:WangEditor上传图片规定的返回格式
 */
public class EditorResponse {

    private String errno;
    private String[] data;

    //WangEditor规定：上传图片的返回格式，以及"0"为成功，其它为失败
    private static final String SUCCESS_CODE = "0";

    private static final String FAILED_CODE = "100";

    public String getErrno() {
        return errno;
    }

    public void setErrno(String errno) {
        this.errno = errno;
    }

    public String[] getData() {
        return data;
    }

    public void setData(String[] data) {
        this.data = data;
    }

    public EditorResponse() {
    }

    public EditorResponse(String code) {
        this.errno = SUCCESS_CODE;
        this.data = null;
    }

    /**
     * 返回成功响应
     */
    public static EditorResponse successResponse(String[] data) {

        EditorResponse response = new EditorResponse(SUCCESS_CODE);
        if (data != null) {
            response.data = data;
        } else {
            response.data = null;
        }
        return response;
    }

    /**
     * 返回指定错误类型的响应
     */
    public static EditorResponse failResponse() {
        EditorResponse response = new EditorResponse(FAILED_CODE);
        return response;
    }
}
