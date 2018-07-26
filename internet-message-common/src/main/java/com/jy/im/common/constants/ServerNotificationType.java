package com.jy.im.common.constants;

public enum ServerNotificationType {

    /**
     * 登录失效
     * */
    TICKET_INVALID((byte) 1, "登录失效"),

    ;


    public static ServerNotificationType getServerNotificationType(byte value) {
        for(ServerNotificationType serverNotificationType: values()) {
            if(serverNotificationType.value == value) {
                return serverNotificationType;
            }
        }
        return null;
    }

    public final byte value;
    public final String name;

    ServerNotificationType(byte value, String name) {
        this.value = value;
        this.name = name;
    }

    @Override
    public String toString() {
        return "ServerNotificationType{" +
            "value=" + value +
            ", name='" + name + '\'' +
            '}';
    }
}
