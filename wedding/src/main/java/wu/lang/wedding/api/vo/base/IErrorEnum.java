package wu.lang.wedding.api.vo.base;

import java.io.Serializable;

public interface IErrorEnum<T extends Serializable> {

    String getErrInfo();

    int getErrCode();
}
