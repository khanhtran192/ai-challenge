package com.mbbank.biz.pro.domain.enumeration;

/**
 * NOTE:
 * - Tránh đụng \"User\" mặc định của JHipster -> dùng AppUser map tới bảng USERS.
 * - Đặt tên bảng bằng cú pháp entity <Name> (<TABLE_NAME>) { ... } theo JDL.
 * - Quan hệ 2 chiều (owner/documents) để tránh lỗi “related field required”.
 */
public enum Role {
    user,
    admin,
}
