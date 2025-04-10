package com.xiaozhou.gymxmjpa.util;

import com.xiaozhou.gymxmjpa.entity.Adminuser;
import lombok.extern.slf4j.Slf4j;

/**
 * @author durunwu
 * @date 2025-04-09-1:08
 */
@Slf4j
public class ThreadLocalHolder {

    private static final ThreadLocal<Adminuser> currentUser = new ThreadLocal<>();

    public static void setCurrentUser(Adminuser user) {
        log.info("设置当前用户 => {}", user.getAdminName());
        currentUser.set(user);
    }

    public static Adminuser getCurrentUser() {
        return currentUser.get();
    }

    public static void removeCurrentUser() {
        currentUser.remove();
    }
}
