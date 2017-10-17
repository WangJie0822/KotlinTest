package com.wj.kotlintest.constant


/**
 * 静态常量
 */
interface Constants {

    companion object {

        /** 闪屏界面延迟时间  */
        val DELAY_MILLIS: Long = 1500

        /** 主界面退出计时  */
        val INTERVAL_MILLIS = 2 * 1000L

        /** 默认请求条目个数  */
        val DEFAULT_PAGE_SIZE = 20

        /** 数据库是否升级  */
        val IS_DB_UPDATE = "is_db_update"

        /** 打开手机相机请求码  */
        val REQUEST_CAMERA = 50001
        /** 获取拍照后的照片  */
        val REQUEST_CROP = 50002
        /** 打开手机相册请求码  */
        val REQUEST_PIC = 50003

        /** 行业、职位选择请求码  */
        val REQUEST_INDUSTRY_POSITION = 34634

        /** 缓存地址  */
        val DOWNLOAD_DIR = "CCDownload"

        val ACTION_DOWNLOAD_PAUSE = "com.hentane.other.service.download.pause"
        val ACTION_DOWNLOADING = "com.hentane.other.service.download.downloading"
    }

    /** 用户信息  */
    interface UserInfo {
        companion object {
            /** 用户名  */
            val USER_NAME = "user_name"
            /** 密码  */
            val USER_PWD = "user_pwd"
            /** 登录类型  */
            val USER_LOGIN_TYPE = "user_login_type"
        }
    }

    /** 用户登录类型  */
    interface LoginType {
        companion object {
            /** 手机号登录  */
            val TYPE_LOGIN_PHONE = "1"
            /** 邮箱登录  */
            val TYPE_LOGIN_MAIL = "2"
            /** QQ登录  */
            val TYPE_LOGIN_QQ = "3"
            /** 微博登录  */
            val TYPE_LOGIN_SINA = "4"
            /** 微信登录  */
            val TYPE_LOGIN_WEIXIN = "5"
        }
    }

    /** 网络状态  */
    interface NetState {
        companion object {
            /** 网络不可用  */
            val NET_NOT_AVAILABLE = "net_not_available"
            /** 移动数据网络  */
            val NET_MOBILE = "net_mobile"
            /** WIFI网络  */
            val NET_WIFI = "net_wifi"
        }
    }

    /** 配置信息，SharedPref键名  */
    interface Configuration {
        companion object {
            /** 是否第一次进入  */
            val FIRST_IN = "first_in"
        }
    }

    /** 网络请求返回码  */
    interface ResponseCode {
        companion object {
            /** 成功  */
            val SUCCESS = 200
            /** 登录状态失效  */
            val LOGIN_INVALID = 101
            /** 已在其他设备登录  */
            val LOGIN_ON_OTHER = 102
            /** 密码已变更  */
            val LOGIN_PWD_CHANGED = 103
        }
    }

}
