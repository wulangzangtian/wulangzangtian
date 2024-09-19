package wu.lang.wedding.api.vo.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import wu.lang.wedding.api.constant.ErrorCode;

import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
public class ResultData<T> {

    @JsonIgnore
    public IErrorEnum errCode;

    /**
     * 错误信息代码
     */
    @JsonProperty
    private Integer code;

    /**
     * 错误信息
     */
    @JsonProperty
    private String msg;

    /**
     * 返回数据
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    /**
     * 带data的构造方法
     *
     * @param errCode
     * @param data
     */
    private ResultData(IErrorEnum errCode, T data) {
        this.data = data;
        this.errCode = errCode;
        this.code = Integer.valueOf(errCode.toString());
        this.msg = errCode.getErrInfo();
    }

    /**
     * 不带data的构造方法
     *
     * @param errCode
     */
    private ResultData(IErrorEnum errCode) {
        this.errCode = errCode;
        this.code = Integer.valueOf(errCode.toString());
        this.msg = errCode.getErrInfo();
    }

    private ResultData(Integer code,String msg,T data){
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    /**
     * 利用数组来快速配出Map
     *
     * @return
     */
    public static ResultData ok(IErrorEnum errCode, String[] keys, Object[] values) {
        if (keys.length != values.length) {
            throw new IllegalArgumentException();
        }
        Map map = new HashMap();
        for (int i = 0; i < keys.length; i++) {
            map.put(keys[i], values[i]);
        }
        return new ResultData(errCode, map);
    }

    private ResultData(Map map) {
        if (map.get("code") != null && map.get("msg") != null) {
            this.code = (Integer) map.get("code");
            this.msg = (String) map.get("msg");
        } else {
            this.code = 1;
            this.msg = "成功";
            this.data = (T) map;
        }
    }

    /**
     * 默认成功方法
     *
     * @return {
     * code:1,
     * msg:"成功"
     * }
     */
    public static ResponseEntity success() {
        return ResponseEntity.ok().body(new ResultData(ErrorCode.SUCCESS));
    }

    /**
     * 默认成功,携带数据方法
     *
     * @param body
     * @param <T>
     * @return {code:1, msg:"成功",data:"any body"}
     */
    public static <T> ResponseEntity<ResultData<T>> ok(T body) {
        return ResponseEntity.ok().body(new ResultData(ErrorCode.SUCCESS, body));
    }


    /**
     * 成功方法,引入不同业务代码
     *
     * @param errCode 业务错误代码
     * @return {code:-10000,msg:"错误信息"}
     */
    public static ResponseEntity ok(IErrorEnum errCode) {
        return ResponseEntity.ok().body(new ResultData(errCode));
    }

    /**
     * 失败方法,用于抛异常的时候捕捉后返回前端的值 ,httpstatus为400
     *
     * @param errCode
     * @return
     */
    public static ResponseEntity error(IErrorEnum errCode) {
        return ResponseEntity.badRequest().body(new ResultData(errCode));
    }

    public static <T> ResultData<T> fail(String errorMsg) {
        return new ResultData(500,"请求错误",errorMsg);
    }

    public static <T> ResultData<T> success(T data) {
        return new ResultData(200,"请求成功",data);
    }

    public static <T> ResultData<T> success(Integer code,T data) {
        return new ResultData(code,"请求成功",data);
    }

    public static <T> ResultData<T> success(ErrorCode errorCode) {
        return new ResultData(errorCode.getErrCode(),"请求成功",errorCode.getErrInfo());
    }

}
