# User Synchronization Guide

## Tổng quan

Hệ thống này tự động đồng bộ dữ liệu giữa bảng `jhi_user` (JHipster default) và bảng `users` (AppUser entity) để đảm bảo tính nhất quán dữ liệu.

## Cách hoạt động

### 1. **Tự động Sync**

Khi có các thao tác sau trên `jhi_user`, hệ thống sẽ tự động sync sang bảng `users`:

- ✅ **Tạo user mới** (`registerUser`, `createUser`)
- ✅ **Cập nhật user** (`updateUser`)
- ✅ **Kích hoạt user** (`activateRegistration`)
- ✅ **Xóa user** (`deleteUser`)

### 2. **Mapping Logic**

#### **User → AppUser Mapping:**

| JHipster User          | AppUser        | Logic                                      |
| ---------------------- | -------------- | ------------------------------------------ |
| `firstName + lastName` | `fullName`     | Concat với space, default "User" nếu empty |
| `email`                | `email`        | Lowercase                                  |
| `password`             | `passwordHash` | Copy trực tiếp                             |
| `authorities`          | `role`         | Map theo rules dưới đây                    |
| `createdDate`          | `createdAt`    | Copy hoặc current time                     |
| `lastModifiedDate`     | `updatedAt`    | Copy hoặc current time                     |

#### **Authority → Role Mapping:**

| JHipster Authority     | AppUser Role |
| ---------------------- | ------------ |
| `ROLE_ADMIN`           | `admin`      |
| `ROLE_MANAGER`         | `manager`    |
| `ROLE_USER` hoặc other | `user`       |

### 3. **Smart Sync**

- **Conditional Sync:** Chỉ sync khi cần thiết (dựa trên `lastModifiedDate`)
- **Error Handling:** Lỗi sync không làm fail các operations chính
- **Duplicate Prevention:** Tìm AppUser existing bằng email trước khi tạo mới

## API Endpoints (Admin Only)

### 1. **Sync tất cả users**

```http
POST /api/admin/user-sync/sync-all
Authorization: Bearer <admin-token>
```

**Response:**

```json
{
  "success": true,
  "data": {
    "totalUsers": 10,
    "syncedCount": 9,
    "errorCount": 1,
    "message": "Synced 9 out of 10 users successfully"
  }
}
```

### 2. **Sync user cụ thể**

```http
POST /api/admin/user-sync/sync-user/{login}
Authorization: Bearer <admin-token>
```

**Response:**

```json
{
  "success": true,
  "data": {
    "id": "550e8400-e29b-41d4-a716-446655440000",
    "fullName": "John Doe",
    "email": "john@example.com",
    "role": "user",
    "createdAt": "2024-01-01T10:00:00Z",
    "updatedAt": "2024-01-01T10:00:00Z"
  },
  "message": "User synced successfully"
}
```

### 3. **Kiểm tra sync status**

```http
GET /api/admin/user-sync/status
Authorization: Bearer <admin-token>
```

**Response:**

```json
{
  "success": true,
  "data": {
    "totalUsers": 10,
    "syncNeededCount": 2,
    "userStatus": [
      {
        "login": "admin",
        "email": "admin@localhost",
        "activated": true,
        "syncNeeded": false,
        "lastModified": "2024-01-01T10:00:00Z"
      },
      {
        "login": "user",
        "email": "user@localhost",
        "activated": true,
        "syncNeeded": true,
        "lastModified": "2024-01-01T11:00:00Z"
      }
    ]
  }
}
```

## Testing

### 1. **Test tạo user mới**

```bash
# Tạo user qua JHipster API
curl -X POST http://localhost:8080/api/admin/users \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <token>" \
  -d '{
    "login": "testuser",
    "firstName": "Test",
    "lastName": "User",
    "email": "test@example.com",
    "activated": true,
    "authorities": ["ROLE_USER"]
  }'

# Kiểm tra AppUser được tạo tự động
curl -X GET http://localhost:8080/api/admin/user-sync/status \
  -H "Authorization: Bearer <token>"
```

### 2. **Test bulk sync**

```bash
# Sync tất cả users hiện tại
curl -X POST http://localhost:8080/api/admin/user-sync/sync-all \
  -H "Authorization: Bearer <token>"
```

## Database Schema Impact

### **Trước khi migrate to UUID:**

```sql
-- Cần chạy migration script trước
-- Xem migrate_to_uuid.sql
```

### **Sau khi migrate:**

```sql
-- Bảng jhi_user (JHipster)
jhi_user.id BIGINT AUTO_INCREMENT

-- Bảng users (AppUser)
users.id VARCHAR(36) UUID
```

## Logs và Monitoring

### **Success Logs:**

```
DEBUG UserSyncService - Syncing User testuser to AppUser table
DEBUG UserSyncService - Successfully synced User testuser to AppUser with ID: 550e8400-e29b-41d4-a716-446655440000
DEBUG UserService - Successfully synced new user to AppUser table: testuser
```

### **Error Logs:**

```
ERROR UserService - Failed to sync user to AppUser table: testuser
ERROR UserSyncService - Failed to sync user testuser: [detailed error]
```

## Best Practices

### 1. **Performance**

- Sync chỉ chạy khi cần thiết (smart sync)
- Không block operations chính nếu sync fail
- Batch sync cho multiple users

### 2. **Data Integrity**

- Luôn sync bằng email (unique identifier)
- Preserve timestamps từ original user
- Handle duplicate emails gracefully

### 3. **Error Handling**

- Log tất cả sync operations
- Retry mechanism có thể thêm sau
- Admin tools để manual sync khi cần

## Troubleshooting

### **Issue 1: User không được sync**

```bash
# Check logs
tail -f logs/application.log | grep UserSync

# Manual sync specific user
curl -X POST http://localhost:8080/api/admin/user-sync/sync-user/username \
  -H "Authorization: Bearer <token>"
```

### **Issue 2: Duplicate AppUsers**

```sql
-- Check for duplicates
SELECT email, COUNT(*) FROM users GROUP BY email HAVING COUNT(*) > 1;

-- Clean up duplicates manually if needed
```

### **Issue 3: Role mapping issues**

```java
// Check authority names in jhi_user_authority table
SELECT * FROM jhi_user_authority jua
JOIN jhi_authority ja ON jua.authority_name = ja.name;
```

## Future Enhancements

1. **Bi-directional Sync:** AppUser changes → JHipster User
2. **Conflict Resolution:** Handle concurrent updates
3. **Audit Trail:** Track all sync operations
4. **Scheduled Sync:** Regular cleanup/sync jobs
5. **Webhooks:** Real-time sync notifications

---

**Tóm tắt:** Hệ thống này đảm bảo mọi user trong `jhi_user` đều có corresponding record trong `users` table, giúp các features như document management hoạt động seamlessly với JHipster authentication.
