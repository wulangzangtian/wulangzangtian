
package wu.lang.wedding.api.constant;

import com.fasterxml.jackson.annotation.JsonProperty;
import wu.lang.wedding.api.vo.base.IErrorEnum;


public enum ErrorCode implements IErrorEnum {
    SUCCESS(1, "成功!"),
    FAIL_COUPON(201,"兑换码已兑换完"),
    FAIL_COUPON_AGAIN(202,"请再试一次"),

    INVITE_HAS_BIND(301,"已经绑定过他人"),
    INVITE_NOT_ROLE(302,"没有角色信息"),
    INVITE_LV_OVER(303,"绑定他们账号等级超过"),
    INVITE_CODE_INVALID(304,"邀请码无效"),
    INVITE_MEMBER_COUNT_OVER(305,"邀请人过多"),
    INVITE_HAVE_RECEIVED_THE_REWARD(306,"已领取过奖励"),
    INVITE_DIFFERENT_QUDAO(307,"渠道不同"),

    RANK_GAME_ROLE_NOT_EXIST(401,"玩家不存在"),
    RANK_TYPE_ERROR(401,"排行榜类型错误"),
    RANK_SCORE_ERROR(403,"排行分数错误"),
    ;


    /**
     * 错误的代码
     */
    @JsonProperty
    private int errCode;

    /**
     * 错误的默认提示信息
     */
    @JsonProperty
    private String errInfo;

    ErrorCode(int errCode, String errInfo) {
        this.errCode = errCode;
        this.errInfo = errInfo;
    }

    /**
     * 默认toString方法改为返回错误代码
     *
     * @return 错误代码
     */
    @Override
    public String toString() {
        return String.valueOf(this.errCode);
    }

    /**
     * 获取错误提示
     *
     * @return
     */
    @Override
    public String getErrInfo() {
        return this.errInfo;
    }

    @Override
    public int getErrCode() {
        return this.errCode;
    }
}

